package services;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import models.*;

public class DBManager {
    
    private static final String WrongAnswersTableQuestionID = "QUESTION_ID";
    private static final String TablesAnswer = "ANSWER";
    private static final String QuestionsTableID = "ID";
    private static final String QuestionsTableQuestion = "QUESTION";
    private static final String QuestionsTableCategory = "CATEGORY";
    private static final String QuestionsTableDifficulty = "DIFFICULTY";
    private static final String QuestionsTableQuestionType = "QUESTION_TYPE";
    public static final String OpenQuestionType = "Open";
    public static final String MultipleAnswerQuestionType = "MultipleAnswer";
    public static final String YesOrNoQuestionType = "YesNo";
    private static final String DeleteWrongAnswers = "Delete From WRONG_ANSWERS Where QUESTION_ID=?";
    private static final String DeleteQuestion = "Delete From QUESTIONS Where ID=?";
    private static final String InsertQuestion = "Insert into QUESTIONS (QUESTION, ANSWER, CATEGORY, DIFFICULTY, QUESTION_TYPE) values(?, ?, ?, ?, ?)";
    private static final String InsertWrongAnswers = "Insert into WRONG_ANSWERS (QUESTION_ID, ANSWER) values(?, ?)";
    private static final String SelectNewlyAddedQuestionId = "Select ID From QUESTIONS Where QUESTION=? AND ANSWER=? AND CATEGORY=? AND DIFFICULTY=? AND QUESTION_TYPE=?"
            + "AND (Select COUNT(*) From WRONG_ANSWERS Where QUESTION_ID=QUESTIONS.ID)=0";
    private static boolean isLoaded = false;
    private static Connection connection;
    private static Statement statement;
    
    private static void LoadIfNeeded() throws ClassNotFoundException
    {
        if (!isLoaded)
        {
            Class.forName("org.apache.derby.jdbc.ClientDriver");
            isLoaded = true;
        }
    }
    
    private static void Connect() throws SQLException
    {
        String urlConnectionString ="jdbc:derby://localhost:1527/TriviaGameDB";
        connection = DriverManager.getConnection(urlConnectionString, "triviaAdmin", "1234");
        statement = connection.createStatement();
    }
    
    private static void Disconnect() throws SQLException
    {
        connection.close();
    }
    
    public static List<Question> GetAllQuestions() throws SQLException, ClassNotFoundException
    {
        HashMap<Integer, ArrayList<String>> wrongAnswers = new HashMap<>();
        List<Question> allQuestions = new ArrayList<>();
        
        LoadIfNeeded();
        Connect();
        
        ResultSet resultSetWrongAnswers = statement.executeQuery("Select * From WRONG_ANSWERS");

        while (resultSetWrongAnswers.next())
        {
            Integer questionID = resultSetWrongAnswers.getInt(WrongAnswersTableQuestionID);
            String wrongAnswer = resultSetWrongAnswers.getString(TablesAnswer);
            
            if (!wrongAnswers.containsKey(questionID))
            {
                wrongAnswers.put(questionID, new ArrayList<>());
            }
            
            wrongAnswers.get(questionID).add(wrongAnswer);
        }
        
        ResultSet resultSetQuestions = statement.executeQuery("Select * From QUESTIONS");
        Category[] allCategories = Category.values();
        QuestionDifficulty[] allDifficulties = QuestionDifficulty.values();
        
        while (resultSetQuestions.next())
        {
            Question currentQuestion = null;
            Integer id = resultSetQuestions.getInt(QuestionsTableID);
            Integer category = resultSetQuestions.getInt(QuestionsTableCategory);
            Integer difficulty = resultSetQuestions.getInt(QuestionsTableDifficulty);
            String answer = resultSetQuestions.getString(TablesAnswer);
            String question = resultSetQuestions.getString(QuestionsTableQuestion);
            String questionType = resultSetQuestions.getString(QuestionsTableQuestionType);
            
            switch (questionType) {
            case OpenQuestionType:
                currentQuestion = new OpenQuestion(id, question, answer,
                        allDifficulties[difficulty], allCategories[category]);
                break;
            case YesOrNoQuestionType:
                currentQuestion = new YesOrNoQuestion(id, question, ConvertBooleanAnswer(answer),
                        allDifficulties[difficulty], allCategories[category]);
                break;
            case MultipleAnswerQuestionType:
                List<String> currentWrongAnswers = wrongAnswers.get(id);
                currentQuestion = new MultipleAnswersQuestion(id, question, currentWrongAnswers, answer,
                        allDifficulties[difficulty], allCategories[category]);
                break;
            default:
                break;
            }
            
            allQuestions.add(currentQuestion);
        }
        
        Disconnect();
        return allQuestions;
    }
    
    private static boolean ConvertBooleanAnswer(String answer)
    {
        return Boolean.toString(true).equals(answer);
    }
    
    public static void AddQuestion(Question questionToAdd) throws ClassNotFoundException, SQLException
    {
        LoadIfNeeded();
        Connect();
        
        PreparedStatement insertPreparedStatement = connection.prepareStatement(InsertQuestion);
        
        insertPreparedStatement.setString(1, questionToAdd.getQuestion());
        insertPreparedStatement.setString(2, questionToAdd.getAnswer());
        insertPreparedStatement.setInt(3, questionToAdd.getCategory().ordinal());
        insertPreparedStatement.setInt(4, questionToAdd.getDifficulty().ordinal());
        
        String questionType;
        
        if (questionToAdd instanceof YesOrNoQuestion)
        {
            questionType = YesOrNoQuestionType;
        }
        else if (questionToAdd instanceof MultipleAnswersQuestion)
        {
            questionType = MultipleAnswerQuestionType;
        }
        else
        {
            questionType = OpenQuestionType;
        }
        
        insertPreparedStatement.setString(5, questionType);
        insertPreparedStatement.executeUpdate();
        
        PreparedStatement selectNewID = connection.prepareStatement(SelectNewlyAddedQuestionId);
        selectNewID.setString(1, questionToAdd.getQuestion());
        selectNewID.setString(2, questionToAdd.getAnswer());
        selectNewID.setInt(3, questionToAdd.getCategory().ordinal());
        selectNewID.setInt(4, questionToAdd.getDifficulty().ordinal());
        selectNewID.setString(5, questionType);
        ResultSet resultSet = selectNewID.executeQuery();
        int questionID = 0;
        
        while (resultSet.next())
        {
            questionID = resultSet.getInt(QuestionsTableID);
        }
        
        if (questionToAdd instanceof MultipleAnswersQuestion)
        {
            MultipleAnswersQuestion multipleAnswer = (MultipleAnswersQuestion)questionToAdd;
            
            for (String currentWrongAnswer : multipleAnswer.getWrongAnswers())
            {
                PreparedStatement insertWrongAnswersPreparedStatement = connection.prepareStatement(InsertWrongAnswers);
                insertWrongAnswersPreparedStatement.setInt(1, questionID);
                insertWrongAnswersPreparedStatement.setString(2, currentWrongAnswer);
                insertWrongAnswersPreparedStatement.executeUpdate();
            }
        }
        
        Disconnect();
    }
    
    public static void RemoveQuestion(Question questionToRemove) throws ClassNotFoundException, SQLException
    {
        LoadIfNeeded();
        Connect();

        if(questionToRemove instanceof MultipleAnswersQuestion) 
        {
            PreparedStatement deleteWrongAnswersPreparedStatement = connection.prepareStatement(DeleteWrongAnswers);
            deleteWrongAnswersPreparedStatement.setInt(1, questionToRemove.getId());
            deleteWrongAnswersPreparedStatement.executeUpdate();
        }

        PreparedStatement deleteQuestionPerparedStatement = connection.prepareStatement(DeleteQuestion);
        deleteQuestionPerparedStatement.setInt(1, questionToRemove.getId());
        deleteQuestionPerparedStatement.executeUpdate();
        
        Disconnect();
    }
}
