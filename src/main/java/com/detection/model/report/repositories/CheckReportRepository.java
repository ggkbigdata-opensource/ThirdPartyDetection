package com.detection.model.report.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.detection.model.report.entities.CrCheckReport;

public interface CheckReportRepository extends JpaRepository<CrCheckReport, String> , JpaSpecificationExecutor<CrCheckReport>{

    public List<CrCheckReport> findByFetchCode(String fetchCode);

    @Query(value = "SELECT t.file_path FROM cr_check_report t WHERE t.report_num = ?1", nativeQuery = true)
    public String findFilePathByReportNum(String reportNum);

    @Query(value = "SELECT t.original_name FROM cr_check_report t WHERE t.report_num = ?1", nativeQuery = true)
    public String findOriginalNameByReportNum(String reportNum);

    @Query(value = "SELECT t.report_num FROM cr_check_report t WHERE t.fetch_code = ?1", nativeQuery = true)
    public String findReportNumByFetchCode(String fetchCode);

    @Query(value = "SELECT t.report_num FROM cr_check_report t ", nativeQuery = true)
    public List<String> findAllReportNum();

    @Transactional
    @Modifying()
    @Query(value = "update cr_check_report t set t.street_id = ?2 where t.report_num = ?1", nativeQuery = true)
    public void updateStreet(String reportNum, Long streetId);

    public List<CrCheckReport> findByStreetId(Long id);

    public CrCheckReport findByReportNum(String reportNum);

    @Transactional
    @Modifying()
    @Query(value = "update cr_check_report t set t.risk_level = ?2 where t.report_num = ?1", nativeQuery = true)
    public void updateRiskLevelByReoprtNum(String reportNum, String riskLevel);

    public List<CrCheckReport> findByCompetentDepartmentIsNotNull();

    @Query(value = "select * from cr_check_report t where t.street_id = ?1 and t.competent_department is not null", nativeQuery = true)
    public List<CrCheckReport> findByStreetIdAndCompetentDepartmentIsNotNull(Long streetId);

    @Query(value = "select t.building_type_small from cr_check_report t group by t.building_type_small", nativeQuery = true)
    public List<String> findGroupByBuildingTypeSmall();

    @Transactional
    @Modifying()
    @Query(value = "update cr_check_report t set t.street_id = ?1,t.block_id=?2,t.risk_level=?3 where t.report_num = ?4", nativeQuery = true)
    public void updateStreetAndBlock(Long streetId, Long blockId, String riskLevel,String reportNum);
    
}