package controllers;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import models.Player;

public class Login extends HttpServlet {

    public static final String PlayerAttribute = "player";
    public static final String FirstNameAttribute = "firstName";
    public static final String ErrorMessageAttribute = "errorMessage";
    public static final String LastNameAttribute = "lastName";
    public static final String RememberMeParameter = "rememberMe";
    public static final String RememberMeParameterCheckedValue = "true";
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        Player player = (Player)session.getAttribute(PlayerAttribute);
        
        if (player == null)
        {
            String firstName = null;
            String lastName = null;
            Cookie[] cookies = request.getCookies();

            if (cookies != null) {
                    for(int i=0; i<cookies.length; i++) {
                        Cookie c = cookies[i];

                        if ((c.getName().equals(FirstNameAttribute)))
                        {
                            firstName = c.getValue();
                        }

                        if ((c.getName().equals(LastNameAttribute)))
                        {
                            lastName = c.getValue();
                        }
                    }
                }

            if (firstName != null && lastName != null)
            {
                session.setAttribute(PlayerAttribute, new Player(firstName, lastName));
                response.sendRedirect("StartGame.html");
            }
            else
            {
                RequestDispatcher rd = request.getRequestDispatcher("Login.jsp");
                rd.forward(request, response);
            }
        }
        else
        {
            response.sendRedirect("StartGame.html");
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
