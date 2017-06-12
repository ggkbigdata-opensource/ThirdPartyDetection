package com.detection.job;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;

import com.detection.model.building.BsBuildingInfo;
import com.detection.model.building.BsBuildingInfoRepository;
import com.detection.model.report.entities.CrCheckReport;
import com.detection.model.report.repositories.CheckReportRepository;
import com.detection.services.CheckReportService;

/**
 * @createDate 2017年6月12日上午9:50:15
 * @author wangzhiwang
 * @description 每天按时更新建筑概况表和检测报告之间的数据
 */
public class UpdateDataJob {

    CheckReportRepository checkReportRepository;
    CheckReportService checkReportService;

    BsBuildingInfoRepository buildingInfoRepository;

    public void checkReportToBuildingInfo() {

        this.checkReportService = SpringUtils.getApplicationContext().getBean(CheckReportService.class);

        Runnable runnable = new Runnable() {
            public void run() {
                checkReportService.checkReportAndBuildingInfo();
            }
        };
        ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();
        service.scheduleAtFixedRate(runnable, 30, 24*60*60, TimeUnit.SECONDS);

    }
}
