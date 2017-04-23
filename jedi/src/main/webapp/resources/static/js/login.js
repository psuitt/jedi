/**
 * Created by b888rml on 4/22/2017.
 */

function submitClicked() {
	$.ajax({
		type: "GET",
		url: "/jedi/api/login/" + $("#username").val() + "," +	$("#userpass").val(),
		contentType : "application/json",
		cache: false,
		async: false,
		success: function(data) {
			if (data) {
				Cookies.set("account", data);
				window.location.href='/jedi/api/home'; 
			} else {
				alert('Invalid username or password.');	
			}
		},
		error: function(xhr, error, thrown) {
			alert('Error.');
		}
	});
}
