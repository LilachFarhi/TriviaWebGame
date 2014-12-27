import java.io.FileNotFoundException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.annotation.WebServlet;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class StartGame extends HttpServlet 
{

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, FileNotFoundException, ClassNotFoundException 
    {
        List<Question> questions = GetSelectedQuestions(request);
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) 
        {
            
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet PlayGame</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet PlayGame at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }
    
    private List<Question> GetSelectedQuestions(HttpServletRequest request) throws IOException, FileNotFoundException, ClassNotFoundException
    {
        
        String path = getServletContext().getRealPath("/");
        DataManager.GetTriviaDataFromFile(path);
        // Fahion
        List<Question> selectedQuestions = new ArrayList<>();
        Category[] allCategories = Category.values();
        for (Category category : allCategories) 
        {
            String categoryName = category.name();
            
            String selectedCategory = request.getParameter(categoryName);
            String categoryDifficulty = request.getParameter(categoryName +"Difficulty");
            
            Map<Type, Map<QuestionDifficulty, List<Question>>> allQuestions = DataManager.triviaManager.getTriviaDataByDifficulty();
            
            if (!selectedCategory.equals("") && categoryDifficulty != null)
            {
               List<Question> questionsToAdd = allQuestions.get(Category.valueOf(selectedCategory)).get(QuestionDifficulty.valueOf(categoryDifficulty));
               selectedQuestions.addAll(questionsToAdd);
            }
        }
        
        return selectedQuestions;
    }


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, FileNotFoundException 
    {
        try {
            processRequest(request, response);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(StartGame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, FileNotFoundException {
        try {
            processRequest(request, response);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(StartGame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
