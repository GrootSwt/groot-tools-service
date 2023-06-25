package com.groot.business.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.groot.business.dto.FriendDTO;
import com.groot.business.dto.FriendWithUnreadMsgCountDTO;
import com.groot.business.model.Friend;

import java.util.List;

public interface FriendMapper extends BaseMapper<Friend> {
    List<FriendDTO> listFriend(String userId, String relationStatus);

    List<FriendWithUnreadMsgCountDTO> listFriendWithUnreadMsgCount(String userId, String readStatus, String relationStatus);
}
