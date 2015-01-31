package controllers;

import services.DataManager;
import models.QuestionDifficulty;
import models.Question;
import models.Category;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class RemoveQuestion extends HttpServlet {

    public static final String AllQuestionsAttribute = "AllQuestions";
    public static final String ErrorMessage = "ErrorMessage";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException 
    {
        boolean isValid = true;
        String path = getServletContext().getRealPath("/");
        HttpSession session = request.getSession();

        Map<Category, Map<QuestionDifficulty, List<Question>>> allQuestionsMap = null;
        session.setAttribute(AllQuestionsAttribute, new ArrayList<Question>());
        try 
        {
            allQuestionsMap = DataManager.GetDataByCategoryAndDifficulty();
        } 
        catch (SQLException | ClassNotFoundException ex) 
        {
            request.setAttribute(ErrorMessage, DataManager.GetErrorMessage(ex));
            isValid = false;
        }

        if (isValid)
        {
            if (DataManager.isMapByCategoryAndDifficultyEmpty(allQuestionsMap)) 
            {
                request.setAttribute(ErrorMessage, "Sorry we don't have questions to delete at this moment.");
            } 
            else 
            {
                List<Map<QuestionDifficulty, List<Question>>> list = new ArrayList<>(allQuestionsMap.values());
                List<List<Question>> allListQuestions = new ArrayList<>();
                list.stream().forEach((list1) -> {allListQuestions.addAll(list1.values());});

                List<Question> allQuestions = new ArrayList<>();
                allListQuestions.stream().forEach((allListQuestion) -> {allQuestions.addAll(allListQuestion); });

                session.setAttribute(AllQuestionsAttribute, allQuestions);
            }
        }

        RequestDispatcher requestDispatcher = request.getRequestDispatcher("RemoveQuestion.jsp");
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
    }// </editor-fold>

}
