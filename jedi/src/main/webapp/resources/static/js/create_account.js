
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
		dataType: "json",
		contentType : "application/json",
		cache: false,
		async: false,
		success: function(data) {
			if (data) {
				window.location.reload(); 
			} else {
				alert("Something went awry.");	
			}
		},
		error: function(xhr, error, thrown) {
			alert('Error.');
		}
	});
}


$( document ).ready(componentHandler.upgradeAllRegistered());