package controllers;

import models.Category;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;
import java.util.Map.Entry;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class FinishGame extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        String firstName = (String) session.getAttribute(Login.FirstNameAttribute);
        String lastName = (String) session.getAttribute(Login.LastNameAttribute);
        Integer score = (Integer)session.getAttribute(StartGame.FinalScoreAttribute);
        Map<Category, Integer> questionsCorrect = 
                            (Map<Category, Integer>)session.getAttribute(StartGame.QuestionsCorrectByCategoryAttribute);
        Map<Category, Integer> questionsAsked = 
                            (Map<Category, Integer>)session.getAttribute(StartGame.QuestionsAskedByCategoryAttribute);
        
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<link rel=\"stylesheet\" href=\"Main.css\"/>");
            out.println("<title>Finish Game</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Thank you for playing, " + firstName + " " + lastName + "!" + "</h1><br>");
            out.println("<table>");
            out.println("<tr>");
            out.println("<th>Categories you have played</th>");
            out.println("<th>Number of questions you have been asked</th>");
            out.println("<th>Number of questions you have answered correctly</th>");
            out.println("</tr>");
            
            for (Entry<Category, Integer> askedQuestions : questionsAsked.entrySet())
            {
                out.println("<tr>");
                out.println("<td>" + askedQuestions.getKey().name() + "</td>");
                out.println("<td>" + askedQuestions.getValue() + "</td>");
                out.println("<td>" + questionsCorrect.get(askedQuestions.getKey()) + "</td>");
                out.println("</tr>");
            }
            
            out.println("</table><br><br>");
            out.println("<h3>Your final score is: " + score + "!" + "</h3><br>");
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
