package Trabalho1;

import javax.servlet.http.*;

public interface Tratador {

    public String processar(
            HttpServletRequest request,
            HttpServletResponse response);
}
