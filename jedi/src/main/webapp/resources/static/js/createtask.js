/**
 * Created by b888pcs on 4/22/2017.
 */
/**
 * Created by b888pcs on 4/22/2017.
 */
var court = court || {};
court.hack = court.hack || {};
court.hack.createtask = court.hack.createtask || {};

court.hack.createtask = function() {

    var _addListeners = function() {

        $("#saveCreateTask").click(function() {

            var m = new moment();
                m.subtract(1, 'd');

            var account = Cookies.get("account");

            if (!account) {
                location.href = window.location.origin + '/jedi/api/login';
            }

            account = JSON.parse(account);

            var json = {
                title: $("#title").val(),
                desc: $("#desc").val(),
                date: new moment($("#date").val()).toDate(),
                status: "P",
                creatorId: account.accountId,
                ownerId: $("#ownerId").val(),
                email: $("#ownerEmail").val(),
                //eventId,
                reminderDate: m.toDate(),
                sentFlag: "N"
            };

            $.ajax({
                url: "/jedi/api/createtask",
                method: "PUT",
                data: JSON.stringify(json),
                contentType: "application/json",
                success: function (data) {
                    if (!data) {
                        document.querySelector('#title').parentNode.MaterialTextfield.change("");
                        document.querySelector('#desc').parentNode.MaterialTextfield.change("");
                        document.querySelector('#date').parentNode.MaterialTextfield.change("");
                        document.querySelector('#ownerEmail').parentNode.MaterialTextfield.change("");
                    } else {
                        alert(data);
                    }
                },
                error: function (xhr, status, error) {

                },
                complete: function () {

                }
            });
        });
    };

    var _init = function() {
        componentHandler.upgradeAllRegistered();
       _addListeners();
		var accountString = Cookies.get("account");
		if (accountString == null) {
			window.location.href='/jedi/api/login'; 
		} else {
			var account = JSON.parse(accountString);
			if (account.accountType === "USER") {
				document.querySelector('#ownerId').parentNode.MaterialTextfield.change(account.accountId);
				$("#ownerEmail").parent().hide();
			}
		}
    };

    return {
        init: function() {
            _init();
        }
    }

}();

$( document ).ready( court.hack.createtask.init() );

//# sourceURL=createtask.js