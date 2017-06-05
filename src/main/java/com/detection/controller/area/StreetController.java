package com.detection.controller.area;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.detection.model.area.Street;
import com.detection.services.OwnerUnitService;
import com.detection.services.StreetService;

/**
 * @fileName OwnerUnitRestController.java
 * @author csk
 * @createTime 2017年3月3日 下午5:59:34
 * @version 1.0
 * @function
 */

@Controller
@RequestMapping("/street")
public class StreetController {
    
    
    @Autowired
    private StreetService streetService;
    
    
    @RequestMapping(value = "/getAll", method = RequestMethod.GET)
    @ResponseBody
    public List<Street> getAll() {
        List<Street> streets = streetService.findAll();
        return streets;
    }

}
