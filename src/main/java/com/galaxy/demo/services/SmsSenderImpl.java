//package com.galaxy.demo.services;
//
//import com.galaxy.demo.configs.TwilioConfiguration;
//import com.galaxy.demo.payloads.SmsRequest;
//import com.galaxy.demo.services.Interfaces.SmsSender;
//import com.twilio.type.PhoneNumber;
//import org.springframework.beans.factory.annotation.Autowired;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import com.twilio.rest.api.v2010.account.Message;
//import com.twilio.rest.api.v2010.account.MessageCreator;
//import org.springframework.stereotype.Service;
//
//@Service("twilioJSON")
//public class SmsSenderImpl implements SmsSender {
//    private static final Logger LOGGER = LoggerFactory.getLogger(SmsSenderImpl.class);
//    private final TwilioConfiguration twilioConfiguration;
//
//    @Autowired
//    public SmsSenderImpl(TwilioConfiguration twilioConfiguration){
//        this.twilioConfiguration = twilioConfiguration;
//    }
//
//    @Override
//    public void sendSms(SmsRequest smsRequest) {
//        if(isPhoneNumberValid(smsRequest.getPhoneNumber())){
//            PhoneNumber to = new PhoneNumber(smsRequest.getPhoneNumber());
//            PhoneNumber from = new PhoneNumber(twilioConfiguration.getTrialNumber());
//            String message = smsRequest.getMessage();
//
//            MessageCreator creator = Message.creator(to, from, message);
//            Message msg = creator.setProvideFeedback(true).create();
//            LOGGER.info("Send sms {}", smsRequest);
//            LOGGER.info("MessageSid:", msg.getSid());
//        }else{
//            throw new IllegalArgumentException(
//                "Phone number [" + smsRequest.getPhoneNumber() + "] is not a valid number"
//            );
//        }
//    }
//
//    private boolean isPhoneNumberValid(String phoneNumber){
//        return true;
//    }
//}
