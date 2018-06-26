package cn.edu.nju.software.service;

import cn.edu.nju.software.command.mooctest.AccessTokenCommand;
import cn.edu.nju.software.common.exception.ServiceException;
import cn.edu.nju.software.data.mooctest.AccessToken;
import cn.edu.nju.software.data.mooctest.SessionTicket;
import cn.edu.nju.software.dto.UserDto;
import cn.edu.nju.software.service.feign.MoocTestFeign;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Service
@Slf4j
public class MoocTestService {

    @Autowired
    private MoocTestFeign moocTestFeign;
    @Autowired
    private HttpServletRequest request;

    // TODO 为了测试该接口将该方法设置为了public之后需要设置为private
    //默认从session获取token，如果session失效就从mooctest接口中重新获取acesstoken
    public String accessToken() {
        HttpSession session = request.getSession();
        String accessToken = (String) session.getAttribute("access_token");
        if (accessToken == null) {
            AccessToken token = moocTestFeign.getAccessToken(new AccessTokenCommand().getGrantType());
            if (token == null)
                throw new ServiceException("获取mooctest的token失败!");
            accessToken = token.getAccessToken();
            session.setAttribute("access_token", accessToken);
            // 单位是s
            session.setMaxInactiveInterval(token.getExpiresIn());
        }
        return accessToken;
    }

    //获取用户信息
    public UserDto getUser() {
        HttpSession session = request.getSession();
        UserDto dto = (UserDto) session.getAttribute("user");
        if (dto == null) {
            String auth_code = (String) session.getAttribute("authrization_code");
            if (auth_code == null || auth_code.trim().isEmpty()) {
                throw new ServiceException("用户信息丢失，请重新从慕测平台进入该考试！");
            }
            SessionTicket ticket = moocTestFeign.getSessionTicket(accessToken(), auth_code);
            session.setAttribute("session_ticket", ticket.getSessionTicket());
            dto = moocTestFeign.getUser(accessToken(), ticket.getSessionTicket());
            session.setAttribute("user", dto);
            session.setAttribute("user_id", dto.getOpenId());
        }
        return dto;
    }

    public void assignTask() {
        //提交用户的考试成绩
    }
}
