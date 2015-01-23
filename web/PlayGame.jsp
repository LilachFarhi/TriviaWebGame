<%@page import="models.OpenQuestion"%>
<%@page import="java.util.Collections"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="models.MultipleAnswersQuestion"%>
<%@page import="controllers.PlayGame"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <link rel="stylesheet" href="Main.css"/>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Play Game</title>
    </head>
    <body>
        <jsp:useBean id="question" type="models.Question" scope="session"/> 
        <jsp:useBean id="player" scope="session" class="models.Player"/> 
        <h2> Current player: <jsp:getProperty name="player" property="firstName"/> 
            <jsp:getProperty name="player" property="lastName"/></h2>
            
            <% 
                Object errorMessage = request.getAttribute(PlayGame.ErrorMessageAttribute);
                if (errorMessage != null)
                { %>
                <h3 id="errorMessage"><%= errorMessage %></h3>  
                <% }
            %>
            
            <form action="CheckQuestionAnswer" method="GET">
                <h3> Question: <jsp:getProperty name="question" property="question"/></h3><br>
                
                <% if (question instanceof MultipleAnswersQuestion) { %>
                    <h3>Choose the correct answer: </h3>
                    <% MultipleAnswersQuestion multipleQuestion = (MultipleAnswersQuestion) question;
                    List<String> allAnswers = new ArrayList(multipleQuestion.getWrongAnswers());
                    allAnswers.add(multipleQuestion.getAnswer());
                    Collections.shuffle(allAnswers);
                    for (String currentAnswer : allAnswers) {
                    %>
                    <input type="radio" name="answer" value="<%= currentAnswer %>"><%= currentAnswer %><br>
                <% } } 
                else if (question instanceof OpenQuestion) { %>
                    <h3>Enter the answer: </h3>
                    <input type="text" name="answer" size="75"><br>
                <% } %>
                
                <br><input type="submit" value="Submit">
            </form><br>
            <form action="FinishGame.jsp" method="GET">
                <input type="submit" value="Finish Game">
            </form>
    </body>
</html>
