package com.groot.business.controller;

import com.groot.business.bean.response.base.Response;
import com.groot.business.bean.response.FriendResponse;
import com.groot.business.bean.response.FriendWithUnreadMsgCountResponse;
import com.groot.business.service.FriendService;

import cn.dev33.satoken.annotation.SaCheckLogin;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/friend")
@SaCheckLogin
public class FriendController {

    private final FriendService friendService;


    public FriendController(final FriendService friendService) {
        this.friendService = friendService;
    }

    @GetMapping(value = "/listFriend")
    public Response<List<FriendResponse>> listFriend() {
        List<FriendResponse> friendResponseList = friendService.listFriend();
        return Response.success("获取朋友列表成功", friendResponseList);
    }

    @GetMapping(value = "/listFriendWithUnreadMsgCount")
    public Response<List<FriendWithUnreadMsgCountResponse>> listFriendWithUnreadMsgCount() {
        List<FriendWithUnreadMsgCountResponse> friendWithUnreadMsgCountDTOList = friendService.listFriendWithUnreadMsgCount();
        return Response.success("获取朋友列表和未读消息数量成功", friendWithUnreadMsgCountDTOList);
    }
}
