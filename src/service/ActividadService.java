package service;

import model.Actividad;
import model.Reserva;
import utils.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ActividadService {

    public List<Actividad> listarActivas() {
        List<Actividad> lista = new ArrayList<>();
        String sql = "SELECT a.*, CONCAT(i.nombre, ' ', i.apellidos) AS nombre_instructor "
                + "FROM actividad a "
                + "JOIN instructor i ON a.id_instructor = i.id_instructor "
                + "WHERE a.activa = TRUE ORDER BY a.nombre";

        try (Connection con = DBConnection.getConnection();
             Statement  st  = con.createStatement();
             ResultSet  rs  = st.executeQuery(sql)) {

            while (rs.next()) {
                lista.add(new Actividad(
                        rs.getInt    ("id_actividad"),
                        rs.getString ("nombre"),
                        rs.getString ("descripcion"),
                        rs.getInt    ("aforo_maximo"),
                        rs.getInt    ("duracion_min"),
                        rs.getInt    ("id_instructor"),
                        rs.getString ("nombre_instructor"),
                        rs.getBoolean("activa")
                ));
            }
        } catch (SQLException e) {
            System.err.println("[ActividadService] Error: " + e.getMessage());
        }
        return lista;
    }

    public boolean crearReserva(int idSocio, int idSesion) {
        String sqlReserva = "INSERT INTO reserva (id_socio, id_sesion, fecha_reserva, estado) "
                + "VALUES (?, ?, NOW(), 'confirmada')";
        String sqlPlazas  = "UPDATE sesion SET plazas_disponibles = plazas_disponibles - 1 "
                + "WHERE id_sesion = ? AND plazas_disponibles > 0";

        Connection con = null;
        try {
            con = DBConnection.getConnection();
            con.setAutoCommit(false);

            PreparedStatement psReserva = con.prepareStatement(sqlReserva);
            psReserva.setInt(1, idSocio);
            psReserva.setInt(2, idSesion);
            int filasReserva = psReserva.executeUpdate();

            PreparedStatement psPlazas = con.prepareStatement(sqlPlazas);
            psPlazas.setInt(1, idSesion);
            int filasPlazas = psPlazas.executeUpdate();

            if (filasReserva > 0 && filasPlazas > 0) {
                con.commit();
                return true;
            } else {
                con.rollback();
                System.out.println("No hay plazas disponibles en esa sesión.");
                return false;
            }

        } catch (SQLException e) {
            try { if (con != null) con.rollback(); } catch (SQLException ex) { /* ignorar */ }
            System.err.println("[ActividadService] Error al crear reserva: " + e.getMessage());
            return false;
        } finally {
            try { if (con != null) con.setAutoCommit(true); } catch (SQLException e) { /* ignorar */ }
        }
    }

    public List<Reserva> listarReservasSocio(int idSocio) {
        List<Reserva> lista = new ArrayList<>();
        String sql = "SELECT r.id_reserva, r.id_socio, r.id_sesion, r.fecha_reserva, r.estado, "
                + "       CONCAT(s2.nombre, ' ', s2.apellidos) AS nombre_socio, "
                + "       a.nombre AS nombre_actividad, "
                + "       ses.fecha_hora "
                + "FROM reserva r "
                + "JOIN socio      s2  ON r.id_socio    = s2.id_socio "
                + "JOIN sesion     ses ON r.id_sesion   = ses.id_sesion "
                + "JOIN actividad  a   ON ses.id_actividad = a.id_actividad "
                + "WHERE r.id_socio = ? AND r.estado = 'confirmada' "
                + "ORDER BY ses.fecha_hora";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idSocio);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Reserva reserva = new Reserva(
                        rs.getInt      ("id_reserva"),
                        rs.getInt      ("id_socio"),
                        rs.getInt      ("id_sesion"),
                        rs.getTimestamp("fecha_reserva").toLocalDateTime(),
                        Reserva.Estado.valueOf(rs.getString("estado").toUpperCase())
                );
                reserva.setNombreSocio    (rs.getString  ("nombre_socio"));
                reserva.setNombreActividad(rs.getString  ("nombre_actividad"));
                reserva.setFechaHoraSesion(rs.getTimestamp("fecha_hora").toLocalDateTime());
                lista.add(reserva);
            }
        } catch (SQLException e) {
            System.err.println("[ActividadService] Error al listar reservas: " + e.getMessage());
        }
        return lista;
    }

    public boolean cancelarReserva(int idReserva) {
        String sqlCancelar = "UPDATE reserva SET estado = 'cancelada' WHERE id_reserva = ?";
        String sqlPlazas   = "UPDATE sesion SET plazas_disponibles = plazas_disponibles + 1 "
                + "WHERE id_sesion = (SELECT id_sesion FROM reserva WHERE id_reserva = ?)";

        Connection con = null;
        try {
            con = DBConnection.getConnection();
            con.setAutoCommit(false);

            PreparedStatement psCancelar = con.prepareStatement(sqlCancelar);
            psCancelar.setInt(1, idReserva);
            psCancelar.executeUpdate();

            PreparedStatement psPlazas = con.prepareStatement(sqlPlazas);
            psPlazas.setInt(1, idReserva);
            psPlazas.executeUpdate();

            con.commit();
            return true;

        } catch (SQLException e) {
            try { if (con != null) con.rollback(); } catch (SQLException ex) { /* ignorar */ }
            System.err.println("[ActividadService] Error al cancelar reserva: " + e.getMessage());
            return false;
        } finally {
            try { if (con != null) con.setAutoCommit(true); } catch (SQLException e) { /* ignorar */ }
        }
    }
}