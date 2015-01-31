package services;

import models.Question;
import models.Category;
import models.OpenQuestion;
import models.MultipleAnswersQuestion;
import models.YesOrNoQuestion;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import models.QuestionDifficulty;

public class TriviaManager {
    
    private Map<Type, Map<QuestionDifficulty, List<Question>>> triviaDataByDifficulty;
    private Map<Type, Map<Category, List<Question>>> triviaDataByCategory;
    private Map<Category, Map<QuestionDifficulty, List<Question>>> triviaDataByCategoryAndDifficulty;
    
    public TriviaManager(List<Question> triviaData)
    {
        this.triviaDataByDifficulty = new HashMap();
        this.triviaDataByDifficulty.put(MultipleAnswersQuestion.class, new HashMap());
        this.triviaDataByDifficulty.put(YesOrNoQuestion.class, new HashMap());
        this.triviaDataByDifficulty.put(OpenQuestion.class, new HashMap());
        
        this.triviaDataByCategory = new HashMap();
        this.triviaDataByCategory.put(MultipleAnswersQuestion.class, new HashMap());
        this.triviaDataByCategory.put(YesOrNoQuestion.class, new HashMap());
        this.triviaDataByCategory.put(OpenQuestion.class, new HashMap());
        
        this.triviaDataByCategoryAndDifficulty = new HashMap();
        
        Category[] allCategories = Category.values();
        QuestionDifficulty[] allDifficulties = QuestionDifficulty.values();
        
        for (Category category : allCategories) 
        {
            this.triviaDataByCategoryAndDifficulty.put(category, new HashMap());
        }
        
        for(Entry<Type, Map<QuestionDifficulty, List<Question>>> entry : 
                this.triviaDataByDifficulty.entrySet()) {
            
            for (QuestionDifficulty questionDifficulty : allDifficulties) 
            {
                entry.getValue().put(questionDifficulty, new ArrayList());
            }
        }
        
        for(Entry<Type, Map<Category, List<Question>>> entry : 
                this.triviaDataByCategory.entrySet()) {
            for (Category category : allCategories) 
            {
                entry.getValue().put(category, new ArrayList());
            }
        }
        
        for(Entry<Category, Map<QuestionDifficulty, List<Question>>> entry : 
                this.triviaDataByCategoryAndDifficulty.entrySet()) {
            for (QuestionDifficulty questionDifficulty : allDifficulties) 
            {
                entry.getValue().put(questionDifficulty, new ArrayList());
            }
        }
        
        CreateTriviaData(triviaData);
    }

    private void CreateTriviaData(List<Question> triviaData) 
    {
        for (Object currentObject : triviaData)
        {
            if (currentObject.getClass().equals(MultipleAnswersQuestion.class))
            {
                MultipleAnswersQuestion question = 
                        (MultipleAnswersQuestion)currentObject;
                triviaDataByDifficulty.get(MultipleAnswersQuestion.class)
                        .get(question.getDifficulty()).add(question);
                triviaDataByCategory.get(MultipleAnswersQuestion.class)
                        .get(question.getCategory()).add(question);
            }
            else if (currentObject.getClass().equals(YesOrNoQuestion.class))
            {
                YesOrNoQuestion question = 
                        (YesOrNoQuestion)currentObject;
                triviaDataByDifficulty.get(YesOrNoQuestion.class)
                        .get(question.getDifficulty()).add(question);
                triviaDataByCategory.get(YesOrNoQuestion.class)
                        .get(question.getCategory()).add(question);
            }
            else if (currentObject.getClass().equals(OpenQuestion.class))
            {
                OpenQuestion question = 
                        (OpenQuestion)currentObject;
                triviaDataByDifficulty.get(OpenQuestion.class)
                        .get(question.getDifficulty()).add(question);
                triviaDataByCategory.get(OpenQuestion.class)
                        .get(question.getCategory()).add(question);
            }
            
            if (currentObject instanceof Question)
            {
                Question baseQuestion = (Question)currentObject;
                triviaDataByCategoryAndDifficulty.get(baseQuestion.getCategory())
                        .get(baseQuestion.getDifficulty()).add(baseQuestion);
            }
        }
    }

    public Map<Type, Map<QuestionDifficulty, List<Question>>> getTriviaDataByDifficulty() {
        return new HashMap<>(triviaDataByDifficulty);
    }
    
    public Map<Type, Map<Category, List<Question>>> getTriviaDataByCategory() {
        return new HashMap<>(triviaDataByCategory);
    }
    
    public Map<Category, Map<QuestionDifficulty, List<Question>>> getTriviaDataByCategoryAndDifficulty() {
        return new HashMap<>(triviaDataByCategoryAndDifficulty);
    }
    
    public List<Question> getQuestionsByCategory(Category category)
    {
        List<Question> allQuestionsByCategory = new ArrayList();

        for (Map.Entry<Type, Map<Category, List<Question>>> entryType
                : triviaDataByCategory.entrySet()) {
            allQuestionsByCategory.addAll(entryType.getValue().get(category));
        }

        return allQuestionsByCategory;
    }
    
    public List<Question> getQuestionsByQuestionDifficulty(QuestionDifficulty questionDifficulty)
    {
        List<Question> allQuestionsByQuestionDifficulty = new ArrayList();

        for (Map.Entry<Type, Map<QuestionDifficulty, List<Question>>> entryType
                : triviaDataByDifficulty.entrySet()) {
            allQuestionsByQuestionDifficulty.addAll(entryType.getValue().get(questionDifficulty));
        }

        return allQuestionsByQuestionDifficulty;
    }
    
    public List<Question> getQuestionsByCategoryAndQuestionDifficulty(Category category, 
            QuestionDifficulty questionDifficulty)
    {
        List<Question> allQuestionsByCategoryAndQuestionDifficulty = new ArrayList();

        for (Map.Entry<Category, Map<QuestionDifficulty, List<Question>>> entryType
                : triviaDataByCategoryAndDifficulty.entrySet()) {
            if (entryType.getKey() == category)
            {
                allQuestionsByCategoryAndQuestionDifficulty.addAll(entryType.getValue().get(questionDifficulty));
            }
        }

        return allQuestionsByCategoryAndQuestionDifficulty;
    }
    
    public void AddQuestion(Question questionToAdd)
    {
        if (questionToAdd != null) 
        {
            AddQuestionToTriviaData(questionToAdd); 
        }
    }
    
     public void DeleteQuestion(Question questionToDelete)
    {   
        if (questionToDelete.getClass().equals(MultipleAnswersQuestion.class))
        {
            triviaDataByDifficulty.get(MultipleAnswersQuestion.class).get(questionToDelete.getDifficulty()).remove(questionToDelete);
            triviaDataByCategory.get(MultipleAnswersQuestion.class).get(questionToDelete.getCategory()).remove(questionToDelete);
        }
        else if (questionToDelete.getClass().equals(YesOrNoQuestion.class))
        {
            triviaDataByDifficulty.get(YesOrNoQuestion.class).get(questionToDelete.getDifficulty()).remove(questionToDelete);
            triviaDataByCategory.get(YesOrNoQuestion.class).get(questionToDelete.getCategory()).remove(questionToDelete);
        }
        else if (questionToDelete.getClass().equals(OpenQuestion.class))
        {
           triviaDataByDifficulty.get(OpenQuestion.class).get(questionToDelete.getDifficulty()).remove(questionToDelete);
           triviaDataByCategory.get(OpenQuestion.class).get(questionToDelete.getCategory()).remove(questionToDelete);
        }
        
        triviaDataByCategoryAndDifficulty.get(questionToDelete.getCategory())
                .get(questionToDelete.getDifficulty()).remove(questionToDelete);
    }

    private void AddQuestionToTriviaData(Question currentObject) 
    {
        if (currentObject.getClass().equals(MultipleAnswersQuestion.class))
        {
            MultipleAnswersQuestion question = (MultipleAnswersQuestion)currentObject;
            triviaDataByDifficulty.get(MultipleAnswersQuestion.class).get(question.getDifficulty()).add(question);
            triviaDataByCategory.get(MultipleAnswersQuestion.class).get(question.getCategory()).add(question);
        }
        else if (currentObject.getClass().equals(YesOrNoQuestion.class))
        {
            YesOrNoQuestion question = (YesOrNoQuestion)currentObject;
            triviaDataByDifficulty.get(YesOrNoQuestion.class).get(question.getDifficulty()).add(question);
            triviaDataByCategory.get(YesOrNoQuestion.class).get(question.getCategory()).add(question);
        }
        else if (currentObject.getClass().equals(OpenQuestion.class))
        {
            OpenQuestion question = (OpenQuestion)currentObject;
            triviaDataByDifficulty.get(OpenQuestion.class).get(question.getDifficulty()).add(question);
            triviaDataByCategory.get(OpenQuestion.class).get(question.getCategory()).add(question);
        }
        
        triviaDataByCategoryAndDifficulty.get(currentObject.getCategory())
                .get(currentObject.getDifficulty()).add(currentObject);
    }
}
