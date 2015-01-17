package controllers;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ValidateUserData extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        String firstName = request.getParameter(Login.FirstNameAttribute);
        String lastName = request.getParameter(Login.LastNameAttribute);
        String rememberMe = request.getParameter(Login.RememberMeParameter);
        String errorMessage = "";
        
        if (firstName == null || "".equals(firstName))
        {
            errorMessage += "You must enter your first name.<br>";
        }
        if (lastName == null || "".equals(lastName))
        {
            errorMessage += "You must enter your last name.<br>";
        }
        
        if ("".equals(errorMessage))
        {
            if (rememberMe != null && rememberMe.equals(Login.RememberMeParameterCheckedValue))
            {
                Cookie firstNameCookie = new Cookie(Login.FirstNameAttribute, firstName);
                firstNameCookie.setMaxAge(60*60*24*7);
                response.addCookie(firstNameCookie);

                Cookie lastNameCookie = new Cookie(Login.LastNameAttribute, lastName);
                lastNameCookie.setMaxAge(60*60*24*7);
                response.addCookie(lastNameCookie);
            }
            
            session.setAttribute(Login.FirstNameAttribute, firstName);
            session.setAttribute(Login.LastNameAttribute, lastName);
            response.sendRedirect("StartGame.html");
        }
        else
        {
            request.setAttribute(Login.ErrorMessageAttribute, errorMessage);
            RequestDispatcher rd = request.getRequestDispatcher("Login");
            rd.forward(request, response);
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
