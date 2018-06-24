package cn.edu.nju.software.data.mooctest;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SessionTicket {
    @JSONField(name = "session_ticket")
    private String sessionTicket;
}
