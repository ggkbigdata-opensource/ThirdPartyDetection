/*
 * File Name：CheckReportInfo.java
 *
 * Copyrighe：copyright@2017 www.ggkbigdata.com. All Rights Reserved
 *
 * Create Time: 2017年2月21日 下午4:16:53
 */
package com.detection.model.report.entities;

import java.util.Date;

import javax.persistence.Entity;

/**
 *
 * 视图对象 report和info
 */
public class CheckReportAndInfo {

    

    private Long streetId;
    private Long blockId;
    private Date createDate;
    private Date modifyDate;
    private String creatorName;
    private String modifierName;
    private String filePath;
    private String fileName;
    private String originalName;
    private String verifyToken;
    private String fetchCode;

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
    private String reportConclusion;
    private String riskLevel;
    private float riskScore;

    public CheckReportAndInfo() {
        super();
    }

    public CheckReportAndInfo(Long streetId, Long blockId, Date createDate, Date modifyDate, String creatorName,
            String modifierName, String filePath, String fileName, String originalName, String verifyToken,
            String fetchCode, String reportNum, String projectName, String projectAddress, String agentName,
            String qaName, String qaAddress, String contactTel, String contactFax, String contactPostCode,
            String message, String reportConclusion, String riskLevel, float riskScore) {
        super();
        this.streetId = streetId;
        this.blockId = blockId;
        this.createDate = createDate;
        this.modifyDate = modifyDate;
        this.creatorName = creatorName;
        this.modifierName = modifierName;
        this.filePath = filePath;
        this.fileName = fileName;
        this.originalName = originalName;
        this.verifyToken = verifyToken;
        this.fetchCode = fetchCode;
        this.reportNum = reportNum;
        this.projectName = projectName;
        this.projectAddress = projectAddress;
        this.agentName = agentName;
        this.qaName = qaName;
        this.qaAddress = qaAddress;
        this.contactTel = contactTel;
        this.contactFax = contactFax;
        this.contactPostCode = contactPostCode;
        this.message = message;
        this.reportConclusion = reportConclusion;
        this.riskLevel = riskLevel;
        this.riskScore = riskScore;
    }
    public Long getStreetId() {
        return streetId;
    }

    public void setStreetId(Long streetId) {
        this.streetId = streetId;
    }

    public Long getBlockId() {
        return blockId;
    }

    public void setBlockId(Long blockId) {
        this.blockId = blockId;
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

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getOriginalName() {
        return originalName;
    }

    public void setOriginalName(String originalName) {
        this.originalName = originalName;
    }

    public String getVerifyToken() {
        return verifyToken;
    }

    public void setVerifyToken(String verifyToken) {
        this.verifyToken = verifyToken;
    }

    public String getFetchCode() {
        return fetchCode;
    }

    public void setFetchCode(String fetchCode) {
        this.fetchCode = fetchCode;
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

    public String getRiskLevel() {
        return riskLevel;
    }

    public void setRiskLevel(String riskLevel) {
        this.riskLevel = riskLevel;
    }

    public float getRiskScore() {
        return riskScore;
    }

    public void setRiskScore(float riskScore) {
        this.riskScore = riskScore;
    }

}
