package com.galaxy.demo.dto.vos;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Id;
import javax.persistence.PrePersist;

import java.io.Serializable;
import java.util.Date;

@Document(collection="userVo")
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserVo implements Serializable {
    @Id
    private String id;
    private String userEmail;
    private String userNumber;

    private String twilioToken;
    private String channelSid;
    private String serviceSid;
    private String webhookSid;

    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date createdAt;
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date updatedAt;

    @PrePersist
    protected void onCreate(){
        this.createdAt = new Date();
    }

    @PrePersist
    protected void onUpdate(){
        this.updatedAt = new Date();
    }

}


