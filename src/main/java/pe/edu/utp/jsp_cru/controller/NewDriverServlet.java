package pe.edu.utp.jsp_cru.controller;

import pe.edu.utp.jsp_cru.business.ReservaDAO;
import pe.edu.utp.jsp_cru.model.Reserva;
import pe.edu.utp.jsp_cru.service.Validador;
import pe.edu.utp.jsp_cru.utils.LogFile;
import pe.edu.utp.jsp_cru.model.TipoVehiculo;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;

@WebServlet(name = "driver_new", urlPatterns = {"/driver_new"})
public class NewDriverServlet extends HttpServlet {


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //libro(id, titulo, genero, precio, fecha_pub, img, descripcion)
        String paramfechaEntrada = req.getParameter("fechaHora");
        String TipoVehiculo = req.getParameter("tipoVehiculo");
        String placa = req.getParameter("placa");
        String dni = req.getParameter("dni");
        String nombre = req.getParameter("nombreChofer");
        String lavado = req.getParameter("lavado") == null ? "nolavado" : req.getParameter("lavado");

        TipoVehiculo tipoVehiculo = pe.edu.utp.jsp_cru.model.TipoVehiculo.valueOf(String.valueOf(pe.edu.utp.jsp_cru.model.TipoVehiculo.valueOf(TipoVehiculo)));

        // Crear nuevo libro y agregarlo al catalogo
        try {
            // Hacemos un formateo para la fecha y año del libro
            LocalDateTime entrada = LocalDateTime.parse(paramfechaEntrada);

            // Generarmos un validador para el nuevo libro
            Validador.validarConductor(nombre, dni, placa, tipoVehiculo, entrada);

            // Determinar tipo de pedido
            boolean reserva_regular = lavado.equalsIgnoreCase("nolavado");
            boolean reserva_completo = lavado.equalsIgnoreCase("lavado");

            Reserva reserva = null;
            if (reserva_regular){
                System.out.println(">>>>>>>>>>>>>>>>> Reserva regular");
                reserva = crearReservaRegular(nombre, dni, placa, tipoVehiculo, entrada);
            }else{
                System.out.printf(">>>>>>>>>>>>>>>>> Pedido Total (lavado:%s)%n", lavado);
                reserva = crearReservaConLavado(nombre, dni, placa, tipoVehiculo, entrada, lavado);
            }

            // Agregamos la reserva al registro
            ReservaDAO.newConductor(reserva);
            req.setAttribute("reserva", reserva);
            req.getRequestDispatcher("resumen.jsp").forward(req, resp);
        } catch ( IOException | ServletException | IllegalArgumentException | IllegalStateException e) {
            LogFile.error(e.getMessage());
            resp.sendRedirect(req.getContextPath() + "/error.jsp");
        }

    }

    public Reserva crearReservaRegular(String nombre, String dni, String placa, TipoVehiculo tipoVehiculo, LocalDateTime entrada){
        int DNI = Integer.parseInt(dni);
        return new Reserva.Builder(nombre, DNI, tipoVehiculo, placa, entrada).build();
    }

    public Reserva crearReservaConLavado(String nombre, String dni, String placa, TipoVehiculo tipoVehiculo, LocalDateTime entrada, String lavado){
        int DNI = Integer.parseInt(dni);
        return new Reserva.Builder(nombre, DNI, tipoVehiculo, placa, entrada).withLavado().build();
    }
}
