/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Trabalho1;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Marcos Filho
 */
public class ConfigBD {
    
    public static Connection connect() { 
        Connection conn =null ;
        String url ="jdbc:postgresql://localhost:5432/referenciasbibliograficas";
        String user ="postgres";
        String pass ="marcos";
        try {  
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ConfigBD.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            conn = DriverManager.getConnection(url,user,pass);
        } catch (SQLException ex) {
            Logger.getLogger(ConfigBD.class.getName()).log(Level.SEVERE, null, ex);
        }
        return conn;
    }
    
    public static void disconnect(Connection conn){
        try {
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(ConfigBD.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
