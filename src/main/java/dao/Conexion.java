/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import interfaces.IConexion;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author martinez
 */
public class Conexion implements IConexion{
    private String cadenaConexion="jdbc:mysql://localhost:3306/pizzeria";
    private String usuario="root";
    private String pass="mysql123";

    @Override
    public Connection crearConexion() {
        try{
            Connection c = DriverManager.getConnection(cadenaConexion, usuario, pass);
            return c;
        }
        catch(SQLException e){
            System.out.println("Hubo un error.");
        }
        return null;
    }
}