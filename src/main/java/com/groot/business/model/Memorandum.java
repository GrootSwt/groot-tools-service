package com.groot.business.model;

import com.groot.business.bean.enums.MemorandumContentType;
import com.groot.business.model.base.BaseModel;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.Length;

@EqualsAndHashCode(callSuper = true)
@Data
public class Memorandum extends BaseModel {
    /**
     * 用户id
     */
    private String userId;
    /**
     * 内容
     */
    @NotBlank(message = "备忘录内容不可为空")
    @Length(min = 1, max = 2000, message = "备忘录内容长度位于1～2000之间")
    private String content;

    private MemorandumContentType contentType;
}
