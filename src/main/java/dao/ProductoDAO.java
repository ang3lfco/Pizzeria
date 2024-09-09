/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import interfaces.IConexion;
import interfaces.IProducto;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import objetos.Producto;

/**
 *
 * @author martinez
 */
public class ProductoDAO implements IProducto{
    private IConexion conexion;

    public ProductoDAO(IConexion conexion) {
        this.conexion = conexion;
    }

    @Override
    public boolean agregar(Producto producto) {
        String sql = "INSERT INTO productos (nombre, precio, descripcion) VALUES (?, ?, ?)";
        try (Connection conn = conexion.crearConexion();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            stmt.setString(1, producto.getNombre());
            stmt.setFloat(2, producto.getPrecio());
            stmt.setString(3, producto.getDescripcion());

            int rowsInserted = stmt.executeUpdate();
            if (rowsInserted > 0) {
                // Recuperar el ID generado automáticamente
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        producto.setId(generatedKeys.getInt(1));
                    }
                }
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean eliminar(int id) {
        try {
            Connection bd = conexion.crearConexion();
            String eliminar = "DELETE FROM productos WHERE _id = ?";
            PreparedStatement pst = bd.prepareStatement(eliminar);
            pst.setInt(1, id);
            int filasAfectadas = pst.executeUpdate();
            return filasAfectadas > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean actualizar(Producto producto) {
        try {
            Connection bd = conexion.crearConexion();
            String actualizar = "UPDATE productos SET nombre = ?, precio = ?, descripcion = ? WHERE _id = ?";
            PreparedStatement pst = bd.prepareStatement(actualizar);
            pst.setString(1, producto.getNombre());
            pst.setFloat(2, producto.getPrecio());
            pst.setString(3, producto.getDescripcion());
            pst.setInt(4, producto.getId());
            int filasAfectadas = pst.executeUpdate();
            return filasAfectadas > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public Producto consultar(int id) {
        Producto producto = null;
        Connection bd = null;
        PreparedStatement pst = null;
        ResultSet rs = null;

        try {
            bd = conexion.crearConexion();
            String consulta = "SELECT * FROM productos WHERE _id = ?";
            pst = bd.prepareStatement(consulta);
            pst.setInt(1, id);
            rs = pst.executeQuery();

            if (rs.next()) {
                int productoId = rs.getInt("_id");
                String nombre = rs.getString("nombre");
                float precio = rs.getFloat("precio");
                String descripcion = rs.getString("descripcion");

                producto = new Producto(nombre, precio, descripcion);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (pst != null) pst.close();
                if (bd != null) bd.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return producto;
    }

    @Override
    public List<Producto> consultar() {
        List<Producto> productos = new ArrayList<>();
        String sql = "SELECT _id, nombre, precio, descripcion FROM productos";

        try (Connection conn = conexion.crearConexion();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Producto producto = new Producto();
                producto.setId(rs.getInt("_id"));  // Asegurarse de recuperar correctamente el ID
                producto.setNombre(rs.getString("nombre"));
                producto.setPrecio(rs.getFloat("precio"));
                producto.setDescripcion(rs.getString("descripcion"));
                productos.add(producto);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return productos;
    }
}