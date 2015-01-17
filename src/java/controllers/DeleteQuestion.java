package controllers;

import services.DataManager;
import models.Question;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class DeleteQuestion extends HttpServlet 
{
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException 
    {
        HttpSession session = request.getSession();
        String path = getServletContext().getRealPath("/");
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) 
        {
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet DeleteQuestion</title>");            
            out.println("</head>");
            out.println("<link rel=\"stylesheet\" href=\"Main.css\"/>");
            out.println("<body>");
            out.println("</body>");
            out.println("</html>");
        
            List<Question> allQuestions = (List<Question>)session.getAttribute(RemoveQuestion.AllQuestionsAttribute);
            String selectedQuestionIndex = request.getParameter("Question");
            
            if (selectedQuestionIndex != null && !selectedQuestionIndex.equals("")) 
            {
                Question questionToDelete = allQuestions.get(Integer.parseInt(selectedQuestionIndex));
                
                if (questionToDelete != null) 
                {
                    String errorMessage = DataManager.RemoveQuestionFromTriviaData(questionToDelete, path, false);

                    if(!errorMessage.equals(""))
                    {
                        out.println("<h3 id=\"errorMessage\">" + errorMessage + "</h3>");
                    }
                    else
                    {
                        out.println("<br>");
                        out.println("<br>");
                        out.println("<h1>The Question was successfully deleted.</h1> ");
                        out.println("<br>");
                        out.println("<img src=\"ThumbsUp.gif\" width=\"150\" height=\"250\">");
                    }
                }
                else
                {
                    out.println("<img src=\"oops.jpg\" width=\"300\" height=\"150\" >");
                    out.println("<br>");
                    out.println("<h3 id=\"errorMessage\"> Something went wrong, please contact system administrator</h3>");
                }
            }
            else
            {
                out.println("<br>");
                out.println("<br>");
                out.println("Please choose a question to delete");
                out.println("<br>");
                out.println("<a href=\"RemoveQuestion\"> Remove Question </a>");
            }
        }
    }


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException 
    {
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
