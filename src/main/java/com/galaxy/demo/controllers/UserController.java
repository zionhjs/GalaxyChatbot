 package com.galaxy.demo.controllers;

import com.galaxy.demo.dao.UserVoDao;
import com.galaxy.demo.dto.vos.UserVo;
import com.galaxy.demo.utils.JsonBinderUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/uservo")
public class UserController {
    private final static Logger LOGGER = LoggerFactory.getLogger(UserController.class);
    @Autowired
    private UserVoDao userVoDao;

    @RequestMapping(value="/subscribe", method=RequestMethod.POST)
    public ResponseEntity createUser(@RequestParam String email, @RequestParam String number){
        UserVo userVo = new UserVo();
        userVo.setUserEmail(email);
        userVo.setUserNumber(number);
        userVoDao.createUserVo(userVo);
        return ResponseEntity.ok().body(JsonBinderUtil.toJson(userVo));
    }

    @RequestMapping(value="/alluservo", method=RequestMethod.GET)
    public ResponseEntity getaAllUser(){
        LOGGER.info("fetching all uservos");
        List<UserVo> userVos = null;
        userVos = userVoDao.getUserVos();
        LOGGER.info("uservos: " + userVos.toString());
        return ResponseEntity.ok().body(JsonBinderUtil.toJson(userVos.toString()));
    }

    @RequestMapping(value="/unsubscribe", method=RequestMethod.POST)
    public ResponseEntity unsubscribe(@RequestParam String email){
        UserVo userVo = userVoDao.findUserVoByEmail(email);
        userVoDao.removeUserVo(userVo.getId());
        return ResponseEntity.ok().body("SuccessFully unsubscribed " + email);
    }
}
