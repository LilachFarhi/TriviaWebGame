
<%@page import="java.util.List"%>
<%@page import="models.Question"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.HashMap"%>
<%@page import="models.QuestionDifficulty"%>
<%@page import="models.Category"%>
<%@page import="java.util.Map.Entry" %>
<%@page import="controllers.StartGame"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <link rel="stylesheet" href="Main.css"/>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Error in Start Game</title>
    </head>
    <body>
        <% HashMap<Category, QuestionDifficulty> missingQuestions = (HashMap<Category, QuestionDifficulty>)request.getAttribute(StartGame.MissingQuestionAttribute); 
         boolean nothingSelected = (boolean)request.getAttribute(StartGame.NothingSelectedAttribute); 
         HashMap<String, List<Category>> missingCategoryOrDifficutly = (HashMap<String, List<Category>>)request.getAttribute(StartGame.MissingCategoryOrDifficutlyAttribute); 
         if (nothingSelected) 
            {%>
                 <img src="oops.jpg" width="300" height="150" >
                 <h1 id="errorMessage">Please chose categories and difficulties </h1>
            <%}
            else
            {%>
                <img src="Missing.jpg" width="150" height="150">
                <% if(!missingCategoryOrDifficutly.isEmpty())
                {
                    for(Entry<String, List<Category>> entry : missingCategoryOrDifficutly.entrySet())
                    {%>
                        <h1 id="errorMessage"> <%= entry.getKey() %> </h1>
                        <ul id="errorMessage" >
                        <%for(Category category : entry.getValue())
                        { %> 
                            <li> <h2> <%= category.name() %> </h2></li>
                        <%}%>
                        </ul>
                        <br>
                        <br>
                     <%}
                   
               } 
               else
               { %>
                    <h1 id="errorMessage">For these categories and difficulties we don't have questions </h1>
                    <h1 id="errorMessage">If you would like to proceed please add a new question </h1>
                    <ul id="errorMessage">
                   <% for(Entry<Category, QuestionDifficulty> entry : missingQuestions.entrySet())
                   {%>
                   <li> <h2> <%=entry.getKey().name() %> , <%=entry.getValue().name() %> </h2> </li>
                       
                 <%  }%>
                   </ul>
                <%  }
            }%>
              
                <img src="Error.png" width="150" height="150" >
                <br>
                <br>
                <a href="StartGame.html">Back</a>
    </body>
</html>
