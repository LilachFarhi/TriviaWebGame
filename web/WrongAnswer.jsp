<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <link rel="stylesheet" href="Main.css"/>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Wrong Answer</title>
    </head>
    <body>
        <jsp:useBean id="player" class="models.Player" scope="session"/> 
        <jsp:useBean id="PreviousAskedQuestion" class="models.Question" scope="session"/> 
        <h2> Current player: <jsp:getProperty name="player" property="firstName"/> 
            <jsp:getProperty name="player" property="lastName"/></h2>
        <img src="WrongAnswer.jpg" width="100" height="100"></img><br>
        <h3 id="wrongAnswer">Your answer is wrong. Better luck next time.</h3>
        <h4>The right answer is: <jsp:getProperty name="PreviousAskedQuestion" property="answer"/></h4>
        <img src="WrongAnswer2.jpg" width="100" height="100"></img>
        <br><br><a href="PlayGame">Next Question</a><br><br><br>
        <form action="FinishGame" method="GET">
            <input type="submit" value="FinishGame">
        </form>
    </body>
</html>