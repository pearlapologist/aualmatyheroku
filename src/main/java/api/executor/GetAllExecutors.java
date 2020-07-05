/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package api.executor;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import models.*;

/**
 *
 * @author bayan
 */
public class GetAllExecutors extends HttpServlet {

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

        DbHelper db = new models.DbHelper();
        ArrayList<Executor> r = db.getAllExecutors();

        if (r == null) {
            javax.json.JsonObjectBuilder objectBuilder = javax.json.Json.createObjectBuilder().
                    add("Ошибка", "Специалистов не найдено");
            out.print(objectBuilder.build().toString());
            return;
        }

        javax.json.JsonObjectBuilder builderr = javax.json.Json.createObjectBuilder();
        javax.json.JsonArrayBuilder arrayBuilder = javax.json.Json.createArrayBuilder();

        for (Executor executor : r) {
            javax.json.JsonObjectBuilder executorBuilderr = javax.json.Json.createObjectBuilder();

            executorBuilderr.add("id", executor.getId());
            executorBuilderr.add("pId", executor.getPersonId());
            executorBuilderr.add("cId", executor.getSectionId());
            executorBuilderr.add("spec", executor.getSpecialztn());
            executorBuilderr.add("desc", executor.getDescriptn());

            javax.json.JsonArrayBuilder servicesArrayBuilder = javax.json.Json.createArrayBuilder();

            for (models.Service s : executor.getServices()) {
                javax.json.JsonObjectBuilder servicesBuilder
                                             = javax.json.Json.createObjectBuilder()
                                .add("sId", s.getId())
                                .add("sTitle", s.getTitle())
                                .add("sPrice", s.getPrice());

                servicesArrayBuilder.add(servicesBuilder);
            }

            executorBuilderr.add("services", servicesArrayBuilder);
            arrayBuilder.add(executorBuilderr);

        }

        builderr.add("executors", arrayBuilder);

        javax.json.JsonObject jsonObject = builderr.build();
        out.print(jsonObject.toString());

    }


    /*
{"executors":[{
{id:1,
"services":[{
"title":"title1"
}]
},
{},{},

}]}

     */
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
