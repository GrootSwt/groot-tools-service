package com.groot.business.service;

import com.groot.business.dto.FriendDTO;
import com.groot.business.dto.FriendWithUnreadMsgCountDTO;

import java.util.List;

public interface FriendService {
    List<FriendDTO> listFriend();

    List<FriendWithUnreadMsgCountDTO> listFriendWithUnreadMsgCount();
}
