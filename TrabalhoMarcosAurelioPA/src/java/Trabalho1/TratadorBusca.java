/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Trabalho1;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Marcos Filho
 */
public class TratadorBusca implements Tratador{
    int nResults = 3;
    @Override
    public String processar(
            HttpServletRequest request,
            HttpServletResponse response) {
        String url ="";
        String botaoEscolhido = request.getParameter("botaoEscolhido");
        Livro l = new Livro();
        request.getSession().setAttribute("query",request.getParameter("query"));

        switch (botaoEscolhido) {
            case "Buscar":{
                int tipo = Integer.parseInt(request.getParameter("tipo"));
                String query = request.getParameter("query");
                List resultado = null;
                
                try {
                     resultado = (new BancoDAO()).doSelect(tipo,query);
                } catch (SQLException ex) {
                    Logger.getLogger(TratadorBusca.class.getName()).log(Level.SEVERE, null, ex);
                }
                request.getSession().setAttribute("responseBuscaLen", Integer.toString(resultado.size()));
                    resultado = null;
                try {
                    resultado = (new BancoDAO()).doSelect(tipo,query);
                } catch (SQLException ex) {
                    Logger.getLogger(TratadorBusca.class.getName()).log(Level.SEVERE, null, ex);
                }
                
                List pg =new ArrayList() ;
                if(resultado.size()%nResults==0){
                    for(int i = 0;i < (int)(resultado.size()/nResults) ;i++){
                        pg.add(i);
                    }
                }else{
                    for(int i = 0;i < (int)(resultado.size()/nResults)+1 ;i++){
                        pg.add(i);
                    }     
                }
                request.getSession().setAttribute("pages", pg);

                
                request.getSession().setAttribute("responseBusca", resultado);
                
                List resultPage = new ArrayList();
                for(int i=0;i<nResults;i++){
                   if(resultado.size()-1 < i){
                                break;
                   }   
                   resultPage.add(resultado.get(i));
                }
                request.getSession().setAttribute("pageResult",resultPage);

                url="/busca.jsp";
                break;
            }
            case "catalogo":{
                String id = request.getParameter("idEscolhido");
                List resultado = null;
                try {
                     resultado = (new BancoDAO()).doSelect(2,id);
                } catch (SQLException ex) {
                    Logger.getLogger(TratadorBusca.class.getName()).log(Level.SEVERE, null, ex);
                }
                Livro res =(Livro) resultado.get(0);
                request.getSession().setAttribute("idC","ID:"+res.getId());
                request.getSession().setAttribute("autorC",res.getAutor());
                request.getSession().setAttribute("tituloC",res.getNome());
    
                url="/catalogo.jsp";
                break;
            }
            case "pagina":{
                        int pagina = Integer.parseInt(request.getParameter("idPagina"));
                        List all = (List) request.getSession().getAttribute("responseBusca");
                        List resp = new ArrayList();
                        for(int i=(pagina)*nResults ; i < (pagina+1)*nResults ; i++){
                            if(all.size()-1 < i){
                                break;
                            }         
                            resp.add(all.get(i));
                        }
                        request.getSession().setAttribute("pageResult",resp);

                        url="/busca.jsp";
                        
                        break;
            }
        }
        return url;  
    }
    
}
