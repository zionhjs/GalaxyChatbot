//package com.galaxy.demo.services;
//
//import com.galaxy.demo.payloads.SmsRequest;
//import com.galaxy.demo.services.Interfaces.SmsSender;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.stereotype.Service;
//
//@Service
//public class SmsService {
//    private SmsSender smsSender;
//
//    @Autowired
//    public SmsService(@Qualifier("twilioJSON") SmsSenderImpl smsSender){
//        this.smsSender = smsSender;
//    }
//
//    public void sendSms(SmsRequest smsRequest){
//        smsSender.sendSms(smsRequest);
//    }
//}
//
//
