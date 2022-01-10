package com.tsy.yebserver.dao.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * @author Steven.T
 * @date 2022/1/10
 */
@Data
@EqualsAndHashCode
@Accessors(chain = true)
public class ChatMessage {

    private String from;

    private String to;

    private String content;

    private LocalDateTime time;

    private String fromNickName;

}
