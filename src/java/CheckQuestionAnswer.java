import java.io.IOException;
import java.io.PrintWriter;
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
        String firstName = (String) session.getAttribute(Login.FirstNameAttribute);
        String lastName = (String) session.getAttribute(Login.LastNameAttribute);
        
        Question question = (Question)request.getAttribute(PlayGame.PreviousAskedQuestionAttribute);
        String userAnswer = request.getParameter(NewQuestion.AnswerParameter);
        
        if (userAnswer == null || userAnswer.equals(""))
        {
            if (question instanceof MultipleAnswersQuestion) {
                request.setAttribute(PlayGame.ErrorMessageAttribute, "Please choose your answer");
            }
            else if (question instanceof OpenQuestion) {
                request.setAttribute(PlayGame.ErrorMessageAttribute, "Please enter your answer");
            }
            
            RequestDispatcher rd = request.getRequestDispatcher("PlayGame");
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
                out.println("<title>Checking your answer</title>");            
                out.println("</head>");
                out.println("<body>");
                out.println("<h2> Current player: " + firstName + " " + lastName + "</h2>");
                
                if (userAnswer.equalsIgnoreCase(question.getAnswer()))
                {
                    out.println("<img src=\"CorrectAnswer.jpg\" width=\"100\" height=\"100\"></img><br>");
                    out.println("<h3 id=\"correctAnswer\">Your answer is correct!</h3>");
                    out.println("<img src=\"CorrectAnswer2.jpg\" width=\"100\" height=\"100\"></img>");
                }
                else
                {
                    out.println("<img src=\"WrongAnswer.jpg\" width=\"100\" height=\"100\"></img><br>");
                    out.println("<h3 id=\"wrongAnswer\">Your answer is wrong. Better luck next time.</h3>");
                    out.println("<h4>The right answer if :" + question.getAnswer() + "</h4>");
                    out.println("<img src=\"WrongAnswer2.jpg\" width=\"100\" height=\"100\"></img>");
                }
                
                out.println("<a href=\"PlayGame\">Next Question</a>");
                out.println("</body>");
                out.println("</html>");
            }
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
