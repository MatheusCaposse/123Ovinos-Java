package com.ovinos.resource;

import com.ovinos.DTO.*;
import com.ovinos.entity.Enum.ConditionSheep;
import com.ovinos.entity.Enum.SheepSex;
import com.ovinos.entity.superClass.Sheep;
import com.ovinos.service.SheepService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping(value = "/sheep")
public class SheepResource {

    @Autowired
    private SheepService sheepService;

    @GetMapping
    public ResponseEntity<List<Sheep>> findAll(){
        List<Sheep> list = sheepService.findAll();
        return ResponseEntity.ok().body(list);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Sheep> findById(@PathVariable String id){
        Sheep obj = sheepService.findById(id);
        return ResponseEntity.ok().body(obj);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable String id){
        sheepService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping(value = "/create-sheep")
    public ResponseEntity<Sheep> createSheep(@RequestBody SheepDTO sheep){
        return ResponseEntity.ok().body(sheepService.createSheep(sheep));
    }

    @GetMapping("/status/{sex}")
    public List<ConditionSheep> getStatusBySex(@PathVariable SheepSex sex) {
        return Arrays.stream(ConditionSheep.values())
                .filter(status -> status.canBe(sex))
                .toList();
    }

    @PostMapping(value = "/{id}/characteristics")
    public ResponseEntity<Void> addCharacteristics(@PathVariable String id, @RequestBody CharacteristicsDTO characteristicsDTO){
        sheepService.addCharacteristics(id, characteristicsDTO);

        return ResponseEntity.noContent().build();
    }

    @PostMapping(value = "/{id}/treatments")
    public ResponseEntity<Void> addTreatment(@PathVariable String id, @RequestBody TreatmentDTO treatment){

        sheepService.addTreatment(id, treatment);

        return ResponseEntity.noContent().build();
    }

    @PostMapping(value = "/{id}/treatmentCompleted")
    public ResponseEntity<Void> treatmentCompleted (@PathVariable String id){
        sheepService.treatmentCompleted(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping(value = "/{id}/pregnancy")
    public ResponseEntity<Void> addPregnancy(@PathVariable String id, @RequestBody PregnancyDTO pregnancyDTO){
        sheepService.addPregnancy(id , pregnancyDTO);
        return ResponseEntity.noContent().build();
    }

    @PostMapping(value = "/{id}/weight")
    public ResponseEntity<Void> addWeight(@PathVariable String id, @RequestBody WeightDTO dto){
        sheepService.addWeight(id, dto);
        return ResponseEntity.noContent().build();
    }

    @PostMapping(value = "/{id}/notes")
    public ResponseEntity<Void> addNote(@PathVariable String id, @RequestBody NotesDTO dto){
        sheepService.addNote(id, dto);
        return ResponseEntity.noContent().build();
    }

    @PostMapping(value = "/{id}/activity")
    public ResponseEntity<Void> addActivity(@PathVariable String id, @RequestBody ActivityDTO dto){
        sheepService.addActivity(id, dto);
        return ResponseEntity.noContent().build();
    }

    @PostMapping(value = "/{id}/activityCompleted")
    public ResponseEntity<Void> activityCompleted(@PathVariable String id){
        sheepService.activityCompleted(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping(value = "/{id}/kinship")
    public ResponseEntity<Void> addKinship(@PathVariable String id, @RequestBody KinshipDTO dto){
        sheepService.addKinship(id, dto);
        return ResponseEntity.noContent().build();
    }
}
