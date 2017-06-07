/*
 * File Name：ReportViewController.java
 *
 * Copyrighe：copyright@2017 www.ggkbigdata.com. All Rights Reserved
 *
 * Create Time: 2017年2月22日 下午2:00:35
 */
package com.detection.controller.analyse;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.detection.model.area.Street;
import com.detection.model.report.entities.CrCheckReport;
import com.detection.services.AuthenticationService;
import com.detection.services.CheckReportInfoService;
import com.detection.services.CheckReportService;
import com.detection.services.PDFParserService;
import com.detection.services.StreetService;
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
    private CheckReportInfoService checkReportInfoService;
    @Autowired
    private UserControlService userControlService;
    @Autowired
    private AuthenticationService authService;
    @Autowired
    private PDFParserService pdfParser;
    @Autowired
    private StreetService streetService;

    @Value("${uploadPath}")
    private String uploadPath;

    @RequestMapping(value = "/checkReport", method = RequestMethod.GET)
    public String toPage(Long streetId, Long blockId, String report, HttpServletRequest request) {
        request.setAttribute("streetId", streetId);
        request.setAttribute("blockId", blockId);
        request.setAttribute("report", report);
        return "analyse/check-report-analyse";
    }

    /**
     * @createDate 2017年6月7日上午11:24:33
     * @author wangzhiwang
     * @return
     * @description 综合分析
     */
    @RequestMapping(value = "/streetAndScore", method = RequestMethod.GET)
    @ResponseBody
    public List<JSONObject> streetAndScore() {

        List<Street> streets = streetService.findAll();
        List<JSONObject> result = new ArrayList<JSONObject>();
        for (Street street : streets) {
            JSONObject obj = new JSONObject();
            obj.put("streetId", street.getId());
            obj.put("streetName", street.getName());
            obj.put("score", "");
            double allScore = 0.000;
            List<CrCheckReport> reports = checkReportService.findByStreetId(street.getId());
            if (reports != null && reports.size() > 0) {
                for (CrCheckReport report : reports) {
                    if (report.getScore() != null) {
                        allScore = allScore + report.getScore();
                    }
                }
                obj.put("score", allScore / reports.size());
            }
            result.add(obj);
        }

        return result;
    }

    /**
     * @createDate 2017年6月7日下午12:51:46
     * @author wangzhiwang
     * @param streetId
     * @return
     * @description 通过街道获取危险等级和分数
     */
    @RequestMapping(value = "/levelAndScore", method = RequestMethod.GET)
    @ResponseBody
    public List<JSONObject> levelAndScore() {

        List<Street> streets = streetService.findAll();
        List<JSONObject> result = new ArrayList<JSONObject>();
        for (Street street : streets) {

            int countLevel1 = 0;
            int countLevel2 = 0;
            int countLevel3 = 0;
            int countLevel4 = 0;

            JSONObject obj = new JSONObject();

            obj.put("streetId", street.getId());
            obj.put("streetName", street.getName());

            List<CrCheckReport> reports = checkReportService.findByStreetId(street.getId());
            if (reports != null && reports.size() > 0) {
                for (CrCheckReport report : reports) {
                    if (report.getScore() != null) {
                        if (report.getRiskLevel() != null && report.getRiskLevel().contains("1")) {
                            countLevel1++;
                        }
                        if (report.getRiskLevel() != null && report.getRiskLevel().contains("2")) {
                            countLevel2++;
                        }
                        if (report.getRiskLevel() != null && report.getRiskLevel().contains("3")) {
                            countLevel3++;
                        }
                        if (report.getRiskLevel() != null && report.getRiskLevel().contains("4")) {
                            countLevel4++;
                        }
                    }
                }
            }
            int[] arr = { countLevel1, countLevel2, countLevel3, countLevel4 };
            obj.put("list", arr);

            result.add(obj);
        }

        return result;
    }
    /*
     * @RequestMapping(value = "/levelAndScore", method = RequestMethod.GET)
     * 
     * @ResponseBody public JSONObject levelAndScore(Long streetId) {
     * 
     * //List<JSONObject> result = new ArrayList<JSONObject>(); JSONObject
     * result = new JSONObject(); List<CrCheckReport> reports=null; if
     * (streetId==null) { reports = checkReportService.findAll(); }else {
     * reports = checkReportService.findByStreetId(streetId); } if
     * (reports==null||reports.size()<1) { return null; } double allScore1=0;
     * double allScore2=0; double allScore3=0; double allScore4=0;
     * 
     * int countLevel1=0; int countLevel2=0; int countLevel3=0; int
     * countLevel4=0; for (CrCheckReport report : reports) { if
     * (report.getScore()!=null) { if
     * (report.getRiskLevel()!=null&&report.getRiskLevel().contains("1")) {
     * allScore1=allScore1+report.getScore(); countLevel1++; } if
     * (report.getRiskLevel()!=null&&report.getRiskLevel().contains("2")) {
     * allScore2=allScore2+report.getScore(); countLevel2++; } if
     * (report.getRiskLevel()!=null&&report.getRiskLevel().contains("3")) {
     * allScore3=allScore3+report.getScore(); countLevel3++; } if
     * (report.getRiskLevel()!=null&&report.getRiskLevel().contains("4")) {
     * allScore4=allScore4+report.getScore(); countLevel4++; } } } JSONObject
     * one = new JSONObject(); one.put("level", 1); one.put("score",
     * countLevel1==0?0:allScore1/countLevel1); JSONObject two = new
     * JSONObject(); two.put("level", 2); two.put("score",
     * countLevel2==0?0:allScore2/countLevel2); JSONObject three = new
     * JSONObject(); three.put("level", 3); three.put("score",
     * countLevel3==0?0:allScore3/countLevel3); JSONObject four = new
     * JSONObject(); four.put("level", 4); four.put("score",
     * countLevel4==0?0:allScore4/countLevel4);
     * 
     * double[] arr =
     * {countLevel1==0?0:allScore1/countLevel1,countLevel2==0?0:allScore2/
     * countLevel2,countLevel3==0?0:allScore3/countLevel3,countLevel4==0?0:
     * allScore4/countLevel4};
     * 
     * result.put("list", arr);
     * 
     * result.add(one); result.add(two); result.add(three); result.add(four);
     * 
     * return result; }
     */

    /**
     * @createDate 2017年6月7日下午1:29:47
     * @author wangzhiwang
     * @return
     * @description 通过街道查询类型和个数
     */
    @RequestMapping(value = "/streetAndType", method = RequestMethod.GET)
    @ResponseBody
    public JSONObject streetAndType(Long streetId) {

        JSONObject result = new JSONObject();
        List<CrCheckReport> reports=null;
        if (streetId==null||"".equals(streetId)) {
            reports = checkReportService.findAll();
        }else {
            reports = checkReportService.findByStreetId(streetId);
        }
        int value1 = 0;// "住宅建筑"；
        int value2 = 0;// "党政机关、事业单位等行政办公"
        int value3 = 0;// 
        int value4 = 0;// 
        int value5 = 0;// 
        int value6 = 0;// 
        int value7 = 0;// 
        int value8 = 0;// "物流仓储建筑"
        int value9 = 0;// "火车站、码头、客运站、机场航站楼等交通枢纽建筑"
        int value10 = 0;//
        int value11 = 0;//
        int value12 = 0;//
        int value13 = 0;//
        int value14 = 0;//
        int value15 = 0;//
        int value16 = 0;//
        for (CrCheckReport report : reports) {
            if (StringUtils.isEmpty(report.getBuildingTypeSmall())) {
                continue;
            }
            if ("中学".equals(report.getBuildingTypeSmall().trim())) {
                value1++;
            }
            if ("交通枢纽建筑".equals(report.getBuildingTypeSmall().trim())) {
                value2++;
            }
            if ("公共娱乐建筑".equals(report.getBuildingTypeSmall().trim())) {
                value3++;
            }
            if ("养老院".equals(report.getBuildingTypeSmall().trim())) {
                value4++;
            }
            if ("医院".equals(report.getBuildingTypeSmall().trim())) {
                value5++;
            }
            if ("商业建筑".equals(report.getBuildingTypeSmall().trim())) {
                value6++;
            }
            if ("大学".equals(report.getBuildingTypeSmall().trim())) {
                value7++;
            }
            if ("小学".equals(report.getBuildingTypeSmall().trim())) {
                value8++;
            }
            if ("幼儿园".equals(report.getBuildingTypeSmall().trim())) {
                value9++;
            }
            if ("教育科研建筑".equals(report.getBuildingTypeSmall().trim())) {
                value10++;
            }
            if ("文化设施建筑".equals(report.getBuildingTypeSmall().trim())) {
                value11++;
            }
            if ("物流仓储建筑".equals(report.getBuildingTypeSmall().trim())) {
                value12++;
            }
            if ("科研院".equals(report.getBuildingTypeSmall().trim())) {
                value13++;
            }
            if ("综合性办公建筑".equals(report.getBuildingTypeSmall().trim())) {
                value14++;
            }
            if ("行政办公建筑".equals(report.getBuildingTypeSmall().trim())) {
                value15++;
            }
            if ("院所等教育科研建筑".equals(report.getBuildingTypeSmall().trim())) {
                value16++;
            }
        }
        int[] arr = { value1, value2, value3, value4, value5, value6, value7, value8, value9, value10, value11,
                value12, value13, value14, value15, value16 };
        result.put("list", arr);

        return result;
    }

    /**
     * @createDate 2017年6月7日下午2:16:27 
     * @author wangzhiwang
     * @return 
     * @description 通过街道获取监管部门和个数
     */
    @RequestMapping(value = "/streetAndDepartment", method = RequestMethod.GET)
    @ResponseBody
    public List<JSONObject> streetAndCompetentDepartment(Long streetId) {

        List<CrCheckReport> reports=null;
        if (streetId==null||"".equals(streetId)) {
            reports = checkReportService.findByCompetentDepartmentIsNotNull();
        }else {
            reports = checkReportService.findByStreetIdAndCompetentDepartmentIsNotNull(streetId);
        }
        
        
        int hospital = 0;//医院
        int kindergarten = 0;//幼儿园
        int primarySchool = 0;//小学
        int highSchool = 0;//中学
        int university = 0;//大学
        int nursingHome = 0;//养老院
        
        double hospitalScore = 0;//医院
        double kindergartenScore = 0;//幼儿园
        double primarySchoolScore = 0;//小学
        double highSchoolScore = 0;//中学
        double universityScore = 0;//大学
        double nursingHomeScore = 0;//养老院
        
        for (CrCheckReport report : reports) {
            if (report.getBuildingTypeSmall()!=null&&report.getBuildingTypeSmall().contains("医院")) {
                hospital++;
                if (report.getScore()!=null) {
                    hospitalScore=hospitalScore+report.getScore();
                }
            }
            if (report.getBuildingTypeSmall()!=null&&report.getBuildingTypeSmall().contains("幼儿园")) {
                kindergarten++;
                if (report.getScore()!=null) {
                    kindergartenScore=kindergartenScore+report.getScore();
                }
            }
            if (report.getBuildingTypeSmall()!=null&&report.getBuildingTypeSmall().contains("小学")) {
                primarySchool++;
                if (report.getScore()!=null) {
                    primarySchoolScore=primarySchoolScore+report.getScore();
                }
            }
            if (report.getBuildingTypeSmall()!=null&&report.getBuildingTypeSmall().contains("中学")) {
                highSchool++;
                if (report.getScore()!=null) {
                    highSchoolScore=highSchoolScore+report.getScore();
                }
            }
            if (report.getBuildingTypeSmall()!=null&&report.getBuildingTypeSmall().contains("大学")) {
                university++;
                if (report.getScore()!=null) {
                    universityScore=universityScore+report.getScore();
                }
            }
            if (report.getBuildingTypeSmall()!=null&&report.getBuildingTypeSmall().contains("养老院")) {
                nursingHome++;
                if (report.getScore()!=null) {
                    nursingHomeScore=nursingHomeScore+report.getScore();
                }
            }
        }
        List<JSONObject> result = new ArrayList<JSONObject>();
        JSONObject obj1 = new JSONObject();
        obj1.put("count", hospital); 
        obj1.put("score", hospital==0?0:hospitalScore/hospital); 
        result.add(obj1);
        
        JSONObject obj2 = new JSONObject();
        obj2.put("count", kindergarten); 
        obj2.put("score", kindergarten==0?0:kindergartenScore/kindergarten); 
        result.add(obj2);
        
        JSONObject obj3 = new JSONObject();
        obj3.put("count", primarySchool+""); 
        obj3.put("score", primarySchool==0?0:primarySchoolScore/primarySchool); 
        result.add(obj3);
        
        JSONObject obj4 = new JSONObject();
        obj4.put("count", highSchool+""); 
        obj4.put("score", highSchool==0?0:highSchoolScore/highSchool); 
        result.add(obj4);
        
        JSONObject obj5 = new JSONObject();
        obj5.put("count", university+""); 
        obj5.put("score", university==0?0:universityScore/university); 
        result.add(obj5);
        
        JSONObject obj6 = new JSONObject();
        obj6.put("count", nursingHome+""); 
        obj6.put("score", nursingHome==0?0:nursingHomeScore/nursingHome); 
        result.add(obj6);
        return result;
    }

    /**
     * @createDate 2017年6月7日下午3:04:31 
     * @author wangzhiwang
     * @param streetId
     * @return 
     * @description 通过街道查询高度和分数
     */
    @RequestMapping(value = "/heightAndScore", method = RequestMethod.GET)
    @ResponseBody
    public List<JSONObject> streetAndHeight(Long streetId) {

        List<CrCheckReport> reports=null;
        if (streetId==null||"".equals(streetId)) {
            reports = checkReportService.findAll();
        }else {
            reports = checkReportService.findByStreetId(streetId);
        }
        
        int one =0;//多层
        int two =0;//高层
        int three =0;//超高层
        
        double oneScore=0;
        double twoScore=0;
        double threeScore=0;
        for (CrCheckReport report : reports) {
            if (report.getHeightType()!=null&&report.getHeightType().contains("多层")) {
                one++;
                if (report.getScore()!=null) {
                    oneScore=oneScore+report.getScore();
                }
            }
            if (report.getHeightType()!=null&&report.getHeightType().contains("高层")) {
                two++;
                if (report.getScore()!=null) {
                    twoScore=twoScore+report.getScore();
                }
            }
            if (report.getHeightType()!=null&&report.getHeightType().contains("超高层")) {
                three++;
                if (report.getScore()!=null) {
                    threeScore=threeScore+report.getScore();
                }
            }
        }
        List<JSONObject> result = new ArrayList<JSONObject>();
        JSONObject obj1 = new JSONObject();
        obj1.put("count",one); 
        obj1.put("score", one==0?0:oneScore/one);
        result.add(obj1);
        
        JSONObject obj2 = new JSONObject();
        obj2.put("count", two); 
        obj2.put("score", two==0?0:twoScore/two); 
        result.add(obj2);
        
        JSONObject obj3 = new JSONObject();
        obj3.put("count", three); 
        obj3.put("score", three==0?0:threeScore/three); 
        result.add(obj3);
        return result;
    }

}
