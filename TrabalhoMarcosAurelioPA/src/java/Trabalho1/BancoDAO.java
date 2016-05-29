/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Trabalho1;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Marcos Filho
 */
public class BancoDAO {
    private Connection conn;
    private PreparedStatement query;
    private ResultSet result;
    
    public List doSelect(int tipo , String arg) throws SQLException{//
        List livros = new ArrayList();
        Livro l;
        String q = null;
        conn=ConfigBD.connect();
        switch(tipo){             
            case 0:{ //Autor
                String[] palavras = arg.split(" ");
                for(int i=1;i<=palavras.length;i++){
                    if(i==1){
                        q = "SELECT * FROM referencias WHERE (autoria like ?) ";
                    }else{
                        q+="or (autoria like ?)";
                    } 
                }
                query=conn.prepareStatement(q);
                for(int i=1;i<=palavras.length;i++){
                    query.setString(i, "%"+palavras[i-1]+"%");
                }
                result = query.executeQuery();
                while(result.next()){
                    l = new Livro();
                    l.setId(result.getInt("serialno"));
                    l.setNome(result.getString("titulo"));
                    l.setAutor(result.getString("autoria"));
                    livros.add(l);
                }
                
                break;
            }
            case 1:{//Titulo   
                String[] palavras = arg.split(" ");
                for(int i=1;i<=palavras.length;i++){
                    if(i==1){
                        q = "SELECT * FROM referencias WHERE (titulo like ?) ";
                    }else{
                        q+="or (titulo like ?)";
                    } 
                }               
                query=conn.prepareStatement(q);
                for(int i=1;i<=palavras.length;i++){
                    query.setString(i, "%"+palavras[i-1]+"%");
                }
                result = query.executeQuery();
                while(result.next()){
                    l = new Livro();
                    l.setId(result.getInt("serialno"));
                    l.setNome(result.getString("titulo"));
                    l.setAutor(result.getString("autoria"));
                    livros.add(l);
                }
                break;
            }
            case 2:{
                query=conn.prepareStatement("SELECT * FROM referencias where serialno = ?");
                query.setInt(1,Integer.parseInt(arg));
                result = query.executeQuery();
                while(result.next()){
                    l = new Livro();
                    l.setId(result.getInt("serialno"));
                    l.setNome(result.getString("titulo"));
                    l.setAutor(result.getString("autoria"));
                    livros.add(l);
                }
                break;
            }
        }
        
        ConfigBD.disconnect(conn);
        query.close();
        result.close();
        
        return livros;
    }
    
    public void doInsert(Livro l) throws SQLException{
        conn=ConfigBD.connect();
        query=conn.prepareStatement("INSERT INTO referencias(titulo,autoria) VALUES(?,?) RETURNING serialno ");
        query.setString(1,l.getNome());
        query.setString(2,l.getAutor());
        query.executeUpdate();
        result = query.getResultSet();
        int last = result.getInt(1);
        String palavras[];
        palavras = l.getNome().split(" ");
        for (String palavra : palavras) {
           query=conn.prepareStatement("INSERT INTO palavrasdotitulo(palavra,serialno_referencias) VALUES(?,?)");
           query.setString(1, palavra);
           query.setInt(2,last);
           query.executeUpdate();
        }
        ConfigBD.disconnect(conn);
        query.close();
        result.close();
    }
    
    public void doDelete(int id) throws SQLException{
        conn=ConfigBD.connect();
        query=conn.prepareStatement("DELETE FROM referencias WHERE serialno = ?");
        query.setInt(1, id);
        query.executeUpdate();
        ConfigBD.disconnect(conn);
        query.close();
    }
    
    public void doUpdate(Livro l) throws SQLException{
        conn=ConfigBD.connect();
        query=conn.prepareStatement("DELETE FROM palavrasdotitulo WHERE serialno_referencias = ?");
        query.setInt(1,l.getId());
        query.executeUpdate();
        query=conn.prepareStatement("UPDATE referencias SET autoria = ? , titulo = ? WHERE serialno = ?");
        query.setString(1,l.getAutor());
        query.setString(2,l.getNome());
        query.setInt(3, l.getId());
        query.executeUpdate();
        String palavras[];
        palavras = l.getNome().split(" ");
        for (String palavra : palavras) {
           query=conn.prepareStatement("INSERT INTO palavrasdotitulo(palavra,serialno_referencias) VALUES(?,?)");
           query.setString(1, palavra);
           query.setInt(2,l.getId());
           query.executeUpdate();
        }
    }
    
}
