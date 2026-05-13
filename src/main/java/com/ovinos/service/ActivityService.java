package com.ovinos.service;

import com.ovinos.DTO.ActivityDTO;
import com.ovinos.entity.auxiliarData.Activity;
import com.ovinos.repository.AcitivityRepository;
import com.ovinos.service.exception.ActivityException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ActivityService {
    @Autowired
    private AcitivityRepository repository;

    public List<Activity> findAll(){
        List<Activity> list = repository.findAll();
        return list;
    }

    public Integer getActivityToday(){
        List<Activity> list = repository.findAll()
                .stream()
                .filter(x -> x.getDateActivity().equals(LocalDate.now()))
                .toList();

        return list.size();
    }

    public Activity nextActivity(){
        List<Activity> list = repository.findAll();

        return list.stream()
                .filter(x -> x.getDateActivity().isAfter(LocalDate.now()))
                .min(Comparator.comparing(Activity::getDateActivity))
                .orElse(null);
    }

    public Activity createActivity(ActivityDTO obj){
        Activity activity = new Activity(obj.getDateActivity(), obj.getActivity());
        return repository.save(activity);
    }

    public void deleteActivity(Long id){
        Activity activity = repository.findById(id).orElseThrow(()-> new ActivityException("Atividade não encontrada"));
        repository.delete(activity);
    }
}
