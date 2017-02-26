/*
 * File Name：CheckReportService.java
 *
 * Copyrighe：copyright@2017 www.ggkbigdata.com. All Rights Reserved
 *
 * Create Time: 2017年2月21日 下午4:37:13
 */
package com.detection.services;

import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.detection.model.report.entities.CheckReport;

/**
 *
 * @author lcc (lincc@ggkbigdata.com)
 * @version 1.0, 2017年2月21日 下午4:37:13
 */
public interface CheckReportService {
    
    /**
     * @author lcc
     * @version 1.0
     * @function 保存检测报告的meta信息(检测报告的封面基础信息)
     */
    public String saveCheckReportInfo(JSONObject info);
    /**
     * @author lcc
     * @version 1.0
     * @function 批量保存单项评定结果
     */
    public void saveCheckReportResultStat(JSONArray resultStatObj, String reportNum);
    
    /**
     * @author lcc
     * @version 1.0
     * @function 批量保存检测情况统计表
     */
    public void saveCheckItemDetail(JSONArray resultStatObj, String reportNum);
    
    /**
     * @author csk
     * @version 1.0
     * @throws IOException 
     * @function 上传报告文件，解析并保存到数据库
     */
    public boolean uploadAndSaveReport(String path, MultipartFile file) throws IOException;
    
    /**
     * @author csk
     * @version 1.0
     * @throws IOException 
     * @function 解析文件并保存到数据库
     */
    public boolean parseAndSaveReportToDB(String upFilePath, String downloadPath) throws IOException;
    
    /**
     * @author csk
     * @version 1.0
     * @throws IOException 
     * @function 按报告编号删除一份报告
     */
    public void deleteReportByReportNum(String reportNum);
    
    /**
     * @author csk
     * @version 1.0
     * @throws IOException 
     * @function 按报告编号查找一份报告
     */
    public CheckReport getReportByReportNum(String reportNum);
    
    /**
     * @author csk
     * @version 1.0
     * @throws IOException 
     * @function 查找所有报告
     */
    public List<CheckReport> getAllReports();
}

