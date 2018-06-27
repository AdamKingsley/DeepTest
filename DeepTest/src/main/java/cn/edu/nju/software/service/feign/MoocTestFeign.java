package cn.edu.nju.software.service.feign;

import cn.edu.nju.software.command.mooctest.AssignTaskCommand;
import cn.edu.nju.software.data.mooctest.AccessToken;
import cn.edu.nju.software.data.mooctest.SessionTicket;
import cn.edu.nju.software.dto.UserDto;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 访问mooctest 开放api的feign
 */
@FeignClient(url = "${mooctest.url}", name = "mooctest")
public interface MoocTestFeign {

    // 定期更新access_token  测试成功
    @PostMapping(value = "/oauth/token", headers = {"Content-Type=application/x-www-form-urlencoded",
            "Authorization=Basic " + "${mooctest.secretKey}"})
    public AccessToken getAccessToken(@RequestParam("grant_type") String grantType);

    // 获取用户ticket
    @GetMapping(value = "/api/v1/oauth/ticket")
    public SessionTicket getSessionTicket(@RequestParam("access_token") String accessToken, @RequestParam("code") String authrizationCode);

    //获取用户具体信息
    @GetMapping(value = "/api/v1/user")
    public UserDto getUser(@RequestParam("access_token") String accessToken, @RequestParam("session_ticket") String sessionTicket);

    //提交用户成绩
    @PostMapping(value = "/api/v1/assignTask")
    public UserDto assignTask(@RequestParam("access_token") String accessToken, @RequestBody AssignTaskCommand assignTaskCommand);

}
