import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DataManager {
    private static final String TriviaDataFilePath = "src/TriviaData.txt";
    private static FileManager fileManager;
    private static TriviaManager triviaManager;
    
    public static String AddQuestionToTriviaData(Question questionToAdd) 
    {
        try 
        {
            GetTriviaDataFromFile();
            triviaManager.AddQuestion(questionToAdd);
            
            List<Object> allQuestions = GetAllQuestionsForSave(triviaManager.getTriviaDataByDifficulty());
            fileManager.WriteAllDataToFile(allQuestions);
            return "";
        }
        catch (IOException | ClassNotFoundException ex) 
        {
            return GetErrorMessage(ex);
        }
    }
    
    public static String RemoveQuestionFromTriviaData(Question questionToRemove)
    {
        try 
        {
            GetTriviaDataFromFile();
            triviaManager.DeleteQuestion(questionToRemove);
            
            List<Object> allQuestions = GetAllQuestionsForSave(triviaManager.getTriviaDataByDifficulty());
            fileManager.WriteAllDataToFile(allQuestions);
            return "";
        }
        catch (IOException | ClassNotFoundException ex) 
        {
            return GetErrorMessage(ex);
        }
    }
    
    private static void GetTriviaDataFromFile() throws IOException, 
            FileNotFoundException, ClassNotFoundException
    {
        fileManager = new FileManager(TriviaDataFilePath);
        triviaManager = new TriviaManager(fileManager.GetAllDataFromFile());
    }
    
    private static String GetErrorMessage(Exception ex)
    {
        return ("An error occurred while trying to connect "
                + "to the trivia data file : \'" + TriviaDataFilePath
                + "\'.\n"
                + "Please check the file location or contact "
                + "the system administrator.\n"
                + "Error data : " + ex);
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
}