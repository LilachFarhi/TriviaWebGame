<%@page import="models.*"%>
<%@page import="controllers.NewQuestion"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
         <link rel="stylesheet" href="Main.css"/>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>New Question</title>
    </head>
    <body>
         <% Object errMessage = request.getAttribute(NewQuestion.ErrorMessageAttribute); 
           String type = request.getParameter(NewQuestion.QuestionTypeAttribute);
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
            <input type="hidden" name="questionType" value="Open">
            <input type="text" name="answer" size="100" value="${param.answer}">
            
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
            <input type="submit" value="Submit">
        </form>
    </body>
</html>
