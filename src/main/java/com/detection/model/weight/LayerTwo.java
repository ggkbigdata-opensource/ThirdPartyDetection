package com.detection.model.weight;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * @fileName LevelTwo.java
 * @author csk
 * @createTime 2017年3月15日 下午4:54:32
 * @version 1.0
 * @function
 */
@Entity
public class LayerTwo {
    @Id
    @GeneratedValue
    private int id;
    private String code;
    private float weight;
    
    public LayerTwo(){
        
    }
    public LayerTwo(String code, float weight){
        this.code = code;
        this.weight = weight;
    }
    
    public LayerTwo(String code){
        this.code = code;
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

    
}
