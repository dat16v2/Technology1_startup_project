/*
 * http://stackoverflow.com/questions/18260815/use-gapi-client-javascript-to-execute-my-custom-google-api
 * https://developers.google.com/appengine/docs/java/endpoints/consume_js
 * https://developers.google.com/api-client-library/javascript/reference/referencedocs#gapiclientload
 *
 */

/**
 * After the client library has loaded, this init() function is called.
 * The init() function loads the helloworldendpoints API.
 */

function init() {

	// You need to pass the root path when you load your API
	// otherwise calls to execute the API run into a problem

	// rootpath will evaulate to either of these, depending on where the app is running:
	// //localhost:8080/_ah/api
	// //your-app-id/_ah/api

	var rootpath = "//" + window.location.host + "/_ah/api";

	// Load the helloworldendpoints API
	// If loading completes successfully, call loadCallback function
	gapi.client.load('helloworldendpoints', 'v1', loadCallback, rootpath);
}

/*
 * When helloworldendpoints API has loaded, this callback is called.
 *
 * We need to wait until the helloworldendpoints API has loaded to
 * enable the actions for the buttons in index.html,
 * because the buttons call functions in the helloworldendpoints API
 */
function loadCallback () {
	// Enable the button actions
	enableButtons ();
}

function enableButtons () {
	// Set the onclick action for the first button
	btn = document.getElementById("input_greet_generically");
	btn.onclick= function(){greetGenerically();};

	// Update the button label now that the button is active
	btn.value="Click me for a generic greeting";

	// Set the onclick action for the second button
	btn = document.getElementById("input_greet_by_name");
	btn.onclick=function(){greetByName();};

	// Update the button label now that the button is active
	btn.value="Click me for a personal greeting";

	// Set the onclick action for the third button
	btn = document.getElementById("input_greet_by_period");
	btn.onclick=function(){greetByTimeOfDay();};

	// Update the button label now that the button is active
	btn.value="Click me for an even more personalized greeting";
}

/*
 * Execute a request to the sayHello() endpoints function
 */
function greetGenerically () {
	// Construct the request for the sayHello() function
	var request = gapi.client.helloworldendpoints.sayHello();

	// Execute the request.
	// On success, pass the response to sayHelloCallback()
	request.execute(sayHelloCallback);
}

/*
 * Execute a request to the sayHelloByName() endpoints function.
 * Illustrates calling an endpoints function that takes an argument.
 */
function greetByName () {
	// Get the name from the name_field element
	var name = document.getElementById("name_field").value;

	// Call the sayHelloByName() function.
	// It takes one argument "name"
	// On success, pass the response to sayHelloCallback()
	var request = gapi.client.helloworldendpoints.sayHelloByName({'name': name});
	request.execute(sayHelloCallback);
}

/*
 * Execute a request to the greetByPeriod() endpoints function.
 * Illustrates calling an endpoints function that takes an argument.
 */
function greetByTimeOfDay () {
	// Get the name from the name_field element
	var name = document.getElementById("name_field").value;
	var period = document.getElementById("period_select").value;

	// Call the greetByPeriod() function.
	// On success, pass the response to sayHelloCallback()
	var request = gapi.client.helloworldendpoints.greetByPeriod(
		{'name': name, 'period' : period});
	request.execute(sayHelloCallback);
}

// Process the JSON response
// In this case, just show an alert dialog box
// displaying the value of the message field in the response
function sayHelloCallback (response) {
	alert(response.message);
}

function login(username, password) {
    var request = new XMLHttpRequest();
    var params = "username=" + username + "&password=" + password;
    var paramsEncoded = encodeURI(params);
    request.open('POST', '/api/login', true);
    request.setRequestHeader("Content-type", "application/x-www-form-urlencoded");

    request.onload = function() {
        if (request.status >= 200 && request.status < 400) {
            var data = JSON.parse(request.responseText);

            if (data["status"] == false)
            {
                console.log("Error, either the username or password is wrong.");
                var statusMsgDiv = document.getElementById("statusMessage");
                statusMsgDiv.innerHTML = "<div class=\"alert alert-danger\">" +
                    "<a href=\"#\" class=\"close\" data-dismiss=\"alert\" aria-label=\"close\">&times;</a>" +
                    "<strong>Error!</strong> Either the username of password is wrong. Try again." +
                    "</div>";
            } else {
                console.log("Login successful, redirecting to user page.");
                window.location.href = "/user";
            }
        }
    };

    request.send(paramsEncoded);
}


window.onload=function(){
    var wrapper = document.getElementById("loginForm");
    var submitButton = document.getElementById("loginb");

    submitButton.addEventListener("click", function (event) {
        var username = document.getElementById("username").value;
        var password = document.getElementById("password").value;

        login(username, password);
    });
};