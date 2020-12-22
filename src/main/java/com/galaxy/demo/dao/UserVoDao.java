package com.galaxy.demo.dao;

import com.galaxy.demo.dto.vos.UserVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserVoDao {
    private final static Logger LOGGER = LoggerFactory.getLogger(UserVoDao.class);
    @Autowired
    private MongoTemplate mongoTemplate;

    public UserVo createUserVo(UserVo userVo){
        return mongoTemplate.insert(userVo);
    }

    public UserVo findUserVo(String userId){
        Query query = new Query();
        query.addCriteria((Criteria.where("userId").is(userId)));
        return mongoTemplate.findOne(query, UserVo.class);
    }

    public UserVo findUserVoByEmail(String userEmail){
        Query query = new Query();
        query.addCriteria((Criteria.where("userEmail").is(userEmail)));
        return mongoTemplate.findOne(query, UserVo.class);
    }

    public UserVo findUserVoAndModify(String id, String userEmail, String userNumber, String twilioToken, String channelSid, String serviceSid, String webhookSid){
        Query query = new Query();
        query.addCriteria((Criteria.where("id").is(id)));
        Update update = new Update();
        update.set("userEmail", userEmail);
        update.set("userNumber", userNumber);
        update.set("twilioToken", twilioToken);
        update.set("channelSid", channelSid);
        update.set("serviceSid", serviceSid);
        update.set("webhookSid", webhookSid);
        UserVo userVo = mongoTemplate.findOne(query, UserVo.class);
        return mongoTemplate.findAndModify(query, update, UserVo.class);
    }

    public List<UserVo> getUserVos(){
        LOGGER.info("fetching all uservos - DAO");
        return mongoTemplate.findAll(UserVo.class);
    }

    public UserVo removeUserVo(String id){
        Query query = new Query();
        query.addCriteria((Criteria.where("id").is(id)));
        return mongoTemplate.findAndRemove(query, UserVo.class);
    }
}
