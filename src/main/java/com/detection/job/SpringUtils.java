package com.detection.job;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class SpringUtils implements ApplicationContextAware{  
  
    private static ApplicationContext applicationContext;  
      
    @SuppressWarnings("static-access")  
    @Override  
    public void setApplicationContext(ApplicationContext applicationContext)  
            throws BeansException {  
        this.applicationContext = applicationContext;  
          
    }  
  
    public static ApplicationContext getApplicationContext(){  
        return applicationContext;  
    }  
      
    public static Object getBean(String name) throws BeansException{  
        return applicationContext.getBean(name);  
    }  
} 