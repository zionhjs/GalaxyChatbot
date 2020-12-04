//package com.galaxy.demo.services.twilio;
//
//import com.galaxy.demo.GalaxychatbotsApplication;
//import com.galaxy.demo.configs.AppConfig;
//import com.galaxy.demo.exceptions.IncompleteConfigException;
//import com.twilio.jwt.accesstoken.AccessToken;
//import com.twilio.jwt.accesstoken.ChatGrant;
//import org.springframework.boot.SpringApplication;
//import org.springframework.context.ConfigurableApplicationContext;
//import org.springframework.stereotype.Component;
//
//@Component
//public class TwilioTokenCreator {
//    private final AppConfig appConfig;
//
//    public TwilioTokenCreator(){
//        ConfigurableApplicationContext context = SpringApplication.run(GalaxychatbotsApplication.class);
//        AppConfig appConfig = context.getBean(AppConfig.class);
//        this.appConfig = appConfig;
//        if(appConfig.isIncomplete()){
//            throw new IncompleteConfigException(appConfig);
//        }
//    }
//
//    public TwilioTokenCreator(AppConfig appConfig){
//        this.appConfig = appConfig;
//        if(appConfig.isIncomplete()){
//            throw new IncompleteConfigException(appConfig);
//        }
//    }
//
//    public String generateToken(String identity) {
//        ChatGrant grant = new ChatGrant();
//        grant.setServiceSid(appConfig.getTwilioChatServiceSID());
//
//        AccessToken token = new AccessToken.Builder(
//                appConfig.getTwilioAccountSID(),
//                appConfig.getTwilioAPIKey(),
//                appConfig.getTwilioAPISecret()
//        ).identity(identity).grant(grant).build();
//
//        return token.toJwt();
//    }
//}
