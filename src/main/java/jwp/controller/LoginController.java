package jwp.controller;

import core.db.MemoryUserRepository;
import jwp.model.User;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/user/login")
public class LoginController extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String userId = req.getParameter("userId");
        String password = req.getParameter("password");

        User findUser = MemoryUserRepository.getInstance().findUserById(userId);

        if (findUser != null && findUser.matchPassword(password)) {
            // 세션 정보 저장
            HttpSession session = req.getSession();
            session.setAttribute("user", findUser);

            resp.sendRedirect("/");
            return;
        }

        resp.sendRedirect("/user/loginFailed.jsp");
    }
}
