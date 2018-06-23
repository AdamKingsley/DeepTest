package cn.edu.nju.software.command.mooctest;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccessTokenCommand {
    @JSONField(name = "grant_type")
    private String grantType = "client_credentials";
}
