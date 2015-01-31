package models;

import java.util.List;

public class MultipleAnswersQuestion extends Question {

    private String question;
    private String correctAnswer;
    private Category category;
    private List<String> wrongAnswers;
    private QuestionDifficulty difficulty;
    private Integer id;

    public MultipleAnswersQuestion(String question,
            List<String> wrongAnswers, String answer, 
            QuestionDifficulty difficulty, Category category) {
        this(0, question, wrongAnswers, answer, difficulty, category);
    }
    
    public MultipleAnswersQuestion(Integer id,
            String question,
            List<String> wrongAnswers, String answer, 
            QuestionDifficulty difficulty, Category category) {
        this.id = id;
        this.question = question;
        this.correctAnswer = answer;
        this.wrongAnswers = wrongAnswers;
        this.difficulty = difficulty;
        this.category = category;
    }

    @Override
    public String getQuestion() {
        return question;
    }

    @Override
    public String getAnswer() {
        return correctAnswer;
    }

    @Override
    public QuestionDifficulty getDifficulty() {
        return difficulty;
    }

    @Override
    public Category getCategory() {
        return category;
    }
    
    public List<String> getWrongAnswers() {
        return wrongAnswers;
    }

    @Override
    public Integer getId() {
        return id;
    }
}
