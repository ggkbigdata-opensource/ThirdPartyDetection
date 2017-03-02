/*
 * File Name：CheckReportServiceImpl.java
 *
 * Copyrighe：copyright@2017 www.ggkbigdata.com. All Rights Reserved
 *
 * Create Time: 2017年2月21日 下午4:37:46
 */
package com.detection.services.impl;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.detection.model.pdfparse.Cover;
import com.detection.model.pdfparse.ListResult;
import com.detection.model.pdfparse.PDFParserResult;
import com.detection.model.pdfparse.Result;
import com.detection.model.report.entities.CheckItemDetail;
import com.detection.model.report.entities.CheckReport;
import com.detection.model.report.entities.CheckReportInfo;
import com.detection.model.report.entities.CheckReportResultStat;
import com.detection.model.report.entities.CheckReportUnqualifiedItemDetail;
import com.detection.model.report.repositories.CheckItemDetailRepository;
import com.detection.model.report.repositories.CheckReportRepository;
import com.detection.model.report.repositories.CheckReportResultStatRepository;
import com.detection.services.CheckReportService;
import com.detection.services.PDFParserService;

/**
 *
 * @author lcc (lincc@ggkbigdata.com)
 * @version 1.0, 2017年2月21日 下午4:37:46
 */
@Service
public class CheckReportServiceImpl implements CheckReportService {
    // @Autowired
    // private CheckReportMapper mapper;

    @Autowired
    private CheckReportRepository checkReportRepo;
    @Autowired
    private CheckReportResultStatRepository checkReportResultStatRepo;
    @Autowired
    private CheckItemDetailRepository checkItemDetailRepo;

    @Autowired
    private PDFParserService pdfParser;

    @Value("${uploadPath}")
    private String uploadPath;

    @Value("${downloadPath}")
    private String downloadPath;

    @Override
    public boolean uploadAndSaveReport(String fileName, MultipartFile file) throws IOException {
        boolean result = false;
        String upFilePath = uploadPath + fileName;
        String downFilePath = downloadPath + fileName;

        File outPath = new File(uploadPath);
        if (!outPath.exists()) {
            outPath.mkdirs();
        }
        BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(new File(upFilePath)));

        out.write(file.getBytes());
        out.flush();
        out.close();
        parseAndSaveReportToDB(upFilePath, downFilePath);
        result = true;

        return result;
    }

    @Override
    public boolean parseAndSaveReportToDB(String upFilePath, String downloadPath) throws IOException {

        boolean result = false;
        // 解析报告并入库
        PDFParserResult parseResult = pdfParser.parse(new File(upFilePath));

        Cover reportCover = parseResult.getCover();
        List<Result> firstPart = parseResult.getFirstPart();
        // String reportConclusion = parseResult.getSecondPart();
        List<Result> thirdPart = parseResult.getThirdPart();
        List<ListResult> forthPart = parseResult.getForthPart();
        // TODO delete old report
        if (reportCover.getReportNum() != null) {
            deleteReportByReportNum(reportCover.getReportNum());
        }

        // 保存report对象
        CheckReport checkReport = new CheckReport();
        CheckReportInfo checkReportInfo = new CheckReportInfo();
        List<CheckReportResultStat> checkReportStatList = new ArrayList<CheckReportResultStat>();
        List<CheckItemDetail> checkItemDetailList = new ArrayList<CheckItemDetail>();
        List<CheckReportUnqualifiedItemDetail> checkReportUnqualifiedItemList = new ArrayList<CheckReportUnqualifiedItemDetail>();
        // report basic info
        checkReport.setReportNum(reportCover.getReportNum());
        checkReport.setCreateDate(new Date());
        checkReport.setModifyDate(new Date());

        // process on report cover info
        checkReportInfo.setReportNum(reportCover.getReportNum());
        checkReportInfo.setAgentName(reportCover.getAgentName());
        checkReportInfo.setContactFax(reportCover.getContactFax());
        checkReportInfo.setContactPostCode(reportCover.getContactPostcode());
        checkReportInfo.setContactTel(reportCover.getContactTel());
        checkReportInfo.setProjectAddress(reportCover.getProjectAddress());
        checkReportInfo.setProjectName(reportCover.getProjectName());
        checkReportInfo.setQaAddress(reportCover.getQaAddress());
        checkReportInfo.setQaName(reportCover.getQaName());
        checkReportInfo.setReportConclusion(reportCover.getReportConclusion());
        checkReportInfo.setFilePath(downloadPath);

        // process on first part
        Iterator<Result> it1 = firstPart.iterator();
        while (it1.hasNext()) {
            CheckReportResultStat element = new CheckReportResultStat();
            Result nextItem = it1.next();
            element.setImportantGrade(nextItem.getLevel());
            if (nextItem.getValue1() != null && !nextItem.getValue1().equals("")) {
                element.setCheckNum(Integer.parseInt(nextItem.getValue1()));
            }
            element.setItemCode(nextItem.getLabel());
            element.setItemName(nextItem.getName());
            if (nextItem.getValue1() != null && !nextItem.getValue1().equals("")) {
                element.setUnqualifiedNum(Integer.parseInt(nextItem.getValue2()));
            }
            checkReportStatList.add(element);
        }

        // process on third part
        Iterator<Result> it2 = thirdPart.iterator();
        while (it2.hasNext()) {
            CheckItemDetail element = new CheckItemDetail();
            Result nextItem = it2.next();
            element.setImportantGrade(nextItem.getLevel());
            if (nextItem.getValue1() != null && !nextItem.getValue1().equals("")) {
                element.setCheckNum(Integer.parseInt(nextItem.getValue1()));
            }
            element.setItemCode(nextItem.getLabel());
            element.setItemName(nextItem.getName());
            if (nextItem.getValue1() != null && !nextItem.getValue1().equals("")) {
                element.setUnqualifiedNum(Integer.parseInt(nextItem.getValue2()));
            }
            checkItemDetailList.add(element);
        }
        // TODO 第四第五部分保存内容
        // process on fourth part
        Iterator<ListResult> it3 = forthPart.iterator();
        while (it3.hasNext()) {
            CheckReportUnqualifiedItemDetail element = new CheckReportUnqualifiedItemDetail();
            ListResult nextItem = it3.next();
            element.setImportantGrade(nextItem.getImportantGrade());
            element.setRequirements(nextItem.getRequirements());
            element.setTestItem(nextItem.getTestItem());
            element.setUnqualifiedCheckPointByStringList(nextItem.getNonstandardItems());
            checkReportUnqualifiedItemList.add(element);
        }

        checkReport.setCheckReportInfo(checkReportInfo);
        checkReport.setCheckItemDetail(checkItemDetailList);
        checkReport.setCheckReportResultStat(checkReportStatList);
        checkReport.setUnqualifiedItemDetail(checkReportUnqualifiedItemList);

        checkReportRepo.save(checkReport);
        result = true;

        return result;
    }

    @Override
    public void deleteReportByReportNum(String reportNum) {
        if (checkReportRepo.findOne(reportNum) != null) {
            checkReportRepo.delete(reportNum);
        }
    }

    @Override
    public JSONObject getAllReports() {
        JSONObject result = new JSONObject();
        int code = 200;
        String message = "success";

        List<CheckReport> reportlist = checkReportRepo.findAll();
        List<CheckReportInfo> dataList = new ArrayList<CheckReportInfo>();
        Iterator<CheckReport> it = reportlist.iterator();
        while (it.hasNext()) {
            dataList.add(it.next().getCheckReportInfo());
        }
        result.put("code", code);
        result.put("message", message);
        result.put("data", dataList);

        return result;
    }

    @Override
    public JSONObject getReportByCondition(String projectName, String reportNum, String riskLevel, String qaName) {
        // TODO Auto-generated method stub
        if (reportNum != null && !reportNum.equals("")) {
            CheckReport result = checkReportRepo.findOne(reportNum);
        }
        return null;
    }

}
