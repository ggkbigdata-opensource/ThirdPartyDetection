/*
 * File Name：PermissionManagerRestController.java
 *
 * Copyrighe：copyright@2017 www.ggkbigdata.com. All Rights Reserved
 *
 * Create Time: 2017年3月1日 上午9:23:11
 */
package com.detection.controller.rest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;

/**
 *
 * @author lcc (lincc@ggkbigdata.com)
 * @version 1.0, 2017年3月1日 上午9:23:11
 */
@RestController
public class PermissionManagerRestController {

    
    /**
     * @author lcc
     * @version 1.0
     * @function 校验用户权限接口，传入用户的token，返回用户的权限和用户名（code,200），如果对应的token不存在则，code返回201
     * 用户权限：1表示具备查看，所有页面权限，0表示，仅仅具有查看单个页面的权限 
     *
     */
    @RequestMapping(value = {"/verifyToken" }, method = RequestMethod.GET)
    public JSONObject verifyToken(@RequestParam String token) {
        JSONObject obj = new JSONObject();
        obj.put("code", 200);
        obj.put("role", 1);
        obj.put("userName", "lcc");
        return obj;
    }
    
    /**
     * @author lcc
     * @version 1.0
     * @function 用户登录的API，其中loginName为登录名，userPassword前端使用md5加密后传输,
     * 账号密码正确返回code200，账号密码不正确返回code = 201，role=0, userName=null
     * 
     */
    @RequestMapping(value = {"/userLogin" }, method = RequestMethod.GET)
    public JSONObject userLogin(@RequestParam String loginName, String userPassword) {
        JSONObject obj = new JSONObject();
        obj.put("token", "dddsfdsgsddasdggdwbeudygyduw2");
        obj.put("role", 1);
        obj.put("userName", "lcc");
        obj.put("code", 200);
        return obj;
    }
}

