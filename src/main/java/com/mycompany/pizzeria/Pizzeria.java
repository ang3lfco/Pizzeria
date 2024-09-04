/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package com.mycompany.pizzeria;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author martinez
 */
public class Pizzeria {

    public static void main(String[] args) {
        System.out.println("Hello World!");

        String cadenaConexion = "jdbc:mysql://localhost:3306/pizzeria";
        String user = "root";
        String pass = "mysql123";
        String i = "INSERT INTO productos (nombre, precio, descripcion) VALUES (?,?,?)";
        String s = "SELECT * FROM productos WHERE nombre = ?";

        try {
            Connection c = DriverManager.getConnection(cadenaConexion, user, pass);
            PreparedStatement insert = c.prepareStatement(i, Statement.RETURN_GENERATED_KEYS);
            insert.setString(1, "Pizza de Champiñon");
            insert.setFloat(2, (float) 100.00);
            insert.setString(3, "Pizza sencilla repleta de champiñones");

            insert.executeUpdate();

            // Realizar la búsqueda
            PreparedStatement busqueda = c.prepareStatement(s);
            busqueda.setString(1, "Pizza de Champiñon");

            ResultSet rs = busqueda.executeQuery();
            if (rs.next()) {
                String nombre = rs.getString("nombre");
                float precio = rs.getFloat("precio");
                String descripcion = rs.getString("descripcion");

                System.out.println("Producto encontrado:");
                System.out.println("Nombre: " + nombre);
                System.out.println("Precio: " + precio);
                System.out.println("Descripción: " + descripcion);
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
