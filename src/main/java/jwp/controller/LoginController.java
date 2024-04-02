package jwp.controller;

import core.db.MemoryUserRepository;
import jwp.model.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LoginController implements Controller {
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        String userId = req.getParameter("userId");
        String password = req.getParameter("password");

        User findUser = MemoryUserRepository.getInstance().findUserById(userId);

        if (findUser != null && findUser.matchPassword(password)) {
            // 세션 정보 저장
            HttpSession session = req.getSession();
            session.setAttribute("user", findUser);

            return "redirect:/";
        }

        return "redirect:/user/loginFailed";
    }
}
