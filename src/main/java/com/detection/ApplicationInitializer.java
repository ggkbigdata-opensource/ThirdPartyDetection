package com.detection;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.detection.model.report.entities.CrCheckReport;
import com.detection.model.report.entities.CrCheckReportInfo;
import com.detection.model.user.CrUser;
import com.detection.model.user.UserRepository;
import com.detection.services.CheckReportInfoService;
import com.detection.services.CheckReportService;
import com.detection.util.EncryptionHelper;

@Component
public class ApplicationInitializer implements CommandLineRunner {

    @Autowired
    private UserRepository userRepo;
    @Autowired
    private CheckReportInfoService checkReportInfoService;
    @Autowired
    private CheckReportService checkReportService;
    @Value("${defaultUser}")
    private String defaultUser;
    @Value("${defaultPassword}")
    private String defaultPassword;

    @Override
    public void run(String... arg0) throws Exception {
        // Auto-generated method stub
        if (userRepo.findAll().size() == 0) {
            CrUser initUser = new CrUser();
            initUser.setUserName(defaultUser);
            initUser.setUserPassword(EncryptionHelper.encryptStringByMD5(defaultPassword));
            initUser.setRole(1);
            userRepo.save(initUser);
        }

        //通过危险等级
       /* List<CrCheckReportInfo> infos = checkReportInfoService.findAll();
        int a=1;
        for (CrCheckReportInfo info : infos) {
            CrCheckReport report = checkReportService.findByReportNum(info.getReportNum());
            report.setRiskLevel(info.getRiskLevel());
            checkReportService.updateRiskLevelByReoprtNum(report);
            System.out.println(">>>>>>>>>>>>>>>>"+a+":更新一条数据，reportNum"+info.getReportNum()+"...>>>>>>>>>>>>>");
            a++;
        }*/

        System.out.println(">>>>>>>>>>>>>>>>初始化完成...>>>>>>>>>>>>>");
    }

}
