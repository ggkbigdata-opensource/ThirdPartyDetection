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
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

/**
 *
 * @author lcc (lincc@ggkbigdata.com)
 * @version 1.0, 2017年2月21日 下午4:16:53
 * @function 检测报告首页
 */

@Entity
public class CrCheckReport {

    @Id
    private String reportNum;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "reportNum")
    private CrCheckReportInfo checkReportInfo;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "reportNum")
    private List<CrCheckReportResultStat> checkReportResultStat = new ArrayList<CrCheckReportResultStat>();

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "reportNum")
    private List<CrCheckItemDetail> checkItemDetail = new ArrayList<CrCheckItemDetail>();

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "reportNum")
    private List<CrCheckReportUnqualifiedItemDetail> unqualifiedItemDetail = new ArrayList<CrCheckReportUnqualifiedItemDetail>();

    private Long streetId;
    private Date createDate;
    private Date modifyDate;
    private String creatorName;
    private String modifierName;
    private String filePath;
    private String fileName;
    private String originalName;
    private String verifyToken;
    private String fetchCode;
    
    private Double score;
    private Long blockId;
    
    private String heightType;//
    private String buildingTypeBig;
    private String buildingTypeSmall;
    private String competentDepartment;//主管部门
    private String riskLevel;//隐患等级

    public CrCheckReport() {
        super();
        // TODO Auto-generated constructor stub
    }

    public String getReportNum() {
        return reportNum;
    }
    
    public void setReportNum(String reportNum) {
        this.reportNum = reportNum;
    }

    public CrCheckReportInfo getCheckReportInfo() {
        return checkReportInfo;
    }

    public void setCheckReportInfo(CrCheckReportInfo checkReportInfo) {
        this.checkReportInfo = checkReportInfo;
    }

    public List<CrCheckReportResultStat> getCheckReportResultStat() {
        return checkReportResultStat;
    }

    public void setCheckReportResultStat(List<CrCheckReportResultStat> checkReportResultStat) {
        this.checkReportResultStat = checkReportResultStat;
    }

    public List<CrCheckItemDetail> getCheckItemDetail() {
        return checkItemDetail;
    }

    public void setCheckItemDetail(List<CrCheckItemDetail> checkItemDetail) {
        this.checkItemDetail = checkItemDetail;
    }

    public List<CrCheckReportUnqualifiedItemDetail> getUnqualifiedItemDetail() {
        return unqualifiedItemDetail;
    }

    public void setUnqualifiedItemDetail(List<CrCheckReportUnqualifiedItemDetail> unqualifiedItemDetail) {
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

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    public String getHeightType() {
        return heightType;
    }

    public void setHeightType(String heightType) {
        this.heightType = heightType;
    }

    public String getBuildingTypeBig() {
        return buildingTypeBig;
    }

    public void setBuildingTypeBig(String buildingTypeBig) {
        this.buildingTypeBig = buildingTypeBig;
    }

    public String getBuildingTypeSmall() {
        return buildingTypeSmall;
    }

    public void setBuildingTypeSmall(String buildingTypeSmall) {
        this.buildingTypeSmall = buildingTypeSmall;
    }

    public String getCompetentDepartment() {
        return competentDepartment;
    }

    public void setCompetentDepartment(String competentDepartment) {
        this.competentDepartment = competentDepartment;
    }

    public String getRiskLevel() {
        return riskLevel;
    }

    public void setRiskLevel(String riskLevel) {
        this.riskLevel = riskLevel;
    }
	
    
    
}
