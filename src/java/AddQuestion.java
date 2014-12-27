import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Map;
import java.util.Map.Entry;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AddQuestion extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String questionType = request.getParameter(NewQuestion.QuestionTypeAttribute);
        
        ArrayList<String> wrongAnswers = new ArrayList<>();
        String errorMessage = "";

        String question = request.getParameter(NewQuestion.QuestionParameter);
        String answer = request.getParameter(NewQuestion.AnswerParameter);
        String category = request.getParameter(NewQuestion.CategoryParameter);
        String difficulty = request.getParameter(NewQuestion.DifficultyParameter);

        errorMessage = ValidateUserInput(question, errorMessage, answer, 
                category, difficulty, questionType, request, wrongAnswers);

        if ("".equals(errorMessage))
        {
            Question questionToAdd = CreateNewQuestion(difficulty, category, 
                    questionType, question, answer, wrongAnswers);

            if (questionToAdd != null)
            {
                String path = getServletContext().getRealPath("/");
                errorMessage = DataManager.AddQuestionToTriviaData(questionToAdd, path);
                
                if ("".equals(errorMessage))
                {
                    response.setContentType("text/html;charset=UTF-8");
                    try (PrintWriter out = response.getWriter()) {
                        out.println("<!DOCTYPE html>");
                        out.println("<html>");
                        out.println("<head>");
                        out.println("<link rel=\"stylesheet\" href=\"Main.css\"/>");
                        out.println("<title>New Question</title>");            
                        out.println("</head>");
                        out.println("<body>");
                        out.println("<h1>Your question has been successfully added!</h1>");
                        out.println("<br>");
                        out.println("<img src=\"Triviaquestion.jpg\">");
                        out.println("</body>");
                        out.println("</html>");
                    }
                }
            }
        }
        
        if (!"".equals(errorMessage))
        {
            request.setAttribute(NewQuestion.ErrorMessageAttribute, errorMessage);
            request.setAttribute(NewQuestion.WrongAnswers, wrongAnswers);
            
            RequestDispatcher rd = request.getRequestDispatcher("NewQuestion");
            rd.forward(request, response);
        }
    }
    
    private String ValidateUserInput(String question, String errorMessage, 
            String answer, String category, String difficulty, 
            String questionType, HttpServletRequest request, 
            ArrayList<String> wrongAnswers) {
        if (question == null || "".equals(question))
        {
            errorMessage += "You must enter a text for the question.<br>";
        }
        if (answer == null || "".equals(answer))
        {
            errorMessage += "You must enter a text for the answer.<br>";
        }
        if (category == null || "".equals(category))
        {
            errorMessage += "You must choose a category.<br>";
        }
        if (difficulty == null || "".equals(difficulty))
        {
            errorMessage += "You must choose a difficulty.<br>";
        }
        if (questionType.equals(NewQuestion.MultipleAnswerQuestionParameter))
        {
            boolean isValid = true;
            Map<String, String[]> map = request.getParameterMap();
            
            for(Entry<String, String[]> entry : map.entrySet()) {
                String key = entry.getKey();
                
                if (key.contains(NewQuestion.WrongAnswersParameter))
                {
                    String value = entry.getValue()[0];
                    
                    if (value == null || "".equals(value))
                    {
                        isValid = false;
                    }
                    
                    wrongAnswers.add(value);
                }
            }
            
            if (!isValid)
            {
                errorMessage += "All wrong answers must contain a value.<br>";
            }
            
            if (wrongAnswers.isEmpty())
            {
                errorMessage += "A Multiple Answer Question must contain atleast one wrong answer.<br>";
            }
        }
        return errorMessage;
    }
    
    private Question CreateNewQuestion(String difficulty, String category, 
            String questionType, String question, String answer, 
            ArrayList<String> wrongAnswers) throws NumberFormatException {
        Question questionToAdd = null;
        
        int difficultyIntOption = Integer.parseInt(difficulty);
        QuestionDifficulty questionDifficulty = QuestionDifficulty.values()[difficultyIntOption];
        
        int categoryIntOption = Integer.parseInt(category);
        Category questionCategory = Category.values()[categoryIntOption];
        
        switch (questionType) {
            case NewQuestion.OpenQuestionParameter:
                questionToAdd = new OpenQuestion(question, answer,
                        questionDifficulty, questionCategory);
                break;
            case NewQuestion.MultipleAnswerQuestionParameter:
                questionToAdd = new MultipleAnswersQuestion(question, wrongAnswers, answer,
                        questionDifficulty, questionCategory);
                break;
            case NewQuestion.YesOrNoQuestionParameter:
                questionToAdd = new YesOrNoQuestion(question, ConvertBooleanAnswer(answer),
                        questionDifficulty, questionCategory);
                break;
            default:
                break;
        }
        return questionToAdd;
    }
    
    private boolean ConvertBooleanAnswer(String answer)
    {
        return Boolean.toString(true).equals(answer);
    }
    
    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
