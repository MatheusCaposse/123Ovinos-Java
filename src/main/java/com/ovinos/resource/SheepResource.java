package com.ovinos.resource;

import com.ovinos.DTO.*;
import com.ovinos.entity.Enum.*;
import com.ovinos.entity.superClass.Sheep;
import com.ovinos.service.CharacteristicsService;
import com.ovinos.service.PregnancyService;
import com.ovinos.service.SheepService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/sheep")
public class SheepResource {

    @Autowired
    private SheepService sheepService;

    @Autowired
    private CharacteristicsService characteristicsService;

    @Autowired
    private PregnancyService pregnancyService;

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

    @PutMapping(value = "/update-sheep")
    public ResponseEntity<Void> updateSheep(@RequestBody UpdateSheepDTO sheep){
        sheepService.updateSheep(sheep);
        return ResponseEntity.noContent().build();
    }

    @GetMapping(value = "/race")
    public ResponseEntity<List<RaceSheep>> getAllRace(){
        List<RaceSheep> list = characteristicsService.getAllRace();
        return ResponseEntity.ok().body(list);
    }

    @GetMapping(value = "/condition/{sex}")
    public ResponseEntity<List<ConditionSheep>> getCondition(@PathVariable SheepSex sex){
        List<ConditionSheep> obj = characteristicsService.getCondition(sex);
        return ResponseEntity.ok().body(obj);
    }
    @GetMapping(value = "/status")
    public ResponseEntity<List<SheepStatus>> getStatus(){
        List<SheepStatus> obj = characteristicsService.getStatus();
        return ResponseEntity.ok().body(obj);
    }

    @GetMapping(value = "/notes")
    public ResponseEntity<List<Alert>> getAlerts(){
        List<Alert> list = sheepService.getAlert();
        return ResponseEntity.ok().body(list);
    }

    @PostMapping(value = "/{id}/treatmentCompleted")
    public ResponseEntity<Void> treatmentCompleted (@PathVariable String id){
        sheepService.treatmentCompleted(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping(value = "/pregnancy")
    public ResponseEntity<List<TypeBirth>> getTypeBirth(){
        List<TypeBirth> obj = pregnancyService.getType();
        return ResponseEntity.ok().body(obj);
    }


    @PostMapping(value = "/{id}/activityCompleted")
    public ResponseEntity<Void> activityCompleted(@PathVariable String id){
        sheepService.activityCompleted(id);
        return ResponseEntity.noContent().build();
    }
}
