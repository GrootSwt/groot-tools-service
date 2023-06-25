package com.groot.business.service.impl;

import com.groot.base.bean.CurrentUser;
import com.groot.base.config.CurrentUserStore;
import com.groot.business.bean.ReadStatusEnum;
import com.groot.business.bean.RelationStatusEnum;
import com.groot.business.dto.FriendDTO;
import com.groot.business.dto.FriendWithUnreadMsgCountDTO;
import com.groot.business.mapper.FriendMapper;
import com.groot.business.service.FriendService;
import com.groot.business.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FriendServiceImpl implements FriendService {
    private final FriendMapper friendMapper;
    private final CurrentUserStore currentUserStore;

    private final UserService userService;

    @Autowired
    public FriendServiceImpl(final FriendMapper friendMapper, final CurrentUserStore currentUserStore, final UserService userService) {
        this.friendMapper = friendMapper;
        this.currentUserStore = currentUserStore;
        this.userService = userService;
    }

    @Override
    public List<FriendDTO> listFriend() {
        userService.checkLoginStatus();
        CurrentUser currentUser = currentUserStore.getCurrentUser();
        return friendMapper.listFriend(currentUser.getUserId(), RelationStatusEnum.FRIEND.getValue());
    }

    @Override
    public List<FriendWithUnreadMsgCountDTO> listFriendWithUnreadMsgCount() {
        userService.checkLoginStatus();
        CurrentUser currentUser = currentUserStore.getCurrentUser();
        return friendMapper.listFriendWithUnreadMsgCount(currentUser.getUserId(), ReadStatusEnum.UNREAD.getValue(), RelationStatusEnum.FRIEND.getValue());
    }

}
