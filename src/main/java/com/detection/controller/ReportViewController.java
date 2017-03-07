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

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
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

    @RequestMapping({ "/" })
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

    @RequestMapping(value = {"/fetchReport/{fetchCode}"}, method = RequestMethod.GET)
    public String fetchReportFile(@PathVariable("fetchCode") String fetchCode){
        JSONObject result = checkReportService.getReportPath(fetchCode);
        
        if(result!=null){
            return "";
        }
        else{
            return "redirect:404";
        }
    }

    @RequestMapping("/getReportPage")
    public String getReport() {
        return "/report/getReportPage";
    }

    @RequestMapping("/showAbstractReportPage")
    public String reportAbstract() {
        return "/report/showAbstractReportPage";
    }
    
    @RequestMapping(value = {"/deleteReportByReportNum"} , method = RequestMethod.GET)
    public String deleteReportByReportNum(@RequestParam String reportNum, HttpServletRequest request){
        request.getAttribute("token");
        return "redirect:main";
    }
    
    @RequestMapping("/showDetailReportPage")
    public ModelAndView frequentBusines(@RequestParam String verifyToken) {
        ModelAndView mv = new ModelAndView("/report/showDetailReportPage");
        JSONObject result = checkReportService.getDetailReportInfo(verifyToken);
        mv.addObject("result", result);
        return mv;
    }
    
    //测试返回信息后页面是否可用。
    @RequestMapping("/testReport")
    public ModelAndView testReport() {
        ModelAndView mv = new ModelAndView("/report/showDetailReportPage");
        JSONObject result = checkReportService.getDetailReportInfo("2FF26EA6577347EB1A73DB450D0B37BA");
        mv.addObject("result", result);
        return mv;
    }

    @RequestMapping({"/404"})
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
