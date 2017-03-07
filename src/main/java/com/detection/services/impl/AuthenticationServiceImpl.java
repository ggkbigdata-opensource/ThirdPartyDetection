package com.detection.services.impl;

import org.springframework.stereotype.Service;

import com.detection.services.AuthenticationService;

/**
 * @fileName AuthenticationServiceImpl.java
 * @author csk
 * @createTime 2017年3月6日 下午3:01:28
 * @version 1.0
 * @function
 */
@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    @Override
    public boolean isPermitted(String token, String role) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean isTokenValid(String token) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean isLoggedin() {
        // TODO Auto-generated method stub
        return false;
    }

}
