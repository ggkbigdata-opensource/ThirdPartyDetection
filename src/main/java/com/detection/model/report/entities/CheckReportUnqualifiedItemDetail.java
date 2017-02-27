package com.detection.model.report.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class CheckReportUnqualifiedItemDetail {

    @Id
    @GeneratedValue
    private int id;
    private String itemCode;
    private String unqualifiedCheckPoint ;
    
    
    public CheckReportUnqualifiedItemDetail(){
        
    }


    public int getId() {
        return id;
    }


    public void setId(int id) {
        this.id = id;
    }


    public String getItemCode() {
        return itemCode;
    }


    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }


    public String getUnqualifiedCheckPoint() {
        return unqualifiedCheckPoint;
    }


    public void setUnqualifiedCheckPoint(String unqualifiedCheckPoint) {
        this.unqualifiedCheckPoint = unqualifiedCheckPoint;
    }
}
