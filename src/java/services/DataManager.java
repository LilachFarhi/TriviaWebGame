package services;

import models.QuestionDifficulty;
import models.Question;
import models.Category;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class DataManager {
    private static DBManager dbManager;
    private static TriviaManager triviaManager;
    
    public static String AddQuestion(Question questionToAdd)
    {
        String errorMessage = "";
        
        try
        {
            dbManager.AddQuestion(questionToAdd);
        }
        catch (ClassNotFoundException | SQLException ex)
        {
            errorMessage = GetErrorMessage(ex);
        }
        
        return errorMessage;
    }
    
    public static String RemoveQuestion(Question questionToRemove) 
    {
        String errorMessage = "";
        
        try
        {
            dbManager.RemoveQuestion(questionToRemove);
        }
        catch (ClassNotFoundException | SQLException ex)
        {
            errorMessage = GetErrorMessage(ex);
        }
        
        return errorMessage;
    }
    
    public static String GetErrorMessage(Exception ex)
    {
        return ("An error occurred while trying to communicate with database.\n"
                + "Please check the file location or contact "
                + "the system administrator.\n"
                + "Error data : " + ex);
    }
    
    public static Map<Category, Map<QuestionDifficulty, List<Question>>> GetDataByCategoryAndDifficulty() 
            throws SQLException, ClassNotFoundException 
    {
        List<Question> allQuestions = dbManager.GetAllQuestions();
        triviaManager = new TriviaManager(allQuestions);
        return triviaManager.getTriviaDataByCategoryAndDifficulty();
    }
    
    public static boolean isMapByCategoryAndDifficultyEmpty(Map<Category, Map<QuestionDifficulty, List<Question>>>  map)
    {
        boolean isEmpty = true;
        
        if (map != null)
        {
            for (Map.Entry<Category, Map<QuestionDifficulty, List<Question>>> categoryEntry : map.entrySet())
            {
                if (!categoryEntry.getValue().isEmpty()) 
                {
                    for (Map.Entry<QuestionDifficulty, List<Question>> difficultyEntry : categoryEntry.getValue().entrySet()) 
                    {
                        if (!difficultyEntry.getValue().isEmpty())
                        {
                            isEmpty = false;
                            break;
                        }
                    }
                }

            }
        }
        return isEmpty;
    }
}
