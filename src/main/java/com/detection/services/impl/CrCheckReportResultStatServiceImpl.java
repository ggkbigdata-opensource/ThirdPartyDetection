package com.detection.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.detection.model.report.entities.CrCheckReportResultStat;
import com.detection.model.report.repositories.CrCheckReportResultStatRepository;
import com.detection.services.CrCheckReportResultStatService;

/**
 * @fileName AuthenticationServiceImpl.java
 * @author csk
 * @createTime 2017年3月6日 下午3:01:28
 * @version 1.0
 * @function
 */
@Service
public class CrCheckReportResultStatServiceImpl implements CrCheckReportResultStatService {

    @Autowired
    private CrCheckReportResultStatRepository reportResultStatRepository;

    @Override
    public List<CrCheckReportResultStat> findAll() {
        List<CrCheckReportResultStat> result = reportResultStatRepository.findAll();
        return result;
    }

    @Override
    public List<CrCheckReportResultStat> findByReportNums(List<String> reportNums) {
        List<CrCheckReportResultStat> result = reportResultStatRepository.findByReportNums(reportNums);
        return result;
    }

    @Override
    public List<CrCheckReportResultStat> findGroupByItemCode() {
        List<CrCheckReportResultStat> result = reportResultStatRepository.findGroupByItemCode();
        return result;
    }

}
