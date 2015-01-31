package models;

import java.util.Arrays;

public class YesOrNoQuestion extends MultipleAnswersQuestion {

    public YesOrNoQuestion(Integer id, String question, boolean answer, 
            QuestionDifficulty difficulty, Category category) {
        super(id, question, Arrays.asList(Boolean.toString(!answer)),
                Boolean.toString(answer), difficulty, category);
    }
    
    public YesOrNoQuestion(String question, boolean answer, 
            QuestionDifficulty difficulty, Category category) {
        this(0, question, answer, difficulty, category);
    }
}
