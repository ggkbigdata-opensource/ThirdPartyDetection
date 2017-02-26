/*
 * File Name：CheckReportInfo.java
 *
 * Copyrighe：copyright@2017 www.ggkbigdata.com. All Rights Reserved
 *
 * Create Time: 2017年2月21日 下午4:16:53
 */
package com.detection.model.report.entities;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;


/**
 *
 * @author lcc (lincc@ggkbigdata.com)
 * @version 1.0, 2017年2月21日 下午4:16:53
 * @function 检测报告首页
 */

@Entity
public class CheckReport {
    
    @Id
    private String reportNum;

    private String projectName;
    private String projectAddress;
    private String agentName;
    private String qaName;
    private String qaAddress;
    private String contactTel;
    private String contactFax;
    private String contactPostCode;
    private String message;
    @Column(length = 2048) 
    private String reportConclusion;
	
	@OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
	@JoinColumn(name = "reportNum")
	private List<CheckReportResultStat> checkReportResultStat = new ArrayList<CheckReportResultStat>();
	
	@OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
	@JoinColumn(name = "reportNum")
	private List<CheckItemDetail> checkItemDetail  = new ArrayList<CheckItemDetail>();
	
	@OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
	@JoinColumn(name = "reportNum")
	private List<CheckReportUnqualifiedItemDetail> unqualifiedItemDetail  = new ArrayList<CheckReportUnqualifiedItemDetail>();
	
	private String filePath;
    private Date createDate;
    private Date modifyDate;
    private String creatorName;
    private String modifierName;
    
    public CheckReport() {
        super();
        // TODO Auto-generated constructor stub
    }

	public String getReportNum() {
		return reportNum;
	}

	public void setReportNum(String reportNum) {
		this.reportNum = reportNum;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getProjectAddress() {
		return projectAddress;
	}

	public void setProjectAddress(String projectAddress) {
		this.projectAddress = projectAddress;
	}

	public String getAgentName() {
		return agentName;
	}

	public void setAgentName(String agentName) {
		this.agentName = agentName;
	}

	public String getQaName() {
		return qaName;
	}

	public void setQaName(String qaName) {
		this.qaName = qaName;
	}

	public String getQaAddress() {
		return qaAddress;
	}

	public void setQaAddress(String qaAddress) {
		this.qaAddress = qaAddress;
	}

	public String getContactTel() {
		return contactTel;
	}

	public void setContactTel(String contactTel) {
		this.contactTel = contactTel;
	}

	public String getContactFax() {
		return contactFax;
	}

	public void setContactFax(String contactFax) {
		this.contactFax = contactFax;
	}

	public String getContactPostCode() {
		return contactPostCode;
	}

	public void setContactPostCode(String contactPostCode) {
		this.contactPostCode = contactPostCode;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getReportConclusion() {
		return reportConclusion;
	}

	public void setReportConclusion(String reportConclusion) {
		this.reportConclusion = reportConclusion;
	}

	public List<CheckReportResultStat> getCheckReportResultStat() {
		return checkReportResultStat;
	}

	public void setCheckReportResultStat(List<CheckReportResultStat> checkReportResultStat) {
		this.checkReportResultStat = checkReportResultStat;
	}

	public List<CheckItemDetail> getCheckItemDetail() {
		return checkItemDetail;
	}

	public void setCheckItemDetail(List<CheckItemDetail> checkItemDetail) {
		this.checkItemDetail = checkItemDetail;
	}

	public List<CheckReportUnqualifiedItemDetail> getUnqualifiedItemDetail() {
		return unqualifiedItemDetail;
	}

	public void setUnqualifiedItemDetail(List<CheckReportUnqualifiedItemDetail> unqualifiedItemDetail) {
		this.unqualifiedItemDetail = unqualifiedItemDetail;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getModifyDate() {
		return modifyDate;
	}

	public void setModifyDate(Date modifyDate) {
		this.modifyDate = modifyDate;
	}

	public String getCreatorName() {
		return creatorName;
	}

	public void setCreatorName(String creatorName) {
		this.creatorName = creatorName;
	}

	public String getModifierName() {
		return modifierName;
	}

	public void setModifierName(String modifierName) {
		this.modifierName = modifierName;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

    
}

