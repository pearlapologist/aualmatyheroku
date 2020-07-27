/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package api;

import java.io.*;
import java.net.*;
import java.util.*;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.Base64;
import javax.xml.bind.*;

/**
 *
 * @author bayan
 */
public class UpdatePhoto extends HttpServlet {

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
            out.println("<title>Servlet UpdatePhoto</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet UpdatePhoto at " + request.getContextPath() + "</h1>");
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

        } catch (Exception e) {
            e.printStackTrace();
        }

        String path = null;
        String fileName = null;
        try {
            models.DbHelper db = new models.DbHelper();
            javax.json.JsonReader jsonReader = javax.json.Json.createReader(request.getReader());

            javax.json.JsonObject jsonObject = jsonReader.readObject();

            String photo = jsonObject.getString("photo");
            byte[] base64Decoded = DatatypeConverter.parseBase64Binary(photo);
            // "C:\\Users\\bayan\\OneDrive\\Документы\\NetBeansProjects\\Aualmaty\\src\\main\\webapp\\Content"; 
            path = getServletContext().getRealPath("/Content");
          //  int position = path9.indexOf("target");
           // path = path9.substring(0, position) + p;
            
            fileName = models.DataUtils.generateRandomString(15) + ".jpg";

            models.DataUtils.savePhotoByBytes(base64Decoded, path, fileName);

            db.updatePersonPhoto(id, fileName);
            
            out.print(id + ";;");
        } catch (Exception e) {
            out.print(e.getMessage());
            e.printStackTrace();
        }
        out.print(path + " " + fileName);
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
