package com.galaxy.demo.controllers;

import com.galaxy.demo.configs.TwilioConfiguration;
import com.galaxy.demo.payloads.SmsRequest;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.rest.api.v2010.account.MessageCreator;
import com.twilio.sdk.resource.factory.CallFactory;
import com.twilio.sdk.resource.instance.Call;
import com.twilio.type.PhoneNumber;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.twilio.sdk.TwilioRestClient;
import com.twilio.sdk.TwilioRestException;
import com.twilio.sdk.resource.factory.MessageFactory;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/test")
public class GreetingController {
    private static final Logger LOGGER = LoggerFactory.getLogger(GreetingController.class);
    @Autowired
    private TwilioConfiguration twilioConfiguration;

    @RequestMapping(value="/greeting", method=RequestMethod.GET)
    public ResponseEntity greeting(){
        return ResponseEntity.ok().body("Hello welcome to galaxy chat~");
    }

    @RequestMapping(value="/sendsms", method= RequestMethod.POST)
    public void sendSms(@Valid @RequestBody SmsRequest smsRequest) {
        if(isPhoneNumberValid(smsRequest.getPhoneNumber())){
            PhoneNumber to = new PhoneNumber(smsRequest.getPhoneNumber());
            PhoneNumber from = new PhoneNumber(twilioConfiguration.getTrialNumber());
            String message = smsRequest.getMessage();

            MessageCreator creator = com.twilio.rest.api.v2010.account.Message.creator(to, from, message);
            Message msg = creator.setProvideFeedback(true).create();
            LOGGER.info("Send sms {}", smsRequest);
            LOGGER.info("MessageSid:", msg.getSid());
        }else{
            throw new IllegalArgumentException(
                    "Phone number [" + smsRequest.getPhoneNumber() + "] is not a valid number"
            );
        }
    }

    private boolean isPhoneNumberValid(String phoneNumber){
        return true;
    }

    @RequestMapping(value="/makecalls", method= RequestMethod.POST)
    private void makeCall(@Valid @RequestBody SmsRequest smsRequest){
        LOGGER.info("try calling user!");
        try{
            TwilioRestClient client = new TwilioRestClient(twilioConfiguration.getAccountSid(), twilioConfiguration.getAuthToken());
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("Url", "http://demo.twilio.com/welcome"));
            params.add(new BasicNameValuePair("To", smsRequest.getPhoneNumber()));
            params.add(new BasicNameValuePair("From", this.twilioConfiguration.getTrialNumber()));

            CallFactory callFactory = client.getAccount().getCallFactory();
            Call call = callFactory.create(params);
        }catch(TwilioRestException e){
            LOGGER.info("something wrong when calling user");
            LOGGER.error(e.getErrorMessage());
        }
    }

}
