/*
 * File Name：ReportViewController.java
 *
 * Copyrighe：copyright@2017 www.ggkbigdata.com. All Rights Reserved
 *
 * Create Time: 2017年2月22日 下午2:00:35
 */
package com.detection.controller.analyse;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.detection.services.AuthenticationService;
import com.detection.services.CheckReportService;
import com.detection.services.PDFParserService;
import com.detection.services.UserControlService;

/**
 *
 * @author lcc (lincc@ggkbigdata.com)
 * @version 1.0, 2017年2月22日 下午2:00:35
 * 
 */
@Controller
public class CheckReportAnalyseController {
    @Autowired
    private CheckReportService checkReportService;
    @Autowired
    private UserControlService userControlService;
    @Autowired
    private AuthenticationService authService;
    @Autowired
    private PDFParserService pdfParser;

    @Value("${uploadPath}")
    private String uploadPath;

    @RequestMapping(value = "/checkReport", method = RequestMethod.GET)
    public String toPage(Long streetId, Long blockId, String report, HttpServletRequest request) {
        request.setAttribute("streetId", streetId);
        request.setAttribute("blockId", blockId);
        request.setAttribute("report", report);
        return "analyse/check-report-analyse";
    }

    @RequestMapping(value = "/analyseData", method = RequestMethod.POST)
    @ResponseBody
    public String checkReportAnalyseData(Long streetId, Long blockId, Long heightType, Long riskLevel,Long buildingType, //建筑类型 
            HttpServletRequest request) {
        Map<String, Object> map = new HashMap<String, Object>();

        if (streetId!=null && !streetId.equals("0")&&!"".equals(streetId)) {
            map.put("streetId", streetId);
        }
        if (blockId!=null && !blockId.equals("0")&&!"".equals(blockId)) {
            map.put("blockId", blockId);
        }
        if (riskLevel!=null && !riskLevel.equals("0")&&!"".equals(riskLevel)) {
            map.put("blockId", blockId);
        }
        if (riskLevel != null && !"".equals(riskLevel)) {
            if (riskLevel == 1) {
                map.put("riskLevel", "危险等级1");
            }
            if (riskLevel == 2) {
                map.put("riskLevel", "危险等级2");
            }
            if (riskLevel == 3) {
                map.put("riskLevel", "危险等级3");
            }
            if (riskLevel == 4) {
                map.put("riskLevel", "危险等级4");
            }
        }

        if (buildingType != null && !"".equals(buildingType)) {
            if (buildingType == 1) {
                map.put("buildingTypeSmall", "住宅建筑");
            }
            if (buildingType == 2) {
                map.put("buildingTypeSmall", "党政机关、事业单位等行政办公建筑");
            }
            if (buildingType == 3) {
                map.put("buildingTypeSmall", "图书、展览等文化设施建筑，学校、科研院所等教育科研建筑");
            }
            if (buildingType == 4) {
                map.put("buildingTypeSmall", "医院、幼儿园、寄宿制学校、养老院等特殊群体居住建筑");
            }
            if (buildingType == 5) {
                map.put("buildingTypeSmall", "商场、市场、超市、餐饮、宾馆、酒店等商业建筑");
            }
            if (buildingType == 6) {
                map.put("buildingTypeSmall", "金融、保险等综合性办公建筑");
            }
            if (buildingType == 7) {
                map.put("buildingTypeSmall", "电影院、网吧、歌舞厅等公共娱乐建筑");
            }
            if (buildingType == 8) {
                map.put("buildingTypeSmall", "物流仓储建筑");
            }
            if (buildingType == 9) {
                map.put("buildingTypeSmall", "火车站、码头、客运站、机场航站楼等交通枢纽建筑");
            }
        }
        if (heightType != null && !heightType.equals("")) {
            if (heightType == 1) {
                map.put("heightType", "多层建筑");
            }
            if (heightType == 2) {
                map.put("heightType", "高层建筑");
            }
            if (heightType == 3) {
                map.put("heightType", "超高层建筑");
            }
        }

        //result = service.getAllReports(map);

        return "analyse/check-report-analyse";
    }

}
