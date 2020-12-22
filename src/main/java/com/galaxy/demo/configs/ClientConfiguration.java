//package com.galaxy.demo.configs;
//
//import com.galaxy.demo.services.twilio.TwilioTokenCreator;
//import com.twilio.Twilio;
//import com.twilio.sdk.TwilioRestClient;
//import lombok.Getter;
//import lombok.Setter;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Configuration;
//
//@Getter
//@Setter
//@Configuration
//public class ClientConfiguration {
//    private static TwilioRestClient twilioRestClient;
//
//    @Autowired
//    private TwilioTokenCreator twilioTokenCreator;
//
//    public synchronized TwilioRestClient getClient(){
//        if(twilioRestClient == null){
//            String token = twilioTokenCreator.generateToken("testuser");
//            twilioRestClient = new TwilioRestClient(token);
//        }
//
//        return twilioRestClient;
//    }
//}

