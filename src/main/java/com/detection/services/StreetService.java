package com.detection.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.detection.model.area.Block;
import com.detection.model.area.BlockRepository;
import com.detection.model.area.Street;
import com.detection.model.area.StreetRepository;


@Service
public interface StreetService  {

    /**
     * @createDate 2017年6月5日下午11:37:28 
     * @author wangzhiwang
     * @return 
     * @description
     */
    List<Street> findAll();


}
