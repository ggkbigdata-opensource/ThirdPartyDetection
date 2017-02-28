package com.detection.controller;

import java.util.Date;
import java.util.List;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.detection.model.owner.OwnerUnit;
import com.detection.model.owner.OwnerUnitRepository;
import com.detection.util.FormCheck;


@Controller
public class DepartmentController {
    static boolean flag = false;
    static String regex = "";
    @Autowired
    private OwnerUnitRepository ownerUnitRepository;
    
    @GetMapping(value = "/registration/addOwnerUnit")
    public String addOwnerUnit(@RequestParam(name = "ownerName") String ownerName,
            @RequestParam(name = "email") String email) throws AddressException, MessagingException {
        OwnerUnit ownerUnit = new OwnerUnit();
        Date current = new Date();
        long currentTimeStamp = current.getTime();
        List<OwnerUnit> findUnit = ownerUnitRepository.findByEmail(email);
        if (!FormCheck.isEmailValid(email)) {
            return "fail";
        }
        if (findUnit.isEmpty()) {
            ownerUnit.setOwnerName(ownerName);
            ownerUnit.setEmail(email);
            ownerUnit.setTimeStamp(currentTimeStamp);
            ownerUnitRepository.save(ownerUnit);
        } else {
            OwnerUnit temp = findUnit.get(0);
            if ((currentTimeStamp - temp.getTimeStamp()) < 15000)
                return "finish";
            else {
                temp.setTimeStamp(currentTimeStamp);
                ownerUnitRepository.save(temp);
            }
        }
        String subject = "项目概况表登记下载";
        String content = "您好," + ownerName + "：" + "\n项目概况表及样表请点击以下链接下载："
                + "\n http://120.25.157.166/regist/profiles.rar" + "\n\n  各业主单位将填写完整的资料（3个工作日内）发至以下公司邮箱："
                + "\n\n  \t广东建筑消防设施检测中心有限公司" + "\n  \t邮箱：thxfxmbgs@163.com   徐小伟：13512774017"
                + "\n\n  \t清大安质消防安全管理质量评价（北京）中心" + "\n  \t邮箱：qdazthxmb@sina.com   张文海：18604608236"
                + "\n\n  \t广东华建电气消防安全检测有限公司" + "\n  \t邮箱：247630634@qq.com   黄志健：18925069535" + "\n\n"
                + "\n\n--------------" + "\n\n天河区消防安全委员会";
        String attachmentPath = "";
        // SendMail(subject,content,attachmentPath,email);
        
        return "finish";
    }

}
