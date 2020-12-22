package com.galaxy.demo.configs;

import com.galaxy.demo.services.twilio.TwilioTokenCreator;
import com.twilio.Twilio;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TwilioInitializer {
    private final static Logger LOGGER = LoggerFactory.getLogger(TwilioInitializer.class);
    @Autowired
    private TwilioConfiguration twilioconfiguration;

    @Autowired
    private TwilioTokenCreator twilioTokenCreator;

    @Autowired
    public TwilioInitializer(TwilioConfiguration twilioConfiguration){
        // initialize twilio client
        Twilio.init(
                twilioConfiguration.getAccountSid(),
                twilioConfiguration.getAuthToken()
        );
        Twilio.setRegion("us1");
        Twilio.setEdge("umatilla");
        LOGGER.info("Twilio initialized ... with account sid {} ", twilioConfiguration.getAccountSid());

        // initialize twilio chatclient instance
//        String token = twilioTokenCreator.generateToken("adminuser");
//        ChatClient.Properties props = new ChatClient.Properties.Builder().setRegion("us1").createProperties();
//        ChatClient.create(context.getApplicationContext(), token, props, this);
    }

}

