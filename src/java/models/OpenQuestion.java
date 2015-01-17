package models;

public class OpenQuestion extends Question {
    
    public OpenQuestion(String question, String answer, 
            QuestionDifficulty difficulty, Category category)
    {
        super.question = question;
        super.answer = answer;
        super.difficulty = difficulty;
        super.category = category;
    }   
}