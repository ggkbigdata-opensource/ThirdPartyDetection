package com.detection.model.report.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.detection.model.report.entities.CrCheckReportResultStat;

public interface CrCheckReportResultStatRepository extends JpaRepository<CrCheckReportResultStat, Integer> {

    /**
     * @createDate 2017年6月8日上午9:25:15 
     * @author wangzhiwang
     * @param reportNums
     * @return 
     * @description
     */
    @Query(value = "SELECT * FROM cr_check_report_result_stat t where t.report_num in ?1", nativeQuery = true)
    List<CrCheckReportResultStat> findByReportNums(List<String> reportNums);

    @Query(value = "SELECT * FROM cr_check_report_result_stat t group by item_code", nativeQuery = true)
    List<CrCheckReportResultStat> findGroupByItemCode();

}
