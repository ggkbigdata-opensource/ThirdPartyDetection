package com.detection.services;

import java.util.List;

import com.detection.model.area.Block;

/**
 * @fileName AuthenticationService.java
 * @author csk
 * @createTime 2017年3月6日 下午2:54:42
 * @version 1.0
 * @function
 */

public interface BlockService {

    /**
     * @createDate 2017年6月5日下午10:28:20 
     * @author wangzhiwang
     * @param blockId
     * @return 
     * @description
     */
    Block findById(Long blockId);

    /**
     * @createDate 2017年6月5日下午11:44:02 
     * @author wangzhiwang
     * @return 
     * @description
     */
    List<Block> findAll();

    /**
     * @createDate 2017年6月5日下午11:45:14 
     * @author wangzhiwang
     * @param streetId
     * @return 
     * @description
     */
    List<Block> findByStreetId(Long streetId);

    
    
}
