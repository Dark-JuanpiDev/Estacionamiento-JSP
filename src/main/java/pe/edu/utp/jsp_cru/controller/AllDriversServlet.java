package pe.edu.utp.jsp_cru.controller;

import pe.edu.utp.jsp_cru.business.ReservaDAO;
import pe.edu.utp.jsp_cru.model.RegistroEntrada;
import pe.edu.utp.jsp_cru.model.RegistroSalida;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "driver", urlPatterns = {"/driver"})
public class AllDriversServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            List<RegistroEntrada> allDriver = ReservaDAO.getRegistroEntrada();
            List<RegistroSalida> allDriverExit = ReservaDAO.getRegistroSalida();
            req.setAttribute("allDriver", allDriver);
            req.setAttribute("allDriverOut", allDriverExit);
            req.getRequestDispatcher("drivers.jsp").forward(req, resp);
        } catch (IOException | ServletException | IllegalStateException e) {
            resp.sendRedirect(req.getContextPath() + "/error.jsp");
        }
    }
}
