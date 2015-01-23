package controllers;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class NewQuestion extends HttpServlet {

    public static final String QuestionTypeAttribute = "questionType";
    public static final String ErrorMessageAttribute = "errorMessage";
    public static final String OpenQuestionParameter = "Open";
    public static final String MultipleAnswerQuestionParameter = "MultipleAnswer";
    public static final String YesOrNoQuestionParameter = "YesNo";
    public static final String AnswerParameter = "answer";
    public static final String WrongAnswersParameter = "wrongAnswers";
    public static final String QuestionParameter = "question";
    public static final String CategoryParameter = "category";
    public static final String DifficultyParameter = "difficulty";
    public static final String WrongAnswers = "WrongAnswers";
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RequestDispatcher rd;
        String type = request.getParameter(QuestionTypeAttribute);

        if (type == null) {
            type = "";
        }
        
        switch (type) {
            case OpenQuestionParameter:
                rd = request.getRequestDispatcher("NewOpenQuestion.jsp");
                break;
            case MultipleAnswerQuestionParameter:
                rd = request.getRequestDispatcher("NewMultipleAnswerQuestion.jsp");
                break;
            case YesOrNoQuestionParameter:
                rd = request.getRequestDispatcher("NewYesNoQuestion.jsp");
                break;
            default:
                request.setAttribute(ErrorMessageAttribute, "Please choose the type of question you would like to add.");
                rd = request.getRequestDispatcher("AddQuestion.jsp");
                break;
        }
        
        rd.forward(request, response);
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
