package com.detection.model.weight;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

import org.apache.hadoop.mapred.legacyjobhistory_jsp;

/**
 * @fileName WeightConfig.java
 * @author csk
 * @createTime 2017年3月15日 下午4:34:34
 * @version 1.0
 * @function
 */
@Entity
public class WeightConfig {
    @Id
    @GeneratedValue
    private int id;
    private String configName;
    private float levelA;
    private float levelB;
    private float levelC;
    private float gradeOneBoundary;
    private float gradeTwoBoundary;
    private float gradeThreeBoundary;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "ConfigId")
    private List<LayerOne> layerOne = new ArrayList<LayerOne>();
    
    public WeightConfig(){
        
    }
    public WeightConfig(String configName, boolean useDefault){
        if(useDefault){
            setDefaultValue();
        }
        this.configName = configName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getConfigName() {
        return configName;
    }

    public void setConfigName(String configName) {
        this.configName = configName;
    }

    public List<LayerOne> getLevelOne() {
        return layerOne;
    }

    public void setLevelOne(List<LayerOne> levelOne) {
        this.layerOne = levelOne;
    }

    public float getLevelA() {
        return levelA;
    }

    public void setLevelA(float levelA) {
        this.levelA = levelA;
    }

    public float getLevelB() {
        return levelB;
    }

    public void setLevelB(float levelB) {
        this.levelB = levelB;
    }

    public float getLevelC() {
        return levelC;
    }

    public void setLevelC(float levelC) {
        this.levelC = levelC;
    }

    public float getGradeOneBoundary() {
        return gradeOneBoundary;
    }

    public void setGradeOneBoundary(float gradeOneBoundary) {
        this.gradeOneBoundary = gradeOneBoundary;
    }

    public float getGradeTwoBoundary() {
        return gradeTwoBoundary;
    }

    public void setGradeTwoBoundary(float gradeTwoBoundary) {
        this.gradeTwoBoundary = gradeTwoBoundary;
    }

    public float getGradeThreeBoundary() {
        return gradeThreeBoundary;
    }

    public void setGradeThreeBoundary(float gradeThreeBoundary) {
        this.gradeThreeBoundary = gradeThreeBoundary;
    }
    
    private void setDefaultValue(){
        levelA=9.0f;
        levelB=5.0f;
        levelC=1.0f;
        gradeOneBoundary=25.0f;
        gradeTwoBoundary=65.0f;
        gradeThreeBoundary=85.0f;
        String[] levelList = {"6","7","8","16","17","18","20"};
        for(String level:levelList){
            if(level.equals("6")){
                layerOne.add(new LayerOne(level, 1.0f, 4));
            }
            else if(level.equals("7")){
                layerOne.add(new LayerOne(level, 1.0f, 5));
            }
            else if(level.equals("8")){
                layerOne.add(new LayerOne(level, 1.0f, 6));
            }
            else if(level.equals("16")){
                layerOne.add(new LayerOne(level, 1.0f, 22));
            }
            else if(level.equals("17")){
                layerOne.add(new LayerOne(level, 1.0f, 8));
            }
            else if(level.equals("18")){
                layerOne.add(new LayerOne(level, 1.0f, 5));
            }
            else if(level.equals("20")){
                layerOne.add(new LayerOne(level, 1.0f, 2));
            }
        }
    }
    
}
