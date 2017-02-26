package com.detection.model.report.entities;

import java.util.List;

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
	
	
}
