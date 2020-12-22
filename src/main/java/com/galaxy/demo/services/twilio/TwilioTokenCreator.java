package com.galaxy.demo.services.twilio;

import com.galaxy.demo.configs.TwilioConfiguration;
import com.twilio.jwt.accesstoken.AccessToken;
import com.twilio.jwt.accesstoken.ChatGrant;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@NoArgsConstructor
@AllArgsConstructor
public class TwilioTokenCreator {
    private final static Logger LOGGER = LoggerFactory.getLogger(TwilioTokenCreator.class);
    @Autowired
    private TwilioConfiguration twilioconfiguration;

    public String generateToken(String identity) {
        ChatGrant grant = new ChatGrant();
        // grant.setServiceSid(appConfig.getTwilioChatServiceSID());
//        AccessToken token = new AccessToken.Builder(
//                appConfig.getTwilioAccountSID(),
//                appConfig.getTwilioAPIKey(),
//                appConfig.getTwilioAPISecret()
//        ).identity(identity).grant(grant).build();
        grant.setServiceSid(twilioconfiguration.getServiceSid());
        AccessToken token = new AccessToken.Builder(
                twilioconfiguration.getAccountSid(),
                twilioconfiguration.getApiKey(),
                twilioconfiguration.getApiKey()
        ).identity(identity).grant(grant).build();

        return token.toJwt();
    }
}
