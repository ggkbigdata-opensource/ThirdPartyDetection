package com.detection.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.detection.model.area.Block;
import com.detection.model.area.BlockRepository;
import com.detection.services.BlockService;

/**
 * @fileName AuthenticationServiceImpl.java
 * @author csk
 * @createTime 2017年3月6日 下午3:01:28
 * @version 1.0
 * @function
 */
@Service
public class BlockServiceImpl implements BlockService {

    @Autowired
    private BlockRepository blockRepository;

    @Override
    public Block findById(Long blockId) {
        Block block = blockRepository.findById(blockId);
        return block;
    }

    @Override
    public List<Block> findAll() {
        List<Block> blocks = blockRepository.findAll();
        return blocks;
    }

    @Override
    public List<Block> findByStreetId(Long streetId) {
        List<Block> blocks = blockRepository.findByStreetId(streetId);
        return blocks;
    }

}
