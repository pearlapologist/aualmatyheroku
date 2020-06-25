/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author bayan
 */
public class getPersonByNumber extends HttpServlet {

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
     /*   PrintWriter out = response.getWriter();
        String number = "";
        try {
            number = request.getParameter("numb");

        } catch (Exception e) {
            e.printStackTrace();
        }

        models.DbHelper db = new models.DbHelper();
        models.Person p = db.getPersonByNumb(number);

        if (p == null) {
            javax.json.JsonObjectBuilder objectBuilder = javax.json.Json.createObjectBuilder().
                    add("Ошибка", "Пользователь не найден");
            out.print(objectBuilder.build().toString());
            return;
        }

        javax.json.JsonObjectBuilder builderr = javax.json.Json.createObjectBuilder();

        builderr.add("id", p.getId());
        builderr.add("name", p.getName());
        builderr.add("lastname", p.getLastname());
        builderr.add("number", p.getNumber());

        javax.json.JsonObject jsonObject = builderr.build();
        out.print(jsonObject.toString());*/
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
        try {
            javax.json.JsonReader jsonReader = javax.json.Json.createReader(request.getReader());

            javax.json.JsonObject jsonObject = jsonReader.readObject();

            String number = jsonObject.getString("numb");
            String passwd = jsonObject.getString("passwd");

            models.Person p = new models.Person();
            p.setNumber(number);
            p.setPasswd(passwd);

            models.DbHelper db = new models.DbHelper();
            db.getPersonByPasswdNNumb(number, passwd);

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
