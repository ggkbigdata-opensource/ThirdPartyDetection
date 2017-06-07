/*
 * File Name：ReportViewController.java
 *
 * Copyrighe：copyright@2017 www.ggkbigdata.com. All Rights Reserved
 *
 * Create Time: 2017年2月22日 下午2:00:35
 */
package com.detection.controller.analyse;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

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

   
    
    @RequestMapping(value = "/checkreportAnalyse" , method = RequestMethod.GET)
    public String checkreportAnalyse(Long streetId,Long blockId,String report,HttpServletRequest request) {
        request.setAttribute("streetId", streetId);
        request.setAttribute("blockId", blockId);
        request.setAttribute("report", report);
        return "analyse/check-report-analyse";
    }

}
