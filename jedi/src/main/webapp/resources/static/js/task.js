/**
 * Created by b888pcs on 4/22/2017.
 */
var court = court || {};
court.hack = court.hack || {};
court.hack.task = court.hack.task || {};

court.hack.task = function() {

    var _dialog;

    var _loadTasks = function(email) {

        var tasks = $("#tasks");

        tasks.html(" ");
        $.ajax({
            url: "/jedi/api/task/data/" + email,
            method: "GET",
            success: function (data) {
                if (data && data.length > 0) {
                    _.each(data, _loadTask);
                }
            },
            error: function (xhr, status, error) {},
            complete: function () {}
        });
    };

    var _loadTask = function(value, index) {

        var tasks = $("#tasks");
        var li = $("<li></li>");
        var date = new Date(value.date);
        var title = value.title;
        var typeIndex =  title.indexOf(":");
        var type = "";

        if (typeIndex > -1) {
            type = value.title.substr(0, typeIndex).toLowerCase();
            title = value.title.substr(typeIndex + 1);
        }

        li.addClass("mdl-list__item mdl-list__item--two-line " + type);

        var spanPrimary = $("<span></span>");
        var spanTitle = $("<span></span>");
        var spanDescription = $("<span></span>");

        spanPrimary.addClass("mdl-list__item-primary-content");

        spanTitle.addClass("span");

        var col1 = $("<span></span>");
        col1.addClass("col-1");
        col1.html(title);

        spanTitle.append(col1);

        var col2 = $("<span></span>");
        col2.addClass("col-2");
        col2.html(new moment(value.date).format("M/D/YYYY"));

        spanTitle.append(col2);

        var col3 = $("<span></span>");
        col3.addClass("col-3");

        if (date.getHours() != 0 || date.getMinutes() != 0) {
	        var hours = date.getHours();
	        var time = "AM";
	
	        if (hours > 11) {
	            hours -= 12;
	            time = "PM"
	        } else if (hours == 0) {
	            hours += 12;
	        }
	
	        var leadingZero = (date.getMinutes() < 10 ? "0":"");
	        col3.html(hours + ":" + leadingZero + date.getMinutes() + " " + time);
        }

        spanTitle.append(col3);

        spanDescription.addClass("mdl-list__item-sub-title");
        spanDescription.text(value.desc);

        spanPrimary.append(spanTitle);
        spanPrimary.append(spanDescription);

        li.append(spanPrimary);

        var spanSecondary = $("<span></span>");

        spanSecondary.addClass("mdl-list__item-secondary-action");

        var icon = $("<i/>");
        icon.addClass("material-icons mdl-list__item-avatar");

        if (type == "appt") {
            icon.html("&#xE85D;");
        } else if (type == "task") {
            icon.html("&#xE8B5;");
        }


        spanPrimary.prepend(icon);

        li.append(spanSecondary);
        li.data("data", value);

        tasks.append(li);

    };

    var _setUpDialog = function() {

        _dialog = document.querySelector('dialog');
        var showDialogButton = document.querySelector('#taskAdd');

        if (! _dialog.showModal) {
            dialogPolyfill.registerDialog(dialog);
        }

        showDialogButton.addEventListener('click', function() {
            _dialog.showModal();
        });

        _dialog.querySelector('.close').addEventListener('click', function() {
            _dialog.close();
            $("#dialogTitle").html("Add Task");
        });

        _dialog.querySelector('.add').addEventListener('click', function() {
            var inputs = $("#dialogInputs input");
            var task =  {
                title: inputs.eq(0).val(),
                desc: inputs.eq(1).val(),
                date: new Date(),
                time: inputs.eq(3).val(),
                status: "close"
            };
            _loadTask(task, $("#tasks li").length);
            componentHandler.upgradeAllRegistered();
            _dialog.close();
            $("#dialogTitle").html("Add Task");
        });

    };

    var _editDblClk = function() {

        var self = $(this),
            data = self.data("data");

        $("#dialogTitle").html("Edit Task");
        self.addClass("selected");

        document.querySelector('#title').parentNode.MaterialTextfield.change(data.title);
        document.querySelector('#description').parentNode.MaterialTextfield.change(data.desc);
        document.querySelector('#date').parentNode.MaterialTextfield.change(data.date);
        document.querySelector('#status').parentNode.MaterialTextfield.change(data.status);

        _dialog.showModal();

    };

    var _init = function() {
        $(document).off("dblclick", "#tasks li", _editDblClk);
        $(document).on("dblclick", "#tasks li", _editDblClk);
    	var accountString = Cookies.get("account");
    	if (accountString == null) {
    		window.location.href='/jedi/api/login'; 
    	} else {
        	var emailParam = Cookies.get("email");
        	if (emailParam === null || emailParam === null || emailParam === "" || typeof emailParam === "undefined") {
        		var account = JSON.parse(accountString);
        		_loadTasks(account.email);
        	} else {
        		Cookies.remove("email");
        		_loadTasks(emailParam);
        	}
    	}
        _setUpDialog();
        getmdlSelect.init(".getmdl-select");
    };

    return {
        init: function() {
            _init();
        }
    }

}();
function readyFn( jQuery ) {
    // Code to run when the document is ready.
}

$( document ).ready( court.hack.task.init() );

//# sourceURL=task.js