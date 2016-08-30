<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>Hello World Endpoints</title>

    <!-- Bootstrap -->
    <link href="//maxcdn.bootstrapcdn.com/bootstrap/3.3.1/css/bootstrap.min.css" rel="stylesheet">

    <script src="hello.js"></script>

    <!--  Load the Google APIs Javascript client library -->
    <!--  Then call the init function, which is defined in hello.js -->
    <script src="https://apis.google.com/js/client.js?onload=init"></script>

    <!-- Reference my style sheet, placed under WEB-INF. -->
    <link rel="stylesheet" type="text/css" href="mystyle.css">
    <link rel="stylesheet" href="http://daneden.github.io/animate.css/animate.min.css">

</head>

<body>
<div id="statusMessage">
</div>
<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>

<!-- Include all compiled plugins (below), or include individual files as needed -->
<script src="//maxcdn.bootstrapcdn.com/bootstrap/3.3.1/js/bootstrap.min.js"></script>

<h1 id="testerino" class="text-center animated infinite shake">My 84th Web App?</h1>

<img class="center-block animated infinite tada" style="display: flex; align-items: center; justify-content: center;border-radius: 400px; border: solid 11px pink; width: 300px;" src="https://scontent-amt2-1.xx.fbcdn.net/v/t1.0-9/1918733_156001206749_5259181_n.jpg?oh=29b076557543936e1ea7b9061620c5f2&oe=58487E34">

<div class="wrapper">
    <form id="loginForm" class="form-signin" method="POST">
        <h2 class="form-signin-heading">Please login</h2>
        <input type="text" class="form-control" id="username" name="username" placeholder="Email Address" required=""/>
        <input type="password" class="form-control" id="password" name="password" placeholder="Password" required=""/>
        <label class="checkbox">
            <input type="checkbox" value="remember-me" id="rememberMe" name="rememberMe"> Remember me
        </label>
        <button class="btn btn-lg btn-primary btn-block" type="button" data-action="submit" id="loginb">Login</button>
    </form>
</div>

</body>


</html>


