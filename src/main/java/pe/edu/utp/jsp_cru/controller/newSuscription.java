package pe.edu.utp.jsp_cru.controller;

import pe.edu.utp.jsp_cru.business.SuscripcionDAO;
import pe.edu.utp.jsp_cru.model.Suscripcion;
import pe.edu.utp.jsp_cru.model.Suscriptor;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;

@WebServlet(name = "newSuscription" , urlPatterns = {"/newSuscription"})
public class newSuscription extends HttpServlet {

    private HashMap<String, Suscripcion> suscripcionHashMap;

    @Override
    public void init() throws ServletException {
        suscripcionHashMap = new HashMap<>();
        suscripcionHashMap.put("GOLD", new Suscripcion("gold",50));
        suscripcionHashMap.put("DIAMOND", new Suscripcion("diamond",250));
        suscripcionHashMap.put("RADIO",new Suscripcion("radio",300));
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String placa = req.getParameter("placa");
        String tarjeta = req.getParameter("tarjeta");
        String dni = req.getParameter("dni");
        String plan = req.getParameter("plan");
        try {
            Suscripcion suscripcion = suscripcionHashMap.get(plan.toUpperCase()).clone();
            Suscriptor suscriptor = new Suscriptor(dni,placa,tarjeta,suscripcion);
            SuscripcionDAO.newSuscripcion(suscriptor);
            req.setAttribute("suscriptor", suscriptor);
            req.getRequestDispatcher("resumenSuscripcion.jsp").forward(req, resp);
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req, resp);
    }
}
