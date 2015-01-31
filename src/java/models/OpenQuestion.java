package models;

public class OpenQuestion extends Question {
    
    private String question;
    private String answer;
    private Category category;
    private QuestionDifficulty difficulty;
    private Integer id;
    
    public OpenQuestion(String question, String answer, 
            QuestionDifficulty difficulty, Category category)
    {
        this(0, question, answer, difficulty, category);
    }
    
    public OpenQuestion(Integer id, String question, String answer, 
            QuestionDifficulty difficulty, Category category)
    {
        this.id = id;
        this.question = question;
        this.answer = answer;
        this.difficulty = difficulty;
        this.category = category;
    }
    
    @Override
    public String getQuestion() 
    {
        return question;
    }

    @Override
    public String getAnswer() 
    {
        return answer;
    }

    @Override
    public QuestionDifficulty getDifficulty() 
    {
        return difficulty;
    }
    
    @Override
    public Category getCategory() 
    {
        return category;
    }  
    
    @Override
    public Integer getId() {
        return id;
    }
}