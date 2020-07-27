/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package api;

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
public class UpdatePersonById extends HttpServlet {

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
            out.println("<title>Servlet UpdatePersonById</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet UpdatePersonById at " + request.getContextPath() + "</h1>");
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
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");

        PrintWriter out = response.getWriter();

        int id = -1;
        try {
            id = Integer.parseInt(request.getParameter("id"));

            JsonReader jsonReader = Json.createReader(request.getReader());

            JsonObject jsonObject = jsonReader.readObject();

            String name = jsonObject.getString("pName");
            String lastname = jsonObject.getString("pLastname");
            String birthday = jsonObject.getString("pBirthday");
            Long lb = Long.valueOf(birthday);
            String photo = jsonObject.getString("photo");
            byte[] base64Decoded = javax.xml.bind.DatatypeConverter.parseBase64Binary(photo);
            
            String path = getServletContext().getRealPath("/Content");
            String fileName = models.DataUtils.generateRandomString(15) + ".jpg";

            models.DataUtils.savePhotoByBytes(base64Decoded, path, fileName);

            DbHelper db = new DbHelper();

            Person p = new Person();
            p.setId(id);
            p.setName(name);
            p.setLastname(lastname);
            p.setBirthday(lb);
            p.setPhoto(fileName);

            db.updatePersonFromAndr(p);
            out.print(id);
        } catch (Exception e) {
            out.print("Ошибка на сервере");
            e.printStackTrace();
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
