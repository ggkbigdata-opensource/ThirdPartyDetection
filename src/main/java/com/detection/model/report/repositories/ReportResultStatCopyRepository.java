package com.detection.model.report.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.detection.model.report.entities.CrCheckReportResultStat;
import com.detection.model.report.entities.ReportResultStatCopy;

public interface ReportResultStatCopyRepository extends JpaRepository<ReportResultStatCopy, Integer> {

}
