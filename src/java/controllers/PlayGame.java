package controllers;


import models.Question;
import models.OpenQuestion;
import models.MultipleAnswersQuestion;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class PlayGame extends HttpServlet {

    public static final String ErrorMessageAttribute = "ErrorMessage";
    public static final String PreviousAskedQuestionAttribute = "PreviousAskedQuestion";
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        String firstName = (String) session.getAttribute(Login.FirstNameAttribute);
        String lastName = (String) session.getAttribute(Login.LastNameAttribute);
        Object errMessage = request.getAttribute(ErrorMessageAttribute);

        List<Question> questionsToShow = (List<Question>) session.getAttribute(StartGame.AllQuestionsAttribute);
        Collections.shuffle(questionsToShow);

        if (questionsToShow.isEmpty()) {
            RequestDispatcher rd = request.getRequestDispatcher("FinishGame");
            rd.forward(request, response);
        } 
        else
        {
            response.setContentType("text/html;charset=UTF-8");
            try (PrintWriter out = response.getWriter()) {
                out.println("<!DOCTYPE html>");
                out.println("<html>");
                out.println("<head>");
                out.println("<link rel=\"stylesheet\" href=\"Main.css\"/>");
                out.println("<title>Play Game</title>");
                out.println("</head>");
                out.println("<body>");
                out.println("<h2> Current player: " + firstName + " " + lastName + "</h2>");

                if (errMessage != null && !errMessage.equals(""))
                {
                    out.println("<h3 id=\"errorMessage\">" + errMessage + "</h3>");
                }

                out.println("<form action=\"CheckQuestionAnswer\" method=\"GET\">");

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

                out.println("<h3> Question: " + question.getQuestion() + "</h3><br>");

                DisplayQuestion(out, question);

                out.println("<br><input type=\"submit\" value=\"Submit\">");
                out.println("</form><br>");
                out.println("<form action=\"FinishGame\" method=\"GET\">");
                out.println("<input type=\"submit\" value=\"FinishGame\">");
                out.println("</form>");
                out.println("</body>");
                out.println("</html>");
            }
        }
    }

    private void DisplayQuestion(final PrintWriter out, Question questionToShow) {
        if (questionToShow instanceof MultipleAnswersQuestion) {
            out.println("<h3>Choose the correct answer: </h3>");
            MultipleAnswersQuestion question = (MultipleAnswersQuestion) questionToShow;
            List<String> allAnswers = new ArrayList(question.getWrongAnswers());
            allAnswers.add(question.getAnswer());
            Collections.shuffle(allAnswers);
            
            for (String currentAnswer : allAnswers)
            {
                out.println("<input type=\"radio\" name=\"" +
                    NewQuestion.AnswerParameter + "\" value=\"" + 
                    currentAnswer + "\">" + currentAnswer + "<br>");
            }
        } 
        else if (questionToShow instanceof OpenQuestion) {
            out.println("<h3>Enter the answer: </h3>");
            OpenQuestion question = (OpenQuestion) questionToShow;
            out.println("<input type=\"text\" name=\"" +
                NewQuestion.AnswerParameter + "\" size=\"75\"><br>");
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
