package com.galaxy.demo.controllers;

import com.galaxy.demo.configs.TwilioConfiguration;
import com.galaxy.demo.dao.UserVoDao;
import com.galaxy.demo.dto.vos.UserVo;
import com.galaxy.demo.error.ResultCode;
import com.galaxy.demo.payloads.Result;
import com.galaxy.demo.payloads.SubscriptionRequest;
import com.galaxy.demo.payloads.UnSubScriptionRequest;
import com.galaxy.demo.services.ChannelService;
import com.galaxy.demo.utils.JsonBinderUtil;
import com.galaxy.demo.utils.ServletResponseUtils;
import com.twilio.base.ResourceSet;
import com.twilio.rest.chat.v2.Service;
import com.twilio.rest.chat.v2.service.channel.Message;
import com.twilio.rest.chat.v2.service.channel.Webhook;
import com.twilio.rest.ipmessaging.v2.service.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/uservo")
public class UserController {
    private final static Logger LOGGER = LoggerFactory.getLogger(UserController.class);
    @Autowired
    private UserVoDao userVoDao;

    @Autowired
    private ChannelService channelService;

    @RequestMapping(value="/subscribe", method=RequestMethod.POST)
    public ServletResponse createUser(@RequestBody SubscriptionRequest request, ServletResponse response){
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        String email = request.getUserEmail();
        LOGGER.info("Subscribing For user-email: " + email);
        Result result = new Result();
        if(userVoDao.findUserVoByEmail(email) == null){
            UserVo userVo = new UserVo();
            LOGGER.info("Subscribing For user-email: " + email);
            userVo.setUserEmail(email);
            if(request.getUserNumber() != null){
                String number = request.getUserNumber();
                userVo.setUserNumber(number);
            }
            if(email != null){
                result.setCode(ResultCode.SUCCESS.code());
                result.setMessage(" Subscribed user to our list: " + JsonBinderUtil.toJson(userVo));
                userVoDao.createUserVo(userVo);
            }else{
                result.setCode(ResultCode.FAIL.code());
                result.setMessage(" Must at least provide a user email!");
            }
        }else{
            result.setCode(ResultCode.FAIL.code());
            result.setMessage("The User already been subscribed!");
        }
    
        try {
            response = ServletResponseUtils.setResponseData(httpResponse, JsonBinderUtil.toJson(result));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return response;
    }

    @RequestMapping(value="/alluservo", method=RequestMethod.GET)
    public ResponseEntity getaAllUser(){
        LOGGER.info("fetching all uservos");
        List<UserVo> userVos = null;
        userVos = userVoDao.getUserVos();
        LOGGER.info("uservos: " + userVos.toString());
        return ResponseEntity.ok().body(JsonBinderUtil.toJson(userVos.toString()));
    }

    @RequestMapping(value="/unsubscribe", method=RequestMethod.POST)
    public ServletResponse unsubscribe(@RequestBody UnSubScriptionRequest request, ServletResponse response){
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        String email = request.getUserEmail();
        LOGGER.info("UnSubscribing For user-email: " + email);
        UserVo userVo = userVoDao.findUserVoByEmail(email);
        Result result = new Result();
        new java.util.Timer().schedule(
                new java.util.TimerTask(){
                    @Override
                    public void run(){
                        LOGGER.info("inside the run!~");
                        if(userVo.getChannelSid() != null) removeChannel(userVo);
                        if(userVo.getServiceSid() != null) removeService(userVo);
                        if(userVo.getWebhookSid() != null) removeWebhook(userVo);
                        if(userVo.getId() != null) userVoDao.removeUserVo(userVo.getId());
                        result.setCode(ResultCode.SUCCESS.code());
                        result.setMessage(" removing user from our list: " + JsonBinderUtil.toJson(userVo));
                    }
                },
                1200
        );

        try {
            response = ServletResponseUtils.setResponseData(httpResponse, JsonBinderUtil.toJson(result));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return response;
        // return ResponseEntity.ok().body("SuccessFully unsubscribed " + unSubScriptionRequest.getUserEmail());
    }

//    private void delayUnsub(UserVo userVo){
//        if(userVo.getChannelSid() != null) removeChannel(userVo);
//        if(userVo.getServiceSid() != null) removeService(userVo);
//        if(userVo.getWebhookSid() != null) removeWebhook(userVo);
//        if(userVo.getId() != null) userVoDao.removeUserVo(userVo.getId());
//    }

    private void removeChannel(UserVo userVo){
        Channel channel = Channel.fetcher(userVo.getServiceSid(), userVo.getChannelSid()).fetch();
        channelService.deleteChannel(channel);
        userVo.setChannelSid(null);
        userVoDao.findUserVoAndModify(userVo.getId(), userVo.getUserEmail(), userVo.getUserNumber(), userVo.getTwilioToken(), userVo.getChannelSid(), userVo.getServiceSid(), userVo.getWebhookSid());
        LOGGER.info("Removed channel from userVo: " + userVo.toString());
    }

    private void removeService(UserVo userVo){
        Service.deleter(userVo.getServiceSid());
        userVo.setServiceSid(null);
        userVoDao.findUserVoAndModify(userVo.getId(), userVo.getUserEmail(), userVo.getUserNumber(), userVo.getTwilioToken(), userVo.getChannelSid(), userVo.getServiceSid(), userVo.getWebhookSid());
        LOGGER.info("Removed service from userVo: " + userVo.toString());
    }

    private void removeWebhook(UserVo userVo){
        Webhook.deleter(userVo.getServiceSid(), userVo.getChannelSid(), userVo.getWebhookSid());
        userVo.setWebhookSid(null);
        userVoDao.findUserVoAndModify(userVo.getId(), userVo.getUserEmail(), userVo.getUserNumber(), userVo.getTwilioToken(), userVo.getChannelSid(), userVo.getServiceSid(), userVo.getWebhookSid());
        LOGGER.info("Removed Webhook from userVo: " + userVo.toString());
    }
}


