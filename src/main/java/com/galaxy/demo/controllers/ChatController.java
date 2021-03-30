package com.galaxy.demo.controllers;

import com.galaxy.demo.configs.TwilioConfiguration;
import com.galaxy.demo.dao.UserVoDao;
import com.galaxy.demo.dto.vos.UserVo;
import com.galaxy.demo.error.ResultCode;
import com.galaxy.demo.payloads.Result;
import com.galaxy.demo.services.ChannelService;
import com.galaxy.demo.utils.JsonBinderUtil;
import com.galaxy.demo.utils.ServletResponseUtils;
import com.twilio.base.ResourceSet;
import com.twilio.rest.chat.v2.service.channel.Message;
import com.twilio.rest.chat.v2.service.channel.Webhook;
import com.twilio.rest.ipmessaging.v2.service.Channel;
import com.twilio.rest.chat.v2.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

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

/***
 * Two useful link for developing this
 * connect autopilot to channel: https://www.twilio.com/docs/autopilot/channels/chat#3-add-autopilot-url-to-channel
 * twilio resources doc: https://www.twilio.com/docs/chat/channels#delete-a-channel
 * full tutorial of building a chat-application: https://www.twilio.com/blog/2018/03/programmable-chat-java-struts-2-app.html
 *
 * The correct way to reference the jar: https://stackoverflow.com/questions/50123202/compiling-maven-project-throws-error-while-using-custom-jar
 */
@Controller
@RequestMapping("/chatbot")
public class ChatController {
    private final static Logger LOGGER = LoggerFactory.getLogger(ChatController.class);
    @Autowired
    TwilioConfiguration twilioConfiguration;

    @Autowired
    private UserVoDao userVoDao;

    @Autowired
    private ChannelService channelService;

    @RequestMapping(value="/connect", method= RequestMethod.POST)
    public ServletResponse connectToChatbot(ServletRequest request, ServletResponse response){
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        String email = httpRequest.getParameter("userEmail");
        LOGGER.info("Conneting to our chat agent! and email is: " + email);
        UserVo userVo = userVoDao.findUserVoByEmail(email);

        if(userVo != null){
            LOGGER.info("uservo exist!");
            Result result = new Result();
            result.setCode(ResultCode.SUCCESS.code());
            result.setMessage("successfully connected the userVo for the provided email");

            // try and get a chennel
            Service service = getService(userVo);
            Channel channel = getChannel(userVo);
            Webhook webhook = getWebhook(userVo);
            LOGGER.info("service: " + service.toString());
            LOGGER.info("channel: " + channel.toString());
            LOGGER.info("webhook: " + webhook.toString());
            // add chatbot to channel -> starts from webhook
            LOGGER.info(" The autopilotUrl for the WebHook is: " + webhook.getConfiguration());
            try{
                response = ServletResponseUtils.setResponseData(httpResponse, JsonBinderUtil.toJson(result));
            }catch(IOException e){
                e.printStackTrace();
            }
        }else {
            LOGGER.error("The provided userEmail is not exist in userVo");
            Result result = new Result();
            result.setCode(ResultCode.FAIL.code());
            result.setMessage("failed to find the userVo for the provided email");
            try {
                response = ServletResponseUtils.setResponseData(httpResponse, JsonBinderUtil.toJson(result));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return response;
    }

    @RequestMapping(value="/disconnect", method=RequestMethod.POST)
    public ServletResponse disconnectFromChatBot(ServletRequest request, ServletResponse response){
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        String email = httpRequest.getParameter("userEmail");
        LOGGER.info("DisConneting to our chat agent! For email: " + email);
        UserVo userVo = userVoDao.findUserVoByEmail(email);
        if(userVo == null){
            Result result = new Result();
            result.setCode(ResultCode.FAIL.code());
            result.setMessage("The userEmail is not exist or already DisConnected!");
            try {
                response = ServletResponseUtils.setResponseData(httpResponse, JsonBinderUtil.toJson(result));
            } catch (IOException e) {
                e.printStackTrace();
            }
            return response;
        }
        removeChannel(userVo);
        removeService(userVo);
        removeWebhook(userVo);
        // For result
        LOGGER.info("Creating Result!");
        Result result = new Result();
        result.setCode(ResultCode.SUCCESS.code());
        result.setMessage("successfully closed connection user from channel:" + " and user is now: " + JsonBinderUtil.toJson(userVo));
        try {
            response = ServletResponseUtils.setResponseData(httpResponse, JsonBinderUtil.toJson(result));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return response;
    }

    @RequestMapping(value="/sendmessage", method=RequestMethod.POST)
    public ServletResponse sendMessage(ServletRequest request, ServletResponse response){
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        String message = httpRequest.getParameter("message");
        LOGGER.info("sending messages and the message is: " + message);
        String userEmail = httpRequest.getParameter("userEmail");
        UserVo userVo = userVoDao.findUserVoByEmail(userEmail);
        // get the right channel
        Channel channel = getChannel(userVo);
        Message twilioMessage = Message.creator(userVo.getServiceSid(), channel.getSid()).setBody(message).create();  // service instance not found
        // Message twilioMessage = Message.creator(userVo.getServiceSid(), (String)channel.getSid()).create();
        LOGGER.info("twilioMessage: " + twilioMessage.toString());
        Webhook webhook = getWebhook(userVo);
        LOGGER.info("the current webhook autopilotUrl is: " + webhook.getConfiguration().toString());

        ResourceSet<Message> messages = Message.reader(userVo.getServiceSid(), channel.getSid()).limit(399).read();
        // String messageStr = "";
        List<String> messageStr = new ArrayList<>();
        for(Message msg: messages){
            LOGGER.info("MSG: " + msg.toString());
            // String retMsg = msg.getFrom()
            if(msg.getIndex() > twilioMessage.getIndex()){
                messageStr.add(msg.getBody());
            }
        }
        Result result = new Result();
        result.setCode(ResultCode.SUCCESS.code());
        result.setMessage("successfully fetches all the messages!");
        // result.setData(messageStr);
        result.setDatas(messageStr);
        LOGGER.info("Returning result!");
        // return twilioMessage.toString();
        try{
            response = ServletResponseUtils.setResponseData(httpResponse, JsonBinderUtil.toJson(result));
        }catch(IOException e){
            e.printStackTrace();
        }
        LOGGER.info("response is:" + response.toString());
        return response;
    }

    // get messages is to get all the chat history
    @RequestMapping(value="/getmessages", method=RequestMethod.POST)
    public ServletResponse getMessages(ServletRequest request, ServletResponse response){
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        String userEmail = httpRequest.getParameter("userEmail");
        UserVo userVo = userVoDao.findUserVoByEmail(userEmail);
        // get the right channel
        Channel channel = getChannel(userVo);
        LOGGER.info("message count is: " + channel.getMessagesCount() + " Members count: " + channel.getMembersCount());
        ResourceSet<Message> messages = Message.reader(userVo.getServiceSid(), channel.getSid()).limit(99).read();
        LOGGER.info("Getting all the  messages and the messages are: " + messages.toString());
        Result result = new Result();
        result.setCode(ResultCode.SUCCESS.code());
        result.setMessage("successfully fetches all the messages!");
        List<String> messageStr = new ArrayList<>();
        for(Message msg: messages){
            LOGGER.info("Msg: " + msg.getBody() + " from: " + msg.getFrom() + " to: " + msg.getTo());
            messageStr.add(msg.getBody());
        }
        result.setDatas(messageStr);
        try{
            response = ServletResponseUtils.setResponseData(httpResponse, JsonBinderUtil.toJson(result));
        }catch(IOException e){
            e.printStackTrace();
        }

        return response;
    }

    private Channel getChannel(UserVo userVo){
        // try and get a channel
        Channel channel = null;
        if(userVo.getChannelSid() != null){
            channel = Channel.fetcher(userVo.getServiceSid(), userVo.getChannelSid()).fetch();
        }else{
            channel = channelService.createChannel(userVo.getServiceSid());
            channelService.onChannelAdded(channel);
            userVo.setChannelSid(channel.getSid());
            userVoDao.findUserVoAndModify(userVo.getId(), userVo.getUserEmail(), userVo.getUserNumber(), userVo.getTwilioToken(), userVo.getChannelSid(), userVo.getServiceSid(), userVo.getWebhookSid());
        }
        LOGGER.info("created channel: " + channel.toString());
        return channel;
    }

    private Service getService(UserVo userVo){
        Service service = null;
        if(userVo.getServiceSid() != null){
            service = Service.fetcher(userVo.getServiceSid()).fetch();
        }else{
            service = Service.creator(userVo.getUserEmail()).create();
            userVo.setServiceSid(service.getSid());
            userVoDao.findUserVoAndModify(userVo.getId(), userVo.getUserEmail(), userVo.getUserNumber(), userVo.getTwilioToken(), userVo.getChannelSid(), userVo.getServiceSid(), userVo.getWebhookSid());
        }
        LOGGER.info("created service: " + service.toString());
        return service;
    }

    private Webhook getWebhook(UserVo userVo){
        Webhook webhook = null;
        if(userVo.getWebhookSid() != null){
            webhook = Webhook.fetcher(userVo.getServiceSid(), userVo.getChannelSid(), userVo.getWebhookSid()).fetch();
            LOGGER.info("fetched webhook is: " + webhook.toString());
        }else{
            LOGGER.info("autopilotUrl is: " + twilioConfiguration.getAutopilotUrl());
            String url = "";
            try{
                url = URLEncoder.encode("{\"userEmail\":\"admin@galaxy.com\"}", "UTF-8");
            }catch (UnsupportedEncodingException e){
                e.printStackTrace();
            }
            webhook = Webhook.creator(userVo.getServiceSid(), userVo.getChannelSid(), Webhook.Type.WEBHOOK)
                    .setConfigurationFilters(Arrays.asList("onMessageSent"))
                    .setConfigurationMethod(Webhook.Method.POST)
                    .setConfigurationUrl(twilioConfiguration.getAutopilotUrl() + "?Memory=" + url)
                    .create();
                    //.setConfigurationUrl(twilioConfiguration.getAutopilotUrl())
                    //.setConfigurationUrl(twilioConfiguration.getAutopilotUrl() + "?Memory={\"userEmail\":\"" + userVo.getUserEmail() + "\"}")
                    //.create();
            userVo.setWebhookSid(webhook.getSid());
            userVoDao.findUserVoAndModify(userVo.getId(), userVo.getUserEmail(), userVo.getUserNumber(), userVo.getTwilioToken(), userVo.getChannelSid(), userVo.getServiceSid(), userVo.getWebhookSid());
        }
        return webhook;
    }

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



