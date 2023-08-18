package com.groot.business.service;

import com.groot.business.bean.response.FriendResponse;
import com.groot.business.bean.response.FriendWithUnreadMsgCountResponse;

import java.util.List;

public interface FriendService {
    List<FriendResponse> listFriend();

    List<FriendWithUnreadMsgCountResponse> listFriendWithUnreadMsgCount();
}
