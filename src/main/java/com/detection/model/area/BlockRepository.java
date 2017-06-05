package com.detection.model.area;

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

}
