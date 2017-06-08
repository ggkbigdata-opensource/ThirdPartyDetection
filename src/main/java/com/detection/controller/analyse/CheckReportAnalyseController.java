/*
 * File Name：ReportViewController.java
 *
 * Copyrighe：copyright@2017 www.ggkbigdata.com. All Rights Reserved
 *
 * Create Time: 2017年2月22日 下午2:00:35
 */
package com.detection.controller.analyse;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

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
import com.detection.model.report.entities.CrCheckReportResultStat;
import com.detection.services.AuthenticationService;
import com.detection.services.CheckReportInfoService;
import com.detection.services.CheckReportService;
import com.detection.services.CrCheckReportResultStatService;
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
    private StreetService streetService;
    @Autowired
    private CrCheckReportResultStatService checkReportResultStatService;

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
            obj.put("count", 0);
            double allScore = 0.000;
            int count = 00;
            List<CrCheckReport> reports = checkReportService.findByStreetId(street.getId());
            if (reports != null && reports.size() > 0) {
                for (CrCheckReport report : reports) {
                    if (report.getScore() != null) {
                        allScore = allScore + report.getScore();
                    }
                }
                count=reports.size();
                obj.put("score", allScore / reports.size());
            }
            obj.put("count", count);
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
    public  Map<String, JSONObject> streetAndType(Long streetId) {

        List<CrCheckReport> reports = null;
        List<CrCheckReport> allReport = checkReportService.findAll();
        if (streetId == null || "".equals(streetId)) {
            reports=allReport;
        } else {
            reports = checkReportService.findByStreetId(streetId);
        }
        List<String> types = checkReportService.findGroupByBuildingTypeSmall();
        Map<String, JSONObject> result = new HashMap<String,JSONObject>();
        for (String type : types) {
            JSONObject obj = new JSONObject();
            obj.put("count", 0);
            obj.put("proportion", 0);
            obj.put("typeName", type);
            result.put(type, obj);
        }
        for (CrCheckReport report : reports) {
            System.out.println(report.getReportNum());
            JSONObject obj = result.get(report.getBuildingTypeSmall().trim());
            Integer count = (Integer)obj.get("count");
            
            count++;
            
            obj.put("count", count);
            obj.put("proportion", (float)count/(float)reports.size());
        }

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

        List<CrCheckReport> reports = null;
        if (streetId == null || "".equals(streetId)) {
            reports = checkReportService.findByCompetentDepartmentIsNotNull();
        } else {
            reports = checkReportService.findByStreetIdAndCompetentDepartmentIsNotNull(streetId);
        }

        int hospital = 0;// 医院
        int kindergarten = 0;// 幼儿园
        int primarySchool = 0;// 小学
        int highSchool = 0;// 中学
        int university = 0;// 大学
        int nursingHome = 0;// 养老院

        double hospitalScore = 0;// 医院
        double kindergartenScore = 0;// 幼儿园
        double primarySchoolScore = 0;// 小学
        double highSchoolScore = 0;// 中学
        double universityScore = 0;// 大学
        double nursingHomeScore = 0;// 养老院

        for (CrCheckReport report : reports) {
            if (report.getBuildingTypeSmall() != null && report.getBuildingTypeSmall().contains("医院")) {
                hospital++;
                if (report.getScore() != null) {
                    hospitalScore = hospitalScore + report.getScore();
                }
            }
            if (report.getBuildingTypeSmall() != null && report.getBuildingTypeSmall().contains("幼儿园")) {
                kindergarten++;
                if (report.getScore() != null) {
                    kindergartenScore = kindergartenScore + report.getScore();
                }
            }
            if (report.getBuildingTypeSmall() != null && report.getBuildingTypeSmall().contains("小学")) {
                primarySchool++;
                if (report.getScore() != null) {
                    primarySchoolScore = primarySchoolScore + report.getScore();
                }
            }
            if (report.getBuildingTypeSmall() != null && report.getBuildingTypeSmall().contains("中学")) {
                highSchool++;
                if (report.getScore() != null) {
                    highSchoolScore = highSchoolScore + report.getScore();
                }
            }
            if (report.getBuildingTypeSmall() != null && report.getBuildingTypeSmall().contains("大学")) {
                university++;
                if (report.getScore() != null) {
                    universityScore = universityScore + report.getScore();
                }
            }
            if (report.getBuildingTypeSmall() != null && report.getBuildingTypeSmall().contains("养老院")) {
                nursingHome++;
                if (report.getScore() != null) {
                    nursingHomeScore = nursingHomeScore + report.getScore();
                }
            }
        }
        List<JSONObject> result = new ArrayList<JSONObject>();
        JSONObject obj1 = new JSONObject();
        obj1.put("count", hospital);
        obj1.put("score", hospital == 0 ? 0 : hospitalScore / hospital);
        result.add(obj1);

        JSONObject obj2 = new JSONObject();
        obj2.put("count", kindergarten);
        obj2.put("score", kindergarten == 0 ? 0 : kindergartenScore / kindergarten);
        result.add(obj2);

        JSONObject obj3 = new JSONObject();
        obj3.put("count", primarySchool + "");
        obj3.put("score", primarySchool == 0 ? 0 : primarySchoolScore / primarySchool);
        result.add(obj3);

        JSONObject obj4 = new JSONObject();
        obj4.put("count", highSchool + "");
        obj4.put("score", highSchool == 0 ? 0 : highSchoolScore / highSchool);
        result.add(obj4);

        JSONObject obj5 = new JSONObject();
        obj5.put("count", university + "");
        obj5.put("score", university == 0 ? 0 : universityScore / university);
        result.add(obj5);

        JSONObject obj6 = new JSONObject();
        obj6.put("count", nursingHome + "");
        obj6.put("score", nursingHome == 0 ? 0 : nursingHomeScore / nursingHome);
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

        List<CrCheckReport> reports = null;
        if (streetId == null || "".equals(streetId)) {
            reports = checkReportService.findAll();
        } else {
            reports = checkReportService.findByStreetId(streetId);
        }

        int one = 0;// 多层
        int two = 0;// 高层
        int three = 0;// 超高层

        double oneScore = 0;
        double twoScore = 0;
        double threeScore = 0;
        for (CrCheckReport report : reports) {
            if (report.getHeightType() != null && report.getHeightType().contains("多层")) {
                one++;
                if (report.getScore() != null) {
                    oneScore = oneScore + report.getScore();
                }
            }
            if ("高层建筑".equals(report.getHeightType())) {
                two++;
                if (report.getScore() != null) {
                    twoScore = twoScore + report.getScore();
                }
            }
            if (report.getHeightType() != null && report.getHeightType().contains("超高层")) {
                three++;
                if (report.getScore() != null) {
                    threeScore = threeScore + report.getScore();
                }
            }
        }
        List<JSONObject> result = new ArrayList<JSONObject>();
        JSONObject obj1 = new JSONObject();
        obj1.put("count", one);
        obj1.put("score", one == 0 ? 0 : oneScore / one);
        result.add(obj1);

        JSONObject obj2 = new JSONObject();
        obj2.put("count", two);
        obj2.put("score", two == 0 ? 0 : twoScore / two);
        result.add(obj2);

        JSONObject obj3 = new JSONObject();
        obj3.put("count", three);
        obj3.put("score", three == 0 ? 0 : threeScore / three);
        result.add(obj3);
        return result;
    }

    /**
     * @createDate 2017年6月7日下午3:04:31 
     * @author wangzhiwang
     * @param streetId
     * @return 
     * @description 通过街道查询检测项个数和不合格数
     */
    @RequestMapping(value = "/streetAndItem", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, JSONObject> streetAndItem(Long streetId) {
        
        List<CrCheckReport> reports=null;
        List<CrCheckReportResultStat> resultStats=null;
        if (streetId==null||"".equals(streetId)) {
            resultStats = checkReportResultStatService.findAll();
        }else {
            reports = checkReportService.findByStreetId(streetId);
            List<String> reportNums = new ArrayList<String>();
            for (CrCheckReport report : reports) {
                reportNums.add(report.getReportNum());
            }
            resultStats = checkReportResultStatService.findByReportNums(reportNums);
        }
        
        //获取分类
        List<CrCheckReportResultStat>  allType = checkReportResultStatService.findGroupByItemCode();
        
        Map<String, JSONObject> result = new HashMap<String,JSONObject>();
        for (CrCheckReportResultStat type : allType) {
            JSONObject obj = new JSONObject();
            obj.put("qualified", 0);
            obj.put("unQualified", 0);
            obj.put("passRate", 0);
            obj.put("itemName", type.getItemName());
            result.put(type.getItemCode().trim(), obj);
        }
        for (CrCheckReportResultStat reportResultStat : resultStats) {
            JSONObject obj = result.get(reportResultStat.getItemCode().trim());
            Integer qualified = (Integer)obj.get("qualified");
            Integer unQualified = (Integer)obj.get("unQualified");
            
            qualified =  qualified+reportResultStat.getCheckNum()-reportResultStat.getUnqualifiedNum();
            unQualified =  unQualified+reportResultStat.getUnqualifiedNum();
            obj.put("qualified",qualified );
            obj.put("unQualified", unQualified);
            
            if ((unQualified+qualified)!=0) {
                BigDecimal bg = new BigDecimal((float)unQualified/(float)(unQualified+qualified)).setScale(2, RoundingMode.UP);
                obj.put("passRate", bg.doubleValue()*100);
            }
            
            result.put(reportResultStat.getItemCode(), obj);
        }
        
        return result;
    }

}
