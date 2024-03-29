package com.groot.business.service.impl;

import com.groot.business.bean.enums.ReadStatus;
import com.groot.business.bean.enums.RelationStatus;
import com.groot.business.bean.response.FriendResponse;
import com.groot.business.bean.response.FriendWithUnreadMsgCountResponse;
import com.groot.business.mapper.FriendMapper;
import com.groot.business.service.FriendService;

import cn.dev33.satoken.stp.StpUtil;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FriendServiceImpl implements FriendService {
    private final FriendMapper friendMapper;


    public FriendServiceImpl(final FriendMapper friendMapper) {
        this.friendMapper = friendMapper;
    }

    @Override
    public List<FriendResponse> listFriend() {
        String userId = StpUtil.getLoginIdAsString();
        return friendMapper.listFriend(userId, RelationStatus.FRIEND.getValue());
    }

    @Override
    public List<FriendWithUnreadMsgCountResponse> listFriendWithUnreadMsgCount() {
        String userId = StpUtil.getLoginIdAsString();
        return friendMapper.listFriendWithUnreadMsgCount(userId, ReadStatus.UNREAD.getValue(),
                RelationStatus.FRIEND.getValue());
    }

}
