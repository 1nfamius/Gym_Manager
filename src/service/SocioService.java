package service;

import model.Socio;
import utils.DBConnection;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class SocioService {

    public boolean insertar(Socio socio) {
        String sql = "INSERT INTO socio (nombre, apellidos, email, telefono, fecha_nacimiento, fecha_alta, activo) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, socio.getNombre());
            ps.setString(2, socio.getApellidos());
            ps.setString(3, socio.getEmail());
            ps.setString(4, socio.getTelefono());
            ps.setDate  (5, socio.getFechaNacimiento() != null
                    ? Date.valueOf(socio.getFechaNacimiento()) : null);
            ps.setDate  (6, Date.valueOf(socio.getFechaAlta()));
            ps.setBoolean(7, socio.isActivo());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("[SocioService] Error al insertar socio: " + e.getMessage());
            return false;
        }
    }

    public List<Socio> listarActivos() {
        List<Socio> lista = new ArrayList<>();
        String sql = "SELECT * FROM socio WHERE activo = TRUE ORDER BY apellidos, nombre";

        try (Connection con = DBConnection.getConnection();
             Statement  st  = con.createStatement();
             ResultSet  rs  = st.executeQuery(sql)) {

            while (rs.next()) {
                lista.add(mapearSocio(rs));
            }
        } catch (SQLException e) {
            System.err.println("[SocioService] Error al listar socios: " + e.getMessage());
        }
        return lista;
    }

    public Socio buscarPorId(int idSocio) {
        String sql = "SELECT * FROM socio WHERE id_socio = ?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idSocio);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return mapearSocio(rs);

        } catch (SQLException e) {
            System.err.println("[SocioService] Error al buscar socio por id: " + e.getMessage());
        }
        return null;
    }

    public Socio buscarPorEmail(String email) {
        String sql = "SELECT * FROM socio WHERE email = ?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return mapearSocio(rs);

        } catch (SQLException e) {
            System.err.println("[SocioService] Error al buscar socio por email: " + e.getMessage());
        }
        return null;
    }

    public boolean actualizar(Socio socio) {
        String sql = "UPDATE socio SET nombre = ?, apellidos = ?, email = ?, telefono = ? "
                + "WHERE id_socio = ?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, socio.getNombre());
            ps.setString(2, socio.getApellidos());
            ps.setString(3, socio.getEmail());
            ps.setString(4, socio.getTelefono());
            ps.setInt   (5, socio.getIdSocio());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("[SocioService] Error al actualizar socio: " + e.getMessage());
            return false;
        }
    }

    public boolean darDeBaja(int idSocio) {
        String sql = "UPDATE socio SET activo = FALSE WHERE id_socio = ?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idSocio);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("[SocioService] Error al dar de baja socio: " + e.getMessage());
            return false;
        }
    }

    private Socio mapearSocio(ResultSet rs) throws SQLException {
        Date fnac = rs.getDate("fecha_nacimiento");
        return new Socio(
                rs.getInt    ("id_socio"),
                rs.getString ("nombre"),
                rs.getString ("apellidos"),
                rs.getString ("email"),
                rs.getString ("telefono"),
                fnac != null ? fnac.toLocalDate() : null,
                rs.getDate   ("fecha_alta").toLocalDate(),
                rs.getBoolean("activo")
        );
    }
}