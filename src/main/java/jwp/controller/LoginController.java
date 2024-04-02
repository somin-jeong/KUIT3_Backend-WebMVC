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
        User findUser = MemoryUserRepository.getInstance().findUserById(req.getParameter("userId"));
        if (findUser != null && findUser.getPassword().equals(req.getParameter("password"))) {
            // 세션 정보 저장
            HttpSession session = req.getSession();
            session.setAttribute("user", findUser);

            resp.sendRedirect("/");
        } else {
            RequestDispatcher rd = req.getRequestDispatcher("/user/login_failed.jsp");
            rd.forward(req,resp);
        }
    }
}
