package com.detection.model.weight;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

/**
 * @fileName LevelOne.java
 * @author csk
 * @createTime 2017年3月15日 下午4:33:02
 * @version 1.0
 * @function
 */
@Entity
public class LayerOne {
    @Id
    @GeneratedValue
    private int id;
    private String code;
    private float weight;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "LayerOneId")
    private List<LayerTwo> layerTwo = new ArrayList<LayerTwo>();

    public LayerOne() {

    }

    public LayerOne(String code) {
        this.code = code;
    }

    public LayerOne(String code, float weight, int maxNum) {
        this.code = code;
        this.weight = weight;
        for (int i = 1; i <= maxNum; i++) {
            String layerTwoCode = code + "." + i;
            layerTwo.add(new LayerTwo(layerTwoCode, weight));
        }

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public float getWeight() {
        return weight;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }

    public List<LayerTwo> getLevelTwo() {
        return layerTwo;
    }

    public void setLevelTwo(List<LayerTwo> levelTwo) {
        this.layerTwo = levelTwo;
    }
}
