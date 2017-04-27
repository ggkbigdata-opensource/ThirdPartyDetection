package com.detection.model.building;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class BsBuildingInfo {

    @Id
    @GeneratedValue
    private Long id;
    private String itemNumber;
    private Long streetId;
    private String propertyCompanyName;
    private String buildingAddress;
    private String designCompany;
    private String buildCompany;
    private String securityPersonLiable;
    private String linkmanName;
    private String linkmanTel;
    private String superiorCompanyName;
    private String supervisionDepartment;
    private String constructionCategory;
    private String streetAndCommittee;
    private String constructionHeight;
    private String occupyArea;
    private String constructionAreaAll;
    private String constructionAreaOverground;
    private String constructionAreaUnderground;
    private String constructionLongitude;
    private String constructionLatitude;
    private String pliesNumberOverground;
    private String pliesNumberUnderground;
    private String constructionClass;
    private String useTime;
    private String maintenanceCompany;
    private String thirdPartyFireDetection;
    
    private String remark;
    private String creator;
    private String createTime;
    
    private Date importTime;

    public BsBuildingInfo(){
        
    }

    
    public Long getStreetId() {
        return streetId;
    }

    public void setStreetId(Long streetId) {
        this.streetId = streetId;
    }



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getItemNumber() {
        return itemNumber;
    }

    public void setItemNumber(String itemNumber) {
        this.itemNumber = itemNumber;
    }

    public String getPropertyCompanyName() {
        return propertyCompanyName;
    }

    public void setPropertyCompanyName(String propertyCompanyName) {
        this.propertyCompanyName = propertyCompanyName;
    }

    public String getBuildingAddress() {
        return buildingAddress;
    }

    public void setBuildingAddress(String buildingAddress) {
        this.buildingAddress = buildingAddress;
    }

    public String getDesignCompany() {
        return designCompany;
    }

    public void setDesignCompany(String designCompany) {
        this.designCompany = designCompany;
    }

    public String getBuildCompany() {
        return buildCompany;
    }

    public void setBuildCompany(String buildCompany) {
        this.buildCompany = buildCompany;
    }

    public String getSecurityPersonLiable() {
        return securityPersonLiable;
    }

    public void setSecurityPersonLiable(String securityPersonLiable) {
        this.securityPersonLiable = securityPersonLiable;
    }

    public String getLinkmanName() {
        return linkmanName;
    }

    public void setLinkmanName(String linkmanName) {
        this.linkmanName = linkmanName;
    }

    public String getLinkmanTel() {
        return linkmanTel;
    }

    public void setLinkmanTel(String linkmanTel) {
        this.linkmanTel = linkmanTel;
    }

    public String getSuperiorCompanyName() {
        return superiorCompanyName;
    }

    public void setSuperiorCompanyName(String superiorCompanyName) {
        this.superiorCompanyName = superiorCompanyName;
    }

    public String getSupervisionDepartment() {
        return supervisionDepartment;
    }

    public void setSupervisionDepartment(String supervisionDepartment) {
        this.supervisionDepartment = supervisionDepartment;
    }

    public String getConstructionCategory() {
        return constructionCategory;
    }

    public void setConstructionCategory(String constructionCategory) {
        this.constructionCategory = constructionCategory;
    }

    public String getStreetAndCommittee() {
        return streetAndCommittee;
    }

    public void setStreetAndCommittee(String streetAndCommittee) {
        this.streetAndCommittee = streetAndCommittee;
    }

    public String getConstructionHeight() {
        return constructionHeight;
    }

    public void setConstructionHeight(String constructionHeight) {
        this.constructionHeight = constructionHeight;
    }

    public String getOccupyArea() {
        return occupyArea;
    }

    public void setOccupyArea(String occupyArea) {
        this.occupyArea = occupyArea;
    }

    public String getConstructionAreaAll() {
        return constructionAreaAll;
    }

    public void setConstructionAreaAll(String constructionAreaAll) {
        this.constructionAreaAll = constructionAreaAll;
    }

    public String getConstructionAreaOverground() {
        return constructionAreaOverground;
    }

    public void setConstructionAreaOverground(String constructionAreaOverground) {
        this.constructionAreaOverground = constructionAreaOverground;
    }

    public String getConstructionAreaUnderground() {
        return constructionAreaUnderground;
    }

    public void setConstructionAreaUnderground(String constructionAreaUnderground) {
        this.constructionAreaUnderground = constructionAreaUnderground;
    }

    public String getConstructionLongitude() {
        return constructionLongitude;
    }

    public void setConstructionLongitude(String constructionLongitude) {
        this.constructionLongitude = constructionLongitude;
    }

    public String getConstructionLatitude() {
        return constructionLatitude;
    }

    public void setConstructionLatitude(String constructionLatitude) {
        this.constructionLatitude = constructionLatitude;
    }

    public String getPliesNumberOverground() {
        return pliesNumberOverground;
    }

    public void setPliesNumberOverground(String pliesNumberOverground) {
        this.pliesNumberOverground = pliesNumberOverground;
    }

    public String getPliesNumberUnderground() {
        return pliesNumberUnderground;
    }

    public void setPliesNumberUnderground(String pliesNumberUnderground) {
        this.pliesNumberUnderground = pliesNumberUnderground;
    }

    public String getConstructionClass() {
        return constructionClass;
    }

    public void setConstructionClass(String constructionClass) {
        this.constructionClass = constructionClass;
    }

    public String getUseTime() {
        return useTime;
    }

    public void setUseTime(String useTime) {
        this.useTime = useTime;
    }

    public String getMaintenanceCompany() {
        return maintenanceCompany;
    }

    public void setMaintenanceCompany(String maintenanceCompany) {
        this.maintenanceCompany = maintenanceCompany;
    }

    public String getThirdPartyFireDetection() {
        return thirdPartyFireDetection;
    }

    public void setThirdPartyFireDetection(String thirdPartyFireDetection) {
        this.thirdPartyFireDetection = thirdPartyFireDetection;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
    public Date getImportTime() {
        return importTime;
    }
    public void setImportTime(Date importTime) {
        this.importTime = importTime;
    }
    
}
