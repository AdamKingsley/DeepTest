package cn.edu.nju.software.service;

import cn.edu.nju.software.dto.UserDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Service
@Slf4j
public class CommonService {
    @Autowired
    private HttpServletRequest request;
    @Autowired
    private MoocTestService moocTestService;

    public String getUserId() {
        HttpSession session = request.getSession();
        String userId = (String) session.getAttribute("user_id");
        if (userId == null || "".equals(userId.trim())) {
            log.info("userId is {} ",userId);
            UserDto dto = moocTestService.getUser();
            return dto.getOpenId();
        }
        return userId;
    }
}
