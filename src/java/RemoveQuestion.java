import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class RemoveQuestion extends HttpServlet {
    public static final String AllQuestionsAttribute = "AllQuestions";
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String path = getServletContext().getRealPath("/");
        HttpSession session = request.getSession();
        
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) 
        {
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<link rel=\"stylesheet\" href=\"Main.css\"/>");
            out.println("<title>Remove Question</title>");            
            out.println("</head>");
            out.println("<body>");
            
            Map<Category, Map<QuestionDifficulty, List<Question>>> allQuestionsMap = null;    
            try 
            {
                allQuestionsMap = DataManager.GetDataByCategoryAndDifficulty(path);
            }
            catch (IOException | ClassNotFoundException ex) 
            {
                out.println("<h3 id=\"errorMessage\">"
                        + DataManager.GetErrorMessage(ex, path + "\\" + DataManager.TriviaDataFileName) 
                        + "</h3>");
            }

            if(DataManager.isMapByCategoryAndDifficultyEmpty(allQuestionsMap))
            {
                out.println("<img src=\"oops.jpg\" width=\"300\" height=\"150\" >");
                out.println("<h1 id=\"errorMessage\">Sorry we don't have questions to delete at this moment. </h1>");
            }
            else
            {
                List<Map<QuestionDifficulty, List<Question>>> list = new ArrayList<>(allQuestionsMap.values());
                List<List<Question>> allListQuestions = new ArrayList<>();
                list.stream().forEach((list1) -> { allListQuestions.addAll(list1.values());});

                List<Question> allQuestions = new ArrayList<>();
                allListQuestions.stream().forEach((allListQuestion) -> { allQuestions.addAll(allListQuestion); });

                session.setAttribute(AllQuestionsAttribute, allQuestions);
                
                out.println("<h1> Please choose a question to delete</h1>");
                out.println("<form action=\"DeleteQuestion\" method=\"GET\"");
                
                for (int i = 0; i < allQuestions.size(); i++) 
                {
                    out.println("<div>");
                    out.println("<input type=\"radio\" name=\"Question\" value=\"" + i + "\">" + 
                            allQuestions.get(i).getQuestion());
                    out.println("</div>");
                }
                
                out.println("<div><input type=\"submit\" value=\"Delete\">  </div>");
                out.println("</form>");
            }
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
