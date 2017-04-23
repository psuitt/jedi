/**
 * Created by b888rml on 4/22/2017.
 */

function submitClicked() {
	var data = $("#username").val() + "," +	$("#userpass").val();
	$.ajax({
		type: "POST",
		url: "/jedi/api/login",
		data: data,
		dataType: "json",
		contentType : "application/json",
		cache: false,
		async: false,
		success: function(data) {
			if (data) {
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
