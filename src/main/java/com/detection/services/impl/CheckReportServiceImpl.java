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
public class CheckReportServiceImpl implements CheckReportService{
    //@Autowired
    //private CheckReportMapper mapper;
    
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
    public String saveCheckReportInfo(JSONObject info) {

/*        CheckReport checkInfo = new CheckReport();
        checkInfo.setAgentName(info.getString("agentName") != null ? 
                info.getString("agentName").trim() : null);
        checkInfo.setContactFax(info.getString("contactFax") != null ? 
                info.getString("contactFax").trim() : null);
        checkInfo.setContactPostCode(info.getString("contactPostcode") != null ?
                info.getString("contactPostcode").trim() :null);
        checkInfo.setContactTel(info.getString("contactTel") != null ? 
                info.getString("contactTel").trim() : null);
        checkInfo.setMessage(info.getString("message") != null ? 
                info.getString("message").trim() : null);
        checkInfo.setProjectAddress(info.getString("projectAddress") != null ? 
                info.getString("projectAddress").trim() : null);
        checkInfo.setProjectName(info.getString("projectName").trim() != null ?
                info.getString("projectName") : null);
        checkInfo.setQaAddress(info.getString("qaAddress") != null ? 
                info.getString("qaAddress").trim() : null);
        checkInfo.setQaName(info.getString("qaName") != null ? 
                info.getString("qaName").trim() : null);
        checkInfo.setReportNum(info.getString("reportNum") != null ? 
                info.getString("reportNum").trim() : null);
        checkInfo.setReportConclusion(info.getString("reportConclusion") != null ? 
                info.getString("reportConclusion").trim() : null);
        checkReportRepo.save(checkInfo);*/
        return "success";
    }

    @Override
    public void saveCheckReportResultStat(JSONArray resultStatObj, 
            String reportNum) {
        
        //checkReportResultStatRepo.deleteByReportNum(reportNum);
        
        List<CheckReportResultStat> list = new ArrayList<CheckReportResultStat>();
        int size = resultStatObj.size();
        for(int index = 0; index < size; index++) {
            CheckReportResultStat stat = new CheckReportResultStat();
            JSONObject obj = resultStatObj.getJSONObject(index);
            stat.setCheckNum(obj.getString("value1") != null ? 
                    Integer.parseInt(obj.getString("value1")) : 0);
            stat.setItemCode(obj.getString("label") != null ?
                    obj.getString("label") : null);
            stat.setItemName(obj.getString("name") != null ?
                    obj.getString("name") : null);
            //stat.setReportNum(reportNum);
            stat.setCheckLevel(obj.getString("level") != null ?
                    obj.getString("level") : null);
            stat.setUnqualifiedNum(obj.getString("value2") != null ?
                    Integer.parseInt(obj.getString("value2")) : 0);
            list.add(stat);
        }
        checkReportResultStatRepo.save(list);;
    }

    @Override
    public void saveCheckItemDetail(JSONArray resultDetailObj, String reportNum) {
        //checkItemDetailRepo.deleteByReportNum(reportNum);
        List<CheckItemDetail> list = new ArrayList<CheckItemDetail>();
        int size = resultDetailObj.size();
        for(int index = 0; index < size; index++) {
            JSONObject obj = resultDetailObj.getJSONObject(index);
            CheckItemDetail detail = new CheckItemDetail();
            detail.setCheckLevel(obj.getString("level") != null ?
                    obj.getString("level").trim() : null);
            detail.setCheckNum((obj.getString("value1") != null 
                    && !"".equals(obj.getString("value1").trim())) ?
                    Integer.parseInt(obj.getString("value1").trim()) : null);
            detail.setItemCode(obj.getString("label") != null ?
                    obj.getString("label").trim() : null);
            //detail.setReportNum(reportNum);
            detail.setUnqualifiedNum((obj.getString("value2") != null 
                    && !"".equals(obj.getString("value2").trim())) ?
                    Integer.parseInt(obj.getString("value2").trim()) : null);
            detail.setItemName(obj.getString("name") != null ?
                    obj.getString("name") : null);
            list.add(detail);
        }
        checkItemDetailRepo.save(list);
    }
    
    @Override
    public boolean uploadAndSaveReport(String fileName, MultipartFile file) throws IOException{
        boolean result = false;
        String upFilePath = uploadPath +fileName;
        String downFilePath = downloadPath + fileName;
        
        BufferedOutputStream out = new BufferedOutputStream(
                new FileOutputStream(new File(upFilePath)));
        
        out.write(file.getBytes());
        out.flush();
        out.close();
        parseAndSaveReportToDB(upFilePath,downFilePath);
        result = true;
        
        return result;
    }
    
    @Override
    public boolean parseAndSaveReportToDB(String upFilePath, String downloadPath) throws IOException{
        
        boolean result = false;
        // 解析报告并入库
        PDFParserResult parseResult = pdfParser.parse(new File(upFilePath));
        

        Cover reportCover = parseResult.getCover();
        List<Result> firstPart = parseResult.getFirstPart();
        //String reportConclusion = parseResult.getSecondPart();
        List<Result> thirdPart = parseResult.getThirdPart();
        List<ListResult> forthPart = parseResult.getForthPart();
        //TODO delete old report
        if(reportCover.getReportNum() != null){
            deleteReportByReportNum(reportCover.getReportNum());
        }
        
        //保存report对象
        CheckReport checkReport = new CheckReport();
        CheckReportInfo checkReportInfo = new CheckReportInfo();
        List<CheckReportResultStat> checkReportStatList = new ArrayList<CheckReportResultStat>();
        List<CheckItemDetail> checkItemDetailList = new ArrayList<CheckItemDetail>();
        
        //report basic info
        checkReport.setReportNum(reportCover.getReportNum());
        checkReport.setCreateDate(new Date());
        checkReport.setModifyDate(new Date());
        
        //process on report cover info
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
        
        //process on first part
        Iterator<Result> it1 = firstPart.iterator();
        while(it1.hasNext()){
            CheckReportResultStat element = new CheckReportResultStat();
            Result nextItem = it1.next();
            element.setCheckLevel(nextItem.getLevel());
            if(nextItem.getValue1()!=null && !nextItem.getValue1().equals("")){
                element.setCheckNum(Integer.parseInt(nextItem.getValue1()));
            }
            element.setItemCode(nextItem.getLabel());
            element.setItemName(nextItem.getName());
            if(nextItem.getValue1()!=null && !nextItem.getValue1().equals("")){
                element.setUnqualifiedNum(Integer.parseInt(nextItem.getValue2()));
            }
            checkReportStatList.add(element);
        }
        
        //process on third part
        Iterator<Result> it2 = thirdPart.iterator();
        while(it2.hasNext()){
            CheckItemDetail element = new CheckItemDetail();
            Result nextItem = it2.next();
            element.setCheckLevel(nextItem.getLevel());
            if(nextItem.getValue1()!=null && !nextItem.getValue1().equals("")){
                element.setCheckNum(Integer.parseInt(nextItem.getValue1()));
            }
            element.setItemCode(nextItem.getLabel());
            element.setItemName(nextItem.getName());
            if(nextItem.getValue1()!=null && !nextItem.getValue1().equals("")){
                element.setUnqualifiedNum(Integer.parseInt(nextItem.getValue2()));
            }
            checkItemDetailList.add(element);
        }
        
        
        checkReport.setCheckReportInfo(checkReportInfo);
        checkReport.setCheckItemDetail(checkItemDetailList);
        checkReport.setCheckReportResultStat(checkReportStatList);
        //TODO 第四第五部分保存内容
        
        
        checkReportRepo.saveAndFlush(checkReport);
        result = true;
        
        return result;
    }

    @Override
    public void deleteReportByReportNum(String reportNum) {
        
        if(checkReportRepo.findOne(reportNum)!=null){
            checkReportRepo.delete(reportNum);
        }
    }

    @Override
    public CheckReport getReportByReportNum(String reportNum) {
        
        return checkReportRepo.findOne(reportNum);
    }

    @Override
    public List<CheckReport> getAllReports() {
        
        return checkReportRepo.findAll();
    }
    
    

}

