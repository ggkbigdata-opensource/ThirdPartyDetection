package com.detection.controller.rest;

import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.detection.model.weight.WeightConfig;
import com.detection.model.weight.WeightConfigRepo;

/**
 * @fileName TestRestController.java
 * @author csk
 * @createTime 2017年3月15日 下午7:13:58
 * @version 1.0
 * @function
 */
@RestController
public class TestRestController {

    @Autowired
    private WeightConfigRepo weightConf;
    
    @RequestMapping("/testWeightInit")
    public String TestWeightInit(@RequestParam String configName){
        String result;
        Pattern pattern = Pattern.compile("^test.*");
        WeightConfig conf;
        if(pattern.matcher(configName).find()){
            conf = new WeightConfig(configName,true);
            result = "success initialized with default value";
        }
        else{
            conf = new WeightConfig(configName,false);
            result = "success initialized without default value";
        }
        weightConf.save(conf);
        return result;
    }
}
