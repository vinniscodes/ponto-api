package org.execut.pontoapi.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.execut.pontoapi.repository.FilialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class TenantInterceptor implements HandlerInterceptor {

    @Autowired
    private FilialRepository filialRepository;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // Ignora o CORS preflight (OPTIONS)
        if (request.getMethod().equals("OPTIONS")) return true;

        String clientKey = request.getHeader("X-Client-Key");

        if (clientKey == null || clientKey.trim().isEmpty()) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Acesso Negado: Chave de filial (X-Client-Key) ausente.");
            return false;
        }

        // Valida se a empresa realmente existe no banco de dados
        String tenantId = clientKey.toUpperCase().trim();
        if (!filialRepository.existsById(tenantId)) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Acesso Negado: Filial não encontrada ou inativa.");
            return false;
        }

        // Passa o TenantId validado para a requisição, para os Controllers usarem
        request.setAttribute("tenantId", tenantId);
        return true;
    }
}