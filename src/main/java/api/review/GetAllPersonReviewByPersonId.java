/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package api.review;

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
public class GetAllPersonReviewByPersonId extends HttpServlet {

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
          request.setCharacterEncoding("UTF-8");

        PrintWriter out = response.getWriter();

        DbHelper db = new models.DbHelper();

        int personId = -1;
        try {
            personId = Integer.parseInt(request.getParameter("pId"));

        } catch (Exception e) {
            e.printStackTrace();
        }

        ArrayList<ReviewToOrder> r = db.getAllPersonReviewByPersonId(personId);

        if (r == null) {
            javax.json.JsonObjectBuilder objectBuilder = javax.json.Json.createObjectBuilder().
                    add("Ошибка", "Отзывов не найдено");
            out.print(objectBuilder.build().toString());
            return;
        }

        javax.json.JsonObjectBuilder builderr = javax.json.Json.createObjectBuilder();
        javax.json.JsonArrayBuilder arrayBuilder = javax.json.Json.createArrayBuilder();

        for (ReviewToOrder review : r) {
            javax.json.JsonObjectBuilder reviewsBuilderr = javax.json.Json.createObjectBuilder();

            reviewsBuilderr.add("id", review.getId());
            reviewsBuilderr.add("executorId", review.getExecutrId());
            reviewsBuilderr.add("customerId", review.getCustomerId());
            reviewsBuilderr.add("text", review.getReview_text());
            reviewsBuilderr.add("assessment", review.getAssessment());
            String c = review.getCreatedDate() + "";
            reviewsBuilderr.add("created", c);
            javax.json.JsonArrayBuilder servicesArrayBuilder = javax.json.Json.createArrayBuilder();

            for (models.AnswerToReview answer : review.getAnswers()) {
                javax.json.JsonObjectBuilder servicesBuilder
                                             = javax.json.Json.createObjectBuilder()
                                .add("id", answer.getId())
                                .add("reviewId", answer.getReviewId())
                                .add("whoanswersId", answer.getWhoanswersId())
                                .add("whoposted", answer.getWhopostedId())
                                .add("text", answer.getText())
                                .add("created", answer.getCreatedDate());
                
                servicesArrayBuilder.add(servicesBuilder);
            }

            reviewsBuilderr.add("answers", servicesArrayBuilder);
            arrayBuilder.add(reviewsBuilderr);

        }

        builderr.add("reviews", arrayBuilder);

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
