/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

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
public class GetAllMessages extends HttpServlet {

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
      ArrayList<Message> messages=  db.getAllMesages();
      
  
        if(messages == null){
           javax.json.JsonObjectBuilder objectBuilder = javax.json.Json.createObjectBuilder().add("errorMessage", "404error");
            String result = objectBuilder.build().toString();
            out.print(result);
            return;
        }
        
        JsonObjectBuilder objectBuilder = javax.json.Json.createObjectBuilder();
        
        JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
        
        for(Message g : messages){
        JsonObjectBuilder oB2 = javax.json.Json.createObjectBuilder().add("msg_id", g.getId())
        .add("person_id1", g.getPersonId()).add("person_id2", g.getWhosends())
                .add("text", g.getText());
       
            arrayBuilder.add(oB2);
        }
        
          objectBuilder.add("messages", arrayBuilder);
           javax.json.JsonObject jsonObject = objectBuilder.build();
           out.print(jrsonObject.toString());
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
