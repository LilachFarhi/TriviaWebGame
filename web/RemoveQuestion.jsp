
<%@page import="controllers.RemoveQuestion"%>
<%@page import="java.util.List"%>
<%@page import="models.Question"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <link rel="stylesheet" href="Main.css"/>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Remove Question</title>
    </head>
    <body>
        <% List<Question> allQuestions = (List<Question>)request.getAttribute(RemoveQuestion.AllQuestionsAttribute); %>
        <% Object errorMessageString = request.getAttribute(RemoveQuestion.ErrorMessage); %>
        <% if(allQuestions.size() > 0)
        { %>
            <h1> Please choose a question to delete</h1>
            <form action="DeleteQuestion" method="GET">
            <%for (int i = 0; i < allQuestions.size(); i++) 
                {%>
                    <div>
                        <input type="radio" name="Question" value="<%= i %>"> <%= allQuestions.get(i).getQuestion() %> 
                    </div>
               <% } %>
            <br>
            <div><input type="submit" value="Delete"></div>
        </form>
            <% } else { %>
               <img src="oops.jpg" width="300" height="150" >
               <h3 id="errorMessage" > <%= errorMessageString.toString() %> </h3>
            <% }%>
    </body>
</html>
