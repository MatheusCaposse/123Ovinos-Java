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
    private Double peso;

    private SheepSex sex;

    private Batch batch;

    private Characteristics characteristics;

    public SheepDTO(){}

    public SheepDTO(Sheep sheep) {
        this.id = sheep.getId();
        this.dataNascimento = sheep.getDataNascimento();
        this.peso = sheep.getWeight().getCurrentWeight();
        this.characteristics = sheep.getCharacteristics();
    }

    public Characteristics getCharacteristics() {
        return characteristics;
    }

    public void setCharacteristics(Characteristics characteristics) {
        this.characteristics = characteristics;
    }

    public Batch getBatch() {
        return batch;
    }

    public void setBatch(Batch batch) {
        this.batch = batch;
    }

    public SheepSex getSex() {
        return sex;
    }

    public void setSex(SheepSex sex) {
        this.sex = sex;
    }

    public Double getPeso() {
        return peso;
    }

    public void setPeso(Double peso) {
        this.peso = peso;
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
