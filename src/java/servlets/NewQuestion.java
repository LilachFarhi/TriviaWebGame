package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class NewQuestion extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String type = request.getParameter("questionType");
        
        if (type == null)
        {
            type = "";
        }
        
        boolean isValid = true;
        String answerText;
        
         switch (type) {
            case "Open":  
                answerText = "<input type=\"text\" name=\"answer\" width=\"100\" height=\"100\">";
                break;
            case "MultipleAnswer":
                answerText = "<input type=\"text\" name=\"answer\" width=\"100\" height=\"100\">";
                answerText += "<br>";
                answerText += "<h2>Enter the wrong answers: </h2>";
                answerText += "<br>";
                answerText += "Seperate the wrong answers by entering \"|\" between each answer.";
                answerText += "<br>";
                answerText += "<input type=\"text\" name=\"answer\" width=\"100\" height=\"100\">";
                break;
            case "YesNo":  
                answerText = "<input type=\"radio\" name=\"TrueFalse\" value=\"True\">True";
                answerText += "<br>";
                answerText += "<input type=\"radio\" name=\"TrueFalse\" value=\"False\">False";
                break;
            default: 
                answerText = "Please choose the type of question you would like to add.";
                answerText += "<br>";
                answerText += "<a href=\"AddQuestion.html\">Add New Question</a>";
                isValid = false;
                break;
        }
         
        try (PrintWriter out = response.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            
            if (isValid)
            {
                out.println("<link rel=\"stylesheet\" href=\"Main.css\"/>");
            }
            else
            {
                
            }
            
            out.println("<title>New Question</title>");            
            out.println("</head>");
            out.println("<body>");
            
            if (isValid)
            {
                out.println("<h2>Enter the question: </h2>");
                out.println("<input type=\"text\" name=\"answer\" width=\"100\" height=\"100\">");
                out.println("<br>");
                out.println("<br>");
                out.println("<h2>Enter the answer: </h2>");
            }
            
            out.println(answerText);
            
            if (isValid)
            {
                out.println("<br>");
                out.println("<br>");
                out.println("<input type=\"submit\" value=\"Submit\">");
            }
            
            out.println("</body>");
            out.println("</html>");
        }
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
