package com.groot.business.bean.response;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class FriendWithUnreadMsgCountResponse extends FriendResponse {

    /**
     * 未读消息数量
     */
    private Integer unreadMessageCount;
}
