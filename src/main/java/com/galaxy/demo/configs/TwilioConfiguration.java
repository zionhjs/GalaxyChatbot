package com.galaxy.demo.configs;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties("twilio")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class TwilioConfiguration {
    private String accountSid;
    private String authToken;
    private String trialNumber;
}
