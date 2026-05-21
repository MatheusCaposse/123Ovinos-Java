package com.ovinos.controller;

import com.ovinos.DTO.ActivityDTO;
import com.ovinos.DTO.ActivityInfoDTO;
import com.ovinos.entity.auxiliarData.Activity;
import com.ovinos.service.ActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping(value = "/activity")
public class ActivityController {

    @Autowired
    private ActivityService activityService;

    @GetMapping
    public ResponseEntity<List<Activity>> getAllActivity(){
        return ResponseEntity.ok().body(activityService.findAll());
    }

    @GetMapping(value = "/today")
    public ResponseEntity<Integer> activiyToday(){
        return ResponseEntity.ok().body(activityService.getActivityToday());
    }

    @GetMapping(value = "/next")
    public ResponseEntity<LocalDate> nextActivity(){
        Activity obj = activityService.nextActivity();
        return ResponseEntity.ok().body(obj.getDateActivity());
    }

    @PostMapping
    public ResponseEntity<Activity> createActivity(@RequestBody ActivityDTO obj){
        return ResponseEntity.ok().body(activityService.createActivity(obj));
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteActivity(@PathVariable Long id){
        activityService.deleteActivity(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping(value = "/info")
    public ResponseEntity<ActivityInfoDTO> getInfo(){
        ActivityInfoDTO obj = activityService.getInfo();
        return ResponseEntity.ok().body(obj);
    }
}
