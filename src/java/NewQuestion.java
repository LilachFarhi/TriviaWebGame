import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class NewQuestion extends HttpServlet {

    private static final String QuestionTypeAttribute = "questionType";
    private static final String OpenQuestionParameter = "Open";
    private static final String MultipleAnswerQuestionParameter = "MultipleAnswer";
    private static final String YesOrNoQuestionParameter = "YesNo";
    private static final String AnswerParameter = "answer";
    private static final String TrueOrFalseParameter = "TrueFalse";
    private static final String WrongAnswersParameter = "wrongAnswers";
    private static final String QuestionParameter = "question";
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        HttpSession session = request.getSession();
        String questionType = (String) session.getAttribute(QuestionTypeAttribute);

        if (questionType == null) {
            
            String type = request.getParameter(QuestionTypeAttribute);

            if (type == null) {
                type = "";
            }

            boolean isValid = true;
            String answerText;

            switch (type) {
                case OpenQuestionParameter:
                    session.setAttribute(QuestionTypeAttribute, OpenQuestionParameter);
                    answerText = "<input type=\"text\" name=\"" + 
                            AnswerParameter + "\" size=\"100\">";
                    break;
                case MultipleAnswerQuestionParameter:
                    session.setAttribute(QuestionTypeAttribute, MultipleAnswerQuestionParameter);
                    answerText = "<input type=\"text\" name=\"" + 
                            AnswerParameter + "\" size=\"100\">";
                    answerText += "<br>";
                    answerText += "<br>";
                    answerText += "<h2>Enter the wrong answers: </h2>";
                    answerText += "Enter number of wrong answers (then click on 'Ok'): ";
                    answerText += "<input type=\"text\" id=\"numOfanswers\" name=\"numOfanswers\" value=\"\">";
                    answerText += " <a href=\"#\" id=\"filldetails\" onclick=\"addAnswerField()\">Ok</a>";
                    answerText += "<br>";
                    answerText += "<div>";
                    answerText += "<div id=\"container\"/>";
                    answerText += "</div>";
                    break;
                case YesOrNoQuestionParameter:
                    session.setAttribute(QuestionTypeAttribute, YesOrNoQuestionParameter);
                    answerText = "<input type=\"radio\" name=\"" +
                            TrueOrFalseParameter + "\" value=\"" +
                            Boolean.toString(true) + "\">True";
                    answerText += "<br>";
                    answerText += "<input type=\"radio\" name=\"" +
                            TrueOrFalseParameter + "\" value=\"" +
                            Boolean.toString(false) + "\">False";
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

                if (isValid) {
                    out.println("<link rel=\"stylesheet\" href=\"Main.css\"/>");
                } else {

                }

                out.println("<title>New Question</title>");
                out.println("</head>");
                out.println("<body>");
                out.println("<form action=\"NewQuestion\" method=\"GET\">");

                if (isValid) {
                    out.println("<h2>Enter the question: </h2>");
                    out.println("<input type=\"text\" name=\"" +
                            QuestionParameter + "\" size=\"75\"");
                    out.println("<br>");
                    out.println("<br>");
                    out.println("<br>");
                    out.println("<h2>Enter the answer: </h2>");
                }

                out.println(answerText);

                if (isValid) {
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
        else
        {
            String errorMessage = "";
            String question = request.getParameter(QuestionParameter);
            if (question == null || question == "")
            {
                errorMessage += "You must enter a text for the question.<br>";
            }
            
            switch (questionType) {
                case OpenQuestionParameter:
                    
                    break;
                case MultipleAnswerQuestionParameter:
                    
                    break;
                case YesOrNoQuestionParameter:
                    
                    break;
                default:
                    errorMessage += "A general error has occured. Please contact the" +
                            " system administrator.<br>";
                    break;
            }
        }
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
        out.println("container.appendChild(document.createTextNode(\"Answer \" + (i+1) + \":\"));");
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
