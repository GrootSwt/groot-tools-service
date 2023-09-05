package com.groot;

import com.groot.business.service.MailService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class MailServiceTest {

    private final MailService mailService;

    @Value("{website.name}")
    private String websiteName;
    @Value("{website.location}")
    private String websiteLocation;

    @Autowired
    public MailServiceTest(final MailService mailService) {
        this.mailService = mailService;
    }

    @Test
    public void sendSimpleMail() {
        mailService.sendSimpleMail(
                "sunwentao@fenrir-tec.com",
                "主题：普通邮件主题",
                "内容：普通邮件内容"
        );
    }

    @Test
    public void sendHTMLMail() {
        mailService.sendHTMLMail(
                "sunwentao@fenrir-tec.com",
                "主题：HTML邮件",
                "<h1>注册成功，可以访问该网站了</h1><p><a href=" + websiteLocation + ">" + websiteName + "</a></p>"
        );
    }
}
