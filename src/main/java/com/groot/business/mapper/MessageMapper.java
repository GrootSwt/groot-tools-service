package com.groot.business.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.groot.business.bean.enums.ReadStatus;
import com.groot.business.bean.response.MessageResponse;
import com.groot.business.bean.response.UnreadMessageCountResponse;
import com.groot.business.model.Message;

import java.util.List;

public interface MessageMapper extends BaseMapper<Message> {

    List<MessageResponse> listMessage(Integer pageSize, String senderId, String receiverId, Long rowNumber);

    List<UnreadMessageCountResponse> listUnreadMessageCount(int readStatus, String friendId, String userId);

    void updateMessageToRead(int readStatus, List<String> unreadMessageIds, String friendId, String userId);

    Long listMessageRowNumberById(String userId, String friendId, String prevMessageId);
}
