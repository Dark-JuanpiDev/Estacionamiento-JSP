package pe.edu.utp.jsp_cru.controller;

import pe.edu.utp.jsp_cru.business.SuscripcionDAO;
import pe.edu.utp.jsp_cru.model.Suscripcion;
import pe.edu.utp.jsp_cru.model.Suscriptor;

import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import java.util.List;

@WebServlet(name = "suscripcion" , urlPatterns = {"/suscripcion"})
public class SuscripcionServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req, resp);
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        try{
           List<Suscriptor> allSuscriptor = SuscripcionDAO.getSuscripciones();
           req.setAttribute("allSuscriptor", allSuscriptor);
           req.getRequestDispatcher("suscripcion.jsp").forward(req, resp);
       }catch (IOException | ServletException | IllegalStateException e){
           resp.sendRedirect(req.getContextPath() + "/error.jsp");
       }
    }



}
