var court = court || {};
court.hack = court.hack || {};
court.hack.users = court.hack.users || {};

court.hack.users = function() {

    var _loadUsers = function() {

        var users = $("#users");

        users.html(" ");

        $.ajax({
            url: "/jedi/api/users/data",
            method: "GET",
            success: function (data) {
                if (data && data.length > 0) {
                    _.each(data, _loadTask);
                }
            },
            error: function (xhr, status, error) {

            },
            complete: function () {

            }
        });


    };

    var _loadTask = function(value, index) {
        console.log(index);
        var name;
        var users = $("#users");
        var li = $("<li></li>");
        li.addClass("mdl-list__item mdl-list__item--two-line");

        var spanPrimary = $("<span></span>");
        var spanName = $("<span></span>");
        var personIcon = $("<i></i>");

        spanPrimary.addClass("mdl-list__item-primary-content");
        personIcon.addClass("material-icons mdl-list__item-avatar");


        if(value.middleName){
            spanName.html(value.firstName + " " + value.middleName + " " + value.lastName);
        } else {
            spanName.html(value.firstName + " " + value.lastName);
        }

        personIcon.html("person");

        spanPrimary.append(personIcon);
        spanPrimary.append(spanName);
        
        spanPrimary.on("click", function() {
        	Cookies.set("email", value.email);
    		renderView('/jedi/resources/pages/task.html'); 
        });

        li.append(spanPrimary);

        users.append(li);

    };

    var _init = function() {
        _loadUsers();
    };

    return {
        init: function() {
            _init();
        }
    }

}();

$( document ).ready( court.hack.users.init() );