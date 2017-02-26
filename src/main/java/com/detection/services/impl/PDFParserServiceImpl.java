/*
 * File Name：PDFParserServiceImpl.java
 *
 * Copyrighe：copyright@2017 www.ggkbigdata.com. All Rights Reserved
 *
 * Create Time: 2017年2月21日 下午3:12:26
 */
package com.detection.services.impl;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.stereotype.Service;

import com.detection.model.pdfparse.Cover;
import com.detection.model.pdfparse.ListResult;
import com.detection.model.pdfparse.PDFParserResult;
import com.detection.model.pdfparse.Result;
import com.detection.services.PDFParserService;


@Service
public class PDFParserServiceImpl implements PDFParserService {
    
    static boolean isDebug = false;
    
    @Override
    public PDFParserResult parse_test(File pdfFile) throws IOException {
    	PDDocument pdfDocument = PDDocument.load(pdfFile);
    	PDFParserResult returnObj = new PDFParserResult();
    	PDFTextStripper stripper=new PDFTextStripper();
        stripper.setSortByPosition(false);
        stripper.setStartPage(126);
        stripper.setEndPage(126);
        String paragraph = stripper.getText(pdfDocument);
        {
        	System.out.println(paragraph);
        	System.out.println("------------------------------------------------------------");
	        List<Result> rs = processOnThirdParagraph(paragraph, returnObj);
	        returnObj.setThirdPart(rs);
        }
        return returnObj;
    }

    @Override
    public PDFParserResult parse(File pdfFile) throws IOException {
    	PDDocument pdfDocument = PDDocument.load(pdfFile);
    	PDFParserResult returnObj = new PDFParserResult();
    	int lastPage = pdfDocument.getNumberOfPages();
    	System.out.println("Last page="+lastPage);
    	PDFTextStripper stripper=new PDFTextStripper();
        stripper.setSortByPosition(true);
        stripper.setStartPage(1);
        stripper.setEndPage(lastPage);
        String allText = stripper.getText(pdfDocument);
        int sIndex = 0;
        int eIndex = 0;
        {
	        String end = "建筑消防设施检测报告";
	        eIndex = allText.indexOf(end);
	        String paragraph = allText.substring(0, eIndex);
	        Cover cover = null;
            try {
                cover = this.processOnCover(paragraph);
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
	        returnObj.setCover(cover);
	        returnObj.setReportNum(cover.getReportNum());
        	
        }
        {
	        String start = "单项评定结果";
	        String end = "检测结论说明";
	        sIndex = allText.indexOf(start)+start.length();
	        eIndex = allText.indexOf(end);
	        String paragraph = allText.substring(sIndex, eIndex);
	        List<Result> rs = null;
            try {
                rs = processOnFirstParagraph(paragraph);
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
	        returnObj.setFirstPart(rs);
        }
        {
	        String start = "检测结论说明";
	        String end = "检测情况统计表";
	        sIndex = allText.indexOf(start)+start.length();
	        eIndex = allText.indexOf(end);
	        String paragraph = allText.substring(sIndex, eIndex);
	        String rs = null;
            try {
                rs = processOnSecondParagraph(paragraph);
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
	        returnObj.setSecondPart(rs);
	        returnObj.getCover().setReportConclusion(rs);
        }
        {
	        String start = "检测情况统计表";
	        String end = "消防设施检测不符合规范要求项目";
	        sIndex = allText.indexOf(start)+start.length();
	        eIndex = allText.indexOf(end);
	        String paragraph = allText.substring(sIndex, eIndex);
	        List<Result> rs = null;
            try {
                rs = processOnThirdParagraph(paragraph, returnObj);
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
	        returnObj.setThirdPart(rs);
        }
        {
	        String start = "消防设施检测不符合规范要求项目";
	        String end = "消防设备登记";
	        sIndex = allText.indexOf(start)+start.length();
	        eIndex = allText.indexOf(end);
	        String paragraph = allText.substring(sIndex, eIndex);
	        List<ListResult> rs = null;
            try {
                rs = processOnForthParagraph(paragraph);
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
	        returnObj.setForthPart(rs);
        }
        {
	        String start = "消防设备登记";
	        sIndex = allText.indexOf(start)+start.length();
	        String paragraph = allText.substring(sIndex);
	        List<ListResult> rs = null;
            try {
                rs = processOnFifthParagraph(paragraph);
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
	        returnObj.setFifthPart(rs);
        }
        return returnObj;
    }

    @Override
    public Cover processOnCover(String paragraph) {
    	Cover cover = new Cover();
    	String[] lines = paragraph.split("\r\n");

		Pattern projectName = Pattern.compile("^项目名称:\\s*(.*)\\s*$");
		Pattern projectAddress = Pattern.compile("^项目地址:\\s*(.*)\\s*$");
		Pattern agentName = Pattern.compile("^委托单位:\\s*(.*)\\s*$");
		Pattern qaName = Pattern.compile("^检测单位:\\s*(.*)\\s*$");
		Pattern reportNum = Pattern.compile("^天消\\s*(.*)\\s*$");
		Pattern qaAddress = Pattern.compile("^检测单位地址:\\s*(.*)\\s*$");
		Pattern contactTel = Pattern.compile("^电\\s+话:\\s*(.*)\\s*$");
		Pattern contactFax = Pattern.compile("^传\\s+真:\\s*(.*)\\s*$");
		Pattern contactPostcode = Pattern.compile("^邮\\s+编:\\s*(.*)\\s*$");

		int projectNameLine = 0;
		int projectAddrLine = 0;
    	for(int i=0;i<lines.length;i++){
    		String line = lines[i];
    		if(isDebug) System.out.println("LINE=>"+line);
    		Matcher m = projectName.matcher(line);
    		if(m.find()){
    			cover.setProjectName(m.group(1));
    			projectNameLine = i;
    			continue;
    		}
    		m = projectAddress.matcher(line);
    		if(m.find()){
    			cover.setProjectAddress(m.group(1));
    			projectAddrLine = i;
    			continue;
    		}
    		m = agentName.matcher(line);
    		if(m.find()){
    			cover.setAgentName(m.group(1));
    			continue;
    		}
    		m = qaName.matcher(line);
    		if(m.find()){
    			cover.setQaName(m.group(1));
    			continue;
    		}
    		m = reportNum.matcher(line);
    		if(m.find()){
    			cover.setReportNum(m.group(1));
    			continue;
    		}
    		m = qaAddress.matcher(line);
    		if(m.find()){
    			cover.setQaAddress(m.group(1));
    			continue;
    		}
    		m = contactTel.matcher(line);
    		if(m.find()){
    			cover.setContactTel(m.group(1));
    			continue;
    		}
    		m = contactFax.matcher(line);
    		if(m.find()){
    			cover.setContactFax(m.group(1));
    			continue;
    		}
    		m = contactPostcode.matcher(line);
    		if(m.find()){
    			cover.setContactPostcode(m.group(1));
    			continue;
    		}
    	}
    	// 修改项目名称
    	if(projectAddrLine - projectNameLine > 1){
    	    StringBuffer sb = new StringBuffer(cover.getProjectName());
    	    for(int j = projectNameLine + 1 ; j < projectAddrLine; j++){
    	        sb.append(lines[j].trim());
    	    }
    	    cover.setProjectName(sb.toString());
    	}
    	
    	return cover;
    }
    
    @Override
    public List<ListResult> processOnFifthParagraph(String paragraph) {
    	List<ListResult> rs = new ArrayList<ListResult>();
    	String[] lines = paragraph.split("\n");
    	String label = "";
    	List<String> nums = new ArrayList<String>();
    	List<String> strings = new ArrayList<String>();
		Pattern labpat = Pattern.compile("^\\d+  .*$");
		Pattern txtpat = Pattern.compile("^(\\d+) ([^ ].*)$");
		Pattern skppat = Pattern.compile("^\\s*第 \\d+ 页\\s*.*$");
    	int position = 1;//1=label line, 2=start text line, 3=continue text line.
		String num = "", text = "";

    	for(int i=0;i<lines.length;i++){
    		String line = lines[i];
    		if(isDebug) System.out.println("LINE["+position+"]=>"+line);
    		{
    			Matcher m = skppat.matcher(line);
    			if(m.find()){
    				if(isDebug) System.out.println("    跳过");
    				continue;
    			}
    		}
    		if(position==1){
    			Matcher m = labpat.matcher(line);
    			if(m.find()){
    				label = m.group(0);
    				position = 2;
    				if(isDebug) System.out.println("  编号=>"+label);
    			}
    		}else if(position==2){
    			Matcher m = txtpat.matcher(line);
    			if(m.find()){
    				num = m.group(1);
    				text = m.group(2);
    				position = 3;
    				if(isDebug) System.out.println("    S_F>>"+num+":"+text);
    			}
    		}else if(position==3){
    			Matcher m = txtpat.matcher(line);
    			if(m.find()){
    				if(!"".equals(text)){
        	    		nums.add(num);
        	    		strings.add(text);
        	    		if(isDebug) System.out.println("    END=>"+num+":"+text);
    				}
    				num = m.group(1);
    				text = m.group(2);
    				if(isDebug) System.out.println("    S_N>>"+num+":"+text);
    			}else{
        			Matcher m2 = labpat.matcher(line);
    				if(m2.find()){
        				if(!"".equals(text)){
            	    		nums.add(num);
            	    		strings.add(text);
            	    		if(isDebug) System.out.println("    END=>"+num+":"+text);
        				}
	    				position = 1;
	    				ListResult r = new ListResult(label, nums, strings);
	    				rs.add(r);
	    		    	nums = new ArrayList<String>();
	    		    	strings = new ArrayList<String>();
	    				label = m2.group(0);
	    				position = 2;
	    				if(isDebug) System.out.println("  编号=>"+label);
    				}else{
    					text += line;
    					if(isDebug) System.out.println("    MID>>"+num+":"+text);
    				}
    			}
    		}
    	}
		if(!"".equals(text)){
    		nums.add(num);
    		strings.add(text);
    		if(isDebug) System.out.println("    END=>"+num+":"+text);
			ListResult r = new ListResult(label, nums, strings);
			rs.add(r);
		}
    	return rs;
    }

    @Override
    public List<ListResult> processOnForthParagraph(String paragraph) {
    	List<ListResult> rs = new ArrayList<ListResult>();
    	String[] lines = paragraph.split("\n");
    	String engNum = "";
    	String label = "";
    	List<String> strings = new ArrayList<String>();
		Pattern engpat = Pattern.compile("工程编号\\s*:\\s*(\\d+)");
		Pattern labpat = Pattern.compile("^(\\d+\\.\\d+\\.\\d+).*$");
		Pattern chkpat = Pattern.compile("^\\d{10} .*$");
		Pattern skppat = Pattern.compile("^\\s*第 \\d+ 页\\s*.*$");
		String checkStr = "以下是不符合规范要求的检测点";
    	int position = 0;//0=engineering number, 1=label,2=start point, 3=repeat to retrieve data

    	for(int i=0;i<lines.length;i++){
    		String line = lines[i];
    		if(isDebug) System.out.println("LINE["+position+"]=>"+line);
    		{
    			Matcher m = skppat.matcher(line);
    			if(m.find()){
    				continue;
    			}
    		}
    		if(position==0){
    			Matcher m = engpat.matcher(line);
    			if(m.find()){
    				engNum = m.group(1);
    				position = 1;
    				if(isDebug) System.out.println("  工程编号=>"+engNum);
    			}
    		}else if(position==1){
    			Matcher m = labpat.matcher(line);
    			if(m.find()){
    				label = m.group(1);
    				position = 2;
    				if(isDebug) System.out.println("  项目编号=>"+label);
    			}
    		}else if(position==2){
    			if(line.indexOf(checkStr)>=0){
    				position = 3;
    			}
    		}else if(position==3){
    			Matcher m = chkpat.matcher(line);
    			if(m.find()){
    				String string = m.group(0);
    				strings.add(string);
    				if(isDebug) System.out.println("  ----->"+string);
    			}else{
    				ListResult r = new ListResult(label, strings);
    				rs.add(r);
    				position = 1;
    				strings = new ArrayList<String>();
    			}
    		}
    	}
    	
    	return rs;
    }

    @Override
    public List<Result> processOnThirdParagraph(String paragraph,
            PDFParserResult returnObj) {
    	String level = "", label = "", name = "", value1 = "", value2 = "";
		Pattern valuePatt = Pattern.compile("\\s[\\d]{1,}\\s\\s[\\d]{1,}");
		
		List<Result> rs = new ArrayList<Result>();
		// 匹配换行+数字.组合
		Pattern linePat = Pattern.compile("\r\n[\\d]{1,}[\\.]{0,}");
		Matcher lineMatcher = linePat.matcher(paragraph);
		int start = 0;
		int end = 1;
		String tempStr = "";
		// 获取匹配index，截取字段，分别解析
		while(lineMatcher.find()){
		    start = lineMatcher.start();
		    if(lineMatcher.find()){
		        end = lineMatcher.start();
		    }else{
		        end = paragraph.length()-1;
		    }
		    tempStr = paragraph.substring(start,end);
		    //System.out.println(tempStr);
		    
		    if(null !=tempStr && "".equals(tempStr)){
		        continue;
		    }
		    // set label
		    label = tempStr.substring(0, tempStr.indexOf(" "));
		    tempStr = tempStr.replace(label, "");
		    String[] lines = tempStr.split("\r\n");
		    String line = null;
		    StringBuffer sb = new StringBuffer();
		    boolean flag = false;
		    for(int i=0;i<lines.length;i++){
		        line = lines[i];
		        
		        if(line == null || "".equals(line)){
		            continue;
		        }
		        
		        // 处理脏数据
		        if(line.contains("项目编号") || line.contains("天河区开展第三方消防设施检测项目技术咨询报告") || line.contains(returnObj.getCover().getReportNum())){
		            continue;
		        }
		        // set level
		        flag = false;
		        if(line.startsWith("A")||line.contains(" A")){
		           level = "A"; 
		           flag = true;
		        }else if(line.startsWith("B")||line.contains(" B")){
		            level = "B";
		            flag = true;
		        }else if(line.startsWith("C")||line.contains(" C")){
                    level = "C";
                    flag = true;
                }
		        if(flag){
		            line = line.replace(level, "");
		            line = line.trim();
		            sb.append(line);
		            continue;
		        }
		        // set value1,value2
		        Matcher m = valuePatt.matcher(line);
                if(m.find()){
                    value1 = String.valueOf(m.group().replaceAll(" ", "").charAt(0));// 获取第一位数字
                    value2 = String.valueOf(m.group().replaceAll(" ", "").charAt(1));// 获取第二位数字
                    line = line.replace(m.group(), "");
                    line = line.trim();
                    sb.append(line);
                    continue;
                }
                line = line.trim();
		        sb.append(line);
		    }
		    name = sb.toString();
		    System.out.println(name);
		    rs.add(new Result(label, name, level, value1, value2));
		}
    	return rs;
    }
    
    @Override
    public String processOnSecondParagraph(String paragraph) {
    	int lastIndex = paragraph.trim().lastIndexOf("\n");
    	if(lastIndex<1) return "";
    	return paragraph.substring(0, lastIndex);
    }

    @Override
    public List<Result> processOnFirstParagraph(String paragraph) {
        List<Result> rs = new ArrayList<Result>();
        Pattern linePat = Pattern.compile("\r\n[\\d]{1,}[\\s]{1}");
        Matcher lineMatcher = linePat.matcher(paragraph);
        int start = 0;
        int end = 1;
        String tempStr = "";
        Result result = null;
        List<String> strs = null;
        Pattern labelPat = Pattern.compile("\r\n[\\d]{1,}[\\s]{1}");
        Pattern namePat = Pattern.compile("[A|B|C]{1}[\\s]{2}[\\d]{1,}[\\s]{2}[\\d]{1,}");
        Matcher tempMatcher = null;
        String tempValue = "";
        String label = "";
        String name = "";
        // 获取匹配index，截取字段，分别解析
        while(lineMatcher.find()){
            strs = new ArrayList<String>();
            start = lineMatcher.start();
            if(lineMatcher.find()){
                end = lineMatcher.start();
            }else{
                end = paragraph.length()-1;
            }
            tempStr = paragraph.substring(start,end);
            //System.out.println(tempStr);
            
            if(null !=tempStr && "".equals(tempStr)){
                continue;
            }
            // set label
            tempMatcher = labelPat.matcher(tempStr);
            while(tempMatcher.find()){
                tempValue = tempMatcher.group();
                tempStr.replace(tempValue, "");
                tempValue.replace("\r\n", "");
                label = tempValue.trim();
                tempValue = "";
            }
            // set level value1 value2
            tempMatcher = namePat.matcher(tempStr);
            while(tempMatcher.find()){
                tempValue = tempMatcher.group();
                tempStr.replace(tempValue, "");
                strs.add(tempValue);
            }
            // set name
            name = tempStr.trim();
            for(String str:strs){
                result = new Result();
                result.setName(name);
                result.setLabel(label);
                String[] arr = str.split("\\s\\s");
                result.setLevel(arr[0]);
                result.setValue1(arr[1]);
                result.setValue2(arr[2]);
                rs.add(result);
            }
        }
    	return rs;
    }
}

