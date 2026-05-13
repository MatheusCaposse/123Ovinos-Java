package com.ovinos.service;

import com.ovinos.DTO.*;
import com.ovinos.entity.Batch;
import com.ovinos.entity.Enum.Alert;
import com.ovinos.entity.Enum.SheepSex;
import com.ovinos.entity.Female;
import com.ovinos.entity.Male;
import com.ovinos.entity.auxiliarData.*;
import com.ovinos.entity.superClass.Sheep;
import com.ovinos.repository.*;
import com.ovinos.service.exception.SheepException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Optional;

@Service
public class SheepService {

    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

    @Autowired
    private SheepRepository repository;

    @Autowired
    BatchRepository batchRepository;

    @Autowired
    private FemaleRepository femaleRepository;

    @Autowired
    private CharacteristicsRepository characteristicsRepository;

    @Autowired
    private TreatmentRepository treatmentRepository;

    @Autowired
    private WeightRepository weightRepository;

    @Autowired
    private AcitivityRepository acitivityRepository;

    public List<Sheep> findAll() {
        List<Sheep> list = repository.findAll();
        return list;
    }

    public Sheep findById(String id) {
        Sheep obj = repository.findById(id).orElseThrow(() -> new SheepException("Animal não encontrado"));
        return obj;
    }

    public void deleteById(String id) {
        Sheep sheep = repository.findById(id).orElseThrow(() -> new SheepException("Animal não encontrado"));
        repository.delete(sheep);
    }

    public Sheep createSheep(SheepDTO obj) {
        Optional<Sheep> test = repository.findById(obj.getId());
        if (test.isPresent()) {
            throw new SheepException("Já existe um animal com esse ID");
        }

        Sheep sheep;

        if (obj.getBatch() != null) {
            sheep = (obj.getSex() == SheepSex.FEMEA)
                    ? new Female(obj.getId(), obj.getDataNascimento(), obj.getBatch())
                    : new Male(obj.getId(), obj.getDataNascimento(), obj.getBatch());
        } else {
            sheep = (obj.getSex() == SheepSex.FEMEA)
                    ? new Female(obj.getId(), obj.getDataNascimento())
                    : new Male(obj.getId(), obj.getDataNascimento());
        }
        return repository.save(sheep);
    }

    @Transactional
    public Sheep updateSheep(UpdateSheepDTO sheep) {

        Sheep obj = repository.findById(sheep.getId()).orElseThrow(() -> new SheepException("Animal não encontrado"));

        //BATCH
        if ( sheep.getBatch()!=null && sheep.getBatch().getId()!=null){
            Batch batch = batchRepository.findById(sheep.getBatch().getId()).orElseThrow(()-> new SheepException("Lote nao encontrado"));
            obj.setBatch(batch);
        }

        //SEX
        if (sheep.getSex() != null) {
            obj.setSex(sheep.getSex());
        }

        //DATANASCIMENTO
        if(sheep.getDataNascimento()!=null){
            obj.setDataNascimento(sheep.getDataNascimento());
        }

        // ACTIVITY
        if (sheep.getActivity() != null && sheep.getActivity().getDateActivity() != null) {

            obj.setActivity(sheep.getActivity());
        }

        // CHARACTERISTICS
        if (sheep.getCharacteristics() != null && sheep.getCharacteristics().getConditionSheep() != null) {
            obj.setCharacteristics(sheep.getCharacteristics());
        }

        // KINSHIP
        if (sheep.getKinship() != null && sheep.getKinship().getIdMae() != null) {
            Sheep pai = repository.findById(sheep.getKinship().getIdPai()).orElseThrow(() -> new SheepException("O ID do pai não existe"));
            Sheep mae = repository.findById(sheep.getKinship().getIdMae()).orElseThrow(() -> new SheepException("O ID da mãe não existe"));
            if(pai.getSex()==SheepSex.MACHO && mae.getSex()==SheepSex.FEMEA){
                obj.setKinship(sheep.getKinship());
            }else {
                if(pai.getSex()!=SheepSex.MACHO){
                    throw new SheepException("O ID informado não é de um macho");
                } else if (mae.getSex()!=SheepSex.FEMEA){
                    throw new SheepException("O ID informado não é de uma Femea");
                }
            }
        }

        // NOTES
        if (sheep.getNotes() != null && sheep.getNotes().getAlert() != null) {
            obj.setNotes(sheep.getNotes());
        }

        // TREATMENT
        if (sheep.getTreatment() != null && sheep.getTreatment().getDataAplicacao() != null) {
            obj.setTreatment(sheep.getTreatment());
        }

        // WEIGHT
        if (sheep.getWeight() != null && sheep.getWeight().getCurrentWeight() != null) {
            obj.setWeight(sheep.getWeight());
        }

        // PREGNANCY
        if (obj instanceof Female && sheep.getPregnancy()!=null) {
            Female femaleObj = (Female) obj;

            femaleObj.setPregnancy(sheep.getPregnancy());
        }

        return repository.save(obj);
    }

    @Transactional
    public void addCharacteristics(String id, CharacteristicsDTO characteristicsDTO) {
        Sheep sheep = repository.findById(id).orElseThrow(() -> new SheepException("Animal não encontrado"));

        Characteristics characteristics = new Characteristics(characteristicsDTO.getStatus(), characteristicsDTO.getConditionSheep(), characteristicsDTO.getRaceSheep());

        sheep.setCharacteristics(characteristics);
        repository.save(sheep);

    }

    @Transactional
    public void addTreatment(String id, TreatmentDTO treatmentDTO) {

        Sheep sheep = repository.findById(id)
                .orElseThrow(() -> new SheepException("Animal não encontrado"));

        if (sheep.getTreatment() == null) {
            Treatment treatment = new Treatment(treatmentDTO.getDescricao(), treatmentDTO.getMedicamento(), treatmentDTO.getDosagem(), treatmentDTO.getDataAplicacao());
            sheep.setTreatment(treatment);

        } else {
            Treatment treatment = sheep.getTreatment();

            treatment.setDescricao(treatmentDTO.getDescricao());
            treatment.setMedicamento(treatmentDTO.getMedicamento());
            treatment.setDosagem(treatmentDTO.getDosagem());
            treatment.setDataAplicacao(treatmentDTO.getDataAplicacao());
        }

        repository.save(sheep);
    }

    @Transactional
    public void treatmentCompleted(String id) {
        Sheep sheep = repository.findById(id).orElseThrow(() -> new SheepException("Animal não encontrado"));
        Treatment treatment = treatmentRepository.findById(sheep.getTreatment().getId()).orElseThrow(() -> new SheepException("Treatment not found"));
        treatmentRepository.delete(treatment);

        sheep.setTreatmentCompleted(null);
        repository.save(sheep);
    }

    @Transactional
    public void addPregnancy(String id, PregnancyDTO dto) {
        Female female = femaleRepository.findById(id).orElseThrow(() -> new SheepException("Female not found"));

        Pregnancy pregnancy = new Pregnancy(dto.getTypeBirth(), dto.getIdPai());

        female.setPregnancy(pregnancy);

        femaleRepository.save(female);
    }

    @Transactional
    public void addWeight(String id, WeightDTO dto) {
        Sheep sheep = repository.findById(id).orElseThrow(() -> new SheepException("Sheep not found"));

        if (sheep.getWeight() == null) {
            Weight weight = new Weight(dto.getCurrentWeight(), dto.getLastWeight(), dto.getCurrentWeighing(), dto.getFirstWeighing());
            sheep.setWeight(weight);
        } else {
            Weight weight = sheep.getWeight();
            weight.setCurrentWeight(dto.getCurrentWeight());
            weight.setLastWeight(dto.getLastWeight());
            weight.setCurrentWeighing(dto.getCurrentWeighing());
            weight.setFirstWeighing(dto.getFirstWeighing());
        }
        repository.save(sheep);
    }

    @Transactional
    public void addNote(String id, NotesDTO dto) {
        Sheep sheep = repository.findById(id).orElseThrow(() -> new SheepException("Sheep not found"));

        if (sheep.getNotes() == null) {
            Notes notes = new Notes(dto.getNote(), dto.getAlert());
            sheep.setNotes(notes);
        } else {
            Notes notes = sheep.getNotes();
            notes.setNote(dto.getNote());
            notes.setAlert(dto.getAlert());
        }

        repository.save(sheep);
    }

    public List<Alert> getAlert(){
        List<Alert> list = Alert.getAlerts();
        return list;
    }

    @Transactional
    public void addActivity(String id, ActivityDTO dto) {
        Sheep sheep = repository.findById(id).orElseThrow(() -> new SheepException("Sheep not found"));

        if (sheep.getActivity() == null) {
            Activity activity = new Activity(dto.getDateActivity(), dto.getActivity());
            sheep.setActivity(activity);
        } else {
            Activity activity = sheep.getActivity();
            activity.setDateActivity(dto.getDateActivity());
            activity.setActivity(dto.getActivity());
        }

        repository.save(sheep);
    }

    public void activityCompleted(String id) {
        Sheep sheep = repository.findById(id).orElseThrow(() -> new SheepException("Sheep not found"));
        if (sheep.getActivity() == null) {
            throw new SheepException("This sheep dont have any activity");
        } else {
            Activity activity = acitivityRepository.findById(sheep.getActivity().getId()).orElseThrow(() -> new SheepException("Activity not found"));
            sheep.setActivity(null);
            repository.save(sheep);
            acitivityRepository.delete(activity);
        }
    }

    @Transactional
    public void addKinship(String id, KinshipDTO dto) {
        Sheep sheep = repository.findById(id).orElseThrow(() -> new SheepException("Sheep not found"));

        Sheep sheepMae = repository.findById(dto.getIdMae()).orElseThrow(() -> new SheepException("This mother id dosent exist"));
        if (sheepMae.getSex() != SheepSex.FEMEA) {
            throw new SheepException("This ID is not ID of a mother");
        } else if (dto.getIdMae().equals(id)) {
            throw new SheepException("The id of mather and cub cannot be equal");
        }

        Sheep sheepPai = repository.findById(dto.getIdPai()).orElseThrow(() -> new SheepException("This father id dosent exist"));
        if (sheepPai.getSex() != SheepSex.MACHO) {
            throw new SheepException("This ID is not ID of a father");
        } else if (dto.getIdPai().equals(id)) {
            throw new SheepException("The id of father and cub cannot be equal");
        }

        if (sheep.getKinship() == null) {
            Kinship kinship = new Kinship(dto.getIdPai(), dto.getIdMae());
            sheep.setKinship(kinship);
        } else {
            Kinship kinship = sheep.getKinship();
            kinship.setIdPai(dto.getIdPai());
            kinship.setIdMae(dto.getIdMae());
        }

        repository.save(sheep);
    }
}
