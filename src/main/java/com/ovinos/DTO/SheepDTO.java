package com.ovinos.DTO;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.ovinos.entity.Batch;
import com.ovinos.entity.Enum.SheepSex;
import com.ovinos.entity.auxiliarData.Characteristics;
import com.ovinos.entity.superClass.Sheep;

import java.util.Date;
@JsonPropertyOrder({
        "id",
        "characteristics",
        "peso",
        "dataNascimento"
})
public class SheepDTO {

    private String id;

    private Date dataNascimento;

    private SheepSex sex;

    private Batch batch;

    private Characteristics characteristics;

    public SheepDTO(){}

    public SheepDTO(Sheep sheep) {
        this.id = sheep.getId();
        this.dataNascimento = sheep.getDataNascimento();
        this.sex = sheep.getSex();
        if(sheep.getBatch()!=null){
            this.batch = sheep.getBatch();
        }
    }

    public Batch getBatch() {
        return batch;
    }

    public void setBatch(Batch batch) {
        this.batch = batch;
    }

    public SheepSex getSex() {return sex;}

    public void setSex(SheepSex sex) {
        this.sex = sex;
    }

    public Date getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(Date dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
