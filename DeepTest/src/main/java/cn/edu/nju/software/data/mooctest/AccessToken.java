package cn.edu.nju.software.data.mooctest;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccessToken {
    @JSONField(name = "access_token")
    private String accessToken;
    @JSONField(name = "token_type")
    private String tokenType;
    @JSONField(name = "expires_in")
    private Integer expiresIn;
    private String scope;
}
