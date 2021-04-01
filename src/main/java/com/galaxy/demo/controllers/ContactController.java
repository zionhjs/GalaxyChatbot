package com.galaxy.demo.controllers;

import com.galaxy.demo.dto.vos.UserVo;
import com.galaxy.demo.error.ResultCode;
import com.galaxy.demo.payloads.QuotationRequest;
import com.galaxy.demo.payloads.Result;
import com.galaxy.demo.payloads.SubscriptionRequest;
import com.galaxy.demo.utils.JsonBinderUtil;
import com.galaxy.demo.utils.ServletResponseUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.mail.*;
import javax.mail.internet.*;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.Properties;

@Controller
@RequestMapping("/contact")
public class ContactController {
    private final static Logger LOGGER = LoggerFactory.getLogger(ChatController.class);

    @RequestMapping(value="/quotation", method= RequestMethod.POST)
    public ServletResponse createUser(@RequestBody QuotationRequest request, ServletResponse response) throws AddressException, MessagingException, IOException {
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        String email = request.getUserEmail();
        String message_inside = request.getMessageInside();
        String message_outside = request.getMessageOutside();
        LOGGER.info("Quotation For user-email: " + email);
        LOGGER.info("Current Inside Message: " + message_inside);
        LOGGER.info("Current Outside Message: " + message_outside);

        // for Sending Email Configuration
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("zion@galaxycgi.com", "Aa005443");
            }
        });

        Message msg = new MimeMessage(session);
        msg.setFrom(new InternetAddress("zion@galaxycgi.com", false));

        msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse("zion@galaxycgi.com"));
        msg.setSubject("GalaxyCGI Client Quotation");
        msg.setContent(message_inside, "text/html");
        msg.setSentDate(new Date());

        MimeBodyPart messageBodyPart = new MimeBodyPart();
        messageBodyPart.setContent("Tutorials point email", "text/html");
        Multipart multipart = new MimeMultipart();
        multipart.addBodyPart(messageBodyPart);
        MimeBodyPart attachPart = new MimeBodyPart();

        // send it!
        Transport.send(msg);

        // return some frontend response back
        Result result = new Result();
        result.setCode(ResultCode.SUCCESS.code());
        result.setMessage("Successfully sent Quotation Email from zion@galaxycgi.com to zion@galaxycgi.com");

        try {
            response = ServletResponseUtils.setResponseData(httpResponse, JsonBinderUtil.toJson(result));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return response;
    }
}
