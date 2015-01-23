<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <link rel="stylesheet" href="Main.css"/>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Correct Answer</title>
    </head>
    <body>
        <jsp:useBean id="player" scope="session" class="models.Player"/> 
        <h2> Current player: <jsp:getProperty name="player" property="firstName"/> 
            <jsp:getProperty name="player" property="lastName"/></h2>
        <img src="CorrectAnswer.jpg" width="100" height="100"></img><br>
        <h3 id="correctAnswer">Your answer is correct!</h3>
        <img src="CorrectAnswer2.jpg" width="100" height="100"></img>
        <br><br><a href="PlayGame">Next Question</a><br><br><br>
        <form action="FinishGame.jsp" method="GET">
            <input type="submit" value="Finish Game">
        </form>    
    </body>
</html>