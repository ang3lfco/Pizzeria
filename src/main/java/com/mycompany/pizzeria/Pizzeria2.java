/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.pizzeria;

import dao.Conexion;
import dao.ProductoDAO;
import java.util.List;
import objetos.Producto;

/**
 *
 * @author martinez
 */
public class Pizzeria2 {
    public static void main(String[] args) {
        // Crear una conexión a la base de datos
        Conexion conexion = new Conexion();
        ProductoDAO productoDAO = new ProductoDAO(conexion);

        // Agregar 3 productos
        Producto producto1 = new Producto("Pizza Margherita", 12.50f, "Pizza clásica con tomate y mozzarella");
        Producto producto2 = new Producto("Pizza Mexicana", 14.00f, "Pizza con chorizo, frijol y chile");
        Producto producto3 = new Producto("Pizza Veggie", 13.00f, "Pizza con vegetales variados");
        Producto producto4 = new Producto("Pizza Hawaiana", 15.00f, "Pizza con jamón y piña");
        Producto producto5 = new Producto("Pizza Cuatro Quesos", 16.00f, "Pizza con una mezcla de cuatro quesos");
        Producto producto6 = new Producto("Pizza BBQ", 14.50f, "Pizza con salsa barbacoa, pollo y cebolla");

        productoDAO.agregar(producto4);
        productoDAO.agregar(producto5);
        productoDAO.agregar(producto6);

        // Eliminar un producto
        boolean eliminado = productoDAO.eliminar(3); // Eliminar producto con ID 2
        System.out.println("Producto eliminado: " + eliminado);

        // Consultar un producto por ID
        Producto productoConsultado = productoDAO.consultar(1); // Consultar producto con ID 1
        if (productoConsultado != null) {
            System.out.println("Producto consultado: " + productoConsultado);
        } else {
            System.out.println("Producto no encontrado.");
        }

        // Consultar todos los productos
        List<Producto> todosProductos = productoDAO.consultar();
        System.out.println("Todos los productos:");
        for (Producto p : todosProductos) {
            System.out.println(p);
        }
    }
}
