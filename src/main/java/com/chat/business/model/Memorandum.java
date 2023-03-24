package com.chat.business.model;

import com.chat.base.model.BaseModel;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.Length;

@EqualsAndHashCode(callSuper = true)
@Data
public class Memorandum extends BaseModel {

    private String userId;

    @NotBlank(message = "备忘录内容不可为空")
    @Length(min = 1, max = 2000, message = "备忘录内容长度位于1～2000之间")
    private String content;

}
