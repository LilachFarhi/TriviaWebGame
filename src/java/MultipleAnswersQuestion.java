import java.util.List;

public class MultipleAnswersQuestion extends Question {

    private String question;
    private String correctAnswer;
    private Category category;
    private List<String> wrongAnswers;
    private QuestionDifficulty difficulty;

    public MultipleAnswersQuestion(String question,
            List<String> wrongAnswers, String answer, 
            QuestionDifficulty difficulty, Category category) {
        this.question = question;
        this.correctAnswer = answer;
        this.wrongAnswers = wrongAnswers;
        this.difficulty = difficulty;
        this.category = category;
    }

    @Override
    String getQuestion() {
        return question;
    }

    @Override
    String getAnswer() {
        return correctAnswer;
    }

    @Override
    QuestionDifficulty getDifficulty() {
        return difficulty;
    }

    @Override
    Category getCategory() {
        return category;
    }
    
    List<String> getWrongAnswers() {
        return wrongAnswers;
    }
}
