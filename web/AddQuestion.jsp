<%@page import="controllers.NewQuestion"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <link rel="stylesheet" href="Main.css"/>
        <title>New Question</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
    </head>
    <body>
        <% Object errMessage = request.getAttribute(NewQuestion.ErrorMessageAttribute); %>
        
        <% if (errMessage != null && !errMessage.equals("")) { %>
                <h3 id="errorMessage"> <%= errMessage.toString() %> </h3>
            <% }%>
            
        <form action="NewQuestion" method="GET">
            <h2>Please choose the type of question you would like to add:</h2>
            <input type="radio" name="questionType" value="Open">Open Question<br>
            <input type="radio" name="questionType" value="MultipleAnswer">Multiple Answer Question<br>
            <input type="radio" name="questionType" value="YesNo">Yes or No Question<br><br>
            <input type="submit" value="Submit">
        </form>
    </body>
</html>