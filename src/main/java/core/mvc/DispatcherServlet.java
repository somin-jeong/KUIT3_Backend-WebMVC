package core.mvc;

import jwp.controller.Controller;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "dispathcer", urlPatterns = "/", loadOnStartup = 1)
public class DispatcherServlet extends HttpServlet {
    private static final String REDIRECT_PREFIX = "redirect:";
    private RequestMapper requestMapper;

    @Override
    public void init() throws ServletException {
        requestMapper = new RequestMapper();
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Controller controller = requestMapper.getController(req);
        try {
            String viewName = controller.execute(req, resp);
            move(viewName, req, resp);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void move(String viewName, HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        if (viewName.startsWith("redirect:")) {
            resp.sendRedirect(viewName.substring(REDIRECT_PREFIX.length()));
            return;
        }

        RequestDispatcher rd = req.getRequestDispatcher(viewName);
        rd.forward(req, resp);
    }
}
