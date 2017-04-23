
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
		success: function(data, textStatus, jqXHR) {
			if (data == null || data === "") {
				window.location.reload(); 
			} else {
				alert(data);	
			}
		},
		error: function(xhr, error, thrown) {
			alert('Error.');
		}
	});
}


$( document ).ready(componentHandler.upgradeAllRegistered());