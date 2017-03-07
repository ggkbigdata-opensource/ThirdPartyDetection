package com.detection.services;

/**
 * @fileName AuthenticationService.java
 * @author csk
 * @createTime 2017年3月6日 下午2:54:42
 * @version 1.0
 * @function
 */

public interface AuthenticationService {

    public boolean isPermitted(String token, String role);
    public boolean isTokenValid(String token);
    public boolean isLoggedin();
    
}
