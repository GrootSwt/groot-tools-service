package com.groot.business.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.groot.business.bean.response.FriendResponse;
import com.groot.business.bean.response.FriendWithUnreadMsgCountResponse;
import com.groot.business.model.Friend;

import java.util.List;

public interface FriendMapper extends BaseMapper<Friend> {
    List<FriendResponse> listFriend(String userId, int relationStatus);

    List<FriendWithUnreadMsgCountResponse> listFriendWithUnreadMsgCount(String userId, int readStatus, int relationStatus);
}
