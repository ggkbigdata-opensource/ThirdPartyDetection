/*
 * File Name：ReportViewController.java
 *
 * Copyrighe：copyright@2017 www.ggkbigdata.com. All Rights Reserved
 *
 * Create Time: 2017年2月22日 下午2:00:35
 */
package com.detection.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.detection.services.AuthenticationService;
import com.detection.services.CheckReportService;
import com.detection.services.PDFParserService;
import com.detection.services.UserControlService;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chapter;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Section;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

/**
 *
 * @author lcc (lincc@ggkbigdata.com)
 * @version 1.0, 2017年2月22日 下午2:00:35
 * 
 */
@Controller
public class ReportViewController {
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

    @RequestMapping(value = { "/", "/loginPage" }, method = RequestMethod.GET)
    public String index() {
        return "login";
    }
    
    
    @RequestMapping(value = "/mainEmbeddedAnalyse" , method = RequestMethod.GET)
    public String mainEmbeddedAnalyse(Long streetId,Long blockId,String report,String loginName,String userPassword,HttpServletRequest request) throws Exception {
        request.setAttribute("streetId", streetId);
        request.setAttribute("blockId", blockId);
        request.setAttribute("report", report);
        
        JSONObject result = userControlService.userLogin(loginName, userPassword);
        HttpSession session = request.getSession();
        if(result.getIntValue("code") == 200){
            session.setAttribute("userName", loginName);
            session.setAttribute("token", result.getString("token"));
            session.setAttribute("role", result.getString("role"));
            session.setMaxInactiveInterval(4*60*60);
        }
        
        return "report/main-embeddedAnalyse";
    }
    
    @RequestMapping(value = "/checkReportAnalyse" , method = RequestMethod.GET)
    public String checkreportAnalyse(Long streetId,Long blockId,String report,HttpServletRequest request) {
        request.setAttribute("streetId", streetId);
        request.setAttribute("blockId", blockId);
        request.setAttribute("report", report);
        return "analyse/check-report-analyse";
    }

    @RequestMapping(value = { "/main" }, method = RequestMethod.GET)
    public ModelAndView main(HttpServletRequest request) {

        String result = "report/main";
        int permittedRole = 1;
        if (!authService.isLoggedin(request)) {
            result = "redirect:/";
        } else if (!authService.isPermitted(request, permittedRole)) {
            result = "redirect:nopermissions";
        }
        ModelAndView mv = new ModelAndView(result);
        mv.addObject("userName", (String) request.getSession().getAttribute("userName"));
        return mv;
    }

    @RequestMapping(value = { "/mainEmbed" }, method = RequestMethod.GET)
    public ModelAndView mainEmbed(HttpServletRequest request) {

        String result = "report/main-embedded";
        int permittedRole = 1;
        if (!authService.isLoggedin(request)) {
            result = "redirect:/";
        } else if (!authService.isPermitted(request, permittedRole)) {
            result = "redirect:nopermissions";
        }
        ModelAndView mv = new ModelAndView(result);
        mv.addObject("userName", (String) request.getSession().getAttribute("userName"));
        return mv;
    }

    @RequestMapping(value = { "/embeddedUserLogin" }, method = RequestMethod.GET)
    public String embeddedUserLogin(@RequestParam String loginName, @RequestParam String userPassword,Long streetId,
            HttpServletRequest request) throws Exception {
        JSONObject result = userControlService.userLogin(loginName, userPassword);
        // JSONObject result = userControlService.userLogin(loginName,
        // EncryptionHelper.encryptStringByMD5(userPassword));
        HttpSession session = request.getSession();
        if (result.getIntValue("code") == 200) {
            session.setAttribute("userName", loginName);
            session.setAttribute("token", result.getString("token"));
            session.setAttribute("role", result.getString("role"));
            request.setAttribute("streetId", streetId);
            request.setAttribute("loginName", loginName);
            request.setAttribute("password", userPassword);
            return "report/main-embedded";
        }
        return "redirect:embedded-nopermissions";
    }

    @RequestMapping(value = { "/embeddedMain" }, method = RequestMethod.GET)
    public String embeddedMain(HttpServletRequest request) throws Exception {
        String result = "report/main-embedded";
        int permittedRole = 1;
        if (!authService.isLoggedin(request)) {
            result = "redirect:nopermissions";
        } else if (!authService.isPermitted(request, permittedRole)) {
            result = "redirect:nopermissions";
        }
        return result;
    }

    @RequestMapping(value = { "/embedded-nopermissions" }, method = RequestMethod.GET)
    public String embeddedNopermissions() throws Exception {
        return "errors/embedded-nopermissions";
    }

    @RequestMapping(value = "/uploadReport", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject uploadReport(@RequestParam("files") List<MultipartFile> files, HttpServletRequest request)
            throws Exception {

        JSONObject result = new JSONObject();

        List<String> reportNums = checkReportService.findAllReportNum();

        HashMap<String, String> map = new HashMap<String, String>();
        for (String report : reportNums) {
            map.put(report, "1");
        }

        List<String> uploadReports = new ArrayList<String>();
        for (MultipartFile multipartFile : files) {
            InputStream inputStream = multipartFile.getInputStream();
            String fileName = multipartFile.getOriginalFilename();
            PDDocument pdfDocument = PDDocument.load(inputStream);
            int lastPage = pdfDocument.getNumberOfPages();
            PDFTextStripper stripper = new PDFTextStripper();
            stripper.setSortByPosition(true);
            stripper.setStartPage(1);
            stripper.setEndPage(lastPage);
            String allText = stripper.getText(pdfDocument);
            pdfDocument.close();
            int eIndex = 0;
            String end = "建筑消防设施检测报告";
            eIndex = allText.indexOf(end);
            String paragraph;

            try {
                paragraph = allText.substring(0, eIndex);
            } catch (Exception e) {
                result.put("result", false);
                result.put("status", false);
                result.put("msg", "文件为：" + fileName + "的文件内容格式格式不正确");
                e.printStackTrace();
                return result;
            }

            String osName = System.getProperty("os.name");
            String system;
            if (osName.contains("win") || osName.contains("Win")) {
                system = "\r\n";
            } else if (osName.contains("Linux") || osName.contains("linux")) {
                system = "\n";
            } else {
                system = "\r";
            }

            String[] lines = paragraph.split(system);
            Pattern reportNumP = Pattern.compile("\\d{2}[a-zA-Z]{2}(A|B)(\\d{3})\\s*$");

            String reportNum = null;

            for (int i = 0; i < lines.length; i++) {
                String line = lines[i];
                Matcher m = reportNumP.matcher(line);
                if (m.find()) {
                    reportNum = line.replace(" ", "").trim();
                    if (!reportNum.contains("天消") || reportNum.length() < 10) {
                        result.put("result", false);
                        result.put("status", false);
                        result.put("msg", "文件为：" + fileName + "的项目编号格式不正确");
                        return result;
                    }

                }
            }

            reportNum = reportNum.substring(reportNum.indexOf("天消"));

            uploadReports.add(reportNum);
        }

        result.put("status", "success");
        String concent = "";
        for (String report : uploadReports) {
            if (map.get(report) != null) {
                concent = concent + report + ",";
                result.put("status", false);
                result.put("result", true);
            } else {
                result.put("status", true);
                result.put("status", true);
            }
        }

        if (result.getBoolean("status")) {
            result = this.uploadReportAgain(files, request);
        } else {
            result.put("msg", "存在相同的文件，分别为" + concent + "是否覆盖，并全部导入？");
        }

        return result;

    }

    @RequestMapping(value = "/uploadReport1", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject uploadReportForExcel(@RequestParam("files") List<MultipartFile> files, HttpServletRequest request)
            throws Exception {

        JSONObject result = new JSONObject();

        String fileName = null;

        for (MultipartFile file : files) {
            // 读取数据
            try {
                fileName = file.getOriginalFilename();

                Workbook workbook = WorkbookFactory.create(file.getInputStream());

                Sheet sheetOne = workbook.getSheetAt(0);

                checkReportService.importExcel(sheetOne);
                result.put("msg", "导入成功");
            } catch (Exception e) {
                e.printStackTrace();
                result.put("msg", fileName + e.getMessage());
                return result;
            }
        }
        return result;

    }

    @RequestMapping(value = "/uploadReportAgain", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject uploadReportAgain(@RequestParam("files") List<MultipartFile> files, HttpServletRequest request)
            throws Exception {

        JSONObject result = new JSONObject();
        String fileName = null;
        try {

            // String result = "redirect:main";
            int permittedRole = 1;
            if (!authService.isLoggedin(request)) {
                // result = "redirect:/";
            } else if (!authService.isPermitted(request, permittedRole)) {
                // result = "redirect:nopermissions";
            } else if (!files.isEmpty()) {
                // String ctxPath =
                // request.getSession().getServletContext().getRealPath("");
                String ctxPath = File.separator + "upload" + File.separator + "third" + File.separator;
                int total = files.size();
                int current = 0;
                Iterator<MultipartFile> it = files.iterator();
                while (it.hasNext()) {
                    current++;
                    MultipartFile file = it.next();
                    fileName = file.getOriginalFilename();
                    System.out.println("正在解析第 " + current + " / " + total + " 份报告: " + file.getOriginalFilename());
                    checkReportService.uploadAndSaveReport(file.getOriginalFilename(), file,
                            authService.getUserRealName(request), ctxPath);
                }

            }
            result.put("msg", "导入成功");
            result.put("status", true);
            result.put("result", true);
            return result;
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            result.put("msg", "导入失败，请查看文件" + fileName + "的格式是否正确");
            result.put("result", false);
            result.put("status", false);
            return result;
        }
    }

    @RequestMapping(value = "/uploadRiskLevel", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject uploadRiskLevel(@RequestParam("files") List<MultipartFile> files, HttpServletRequest request)
            throws Exception {

        JSONObject result = new JSONObject();

        try {
            for (MultipartFile file : files) {
                checkReportService.uploadRiskLevel(file);
            }
            result.put("result", true);
            result.put("msg", "导入成功");
            return result;
        } catch (NullPointerException e) {

            result.put("result", false);
            result.put("msg", "导入失败，请检查文件内容格式是否正确");
            e.printStackTrace();
            return result;
        } catch (InvalidFormatException e) {

            result.put("result", false);
            result.put("msg", "导入失败，请检查文件内容格式是否正确");
            e.printStackTrace();
            return result;
        } catch (IOException e) {

            result.put("result", false);
            result.put("msg", "导入失败，请检查文件内容格式是否正确");
            e.printStackTrace();
            return result;
        } catch (Exception e) {

            result.put("result", false);
            result.put("msg", "导入失败，请检查文件内容给格式是否正确");
            e.printStackTrace();
            return result;
        }

        /*
         * String result = "redirect:main"; int permittedRole = 1; if
         * (!authService.isLoggedin(request)) { result = "redirect:/"; } else if
         * (!authService.isPermitted(request, permittedRole)) { result =
         * "redirect:nopermissions"; } else if (!files.isEmpty()) {
         * Iterator<MultipartFile> it = files.iterator(); while (it.hasNext()) {
         * MultipartFile file = it.next();
         * checkReportService.uploadRiskLevel(file); } } return result;
         */ }

    @RequestMapping(value = { "/fetchReport/{reportNum}" }, method = RequestMethod.GET)
    public ResponseEntity<InputStreamResource> fetchReportFile(@PathVariable("reportNum") String reportNum,
            HttpServletResponse response, HttpServletRequest request) throws IOException {

        HttpHeaders headers = new HttpHeaders();
        reportNum = "天消" + reportNum;
        String filePath = checkReportService.getReportURL(reportNum);
        int permittedRole = 1;
        if (authService.isLoggedin(request) && filePath != null && !filePath.equals("")
                && authService.isPermitted(request, permittedRole)) {
            FileSystemResource file = new FileSystemResource(filePath);
            if (file.exists()) {
                headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
                headers.add("Content-Disposition", String.format("inline; filename=\"%s\"",
                        URLEncoder.encode(checkReportService.getOriginalName(reportNum), "UTF-8")));
                // headers.add("Content-Disposition", String.format("inline;
                // filename=\"%s\"",
                // checkReportService.getOriginalName(reportNum), "UTF-8"));
                headers.add("Pragma", "no-cache");
                headers.add("Expires", "0");
                String originalName = checkReportService.getOriginalName(reportNum);
                String encode = URLEncoder.encode(checkReportService.getOriginalName(reportNum), "UTF-8");
                return ResponseEntity.ok().headers(headers).contentLength(file.contentLength())
                        .contentType(MediaType.parseMediaType("application/pdf"))
                        .body(new InputStreamResource(file.getInputStream()));
            }
        }

        return ResponseEntity.notFound().headers(headers).build();
    }

    @RequestMapping(value = "/getReportPage", method = RequestMethod.GET)
    public String getReport() {
        return "report/getReportPage";
    }

    @RequestMapping(value = "/showAbstractReportPage", method = RequestMethod.GET)
    public String reportAbstract(HttpServletRequest request) {
        String result = "report/showAbstractReportPage";
        int permittedRole = 1;
        if (!authService.isLoggedin(request)) {
            result = "redirect:/";
        } else if (!authService.isPermitted(request, permittedRole)) {
            result = "redirect:nopermissions";
        }
        return result;
    }

    /**
     * @createDate 2017年4月25日下午4:56:04
     * @author wangzhiwang
     * @param request
     * @return
     * @throws DocumentException
     * @throws FileNotFoundException
     * @description 下载业主报告，打印成pdf
     */
    @RequestMapping(value = "/downReport", method = RequestMethod.GET)
    public void downReport(HttpServletRequest request, HttpServletResponse response,
            @RequestParam(required = true) String reportNum) throws DocumentException, FileNotFoundException {

        JSONObject abstractReportInfo = checkReportService.getAbstractReportInfo(reportNum);

        String fileName = abstractReportInfo.getString("projectName") + ".pdf";
        String userAgent = request.getHeader("user-agent");

        try {
            if (userAgent != null && userAgent.toLowerCase().indexOf("trident") > -1) {// IE
                                                                                       // Browser
                fileName = URLEncoder.encode(fileName, "UTF-8");
            } else {// Firefox, Chrome etc.
                fileName = new String(fileName.getBytes("UTF-8"), "ISO-8859-1");
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        try {
            ServletOutputStream os = response.getOutputStream();
            response.setContentType("application/pdf");
            // 1.新建document对象
            // 第一个参数是页面大小。接下来的参数分别是左、右、上和下页边距。
            Document document = new Document(PageSize.A4, 50, 50, 100, 100);
            // Document document = new Document(PageSize.A4.rotate());
            PdfWriter.getInstance(document, os);

            // 设置字体
            BaseFont bfChinese = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
            com.itextpdf.text.Font FontChinese24 = new com.itextpdf.text.Font(bfChinese, 24,
                    com.itextpdf.text.Font.BOLD);
            com.itextpdf.text.Font FontChinese18 = new com.itextpdf.text.Font(bfChinese, 18,
                    com.itextpdf.text.Font.BOLD);
            com.itextpdf.text.Font FontChinese16 = new com.itextpdf.text.Font(bfChinese, 16,
                    com.itextpdf.text.Font.BOLD);
            com.itextpdf.text.Font FontChinese12 = new com.itextpdf.text.Font(bfChinese, 12,
                    com.itextpdf.text.Font.NORMAL);
            com.itextpdf.text.Font FontChinese11Bold = new com.itextpdf.text.Font(bfChinese, 11,
                    com.itextpdf.text.Font.BOLD);
            com.itextpdf.text.Font FontChinese11 = new com.itextpdf.text.Font(bfChinese, 11,
                    com.itextpdf.text.Font.ITALIC);
            com.itextpdf.text.Font FontChinese11Normal = new com.itextpdf.text.Font(bfChinese, 11,
                    com.itextpdf.text.Font.NORMAL);

            document.open();
            // test
            Paragraph pg_reportNum = new Paragraph("报告编号:" + abstractReportInfo.getString("reportNum"), FontChinese12);
            document.add(pg_reportNum);
            Paragraph pg_bt = new Paragraph("天河区第三方消防设施检测危险源分析报告", FontChinese24);
            pg_bt.setAlignment(Element.ALIGN_CENTER);
            document.add(pg_bt);

            document.add(new Chunk(abstractReportInfo.getString("projectName") + ":", FontChinese12));
            document.add(Chunk.NEWLINE);

            String content = "            兹有我办委托第三方技术服务机构于" + abstractReportInfo.getString("reportDate")
                    + "对贵单位开展火灾隐患排查及危险源分析，感谢贵单位对天河区消防安全工作的支持与配合！经现场专业化信息采集及后期严谨分析，贵单位消防安全危险等级分析结果为：";
            document.add(new Paragraph(content, FontChinese12));

            document.add(Chunk.NEWLINE);

            PdfPTable pt01 = new PdfPTable(1);
            // BaseColor lightGrey01 = new BaseColor(0xCC,0xCC,0xCC);
            int widthpt01[] = { 2 };
            pt01.setWidths(widthpt01);
            PdfPCell cell01 = new PdfPCell(new Paragraph("登记来源", FontChinese11Bold));
            BaseColor bc = new BaseColor(102, 204, 255);
            cell01.setBackgroundColor(bc);

            // 表格高度
            // cell01.setFixedHeight(25);
            // 水平居中
            cell01.setHorizontalAlignment(Element.ALIGN_CENTER);
            // 垂直居中
            cell01.setHorizontalAlignment(Element.ALIGN_MIDDLE);
            // 边框颜色
            // cell01.setBorderColor(lightGrey01);
            // 去掉左右边框
            // cell01.disableBorderSide(8);

            pt01.addCell(cell01);
            document.add(pt01);

            document.add(new Chunk("            更多详情请关注微信公众号“天河防火墙GZ”，或扫下方二维码，获取更多信息。", FontChinese12));
            document.add(Chunk.NEWLINE);
            document.add(
                    new Chunk(
                            "            详情请在电脑浏览器地址栏输入：http://xf.ggkbigdata.com:6001/third/getReportPage，在打开页面输入提取码：“"
                                    + abstractReportInfo.getString("reportNum") + "”及责任人手机查看消防设施检测危险源分析报告。",
                            FontChinese12));
            document.add(Chunk.NEWLINE);

            response.setContentType("application/octet-stream; charset=UTF-8");
            response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");

            document.close();
            os.flush();
            os.close();

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        /*
         * // 2.建立一个书写器(Writer)与document对象关联，通过书写器(Writer)可以将文档写入到磁盘中。 // 创建
         * PdfWriter 对象 第一个参数是对文档对象的引用，第二个参数是文件的实际名称，在该名称中还会给出其输出路径。 //PdfWriter
         * writer = PdfWriter.getInstance(document, new
         * FileOutputStream("C:\\ITextTest.pdf"));
         * 
         * // 3.打开文档 document.open();
         * 
         * // 4.向文档中添加内容 // 通过 com.lowagie.text.Paragraph
         * 来添加文本。可以用文本及其默认的字体、颜色、大小等等设置来创建一个默认段落 document.add(new
         * Paragraph("报告编号:"+abstractReportInfo.getString("fetchCode")));
         * document.add(new Paragraph("天河区第三方消防设施检测危险源分析报告")); document.add(new
         * Paragraph("ANALYSIS REPORT")); document.add(new
         * Paragraph(abstractReportInfo.getString("projectName")+":")); String
         * content = "兹有我办委托第三方技术服务机构于 "
         * +abstractReportInfo.getString("projectName")+
         * "对贵单位开展火灾隐患排查及危险源分析，感谢贵单位对天河区消防安全工作的支持与配合！经现场专业化信息采集及后期严谨分析，贵单位消防安全危险等级分析结果为：";
         * document.add(new Paragraph(content)); document.add(new
         * Paragraph("更多详情请关注微信公众号“天河防火墙GZ”，或扫下方二维码，获取更多信息。")); document.add(new
         * Paragraph(
         * "详情请在电脑浏览器地址栏输入：http://xf.ggkbigdata.com:6001/third/getReportPage，在打开页面输入提取码：“"
         * +abstractReportInfo.getString("reportNum")+"”及责任人手机查看消防设施检测危险源分析报告。")
         * ); document.add(new Paragraph("天河区消防安全委员会")); document.add(new
         * Paragraph("广州市公安局天河区分局"));
         * 
         * Date date = new Date();
         * 
         * document.add(new Paragraph(date+""));
         */
        // document.add(new Paragraph("Some more text on the first page with
        // different color and font type.",
        // FontFactory.getFont(FontFactory.COURIER, 14F, Font.BOLD, new
        // Color(255, 150, 200))));

    }

    public void writeSimplePdf() throws Exception {

        // 1.新建document对象
        // 第一个参数是页面大小。接下来的参数分别是左、右、上和下页边距。
        Document document = new Document(PageSize.A4, 50, 50, 50, 50);

        // 2.建立一个书写器(Writer)与document对象关联，通过书写器(Writer)可以将文档写入到磁盘中。
        // 创建 PdfWriter 对象 第一个参数是对文档对象的引用，第二个参数是文件的实际名称，在该名称中还会给出其输出路径。
        PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream("C:\\ITextTest.pdf"));

        // 3.打开文档
        document.open();

        // 4.向文档中添加内容
        // 通过 com.lowagie.text.Paragraph 来添加文本。可以用文本及其默认的字体、颜色、大小等等设置来创建一个默认段落
        document.add(new Paragraph("First page of the document."));
        // document.add(new Paragraph("Some more text on the first page with
        // different color and font type.",
        // FontFactory.getFont(FontFactory.COURIER, 14F, Font.BOLD, new
        // Color(255, 150, 200))));
        document.add(new Paragraph("Some more text on the  first page with different color and font type.",
                FontFactory.getFont(FontFactory.COURIER)));

        // 5.关闭文档
        document.close();
    }

    public void writeCharpter() throws Exception {

        // 新建document对象 第一个参数是页面大小。接下来的参数分别是左、右、上和下页边距。
        Document document = new Document(PageSize.A4, 20, 20, 20, 20);

        // 建立一个书写器(Writer)与document对象关联，通过书写器(Writer)可以将文档写入到磁盘中。
        PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream("c:\\ITextTest.pdf"));

        // 打开文件
        document.open();

        // 标题
        document.addTitle("Hello mingri example");

        // 作者
        document.addAuthor("wolf");

        // 主题
        document.addSubject("This example explains how to add metadata.");
        document.addKeywords("iText, Hello mingri");
        document.addCreator("My program using iText");

        // document.newPage();
        // 向文档中添加内容
        document.add(new Paragraph("\n"));
        document.add(new Paragraph("\n"));
        document.add(new Paragraph("\n"));
        document.add(new Paragraph("\n"));
        document.add(new Paragraph("\n"));
        document.add(new Paragraph("First page of the document."));
        document.add(new Paragraph("First page of the document."));
        document.add(new Paragraph("First page of the document."));
        document.add(new Paragraph("First page of the document."));
        // document.add(new Paragraph("Some more text on the first page with
        // different color and font type.",
        // FontFactory.getFont(FontFactory.defaultEncoding, 10, Font.BOLD, new
        // Color(0, 0, 0))));
        document.add(new Paragraph("Some more text on the first page with different color and font type.",
                FontFactory.getFont(FontFactory.COURIER)));
        // Paragraph title1 = new Paragraph("Chapter 1",
        // FontFactory.getFont(FontFactory.HELVETICA, 18, Font.BOLDITALIC, new
        // Color(0, 0, 255)));
        Paragraph title1 = new Paragraph("Chapter 1", FontFactory.getFont(FontFactory.COURIER));

        // 新建章节
        Chapter chapter1 = new Chapter(title1, 1);
        chapter1.setNumberDepth(0);
        // Paragraph title11 = new Paragraph("This is Section 1 in Chapter 1",
        // FontFactory.getFont(FontFactory.HELVETICA, 16, Font.BOLD, new
        // Color(255, 0, 0)));
        Paragraph title11 = new Paragraph("This is Section 1 in Chapter 1", FontFactory.getFont(FontFactory.COURIER));
        Section section1 = chapter1.addSection(title11);
        Paragraph someSectionText = new Paragraph("This text comes as part of section 1 of chapter 1.");
        section1.add(someSectionText);
        someSectionText = new Paragraph("Following is a 3 X 2 table.");
        section1.add(someSectionText);
        document.add(chapter1);

        // 关闭文档
        document.close();
    }

    @RequestMapping("/showDetailReportPage")
    public ModelAndView frequentBusines(HttpServletRequest request) {
        String returnPath = "redirect:getReportPage";
        HttpSession session = request.getSession();
        String verifyToken = (String) session.getAttribute("ownerToken");
        JSONObject result = checkReportService.getDetailReportInfo(verifyToken);
        if (result.getIntValue("code") == 200) {
            returnPath = "report/showDetailReportPage";
        }
        ModelAndView mv = new ModelAndView(returnPath);
        mv.addObject("result", result);
        return mv;
    }

    @RequestMapping(value = { "/changePassword" }, method = RequestMethod.GET)
    public String changePassword(HttpServletRequest request) {
        String result = "redirect:main";
        int permittedRole = 1;
        if (!authService.isLoggedin(request)) {
            result = "redirect:/";
        } else if (!authService.isPermitted(request, permittedRole)) {
            result = "redirect:nopermissions";
        } else {
            result = "changePass";
        }
        return result;
    }

    @RequestMapping({ "/404" })
    public String pageNotFound() {
        return "errors/404";
    }

    @RequestMapping("/505")
    public String visitError() {
        return "errors/505";
    }

    @RequestMapping("/nopermissions")
    public String NoPermisssions() {
        return "errors/nopermissions";
    }
}
