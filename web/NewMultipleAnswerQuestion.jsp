<%@page import="models.QuestionDifficulty"%>
<%@page import="models.Category"%>
<%@page import="java.util.ArrayList"%>
<%@page import="controllers.NewQuestion"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <script type='text/javascript'>
            function isInt(value) {
                return !isNaN(value) && (parseInt(Number(value)) == value) && !isNaN(parseInt(value, 10));
            }
            
            function addAnswerField() {
                var number = document.getElementById("numOfanswers").value;
                if (isInt(number)) {
                    var container = document.getElementById("container");
                    while (container.hasChildNodes()) {
                        container.removeChild(container.lastChild);}
                    var index = parseInt(Number(number));
                    for (i=0; i < index; i++) {
                        var k = i + 1;
                        container.appendChild(document.createTextNode("Answer " + k + ": "));
                        var input = document.createElement("input");
                        input.type = "text";
                        input.name = "wrongAnswers" + k.toString();
                        input.size="100";
                        container.appendChild(input);
                        container.appendChild(document.createElement("br"));
                    }
                }
            }
        </script>
        <link rel="stylesheet" href="Main.css"/>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>New Question</title>
    </head>
    <body>
         <% Object errMessage = request.getAttribute(NewQuestion.ErrorMessageAttribute); 
           String type = request.getParameter(NewQuestion.QuestionTypeAttribute);
           ArrayList<String> wrongAnswers = (ArrayList<String>)request.getAttribute(NewQuestion.WrongAnswers);
           String categoryValue = request.getParameter(NewQuestion.CategoryParameter);
           String difficultyValue = request.getParameter(NewQuestion.DifficultyParameter); %>
        
        <% if (errMessage != null && !errMessage.equals("")) { %>
                <h3 id="errorMessage">There are validation errors: <%= errMessage.toString() %> </h3>
            <% }%>
            
        <form action="AddQuestion" method="POST">
            <h2>Enter the question: </h2>
            <input type="text" name="question" size="75" value='${param.question}'>
            <br><br><br>
            <h2>Enter the answer: </h2>
            <input type="hidden" name="questionType" value="MultipleAnswer">
            <input type="text" name="answer" size="100" value="${param.answer}">
            <br><br>
            <h2>Enter the wrong answers: </h2>
            Enter number of wrong answers (then click on 'Ok'):
            <input type="text" id="numOfanswers" name="numOfanswers" value="">
            <a href="#" id="filldetails" onclick="addAnswerField()">Ok</a><br>
            <div>
                
            <% if (wrongAnswers != null) {
                int i = 1; %>
                <div id="container">
                <% for (String currWrongAnswer : wrongAnswers) {
                    if (currWrongAnswer == null) {
                        currWrongAnswer = "";
                    } %>
                    Answer <%= i %>: 
                    <input type="text" name="wrongAnswers<%= i %>" size="100" value="<%= currWrongAnswer %>">
                    <% i++; %>
                    <br>
                <% } %>
                </div>
            <% }
            else
            { %>
                <div id="container"></div>
            <% } %>
            </div>
            <br><br>
            
            <div id="CategoriesAndDifficulty">
                <table width="100%">
                    <tr>
                        <th width="40px">
                            <h2>Choose a category: </h2>
                        </th>
                        <th width="40px">
                            <h2>Choose a difficulty: </h2>
                        </th>
                    </tr>
                    <tr>
                        <td>
                            <% Category[] allCategories = Category.values();
                                for (Category category : allCategories) 
                                {
                                    String checked = "";
                                    int categoryInt = category.ordinal();
                                    if (categoryValue != null && (Integer.parseInt(categoryValue) == categoryInt)) {
                                        checked="checked";
                                    }
                                     %>
                                     <input type="radio" name="category" value="<%= categoryInt %>" <%= checked %>>
                                    <%= category.name() %>
                                    <br>
                                <% } %>
                        </td>
                        <td>
                            <% QuestionDifficulty[] allDifficulties = QuestionDifficulty.values();
                                for (QuestionDifficulty questionDifficulty : allDifficulties) 
                                {
                                    String checked = "";
                                    int difficultyInt = questionDifficulty.ordinal(); 
                                    if (difficultyValue != null && (Integer.parseInt(difficultyValue) == difficultyInt)) {
                                        checked="checked";
                                    }%>
                                    <input type="radio" name="difficulty" value="<%= difficultyInt %>" <%= checked %>>
                                    <%= questionDifficulty.name() %>
                                    <br>
                                <% } %>
                        </td>
                    </tr>
                </table>
            </div>
            <br><br>
            <div id="submitDiv">
                <input type="submit" value="Submit">
            </div>
        </form>
    </body>
</html>
