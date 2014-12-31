import java.io.FileNotFoundException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class StartGame extends HttpServlet 
{
    public static final String AllQuestionsAttribute = "AllQuestions";
    public static final String QuestionsAskedByCategoryAttribute = "QuestionsAsked";
    public static final String QuestionsCorrectByCategoryAttribute = "QuestionsCorrect";
    public static final String FinalScoreAttribute = "score";
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, FileNotFoundException, ClassNotFoundException 
    {
        HttpSession session = request.getSession();
        String path = getServletContext().getRealPath("/");
        Map<Category, Map<QuestionDifficulty, List<Question>>> allQuestions = 
                DataManager.GetDataByCategoryAndDifficulty(path);
        
        List<Question> selectedQuestions = new ArrayList<>();
        List<Category> categoriesWithoutDifficulty = new ArrayList<>();
        List<Category> difficultiesWithoutCategory = new ArrayList<>();
        Map<Category, QuestionDifficulty> noQuestionsInCategoryAndDifficulty = new HashMap<Category, QuestionDifficulty>() {};
        Map<Category, Integer> categoriesChosenToQuestionsAsked = new HashMap<Category, Integer>();
        Map<Category, Integer> categoriesChosenToQuestionsCorrect = new HashMap<Category, Integer>();
        
        Category[] allCategories = Category.values();
        
        for (Category category : allCategories) 
        {
            String categoryName = category.name();
            
            String selectedCategory = request.getParameter(categoryName);
            String categoryDifficulty = request.getParameter(categoryName + "Difficulty");
            
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
                        noQuestionsInCategoryAndDifficulty.put(Category.valueOf(selectedCategory), QuestionDifficulty.valueOf(categoryDifficulty));
                }
                
                if (!questionsToAdd.isEmpty()) 
                {
                    selectedQuestions.addAll(questionsToAdd);
                    categoriesChosenToQuestionsAsked.put(Category.valueOf(selectedCategory), 0);
                    categoriesChosenToQuestionsCorrect.put(Category.valueOf(selectedCategory), 0);
                }
                else
                {
                    noQuestionsInCategoryAndDifficulty.put(Category.valueOf(selectedCategory), QuestionDifficulty.valueOf(categoryDifficulty));
                }
                
            }
            else if((selectedCategory != null && !selectedCategory.equals("")) && categoryDifficulty == null)
            {
                categoriesWithoutDifficulty.add(category);
            }
            else if((selectedCategory == null  || selectedCategory.equals("")) && categoryDifficulty != null)
            {
                difficultiesWithoutCategory.add(category);
            }
        }
        
        if (difficultiesWithoutCategory.isEmpty() && categoriesWithoutDifficulty.isEmpty()
             &&  noQuestionsInCategoryAndDifficulty.isEmpty()  && !selectedQuestions.isEmpty()) 
        {
            session.setAttribute(AllQuestionsAttribute, selectedQuestions);
            session.setAttribute(QuestionsAskedByCategoryAttribute, categoriesChosenToQuestionsAsked);
            session.setAttribute(QuestionsCorrectByCategoryAttribute, categoriesChosenToQuestionsCorrect);
            session.setAttribute(FinalScoreAttribute, 0);

            RequestDispatcher rd = request.getRequestDispatcher("PlayGame");
            rd.forward(request, response);
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
            
            if (selectedQuestions.isEmpty() && categoriesWithoutDifficulty.isEmpty() &&
                noQuestionsInCategoryAndDifficulty.isEmpty() && difficultiesWithoutCategory.isEmpty()) 
            {
                 out.println("<img src=\"oops.jpg\" width=\"300\" height=\"150\" >");
                 out.println("<h1 id=\"errorMessage\">Please chose categories and difficulties </h1>");
            }
            else
            {
                out.println("<img src=\"Missing.jpg\" width=\"150\" height=\"150\">");
                if (!categoriesWithoutDifficulty.isEmpty()) 
                {
                    out.println("<h1 id=\"errorMessage\">For these categories please select difficulty </h1>");
                    out.println("<ul id=\"errorMessage\" >");
                    categoriesWithoutDifficulty.stream().forEach((category) -> 
                    {
                        out.println("<li> <h2>" + category.name() + "</h2></li>");
                    });
                    
                    out.println("</ul>");
                }
                if (!difficultiesWithoutCategory.isEmpty()) 
                {
                    out.println("<h1 id=\"errorMessage\">For these categories you only selected difficulty </h1>");
                    out.println("<ul id=\"errorMessage\">");
                    difficultiesWithoutCategory.stream().forEach((category) -> 
                    {
                        out.println("<li> <h2>" + category.name() + "</h2> </li>");
                    });
                    out.println("</ul>");
                }
                if (categoriesWithoutDifficulty.isEmpty() && difficultiesWithoutCategory.isEmpty()
                        && !noQuestionsInCategoryAndDifficulty.isEmpty()) 
                {
                    out.println("<h1 id=\"errorMessage\">For these categories and difficulties we don't have questions </h1>");
                    out.println("<h1 id=\"errorMessage\">If you would like to proceed please add a new question </h1>");
                    out.println("<ul id=\"errorMessage\">");
                    noQuestionsInCategoryAndDifficulty.entrySet().stream().forEach((entry) -> 
                    {
                        out.println("<li> <h2>" + entry.getKey().name() + ", " + entry.getValue().name() + "</h2> </li>");
                    });
                    out.println("</ul>");
                }
            }
            
            out.println("<img src=\"Error.png\" width=\"150\" height=\"150\" >");
            out.println("<br><br><a href=\"StartGame.html\">Back</a>");
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
