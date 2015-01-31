package controllers;

import models.Question;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class PlayGame extends HttpServlet {
    public static final String QuestionAttribute = "question";
    public static final String ErrorMessageAttribute = "ErrorMessage";
    public static final String PreviousAskedQuestionAttribute = "PreviousAskedQuestion";
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RequestDispatcher rd;
        HttpSession session = request.getSession();
        Object errMessage = request.getAttribute(ErrorMessageAttribute);

        List<Question> questionsToShow = (List<Question>) session.getAttribute(StartGame.AllQuestionsAttribute);
        Collections.shuffle(questionsToShow);

        if (questionsToShow.isEmpty()) {
            rd = request.getRequestDispatcher("/FinishGame.jsp");
        } 
        else
        {
            Question question;

            if (errMessage == null || errMessage.equals(""))
            {
                question = questionsToShow.get(0);
                questionsToShow.remove(0);
                session.setAttribute(StartGame.AllQuestionsAttribute, questionsToShow);
                session.setAttribute(PreviousAskedQuestionAttribute, question);
            }
            else
            {
                question = (Question)session.getAttribute(PreviousAskedQuestionAttribute);
            }
            
            session.setAttribute(QuestionAttribute, question);
            rd = request.getRequestDispatcher("/PlayGame.jsp");
        }
        
        rd.forward(request, response);
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
