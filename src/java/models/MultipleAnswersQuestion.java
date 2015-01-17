package models;

import java.util.List;

public class MultipleAnswersQuestion extends Question {

    private List<String> wrongAnswers;

    public MultipleAnswersQuestion(String question,
            List<String> wrongAnswers, String answer, 
            QuestionDifficulty difficulty, Category category) {
        super.question = question;
        super.answer = answer;
        this.wrongAnswers = wrongAnswers;
        super.difficulty = difficulty;
        super.category = category;
    }

    public List<String> getWrongAnswers() {
        return wrongAnswers;
    }
}
