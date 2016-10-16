/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author Rodrigo
 */
public class ConnectionFactory {
 
 public static Connection getConnection()
 {
  try 
  {
   return DriverManager.getConnection("jdbc:derby://localhost:1527/album_system", "root", "admin");
  } 
  catch (SQLException e) 
  {
   //Logger.getLogger(ConnectionFactory.class.getName()).log(Level.SEVERE, null, e);
      System.out.println("erro sqlexception");
   throw new RuntimeException("Erro SQLException ao abrir a conexão em connectionFactory", e);
 }

}
}
