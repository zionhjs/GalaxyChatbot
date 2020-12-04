//package com.galaxy.demo.controllers;
//
//import com.galaxy.demo.payloads.SmsRequest;
//import com.galaxy.demo.services.SmsService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.*;
//
//import javax.validation.Valid;
//
//@RestController
//@RequestMapping("api/v1/sms")
//public class SmsController {
//    private SmsService smsService;
//
//    @Autowired
//    public SmsController(SmsService smsService){
//        this.smsService = smsService;
//    }
//
//    @RequestMapping(value="/sendmessage", method=RequestMethod.POST)
//    public void sendSms(@Valid @RequestBody SmsRequest smsRequest){
//        smsService.sendSms(smsRequest);
//    }
//
//}
//
