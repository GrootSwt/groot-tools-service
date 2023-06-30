package com.groot.business.controller;

import com.groot.business.bean.response.base.Response;
import com.groot.business.model.Memorandum;
import com.groot.business.service.MemorandumService;

import cn.dev33.satoken.annotation.SaCheckLogin;
import jakarta.validation.constraints.NotBlank;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping(value = "/memorandum")
@SaCheckLogin
public class MemorandumController {

    private final MemorandumService memorandumService;

    public MemorandumController(MemorandumService memorandumService) {
        this.memorandumService = memorandumService;
    }

    @GetMapping(value = "/listMemorandum")
    public Response<List<Memorandum>> list() {
        List<Memorandum> memorandums = memorandumService.list();
        return Response.success("获取备忘录列表成功！", memorandums);
    }

    @DeleteMapping(value = "{id}/deleteMemorandumById")
    public Response<Void> deleteById(
            @NotBlank(message = "备忘录ID不可为空") @PathVariable String id) throws IOException {
        memorandumService.deleteById(id);
        return Response.success("删除成功");
    }

}
