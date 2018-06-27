package cn.edu.nju.software.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Service
public class CommonService {
    @Autowired
    private HttpServletRequest request;

    public String getUserId() {
        HttpSession session = request.getSession();
        String userId = (String) session.getAttribute("user_id");
        if (userId == null || "".equals(userId.trim())) {
            //TODO 正式环境这里需要抛出异常
            return "123";
            //throw new ServiceException("用户信息失效，请重新进入考试页面");
        }
        return userId;
    }
}
