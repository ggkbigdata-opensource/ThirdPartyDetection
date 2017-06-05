package com.detection.model.area;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonFormat;
/**
 * No modifying
 * Company: Scho Co. Ltd
 * @author Administrator
 * @date 2016-9-28 15:10:32
 */
 
@Entity
public class Block  {
	// Fields
        @Id
		private Long id;
		private String name;
		private String blockType;
		private String code;
		private Long districtId;
		private Long streetId;
		private Double longitude;
		private Double latitude;
		private String images;
		private String coverArea;
		private String population;
		private Integer state;
		private String remark;
		private Long modUser;
		@JsonFormat(
	        pattern = "yyyy-MM-dd HH:mm",
	        timezone = "GMT+08:00"
	    )
		private Date modTime;
	public Block() {
		this.clear();
	}
	public Block(Long id) {
		this();
		this.id=id;
	}
	
	public void clear() {
		id=null;
		name=null;
		blockType=null;
		code=null;
		districtId=null;
		streetId=null;
		longitude=null;
		latitude=null;
		images=null;
		coverArea=null;
		population=null;
		state=null;
		remark=null;
		modUser=null;
		modTime=null;
	}

	// Getters/Setters
		public Long getId(){
			return id;
		}
		public void setId(Long id){
			this.id=id;
		}
		public String getName(){
			return name;
		}
		public void setName(String name){
			this.name=name;
		}
		public String getBlockType(){
			return blockType;
		}
		public void setBlockType(String blockType){
			this.blockType=blockType;
		}
		public String getCode(){
			return code;
		}
		public void setCode(String code){
			this.code=code;
		}
		public Long getDistrictId(){
			return districtId;
		}
		public void setDistrictId(Long districtId){
			this.districtId=districtId;
		}
		public Long getStreetId(){
			return streetId;
		}
		public void setStreetId(Long streetId){
			this.streetId=streetId;
		}
		public Double getLongitude(){
			return longitude;
		}
		public void setLongitude(Double longitude){
			this.longitude=longitude;
		}
		public Double getLatitude(){
			return latitude;
		}
		public void setLatitude(Double latitude){
			this.latitude=latitude;
		}
		public String getImages(){
			return images;
		}
		public void setImages(String images){
			this.images=images;
		}
		public String getCoverArea(){
			return coverArea;
		}
		public void setCoverArea(String coverArea){
			this.coverArea=coverArea;
		}
		public String getPopulation(){
			return population;
		}
		public void setPopulation(String population){
			this.population=population;
		}
		public Integer getState(){
			return state;
		}
		public void setState(Integer state){
			this.state=state;
		}
		public String getRemark(){
			return remark;
		}
		public void setRemark(String remark){
			this.remark=remark;
		}
		public Long getModUser(){
			return modUser;
		}
		public void setModUser(Long modUser){
			this.modUser=modUser;
		}
		public Date getModTime(){
			return modTime;
		}
		public void setModTime(Date modTime){
			this.modTime=modTime;
		}
	
	//toString
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Block=[");
		builder.append("id="+id+",");
		builder.append("name="+name+",");
		builder.append("blockType="+blockType+",");
		builder.append("code="+code+",");
		builder.append("districtId="+districtId+",");
		builder.append("streetId="+streetId+",");
		builder.append("longitude="+longitude+",");
		builder.append("latitude="+latitude+",");
		builder.append("images="+images+",");
		builder.append("coverArea="+coverArea+",");
		builder.append("population="+population+",");
		builder.append("state="+state+",");
		builder.append("remark="+remark+",");
		builder.append("modUser="+modUser+",");
		builder.append("modTime="+modTime+",");
		builder.append("]");
		return builder.toString();
	}
	
 
}
