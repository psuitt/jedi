
function submitClicked() {
	var data = {
			email: $("#email").val(),
			password: $("#userpass").val(),
			firstName: $("#firstName").val(),
			lastName: $("#lastName").val(),
			accountType: $("#accountType").val(),
			phoneNumber: $("#phoneNumber").val()
	}
	$.ajax({
		type: "POST",
		url: "/jedi/api/create_account",
		data: JSON.stringify(data),
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


$( document ).ready(componentHandler.upgradeAllRegistered());