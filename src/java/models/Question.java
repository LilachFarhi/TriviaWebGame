package models;

import java.io.Serializable;

public class Question implements Serializable {
    protected String question;
    protected String answer;
    protected Category category;
    protected QuestionDifficulty difficulty;
    
    public String getQuestion() 
    {
        return question;
    }

    public String getAnswer() 
    {
        return answer;
    }

    public QuestionDifficulty getDifficulty() 
    {
        return difficulty;
    }
    
    public Category getCategory() 
    {
        return category;
    }
}