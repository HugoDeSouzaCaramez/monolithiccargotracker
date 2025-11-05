package com.br.hugo.ddd.monolithiccargotracker.web;

import java.io.*;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/servlet")
public class HelloServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html; charset=UTF-8");
        PrintWriter out = response.getWriter();

        try {
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Cargo Tracker - Servlet Test</title>");
            out.println("<style>");
            out.println("body { font-family: Arial, sans-serif; margin: 40px; background-color: #f5f5f5; }");
            out.println(
                    ".container { background: white; padding: 20px; border-radius: 8px; box-shadow: 0 2px 4px rgba(0,0,0,0.1); }");
            out.println("h1 { color: #2c3e50; }");
            out.println(
                    ".info { background: #e8f4fd; padding: 15px; border-left: 4px solid #3498db; margin: 20px 0; }");
            out.println(".endpoints { background: #f8f9fa; padding: 15px; border-radius: 5px; }");
            out.println("a { color: #2980b9; text-decoration: none; }");
            out.println("a:hover { text-decoration: underline; }");
            out.println("</style>");
            out.println("</head>");
            out.println("<body>");
            out.println("<div class='container'>");
            out.println("<h1>🚢 Cargo Tracker - Sistema DDD</h1>");
            out.println("<div class='info'>");
            out.println("<h3>Servlet funcionando corretamente!</h3>");
            out.println("<p><strong>Timestamp:</strong> " + new java.util.Date() + "</p>");
            out.println("<p><strong>Context Path:</strong> " + request.getContextPath() + "</p>");
            out.println("</div>");

            out.println("<div class='endpoints'>");
            out.println("<h3>📋 Endpoints Disponíveis:</h3>");
            out.println("<ul>");
            out.println("<li><a href='" + request.getContextPath()
                    + "/serviceapi/cargobooking' target='_blank'>POST /serviceapi/cargobooking</a> - Reservar carga</li>");
            out.println("<li><a href='" + request.getContextPath()
                    + "/serviceapi/cargorouting' target='_blank'>POST /serviceapi/cargorouting</a> - Roteirizar carga</li>");
            out.println("<li><a href='" + request.getContextPath()
                    + "/serviceapi/cargohandling' target='_blank'>POST /serviceapi/cargohandling</a> - Registrar manuseio</li>");
            out.println("<li><a href='" + request.getContextPath()
                    + "/serviceapi/voyageRouting/optimalRoute' target='_blank'>GET /serviceapi/voyageRouting/optimalRoute</a> - Rota ótima</li>");
            out.println("</ul>");
            out.println("</div>");

            out.println("<div style='margin-top: 20px;'>");
            out.println("<a href='" + request.getContextPath() + "/index.html'>&larr; Voltar para Home</a>");
            out.println("</div>");

            out.println("</div>");
            out.println("</body>");
            out.println("</html>");

        } finally {
            out.close();
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}
