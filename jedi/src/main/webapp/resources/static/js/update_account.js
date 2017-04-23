
function searchClicked() {
	$.ajax({
		type: "GET",
		url: "/jedi/api/update_account/" + $("#accountId").val(),
		contentType : "application/json",
		cache: false,
		async: false,
		success: function(data) {
			if (data) {
			    document.querySelector('#email').parentNode.MaterialTextfield.change(data.email);
			    document.querySelector('#userpass').parentNode.MaterialTextfield.change(data.password);
			    document.querySelector('#firstName').parentNode.MaterialTextfield.change(data.firstName);
			    document.querySelector('#lastName').parentNode.MaterialTextfield.change(data.lastName);
			    document.querySelector('#accountType').parentNode.MaterialTextfield.change(data.accountType);
			    document.querySelector('#phoneNumber').parentNode.MaterialTextfield.change(data.phoneNumber);
				
				$(".beforeSearch").hide();
				$(".afterSearch").show();
			} else {
				alert("Something went awry.");	
			}
		},
		error: function(xhr, error, thrown) {
			alert('Error.');
		}
	});
}

function submitClicked() {
	var data = {
			accountId: $("#accountId").val(),
			email: $("#email").val(),
			password: $("#userpass").val(),
			firstName: $("#firstName").val(),
			lastName: $("#lastName").val(),
			accountType: $("#accountType").val(),
			phoneNumber: $("#phoneNumber").val()
	}
	$.ajax({
		type: "POST",
		url: "/jedi/api/update_account",
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

function init() {
	$(".afterSearch").hide();
	componentHandler.upgradeAllRegistered();
	var accountString = Cookies.get("account");
	if (accountString == null) {
		window.location.href='/jedi/api/login'; 
	} else {
		var account = JSON.parse(accountString);
		document.querySelector('#accountId').parentNode.MaterialTextfield.change(account.accountId);
		searchClicked();
	}
}


$( document ).ready(init());