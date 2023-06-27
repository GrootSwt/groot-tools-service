package com.groot.business.controller;

import com.groot.base.bean.result.Response;
import com.groot.business.dto.FriendDTO;
import com.groot.business.dto.FriendWithUnreadMsgCountDTO;
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
    public Response<List<FriendDTO>> listFriend() {
        List<FriendDTO> friendDTOList = friendService.listFriend();
        return Response.success("获取朋友列表成功", friendDTOList);
    }

    @GetMapping(value = "/listFriendWithUnreadMsgCount")
    public Response<List<FriendWithUnreadMsgCountDTO>> listFriendWithUnreadMsgCount() {
        List<FriendWithUnreadMsgCountDTO> friendWithUnreadMsgCountDTOList = friendService.listFriendWithUnreadMsgCount();
        return Response.success("获取朋友列表和未读消息数量成功", friendWithUnreadMsgCountDTOList);
    }
}
