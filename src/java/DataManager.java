import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DataManager {
    public static final String TriviaDataFileName = "TriviaData.txt";
    private static FileManager fileManager;
    private static TriviaManager triviaManager;
    
    public static String AddQuestionToTriviaData(Question questionToAdd, String path) 
    {
        try 
        {
            GetTriviaDataFromFile(path + "\\" + TriviaDataFileName);
            triviaManager.AddQuestion(questionToAdd);
            
            List<Object> allQuestions = GetAllQuestionsForSave(triviaManager.getTriviaDataByDifficulty());
            fileManager.WriteAllDataToFile(allQuestions);
            return "";
        }
        catch (IOException | ClassNotFoundException ex) 
        {
            return GetErrorMessage(ex, path + "\\" + TriviaDataFileName);
        }
    }
    
    public static String RemoveQuestionFromTriviaData(Question questionToRemove, String path) 
    {
        try 
        {
            //GetTriviaDataFromFile(path + "\\" + TriviaDataFileName);
            triviaManager.DeleteQuestion(questionToRemove);
            
            List<Object> allQuestions = GetAllQuestionsForSave(triviaManager.getTriviaDataByDifficulty());
            fileManager.WriteAllDataToFile(allQuestions);
            return "";
        }
        catch (IOException ex) 
        {
            return GetErrorMessage(ex, path + "\\" + TriviaDataFileName);
        }
    }
    
    private static void GetTriviaDataFromFile(String path) throws IOException, 
            FileNotFoundException, ClassNotFoundException
    {
        fileManager = new FileManager(path);
        triviaManager = new TriviaManager(fileManager.GetAllDataFromFile());
    }
    
    public static String GetErrorMessage(Exception ex, String path)
    {
        return ("An error occurred while trying to connect "
                + "to the trivia data file : \'" + path
                + "\'.\n"
                + "Please check the file location or contact "
                + "the system administrator.\n"
                + "Error data : " + ex);
    }
    
    public static Map<Category, Map<QuestionDifficulty, List<Question>>> GetDataByCategoryAndDifficulty(String path) throws IOException, 
            FileNotFoundException, ClassNotFoundException
    {
        GetTriviaDataFromFile(path + "\\" + TriviaDataFileName);
        return triviaManager.getTriviaDataByCategoryAndDifficulty();
    }
    
    private static List<Object> GetAllQuestionsForSave(Map<Type, Map<QuestionDifficulty, List<Question>>> triviaData) 
    {
        List<Object> allQuestions = new ArrayList();

        for (Map.Entry<Type, Map<QuestionDifficulty, List<Question>>> entryType : triviaData.entrySet()) 
        {
            for (Map.Entry<QuestionDifficulty, List<Question>> entry : entryType.getValue().entrySet()) 
            {
                allQuestions.addAll(entry.getValue());
            }
        }

        return allQuestions;
    }
    
    public static List<Object> GetAllQuestions(String path) throws IOException, 
            FileNotFoundException, ClassNotFoundException
    {
        GetTriviaDataFromFile(path + "\\" + TriviaDataFileName);
        List<Object> allQuestions = GetAllQuestionsForSave(triviaManager.getTriviaDataByDifficulty());
        return allQuestions;
    }
    
    public static boolean isMapByCategoryAndDifficultyEmpty(Map<Category, Map<QuestionDifficulty, List<Question>>>  map)
    {
        boolean isEmpty = true;
        
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
        return isEmpty;
    }
}
