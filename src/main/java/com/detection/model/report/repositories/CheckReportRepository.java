package com.detection.model.report.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.detection.model.report.entities.CheckReport;

public interface CheckReportRepository extends JpaRepository<CheckReport, String> {
	
}
