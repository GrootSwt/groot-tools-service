package com.groot.business.controller;

import com.groot.base.bean.result.BaseResponse;
import com.groot.base.bean.result.ListResponse;
import com.groot.business.model.Memorandum;
import com.groot.business.service.MemorandumService;
import jakarta.validation.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping(value = "/memorandum")
public class MemorandumController {

    private final MemorandumService memorandumService;

    @Autowired
    public MemorandumController(MemorandumService memorandumService) {
        this.memorandumService = memorandumService;
    }

    @GetMapping(value = "/{userId}/listMemorandumByUserId")
    public ListResponse<Memorandum> listMessageByUserId(@NotBlank(message = "用户ID不可为空") @PathVariable String userId){
        List<Memorandum> memorandums = memorandumService.listByUserId(userId);
        return ListResponse.success("获取备忘录列表成功！", memorandums);
    }

    @DeleteMapping(value = "{id}/{userId}/deleteMemorandumById")
    public BaseResponse deleteMessageById(@NotBlank(message = "备忘录ID不可为空") @PathVariable String id,
                                          @NotBlank(message = "用户ID不可为空") @PathVariable String userId) throws IOException {
        memorandumService.deleteMessageById(id, userId);
        return BaseResponse.success("删除成功");
    }

}
