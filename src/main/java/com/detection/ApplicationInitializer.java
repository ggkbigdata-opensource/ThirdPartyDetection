package com.detection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.detection.model.user.CrUser;
import com.detection.model.user.UserRepository;
import com.detection.util.EncryptionHelper;

@Component
public class ApplicationInitializer implements CommandLineRunner {

    @Autowired
    private UserRepository userRepo;
    @Value("${defaultUser}")
    private String defaultUser;
    @Value("${defaultPassword}")
    private String defaultPassword;
    
    @Override
    public void run(String... arg0) throws Exception {
        //  Auto-generated method stub
        if(userRepo.findAll().size()==0){
            CrUser initUser = new CrUser();
            initUser.setUserName(defaultUser);
            initUser.setUserPassword(EncryptionHelper.encryptStringByMD5(defaultPassword));
            initUser.setRole(1);
            userRepo.save(initUser);
        }
        System.out.println(">>>>>>>>>>>>>>>>初始化完成...>>>>>>>>>>>>>");
    }

}
