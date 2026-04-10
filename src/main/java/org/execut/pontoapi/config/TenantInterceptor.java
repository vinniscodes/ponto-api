package org.execut.pontoapi.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class TenantInterceptor implements HandlerInterceptor {

    private static final String TENANT_HEADER = "X-Client-Key";

    // O ThreadLocal guarda o ID da empresa apenas durante o tempo de vida daquela requisição
    private static final ThreadLocal<String> currentTenant = new ThreadLocal<>();

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String tenantId = request.getHeader(TENANT_HEADER);

        // Se a rota for do mobile e não tiver a chave da empresa, o servidor rejeita na hora (Segurança Zero-Trust)
        if (request.getRequestURI().contains("/api/mobile/") && (tenantId == null || tenantId.isEmpty())) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("{\"erro\": \"Acesso Negado: Chave de Cliente Ausente\"}");
            return false;
        }

        currentTenant.set(tenantId);
        return true;
    }

    public static String getCurrentTenant() {
        return currentTenant.get();
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        // Limpa a memória após processar para evitar vazamento de dados entre clientes
        currentTenant.remove();
    }
}