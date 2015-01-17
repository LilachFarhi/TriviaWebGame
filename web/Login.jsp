<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <link rel="stylesheet" href="Main.css"/>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Login</title>
    </head>
    <body>
        <form action="ValidateUserData" method="POST">
            <img src="Welcome.png" height="400" width="800"/><br><br>
            <h1>Please login to start the game</h1><br>
            <% 
                Object errorMessage = request.getAttribute("errorMessage");
                if (errorMessage != null)
                { %>
                <h3 id="errorMessage">There are validation errors: <%= errorMessage %></h3>  
                <% }
            %>
            
            <h2>First name: </h2>
            <input type="text" name="firstName" size="50" value='${param.firstName}'>
            <h2>Last name: </h2>
            <input type="text" name="lastName" size="50" value='${param.lastName}'><br><br>
            <input type="checkbox" name="rememberMe" value="true">Remember me<br>
            <input type="submit" value="Submit">
        </form>
    </body>
</html>
