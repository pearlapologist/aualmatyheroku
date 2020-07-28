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
public class AddPerson extends HttpServlet {

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

        try {
            JsonReader jsonReader = Json.createReader(request.getReader());

            JsonObject jsonObject = jsonReader.readObject();

            String name = jsonObject.getString("name");
            String lastname = jsonObject.getString("lastname");
            String number = jsonObject.getString("numb");
            String passwd = jsonObject.getString("passwd");
            String birthday = jsonObject.getString("birth");
            Long lb = Long.valueOf(birthday);
            String photo = jsonObject.getString("photo");
            byte[] base64Decoded = javax.xml.bind.DatatypeConverter.parseBase64Binary(photo);

            String path = getServletContext().getRealPath("/Content");
            String fileName = models.DataUtils.generateRandomString(15) + ".jpg";

            models.DataUtils.savePhotoByBytes(base64Decoded, path, fileName);

            Person p = new Person();
            p.setName(name);
            p.setLastname(lastname);
            p.setNumber(number);
            p.setPasswd(passwd);
            p.setBirthday(lb);
            p.setPhoto(fileName);

            models.DbHelper db = new models.DbHelper();
            db.addPerson(p);

            out.print(p.getId());
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
