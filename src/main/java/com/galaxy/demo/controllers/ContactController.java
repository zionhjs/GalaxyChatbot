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

import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
@RequestMapping("/contact")
public class ContactController {
    private final static Logger LOGGER = LoggerFactory.getLogger(ChatController.class);

    @RequestMapping(value="/quotation", method= RequestMethod.POST)
    public ServletResponse createUser(@RequestBody QuotationRequest request, ServletResponse response){
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        String email = request.getUserEmail();
        String message_inside = request.getMessageInside();
        String message_outside = request.getMessageOutside();
        LOGGER.info("Quotation For user-email: " + email);
        LOGGER.info("Current Inside Message: " + message_inside);
        LOGGER.info("Current Outside Message: " + message_outside);

        Result result = new Result();
        result.setMessage("Successfully sent Quotation Email to zion@galaxycgi.com");

        try {
            response = ServletResponseUtils.setResponseData(httpResponse, JsonBinderUtil.toJson(result));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return response;
    }
}
