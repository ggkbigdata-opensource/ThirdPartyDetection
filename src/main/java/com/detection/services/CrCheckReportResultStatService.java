package com.detection.services;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.detection.model.report.entities.CrCheckReportResultStat;
import com.detection.model.user.CrUser;

/**
 * @fileName AuthenticationService.java
 * @author csk
 * @createTime 2017年3月6日 下午2:54:42
 * @version 1.0
 * @function
 */

public interface CrCheckReportResultStatService {

    /**
     * @createDate 2017年6月8日上午9:22:31 
     * @author wangzhiwang
     * @return 
     * @description
     */
    List<CrCheckReportResultStat> findAll();

    /**
     * @createDate 2017年6月8日上午9:24:31 
     * @author wangzhiwang
     * @param reportNums
     * @return 
     * @description
     */
    List<CrCheckReportResultStat> findByReportNums(List<String> reportNums);

    /**
     * @createDate 2017年6月8日上午9:30:05 
     * @author wangzhiwang
     * @return 
     * @description
     */
    List<CrCheckReportResultStat> findGroupByItemCode();

    
}
