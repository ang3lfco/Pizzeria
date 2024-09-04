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
        try{
            Connection bd = conexion.crearConexion();
            String insertar = "INSERT INTO productos (nombre, precio, descripcion) VALUES (?,?,?)";
            PreparedStatement pst = bd.prepareStatement(insertar, Statement.RETURN_GENERATED_KEYS);
            pst.setString(1, producto.getNombre());
            pst.setFloat(2, producto.getPrecio());
            pst.setString(3, producto.getDescripcion());
            pst.executeUpdate();
        }
        catch(SQLException e){
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
        try {
            Connection bd = conexion.crearConexion();
            String consulta = "SELECT * FROM productos";
            PreparedStatement pst = bd.prepareStatement(consulta);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("_id");
                String nombre = rs.getString("nombre");
                float precio = rs.getFloat("precio");
                String descripcion = rs.getString("descripcion");

                Producto producto = new Producto(nombre, precio, descripcion);
                productos.add(producto);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return productos;
    }
}