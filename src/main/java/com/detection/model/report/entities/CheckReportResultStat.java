/*
 * File Name：CheckReportResultStat.java
 *
 * Copyrighe：copyright@2017 www.ggkbigdata.com. All Rights Reserved
 *
 * Create Time: 2017年2月21日 下午5:42:28
 */
package com.detection.model.report.entities;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 *
 * @author lcc (lincc@ggkbigdata.com)
 * @version 1.0, 2017年2月21日 下午5:42:28
 * @function 单项评定结果
 */

@Entity
public class CheckReportResultStat {
    
    @Id
    @GeneratedValue
    private int id;
    //private String reportNum;
    private String itemCode;
    private String itemName;
    private Integer checkNum;
    private String checkLevel;
    private Integer unqualifiedNum;
    
    public CheckReportResultStat(){
    	
    }
    
    
    public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


/*	public String getReportNum() {
        return reportNum;
    }
    public void setReportNum(String reportNum) {
        this.reportNum = reportNum;
    }*/
    public String getItemCode() {
        return itemCode;
    }
    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }
    public String getItemName() {
        return itemName;
    }
    public void setItemName(String itemName) {
        this.itemName = itemName;
    }
    public Integer getCheckNum() {
        return checkNum;
    }
    public void setCheckNum(Integer checkNum) {
        this.checkNum = checkNum;
    }
    public Integer getUnqualifiedNum() {
        return unqualifiedNum;
    }
    public void setUnqualifiedNum(Integer unqualifiedNum) {
        this.unqualifiedNum = unqualifiedNum;
    }
    public String getCheckLevel() {
        return checkLevel;
    }
    public void setCheckLevel(String checkLevel) {
        this.checkLevel = checkLevel;
    }

    
}

