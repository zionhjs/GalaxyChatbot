//package com.galaxy.demo.controllers;
//
//import com.galaxy.demo.services.twilio.TwilioTokenCreator;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.util.HashMap;
//import java.util.Map;
//
//@Controller
//public class TokenController {
//    private static final Logger LOGGER = LoggerFactory.getLogger(TokenController.class);
//    @Autowired
//    private TwilioTokenCreator twilioTokenCreator;
//
//    @RequestMapping(value="/gettoken", method= RequestMethod.GET)
//    public ResponseEntity getToken(HttpServletRequest request, HttpServletResponse response){
//        String identity = request.getParameter("identity");
//        if(identity != null){
//            String generatedToken = twilioTokenCreator.generateToken(identity);
//
//            Map<String, String> jsonMap = new HashMap<>();
//            jsonMap.put("identity", identity);
//            jsonMap.put("token", generatedToken);
//            // renderJson(response, jsonMap);
//            LOGGER.info("token is: " + jsonMap.get("token"));
//            return ResponseEntity.ok().body(jsonMap.get("token"));
//        }else{
//            return ResponseEntity.ok().body("not a valid response");
//        }
//    }
//
////    public void genClient(){
////        ChatClient.Properties props = new ChatClient.Properties.Builder()
////                                                        .setRegion("us1")
////                                                        .createProperties();
////
////        ChatClient.create(context.getApplicationContext(), token, props, this);
////    }
//}
