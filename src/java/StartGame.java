import java.io.FileNotFoundException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class StartGame extends HttpServlet 
{
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, FileNotFoundException, ClassNotFoundException 
    {
        String path = getServletContext().getRealPath("/");
        Map<Category, Map<QuestionDifficulty, List<Question>>> allQuestions = 
                DataManager.GetDataByCategoryAndDifficulty(path);
        
        List<Question> selectedQuestions = new ArrayList<>();
        List<Category> categoriesWithoutDifficulty = new ArrayList<>();
        List<Category> difficultiesWithoudCategory = new ArrayList<>();
        
        
        Category[] allCategories = Category.values();
        
        for (Category category : allCategories) 
        {
            String categoryName = category.name();
            
            String selectedCategory = request.getParameter(categoryName);
            String categoryDifficulty = request.getParameter(categoryName +"Difficulty");
            
            if ((selectedCategory != null && !selectedCategory.equals("")) && categoryDifficulty != null)
            {
                List<Question> questionsToAdd  = new ArrayList<>();
                try
                {
                   questionsToAdd = allQuestions.get(Category.valueOf(selectedCategory)).
                       get(QuestionDifficulty.valueOf(categoryDifficulty));
                }
                catch(IllegalArgumentException exception)
                {
                            
                }
                selectedQuestions.addAll(questionsToAdd);
            }
            else if((selectedCategory != null && !selectedCategory.equals("")) && categoryDifficulty == null)
            {
                categoriesWithoutDifficulty.add(category);
            }
            else if((selectedCategory == null  || selectedCategory.equals("")) && categoryDifficulty != null)
            {
                difficultiesWithoudCategory.add(category);
            }
        }
        
        if (difficultiesWithoudCategory.isEmpty() && categoriesWithoutDifficulty.isEmpty() 
                && !selectedQuestions.isEmpty()) 
        {
            request.setAttribute("Questions",selectedQuestions);

            ServletContext context = request.getServletContext();
            RequestDispatcher requestDispatcher = context.getRequestDispatcher("PlayGame");
            requestDispatcher.forward(request, response);
        }
        
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) 
        {
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<link rel=\"stylesheet\" href=\"Main.css\"/>");
            out.println("<title>Start Game</title>");            
            out.println("</head>");
            out.println("<body>");
            
            if (selectedQuestions.isEmpty() && 
                categoriesWithoutDifficulty.isEmpty() && 
                difficultiesWithoudCategory.isEmpty()) 
            {
                 out.println("<img src=\"oops.jpg\">");
                 out.println("<h1 id=\"errorMessage\">Please chose categories and difficulties </h1>");
            }
            else
            {
                out.println("<img src=\"Missing.jpg\">");
                if (!categoriesWithoutDifficulty.isEmpty()) 
                {
                    out.println("<h1 id=\"errorMessage\">For these categories please select difficulty </h1>");
                    out.println("<ul>");
                    categoriesWithoutDifficulty.stream().forEach((category) -> 
                    {
                        out.println("<li>" + category.name() + "</li>");
                    });
                    
                    out.println("</ul>");
                }
                if (!difficultiesWithoudCategory.isEmpty()) 
                {
                    out.println("<h1 id=\"errorMessage\">For these categories you only selected difficulty </h1>");
                    out.println("<ul>");
                    difficultiesWithoudCategory.stream().forEach((category) -> 
                    {
                        out.println("<li>" + category.name() + "</li>");
                    });
                    out.println("</ul>");
                }
            }
            
            out.println("<img src=\"Error.png\">");
            out.println("</body>");
            out.println("</html>");
        }
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
