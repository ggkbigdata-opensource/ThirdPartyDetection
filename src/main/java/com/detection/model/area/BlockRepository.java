package com.detection.model.area;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;


/**
 * @fileName BsMainFunctionRepository.java
 * @author csk
 * @createTime 2017年3月28日 下午12:48:35
 * @version 1.0
 * @function
 */

public interface BlockRepository extends JpaRepository<Block, Long>, JpaSpecificationExecutor<Block> {

    Block findByName(String name);

    Block findById(Long id);

    /**
     * @createDate 2017年6月5日下午11:46:27 
     * @author wangzhiwang
     * @param streetId
     * @return 
     * @description
     */
    List<Block> findByStreetId(Long streetId);

}
