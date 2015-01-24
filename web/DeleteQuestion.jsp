<%@page import="controllers.DeleteQuestion"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title> Delete Question</title>
        <link rel="stylesheet" href="Main.css"/>
    </head>
    
    <body>
        <% Object errorMessage = request.getAttribute(DeleteQuestion.ErrorMessage); 
         if(errorMessage != null && errorMessage.equals(""))
        { %>
            <br>
            <br>
            <h1>The question was successfully deleted.</h1> 
            <br>
            <img src="ThumbsUp.gif" width="150" height="250">
        <% }  else {%>
                <h3 id="errorMessage"> <%= errorMessage %> </h3>
                <br>
                <br>
                <a href="RemoveQuestion"> Back </a>
        <% } %>
    </body>
</html>
