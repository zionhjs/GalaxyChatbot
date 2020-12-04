package com.galaxy.demo.services.Interfaces;

import com.galaxy.demo.payloads.SmsRequest;

public interface SmsSender {
    public void sendSms(SmsRequest smsRequest);
}
