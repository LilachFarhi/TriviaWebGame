<%@page import="java.util.Map.Entry"%>
<%@page import="java.util.Map"%>
<%@page import="models.Category"%>
<%@page import="controllers.StartGame"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <link rel="stylesheet" href="Main.css"/>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Finish Game</title>
    </head>
    <body>
        <jsp:useBean id="player" class="models.Player" scope="session"/> 
        
        <% Integer score = (Integer)session.getAttribute(StartGame.FinalScoreAttribute);
           Map<Category, Integer> questionsCorrect = 
                            (Map<Category, Integer>)session.getAttribute(StartGame.QuestionsCorrectByCategoryAttribute);
           Map<Category, Integer> questionsAsked = 
                            (Map<Category, Integer>)session.getAttribute(StartGame.QuestionsAskedByCategoryAttribute); %>
                            
        <h1>Thank you for playing, <jsp:getProperty name="player" property="firstName"/> 
            <jsp:getProperty name="player" property="lastName"/>!</h1><br>
            <table>
                <tr>
                    <th>Categories you have played</th>
                    <th>Number of questions you have been asked</th>
                    <th>Number of questions you have answered correctly</th>
                </tr>
                
                <% for (Entry<Category, Integer> askedQuestions : questionsAsked.entrySet()) { %>
                    <tr>
                        <td> <%= askedQuestions.getKey().name() %> </td>
                        <td> <%= askedQuestions.getValue() %> </td>
                        <td> <%= questionsCorrect.get(askedQuestions.getKey()) %> </td>
                    </tr>
                <% } %>
            </table><br><br>
            <h3>Your final score is: <%= score.toString() %> </h3><br>
    </body>
</html>
