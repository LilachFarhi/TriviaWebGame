package controllers;

import services.DataManager;
import models.Question;
import java.io.IOException;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class DeleteQuestion extends HttpServlet {

    public static final String ErrorMessage = "ErrorMessage";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException 
    {
        HttpSession session = request.getSession();
        String path = getServletContext().getRealPath("/");

        List<Question> allQuestions = (List<Question>) session.getAttribute(RemoveQuestion.AllQuestionsAttribute);
        String selectedQuestionIndex = request.getParameter("Question");

        if (selectedQuestionIndex != null && !selectedQuestionIndex.equals("")) 
        {
            Question questionToDelete = allQuestions.get(Integer.parseInt(selectedQuestionIndex));

            if (questionToDelete != null) 
            {
                String errorMessage = DataManager.RemoveQuestion(questionToDelete);
                request.setAttribute(ErrorMessage, errorMessage);
            } 
            else 
            {
                request.setAttribute(ErrorMessage, "Something went wrong, please contact system administrator");
            }
        } 
        else 
        {
            request.setAttribute(ErrorMessage, "Please choose a question to delete");
        }
        
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("DeleteQuestion.jsp");
        requestDispatcher.forward(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }

}
