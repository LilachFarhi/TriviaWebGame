package controllers;

import services.DataManager;
import models.QuestionDifficulty;
import models.Question;
import models.Category;
import java.io.FileNotFoundException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.io.IOException;
import java.sql.SQLException;
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
    public static final String MissingCategoryOrDifficutlyAttribute = "missingCategoryOrDifficutly";
    public static final String MissingQuestionAttribute = "missingQuestions";
    public static final String NothingSelectedAttribute = "nothingSelected";
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, ClassNotFoundException, SQLException, IOException 
    {
        HttpSession session = request.getSession();
        String path = getServletContext().getRealPath("/");
        Map<Category, Map<QuestionDifficulty, List<Question>>> allQuestions = 
                DataManager.GetDataByCategoryAndDifficulty();
        
        List<Question> selectedQuestions = new ArrayList<>();
        List<Category> categoriesWithoutDifficulty = new ArrayList<>();
        List<Category> difficultiesWithoutCategory = new ArrayList<>();
        Map<Category, QuestionDifficulty> noQuestionsInCategoryAndDifficulty = new HashMap<Category, QuestionDifficulty>() {};
        Map<Category, Integer> categoriesChosenToQuestionsAsked = new HashMap<Category, Integer>();
        Map<Category, Integer> categoriesChosenToQuestionsCorrect = new HashMap<Category, Integer>();
        Map<String, List<Category>> missingCategoryOrDifficutly = new HashMap<String, List<Category>>();
        
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
        else
        {
            if (!difficultiesWithoutCategory.isEmpty()) 
            {
                missingCategoryOrDifficutly.put("For these categories you only selected difficulty" , difficultiesWithoutCategory);
            }
            if (!categoriesWithoutDifficulty.isEmpty()) 
            {
                missingCategoryOrDifficutly.put("For these categories please select difficulty" , categoriesWithoutDifficulty);
            }
            if (selectedQuestions.isEmpty() && categoriesWithoutDifficulty.isEmpty() &&
                noQuestionsInCategoryAndDifficulty.isEmpty() && difficultiesWithoutCategory.isEmpty()) 
            {
                request.setAttribute(NothingSelectedAttribute, true);      
            }
            else
            {
                request.setAttribute(NothingSelectedAttribute, false);      
            }
            
            request.setAttribute(MissingQuestionAttribute, noQuestionsInCategoryAndDifficulty);
            request.setAttribute(MissingCategoryOrDifficutlyAttribute, missingCategoryOrDifficutly);
            RequestDispatcher requestDispatcher = request.getRequestDispatcher("ErrorsInStartGame.jsp");
            requestDispatcher.forward(request, response);
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
        } catch (SQLException ex) {
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
        } catch (SQLException ex) {
            Logger.getLogger(StartGame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
