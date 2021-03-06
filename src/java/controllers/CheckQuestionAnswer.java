package controllers;

import models.Question;
import models.Category;
import models.OpenQuestion;
import models.MultipleAnswersQuestion;
import java.io.IOException;
import java.util.Map;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class CheckQuestionAnswer extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();

        Question question = (Question) session.getAttribute(PlayGame.PreviousAskedQuestionAttribute);
        String userAnswer = request.getParameter(NewQuestion.AnswerParameter);

        if (userAnswer == null || userAnswer.equals("")) {
            if (question instanceof MultipleAnswersQuestion) {
                request.setAttribute(PlayGame.ErrorMessageAttribute, "Please choose your answer");
            } 
            else if (question instanceof OpenQuestion) {
                request.setAttribute(PlayGame.ErrorMessageAttribute, "Please enter your answer");
            }

            RequestDispatcher rd = request.getRequestDispatcher("PlayGame");
            rd.forward(request, response);
        } 
        else {
            RequestDispatcher rd = null;

            Map<Category, Integer> questionsAsked
                    = (Map<Category, Integer>) session.getAttribute(StartGame.QuestionsAskedByCategoryAttribute);
            questionsAsked.put(question.getCategory(), questionsAsked.get(question.getCategory()) + 1);
            session.setAttribute(StartGame.QuestionsAskedByCategoryAttribute, questionsAsked);

            if (userAnswer.equalsIgnoreCase(question.getAnswer())) {
                rd = request.getRequestDispatcher("CorrectAnswer.jsp");

                Integer score = (Integer) session.getAttribute(StartGame.FinalScoreAttribute);
                session.setAttribute(StartGame.FinalScoreAttribute,
                        score + question.getDifficulty().ordinal() + 1);

                Map<Category, Integer> questionsCorrect
                        = (Map<Category, Integer>) session.getAttribute(StartGame.QuestionsCorrectByCategoryAttribute);
                questionsCorrect.put(question.getCategory(), questionsCorrect.get(question.getCategory()) + 1);
                session.setAttribute(StartGame.QuestionsCorrectByCategoryAttribute, questionsCorrect);
            } 
            else 
            {
                rd = request.getRequestDispatcher("WrongAnswer.jsp");
            }

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
