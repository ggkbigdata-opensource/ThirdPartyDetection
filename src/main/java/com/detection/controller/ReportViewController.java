/*
 * File Name：ReportViewController.java
 *
 * Copyrighe：copyright@2017 www.ggkbigdata.com. All Rights Reserved
 *
 * Create Time: 2017年2月22日 下午2:00:35
 */
package com.detection.controller;

import java.io.BufferedOutputStream;

import java.io.File;

import java.io.FileNotFoundException;

import java.io.FileOutputStream;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.detection.services.CheckReportService;
import com.detection.services.impl.CheckReportServiceImpl;

/**
 *
 * @author lcc (lincc@ggkbigdata.com)
 * @version 1.0, 2017年2月22日 下午2:00:35
 */
@Controller
public class ReportViewController {

    @Autowired
    private CheckReportService checkReportService;

    @RequestMapping({ "/", "/login" })
    public String index() {
        return "/login";
    }

    @RequestMapping({ "/main" })
    public String main() {
        return "/report/main";
    }

    @PostMapping("/uploadReport")
    public String uploadReport(@RequestParam("file") MultipartFile file) throws IOException {
        if (!file.isEmpty()) {
            checkReportService.uploadAndSaveReport(file.getOriginalFilename(), file);
        }
        return "redirect:main";
    }

    @RequestMapping("/getReportPage")
    public String getReport() {
        return "/report/getReportPage";
    }

    @RequestMapping("/showAbstractReportPage")
    public String reportAbstract() {
        return "/report/showAbstractReportPage";
    }

    @RequestMapping("/showDetailReportPage")
    public String frequentBusines() {
        return "/report/showDetailReportPage";
    }

    @RequestMapping("/404")
    public String pageNotFound() {
        return "/errors/404";
    }

    @RequestMapping("/505")
    public String visitError() {
        return "/errors/505";
    }

    @RequestMapping("/nopermissions")
    public String NoPermisssions() {
        return "/errors/nopermissions";
    }
}
