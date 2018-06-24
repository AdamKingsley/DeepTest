package cn.edu.nju.software.dto;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserDto {
    @JSONField(name = "open_id")
    private String openId;
    private String name;
    private String school;
    private String email;
    private String mobile;
    private Object extra;
}

