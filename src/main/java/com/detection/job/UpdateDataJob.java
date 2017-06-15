package com.detection.job;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.detection.model.building.BsBuildingInfo;
import com.detection.model.building.BsBuildingInfoRepository;
import com.detection.model.report.entities.CrCheckReport;
import com.detection.model.report.entities.CrCheckReportInfo;
import com.detection.model.report.repositories.CheckReportInfoRepository;
import com.detection.model.report.repositories.CheckReportRepository;

/**
 * @createDate 2017年6月12日上午9:50:15
 * @author wangzhiwang
 * @description 每天按时更新建筑概况表和检测报告之间的数据
 */
@Component
@Configurable
@EnableScheduling
public class UpdateDataJob {

    @Autowired
    CheckReportRepository checkReportRepository;
    @Autowired
    CheckReportInfoRepository checkReportInfoRepository;

    @Autowired
    BsBuildingInfoRepository buildingInfoRepository;

    @Scheduled(cron="0 05 23 ? * *") //每天23点05分执行一次
    public void checkReportToBuildingInfo() {

        /*this.checkReportService = SpringUtils.getApplicationContext().getBean(CheckReportService.class);

        Runnable runnable = new Runnable() {
            public void run() {
                checkReportService.checkReportAndBuildingInfo();
            }
        };
        ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();
        service.scheduleAtFixedRate(runnable, 10, 24*60*60, TimeUnit.SECONDS);*/
        System.out.println("Job---checkReportToBuildingInfo------开始执行");
        List<CrCheckReport> reports = checkReportRepository.findAll();
        Map<String, CrCheckReport> reportMap = new HashMap<String,CrCheckReport>();
        for (CrCheckReport crCheckReport : reports) {
            reportMap.put(crCheckReport.getReportNum(), crCheckReport);
        }
        
        List<BsBuildingInfo> infos = buildingInfoRepository.findAll();
        Map<String, BsBuildingInfo> InfoMap = new HashMap<String,BsBuildingInfo>();
        
        List<CrCheckReportInfo> reportInfos = checkReportInfoRepository.findAll();
        Map<String, CrCheckReportInfo> reportInfoMap = new HashMap<String,CrCheckReportInfo>();
        for (CrCheckReportInfo reportInfo : reportInfos) {
            reportInfoMap.put(reportInfo.getReportNum(), reportInfo);
        }
        
        int i=0;
        
        //保存数据
        System.out.println("Job---checkReportToBuildingInfo------开始更新BsBuildingInfo数据");
        for (BsBuildingInfo info : infos) {
            InfoMap.put(info.getItemNumber(), info);
            CrCheckReport report = reportMap.get(info.getItemNumber());
            if (report!=null&&!"".equals(report)) {
                buildingInfoRepository.update(report.getBuildingTypeBig(),report.getBuildingTypeSmall(),report.getScore(),report.getHeightType(),report.getRiskLevel(),info.getItemNumber());
            }
            System.out.println(i++);
        }
        i=0;
        System.out.println("Job---checkReportToBuildingInfo------开始更新CrCheckReport数据");
        for (CrCheckReport report : reports) {
            Long streetId=null;
            Long blockId=null;
            String riskLevel=null;
            
            
            BsBuildingInfo info = InfoMap.get(report.getReportNum());
            
            CrCheckReportInfo checkReportInfo = reportInfoMap.get(report.getReportNum());
            
            if (info!=null&&!"".equals(info)) {
                streetId=info.getStreetId();
                blockId=info.getBlockId();
            }
            if (checkReportInfo!=null&&!"".equals(checkReportInfo)) {
                riskLevel=checkReportInfo.getRiskLevel();
            }
            checkReportRepository.updateStreetAndBlock(streetId,blockId,riskLevel,report.getReportNum());
            
            System.out.println(i++);
        }
        System.out.println("Job---checkReportToBuildingInfo------执行完毕");

    }
}
