/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Trabalho1;

import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Marcos Filho
 */
public class TratadorCatalogo implements Tratador{

    @Override
    public String processar(HttpServletRequest request,HttpServletResponse response) {
        String url ="/catalogo.jsp";
        String botaoEscolhido = request.getParameter("botaoEscolhido");
        Livro l = new Livro();
        switch (botaoEscolhido){
            case "Adcionar":{
                l.setAutor(request.getParameter("autor"));
                l.setNome(request.getParameter("titulo"));
                try {
                    (new BancoDAO()).doInsert(l);
                    request.getSession().setAttribute("infoCatalogo","Criado");
                } catch (SQLException ex) {
                    Logger.getLogger(TratadorCatalogo.class.getName()).log(Level.SEVERE, null, ex);
                }
                request.getSession().setAttribute("query",l.getNome());
                url="/busca.jsp";

                break;
            }
            case "Deletar":{
                int id = Integer.parseInt(request.getParameter("id").split(":")[1]);
                try {
                    (new BancoDAO()).doDelete(id);
                } catch (SQLException ex) {
                    Logger.getLogger(TratadorCatalogo.class.getName()).log(Level.SEVERE, null, ex);
                }
                request.getSession().setAttribute("responseBuscaLen",null);
                request.getSession().setAttribute("responseBusca",null);
                request.getSession().setAttribute("query",request.getParameter("titulo"));

                url="/busca.jsp";
                break;
            }
            case "Alterar":{
                int id = Integer.parseInt(request.getParameter("id").split(":")[1]);
                l.setId(id);
                l.setAutor(request.getParameter("autor"));
                l.setNome(request.getParameter("titulo"));
                try {
                    (new BancoDAO()).doUpdate(l);
                } catch (SQLException ex) {
                    Logger.getLogger(TratadorCatalogo.class.getName()).log(Level.SEVERE, null, ex);
                }
                request.getSession().setAttribute("tituloC", l.getNome());
                request.getSession().setAttribute("infoCatalogo","Alterado!");

                url="catalogo.jsp";

                break;
            }
            case "Limpar":{
                request.getSession().setAttribute("idC", null);
                request.getSession().setAttribute("autorC", null);
                request.getSession().setAttribute("tituloC", null);
                request.getSession().setAttribute("infoCatalogo", null);        
                break;
            }
            case "Voltar":{
                url="/busca.jsp";
                break;
            }
        }
       
        return url;
    }
}
