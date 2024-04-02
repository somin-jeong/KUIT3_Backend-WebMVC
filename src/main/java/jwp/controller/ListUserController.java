package jwp.controller;

import core.db.MemoryUserRepository;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ListUserController implements Controller {
    public static final String USER_SESSION_KEY = "user";
    private boolean isLogined(HttpSession session) {
        return session.getAttribute(USER_SESSION_KEY) != null;
    }

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        if (!isLogined(req.getSession())) {
            return "redirect:/user/loginForm";
        }
        req.setAttribute("users", MemoryUserRepository.getInstance().findAll());
        return "/user/list.jsp";
    }
}
