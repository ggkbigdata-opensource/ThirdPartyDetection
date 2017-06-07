/*
 * File Name：CheckReportService.java
 *
 * Copyrighe：copyright@2017 www.ggkbigdata.com. All Rights Reserved
 *
 * Create Time: 2017年2月21日 下午4:37:13
 */
package com.detection.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Sheet;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONObject;
import com.detection.model.report.entities.CrCheckReport;
import com.detection.model.report.entities.CrCheckReportInfo;

/**
 *
 * @author lcc (lincc@ggkbigdata.com)
 * @version 1.0, 2017年2月21日 下午4:37:13
 */
public interface CheckReportInfoService {

    /**
     * @createDate 2017年6月7日下午12:43:31 
     * @author wangzhiwang
     * @return 
     * @description
     */
    List<CrCheckReportInfo> findAll();

    /**
     * @createDate 2017年6月7日下午12:45:10 
     * @author wangzhiwang
     * @param list
     * @return 
     * @description
     */
    List<CrCheckReportInfo> findByReportNums(List<String> list);

}
