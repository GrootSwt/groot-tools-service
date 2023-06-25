package com.groot.business.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class FriendWithUnreadMsgCountDTO extends FriendDTO {

    /**
     * 未读消息数量
     */
    private Integer unreadMessageCount;
}
