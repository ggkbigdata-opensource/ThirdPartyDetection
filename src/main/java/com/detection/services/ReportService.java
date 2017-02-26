/*
 * File Name：ReportItemService.java
 *
 * Copyrighe：copyright@2017 www.ggkbigdata.com. All Rights Reserved
 *
 * Create Time: 2017年2月21日 上午9:27:21
 */
package com.detection.services;

import com.alibaba.fastjson.JSONObject;

/**
 *
 * @author lcc (lincc@ggkbigdata.com)
 * @version 1.0, 2017年2月21日 上午9:27:21
 */
public interface ReportService {
    /**
     * @author lcc
     * @version 1.0, 2017年2月21日 上午9:27:21
     * @function 根据检测Id,返回检测细项
     */
    public JSONObject getReportItemByReportNum(String reportNum);
    /**
     * @author lcc
     * @version 1.0, 2017年2月21日 上午9:27:21
     * @function 根据检测报告的Id,返回该检测报告的级别，检测报告的级别分别是：
     * 1. 极高水平 (85,100]
     * 2. 高水平 (65,85]
     * 3. 中等水平 (25,65]
     * 4. 低水平 [0,25]
     */
    public JSONObject getReportLevelByReportNum(String reportNum);
    
    /**
     * @author lcc
     * @version 1.0
     * @function 保存结果评分
     */
    public String saveReportResult(String reportNum);
    
    /**
     * @author lcc
     * @version 1.0
     * @function 批量计算和保存在库尚未评级的检测报告
     */
    public String bulkSaveReportResult();
}

