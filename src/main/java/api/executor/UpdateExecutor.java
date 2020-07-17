/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package api.executor;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;
import javax.json.*;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import models.*;

/**
 *
 * @author bayan
 */
public class UpdateExecutor extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet UpdateExecutor</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet UpdateExecutor at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        PrintWriter out = response.getWriter();
          request.setCharacterEncoding("UTF-8");
        int id = -1;
        try {
            id = Integer.parseInt(request.getParameter("id"));

        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            JsonReader jsonReader = Json.createReader(request.getReader());

            JsonObject jsonObject = jsonReader.readObject();

     
            int sectionId = jsonObject.getInt("sId");
            String spec = jsonObject.getString("spec");
            String desc = jsonObject.getString("desc");

            models.DbHelper db = new models.DbHelper();

            ArrayList rserv = new ArrayList();
           JsonArray services = jsonObject.getJsonArray("services");

            for (int i = 0; i < services.size(); i++) {
                JsonObject service = services.getJsonObject(i);
                String title = service.getString("sTitle");
                int price = service.getInt("sPrice");
                Service s = new Service(title, price);
                rserv.add(s);
            }
            Executor executor = new Executor();
            executor.setId(id);
            executor.setSectionId(sectionId);
            executor.setSpecialztn(spec);
            executor.setDescriptn(desc);
            executor.setServices(rserv);

            db.updateExecutor(executor);
            out.print(executor.getId());
        } catch (Exception e) {
            out.print("Error: " + e.getMessage());
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
