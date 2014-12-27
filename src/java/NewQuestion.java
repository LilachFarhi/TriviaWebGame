import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class NewQuestion extends HttpServlet {

    public static final String QuestionTypeAttribute = "questionType";
    public static final String ErrorMessageAttribute = "errorMessage";
    public static final String OpenQuestionParameter = "Open";
    public static final String MultipleAnswerQuestionParameter = "MultipleAnswer";
    public static final String YesOrNoQuestionParameter = "YesNo";
    public static final String AnswerParameter = "answer";
    public static final String WrongAnswersParameter = "wrongAnswers";
    public static final String QuestionParameter = "question";
    public static final String CategoryParameter = "category";
    public static final String DifficultyParameter = "difficulty";
    public static final String WrongAnswers = "WrongAnswers";
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        String type = request.getParameter(QuestionTypeAttribute);
        Object errMessage = request.getAttribute(ErrorMessageAttribute);
        String questionValue = "";
        String answerValue = "";
        String categoryValue = "";
        String difficultyValue = "";
        ArrayList<String> wrongAnswers = null;
        
        if (errMessage != null) {
            questionValue = request.getParameter(NewQuestion.QuestionParameter);
            answerValue = request.getParameter(NewQuestion.AnswerParameter);
            categoryValue = request.getParameter(NewQuestion.CategoryParameter);
            difficultyValue = request.getParameter(NewQuestion.DifficultyParameter);
            wrongAnswers = (ArrayList<String>)request.getAttribute(WrongAnswers);
        }

        if (type == null) {
            type = "";
        }

        boolean isValid = true;
        String answerText = "";

        switch (type) {
            case OpenQuestionParameter:
                answerText = BuildOpenQuestionView(answerText, answerValue);
                break;
            case MultipleAnswerQuestionParameter:
                answerText = BuildMultipleAnswerView(answerText, answerValue, wrongAnswers);
                break;
            case YesOrNoQuestionParameter:
                answerText = BuildYesNoView(answerText, answerValue);
                break;
            default:
                answerText = "Please choose the type of question you would like to add.";
                answerText += "<br>";
                answerText += "<a href=\"AddQuestion.html\">Add New Question</a>";
                isValid = false;
                break;
        }

        try (PrintWriter out = response.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");

            BuildJavaScriptToAddMultipleAnswerFields(out);

            out.println("<link rel=\"stylesheet\" href=\"Main.css\"/>");
            out.println("<title>New Question</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<form action=\"AddQuestion\" method=\"GET\">");
            
            if (errMessage != null && !errMessage.equals("")) {
                out.println("<h3 id=\"errorMessage\">There are validation errors: "
                        + errMessage + "</h3>");
            }

            if (isValid) {
                out.println("<h2>Enter the question: </h2>");
                out.println("<input type=\"text\" name=\"" +
                        QuestionParameter + "\" size=\"75\" ");
                
                if (questionValue != null && !"".equals(questionValue))
                {
                    out.println("value=\"" + questionValue + "\"");
                }
                
                out.println("><br>");
                out.println("<br>");
                out.println("<br>");
                out.println("<h2>Enter the answer: </h2>");
            }

            out.println(answerText);

            if (isValid) {
                out.println("<br>");
                out.println("<br>");
                DisplayCategoriesAndDifficulty(out, categoryValue, difficultyValue);
                out.println("<br>");
                out.println("<br>");
                out.println("<div id=\"submitDiv\">");
                out.println("<input type=\"submit\" value=\"Submit\">");
                out.println("</div>");
            }

            out.println("</form>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    private String BuildYesNoView(String answerText, String answerValue) {
        answerText = "<input type=\"hidden\" name=\"" +
                QuestionTypeAttribute + "\" value=\"" +
                YesOrNoQuestionParameter + "\">";
        answerText += "<input type=\"radio\" name=\"" +
                AnswerParameter + "\" value=\"" +
                Boolean.toString(true) + "\" ";
        
        if (answerValue != null && !"".equals(answerValue) && 
                answerValue.equals(Boolean.toString(true)))
        {
            answerText += "checked=\"checked\"";
        }
        answerText += ">True";
        answerText += "<br>";
        answerText += "<input type=\"radio\" name=\"" +
                AnswerParameter + "\" value=\"" +
                Boolean.toString(false) + "\" ";
        if (answerValue != null && !"".equals(answerValue) && 
                answerValue.equals(Boolean.toString(false)))
        {
            answerText += "checked=\"checked\"";
        }
        answerText += ">False";
        return answerText;
    }
    
    private String BuildOpenQuestionView(String answerText, String answerValue) {
        answerText = "<input type=\"hidden\" name=\"" +
                QuestionTypeAttribute + "\" value=\"" +
                OpenQuestionParameter + "\">";
        answerText += "<input type=\"text\" name=\"" +
                AnswerParameter + "\" size=\"100\" ";
        
        if (answerValue != null && !"".equals(answerValue))
        {
            answerValue += "value =\"" + answerValue + "\"";
        }
        
        answerText += ">";
        return answerText;
    }
    
    private String BuildMultipleAnswerView(String answerText, String answerValue, ArrayList<String> wrongAnswers) {
        answerText = "<input type=\"hidden\" name=\"" +
                QuestionTypeAttribute + "\" value=\"" +
                MultipleAnswerQuestionParameter + "\">";
        answerText += "<input type=\"text\" name=\"" +
                AnswerParameter + "\" size=\"100\" ";
        
        if (answerValue != null && !"".equals(answerValue))
        {
            answerValue += "value =\"" + answerValue + "\"";
        }
        
        answerText += ">";
        answerText += "<br>";
        answerText += "<br>";
        answerText += "<h2>Enter the wrong answers: </h2>";
        answerText += "Enter number of wrong answers (then click on 'Ok'): ";
        answerText += "<input type=\"text\" id=\"numOfanswers\" name=\"numOfanswers\" value=\"\">";
        answerText += " <a href=\"#\" id=\"filldetails\" onclick=\"addAnswerField()\">Ok</a>";
        answerText += "<br>";
        answerText += "<div>";
        
        if (wrongAnswers != null)
        {
            int i = 1;
            answerText += "<div id=\"container\">";
            
            for (String currWrongAnswer : wrongAnswers)
            {
                if (currWrongAnswer == null)
                {
                    currWrongAnswer = "";
                }
                
                answerText += "Answer " + i + ": ";
                answerText += "<input type=\"text\" name=\"" +
                        WrongAnswersParameter + i + "\" size=\"100\" value=\"" +
                        currWrongAnswer + "\">";
                answerText += "<br>";
            }
            
            answerText += "</div>";
        }
        else
        {
            answerText += "<div id=\"container\"/>";
        }
        
        answerText += "</div>";
        return answerText;
    }

    private void DisplayCategoriesAndDifficulty(final PrintWriter out, String categoryValue, String difficultyValue) {
        out.println("<div id=\"CategoriesAndDifficulty\">");
        out.println("<table width=\"100%\">");
        out.println("<tr>");
        out.println("<th padding=\"10px\" width=\"40px\">");
        out.println("<h2>Choose a category: </h2>");
        out.println("</th>");
        out.println("<th padding=\"10px\" width=\"40px\">");
        out.println("<h2>Choose a difficulty: </h2>");
        out.println("</th>");
        out.println("</tr>");
        out.println("<tr>");
        
        int categoryValueInt = -1;
        int difficultyValueInt = -1;
        
        if (categoryValue != null && !"".equals(categoryValue))
        {
            categoryValueInt = Integer.parseInt(categoryValue);
        }
        
        if (difficultyValue != null && !"".equals(difficultyValue))
        {
            difficultyValueInt = Integer.parseInt(difficultyValue);
        }
        
        out.println("<td padding=\"10px\">");
        Category[] allCategories = Category.values();
        for (Category category : allCategories) 
        {
            int categoryInt = category.ordinal();
            out.println("<input type=\"radio\" name=\"" +
                    CategoryParameter + "\" value=\"" +
                    categoryInt + "\" ");
            
            if (categoryValueInt == categoryInt)
            {
                out.println("checked=\"checked\"");
            }
            
            out.println(">" + category.name() + "<br>");
        }
        out.println("</td>");
        
        out.println("<td padding=\"10px\">");
        QuestionDifficulty[] allDifficulties = QuestionDifficulty.values();
        for (QuestionDifficulty questionDifficulty : allDifficulties) 
        {
            int difficultyInt = questionDifficulty.ordinal();
            out.println("<input type=\"radio\" name=\"" +
                    DifficultyParameter + "\" value=\"" +
                    difficultyInt + "\" ");
            
            if (difficultyValueInt == difficultyInt)
            {
                out.println("checked=\"checked\"");
            }
            
            out.println(">" + questionDifficulty.name() + "<br>");
        }
        out.println("</td>");
        
        out.println("</tr>");
        out.println("</table>");
        out.println("</div>");
    }
    
    private void BuildJavaScriptToAddMultipleAnswerFields(final PrintWriter out) {
        out.println("<script type='text/javascript'>");
        BuildIsIntFunction(out);
        out.println("function addAnswerField(){");
        out.println("var number = document.getElementById(\"numOfanswers\").value;");
        out.println("if (isInt(number)){");
        out.println("var container = document.getElementById(\"container\");");
        out.println("while (container.hasChildNodes()) {");
        out.println("container.removeChild(container.lastChild);}");
        out.println("for (i=0;i<number;i++){");
        out.println("container.appendChild(document.createTextNode(\"Answer \" + (i+1) + \": \"));");
        out.println("var input = document.createElement(\"input\");");
        out.println("input.type = \"text\";");
        String inputName = "input.name = \"" + WrongAnswersParameter + "\" + + (i+1)";
        out.println(inputName);
        out.println("input.size=\"100\";");
        out.println("container.appendChild(input);");
        out.println("container.appendChild(document.createElement(\"br\"));}");
        out.println("}}</script>");
    }
    
    private void BuildIsIntFunction(final PrintWriter out)
    {
        out.println("function isInt(value) {");
        out.println("return !isNaN(value) &&");
        out.println("parseInt(Number(value)) == value &&");
        out.println("!isNaN(parseInt(value, 10));}");  
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }

}
