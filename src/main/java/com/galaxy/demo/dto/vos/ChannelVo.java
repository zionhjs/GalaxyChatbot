package com.galaxy.demo.dto.vos;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Id;
import javax.persistence.PrePersist;
import java.io.Serializable;
import java.util.Date;

@Document(collection="channelVo")
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ChannelVo implements Serializable {
    @Id
    private String id;
    private String sid;
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
