package com.detection.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.detection.model.area.Street;
import com.detection.model.area.StreetRepository;
import com.detection.services.StreetService;

/**
 * @fileName AuthenticationServiceImpl.java
 * @author csk
 * @createTime 2017年3月6日 下午3:01:28
 * @version 1.0
 * @function
 */
@Service
public class StreetServiceImpl implements StreetService {

    @Autowired
    private StreetRepository streetRepository;

    @Override
    public List<Street> findAll() {
        List<Street> streets = streetRepository.findAll();
        return streets;
    }


}
