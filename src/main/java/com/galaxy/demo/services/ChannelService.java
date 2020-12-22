package com.galaxy.demo.services;

import com.galaxy.demo.configs.TwilioConfiguration;
import com.twilio.rest.ipmessaging.v2.service.Channel;
import com.twilio.rest.ipmessaging.v2.service.ChannelDeleter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ChannelService {
    private final static Logger LOGGER = LoggerFactory.getLogger(ChannelService.class);

    @Autowired
    TwilioConfiguration twilioConfiguration;

    public Channel createChannel(String serviceSid){
        LOGGER.info("Creating Channel! And ChatServiceSid is: " + serviceSid);
        return Channel.creator(serviceSid).create();
    }

    public ChannelDeleter deleteChannel(Channel channel){
        onChannelDeleted(channel);
        return Channel.deleter(twilioConfiguration.getChatServiceSid(), channel.getSid());
    }

    public void onChannelAdded(Channel channel){
        LOGGER.info("Channel Added:" + " Channel:" + channel.toString());
    }

    public void onChannelDeleted(Channel channel){
        LOGGER.info("Channel Removed:" + " Channel:" + channel.toString());
    }
}
