/*
 * File Name：LevelWeightProperties.java
 *
 * Copyrighe：copyright@2017 www.ggkbigdata.com. All Rights Reserved
 *
 * Create Time: 2017年2月21日 上午10:10:38
 */
package com.detection.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 *
 * @author lcc (lincc@ggkbigdata.com)
 * @version 1.0, 2017年2月21日 上午10:10:38
 */
@Component
@ConfigurationProperties(prefix="fire.detection")
public class LevelWeightProperties {
    
    private int levelA;
    private int levelB;
    private int levelC;
    public int getLevelA() {
        return levelA;
    }
    public void setLevelA(int levelA) {
        this.levelA = levelA;
    }
    public int getLevelB() {
        return levelB;
    }
    public void setLevelB(int levelB) {
        this.levelB = levelB;
    }
    public int getLevelC() {
        return levelC;
    }
    public void setLevelC(int levelC) {
        this.levelC = levelC;
    }
}

