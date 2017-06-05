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
import java.util.Map;

import org.apache.poi.ss.usermodel.Sheet;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONObject;

/**
 *
 * @author lcc (lincc@ggkbigdata.com)
 * @version 1.0, 2017年2月21日 下午4:37:13
 */
public interface CheckReportService {

    /**
     * @author csk
     * @version 1.0
     * @param request
     * @throws IOException
     * @throws Exception
     * @function 上传报告文件，解析并保存到数据库
     */
    public boolean uploadAndSaveReport(String path, MultipartFile file, String operatorName, String ctxPath)
            throws IOException, Exception;

    /**
     * @author csk
     * @version 1.0
     * @throws IOException
     * @throws Exception
     * @function 解析文件并保存到数据库
     */
    public boolean parseAndSaveReportToDB(String upFilePath, String fileName, String encryptedFileName,
            String operatorName) throws IOException, Exception;

    /**
     * @author csk
     * @version 1.0
     * @throws IOException
     * @function 按报告编号删除一份报告
     */
    public JSONObject deleteReportByReportNum(String reportNum);

    /**
     * @author csk
     * @version 1.0
     * @param map 
     * @throws IOException
     * @function 查找所有报告
     */
    public JSONObject getAllReports(Map<String, Object> map);

    /**
     * @author csk
     * @version 1.0
     * @function 获取评估项目概况表的摘要信息，包括： reportNum ： 项目号 reportDate : 评估日期
     *           projectName : 被评估单位名称 riskLevel : 极高水平（4）、高水平（3）、中等水平（2）、低水平（1）
     *           code : 200 success, 201 failure
     */
    public JSONObject getAbstractReportInfo(String reportNum);

    /**
     * @author csk
     * @version 1.0
     * @function 获取报告详情列表
     */
    public JSONObject getDetailReportInfo(String verifyToken);

    /**
     * @author csk
     * @version 1.0
     * @throws Exception
     * @function 处理风险评估报告责任提取码验证 校验reportNum、责任人dutyPerson和责任人电话dutyTel
     *           返回校验token
     */
    public JSONObject submitExtractCode(String reportNum, String dutyPerson, String dutyTel) throws Exception;

    public boolean updateRiskLevel(String reportNum);

    public void updateAllRiskLevel();

    public String getReportURL(String reportNum);

    public String getOriginalName(String reportNum);

    public void uploadRiskLevel(MultipartFile file) throws Exception;

    void deleteReportRecordByReportNum(String reportNum);

    /**
     * @createDate 2017年4月27日下午4:42:37
     * @author wangzhiwang
     * @return
     * @description
     */
    public List<String> findAllReportNum();

    /**
     * @createDate 2017年5月2日下午7:19:14
     * @author wangzhiwang
     * @param id
     * @param streetName
     * @return
     * @description
     */
    public void updateStreet(String reportNum, String streetName);

    /**
     * @createDate 2017年5月5日上午11:57:56
     * @author wangzhiwang
     * @param sheetOne
     * @description
     */
    public void importExcel(Sheet sheetOne);

    /**
     * @createDate 2017年6月4日下午2:15:43 
     * @author wangzhiwang
     * @param map
     * @return 
     * @description
     */
    public JSONObject trendChart(Map<String, Object> map);

}
