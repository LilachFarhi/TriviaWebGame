package models;

import java.io.Serializable;

public abstract class Question implements Serializable {
    
    public abstract String getQuestion();
    public abstract String getAnswer();
    public abstract Category getCategory();
    public abstract QuestionDifficulty getDifficulty();
}