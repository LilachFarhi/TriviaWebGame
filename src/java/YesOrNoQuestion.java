import java.util.Arrays;

public class YesOrNoQuestion extends MultipleAnswersQuestion {

    public YesOrNoQuestion(String question, boolean answer, 
            QuestionDifficulty difficulty, Category category) {
        super(question, Arrays.asList(Boolean.toString(!answer)),
                Boolean.toString(answer), difficulty, category);
    }
}
