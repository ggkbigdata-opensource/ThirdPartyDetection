/*
 * File Name：CheckReportServiceImpl.java
 *
 * Copyrighe：copyright@2017 www.ggkbigdata.com. All Rights Reserved
 *
 * Create Time: 2017年2月21日 下午4:37:46
 */
package com.detection.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.detection.config.LevelWeightProperties;
import com.detection.config.RiskLevelBoundary;
import com.detection.model.area.StreetRepository;
import com.detection.model.building.BsBuildingInfoRepository;
import com.detection.model.owner.OwnerUnitRepository;
import com.detection.model.report.entities.CrCheckReportInfo;
import com.detection.model.report.repositories.CheckItemDetailCopyRepository;
import com.detection.model.report.repositories.CheckReportInfoRepository;
import com.detection.model.report.repositories.CheckReportRepository;
import com.detection.model.report.repositories.ReportResultStatCopyRepository;
import com.detection.services.BlockService;
import com.detection.services.CheckReportInfoService;
import com.detection.services.PDFParserService;

@Service
public class CheckReportInfoServiceImpl implements CheckReportInfoService {

    @Autowired
    private CheckReportRepository checkReportRepo;
    @Autowired
    private OwnerUnitRepository ownerUnitRepo;
    @Autowired
    private BlockService blockService;
    @Autowired
    private PDFParserService pdfParser;
    @Autowired
    private LevelWeightProperties weight;
    @Autowired
    private RiskLevelBoundary boundary;
    @Autowired
    private CheckReportInfoRepository checkReportInfoRepo;
    @Autowired
    private StreetRepository streetRepository;
    @Autowired
    private BsBuildingInfoRepository buildingInfoRepository;
    @Autowired
    private ReportResultStatCopyRepository reportResultStatCopyRepository;
    @Autowired
    private CheckItemDetailCopyRepository checkItemDetailCopyRepository;
    //@Autowired
   // private CheckReportAndInfoRepository checkReportAndInfoRepository;

    @Value("${uploadPath}")
    private String uploadPath;

    @Override
    public List<CrCheckReportInfo> findAll() {
         List<CrCheckReportInfo> result = checkReportInfoRepo.findAll();
        return result;
    }

    /* (non-Javadoc)
     * @see com.detection.services.CheckReportInfoService#findByReportNums(java.util.List)
     */
    @Override
    public List<CrCheckReportInfo> findByReportNums(List<String> list) {
        // TODO Auto-generated method stub
        return null;
    }
}
