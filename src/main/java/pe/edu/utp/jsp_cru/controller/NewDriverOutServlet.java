package pe.edu.utp.jsp_cru.controller;

import pe.edu.utp.jsp_cru.business.ReservaDAO;
import pe.edu.utp.jsp_cru.model.Salida;
import pe.edu.utp.jsp_cru.model.TipoVehiculo;
import pe.edu.utp.jsp_cru.utils.LogFile;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;

@WebServlet(name = "driver_out", urlPatterns = {"/driver_out"})
public class NewDriverOutServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int id = Integer.parseInt(req.getParameter("txtId"));
        int idConductor = Integer.parseInt(req.getParameter("txtIdConductor"));
        int idVehiculo = Integer.parseInt(req.getParameter("txtIdVehiculo"));
        String conductor = req.getParameter("nombreChofer");
        String placa = req.getParameter("placa");
        String vehiculo = req.getParameter("tipoVehiculo");
        LocalDateTime fechaEntrada = LocalDateTime.parse(req.getParameter("fechaHora"));
        String lavadito = req.getParameter("lavadoAuto").toLowerCase();

        boolean lavado = false;
        if (lavadito == "si") {
            lavado = true;
        } else if (lavadito == "no") {
            lavado = false;
        }

        TipoVehiculo tipoVehiculo = pe.edu.utp.jsp_cru.model.TipoVehiculo.valueOf(String.valueOf(pe.edu.utp.jsp_cru.model.TipoVehiculo.valueOf(vehiculo)));
        LocalDateTime fechaSalida = LocalDateTime.now();

        // Crear nuevo libro y agregarlo al catalogo
        try {
            double total = ReservaDAO.getTotal(fechaEntrada, lavado);
            // Creamos el objeto
            Salida salida = new Salida(id, conductor, placa, tipoVehiculo, lavado, fechaEntrada, fechaSalida, total, idConductor, idVehiculo);

            // Agregamos la reserva al registro
            ReservaDAO.newRegistroSalida(salida);
            req.setAttribute("salida", salida);
            req.getRequestDispatcher("driver").forward(req, resp);
        } catch ( IOException | ServletException | IllegalArgumentException | IllegalStateException e) {
            LogFile.error(e.getMessage());
            resp.sendRedirect(req.getContextPath() + "/error.jsp");
        }
    }
}
