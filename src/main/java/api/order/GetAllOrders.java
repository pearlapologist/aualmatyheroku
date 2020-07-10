/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package api.order;

import java.io.IOException;
import java.io.PrintWriter;
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
public class GetAllOrders extends HttpServlet {

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
        PrintWriter out = response.getWriter();

        models.DbHelper db = new models.DbHelper();
        java.util.ArrayList<models.Order> orders = db.getOrders();

        if (orders == null) {
            javax.json.JsonObjectBuilder objectBuilder = javax.json.Json.createObjectBuilder().
                    add("Ошибка", "Заказов не найдено");
            out.print(objectBuilder.build().toString());
            return;
        }

        javax.json.JsonObjectBuilder builderr = javax.json.Json.createObjectBuilder();
        javax.json.JsonArrayBuilder arrayBuilder = javax.json.Json.createArrayBuilder();

        for (Order r : orders) {
            JsonObjectBuilder orderBuilderr = Json.createObjectBuilder();

            orderBuilderr.add("id", r.getId());
            orderBuilderr.add("title", r.getTitle());
            orderBuilderr.add("customer", r.getCustomerId());
            orderBuilderr.add("section", r.getSectionId());
            orderBuilderr.add("price", r.getPrice());
            orderBuilderr.add("desc", r.getDescription());
            String b = r.getDeadline() + "";
            orderBuilderr.add("dealine", b);
            String c = r.getCreated_date() + "";
            orderBuilderr.add("created", c);

            arrayBuilder.add(orderBuilderr);
        }

        builderr.add("orders", arrayBuilder);

        javax.json.JsonObject jsonObject = builderr.build();
        out.print(jsonObject.toString());
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
        processRequest(request, response);
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
