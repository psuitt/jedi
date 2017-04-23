/**
 * Created by b888pcs on 4/22/2017.
 */
var court = court || {};
court.hack = court.hack || {};
court.hack.task = court.hack.task || {};

court.hack.task = function() {

    var _loadTasks = function() {

        var tasks = $("#tasks");

        tasks.html(" ");

        $.ajax({
            url: "/jedi/api/task/data",
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

        var tasks = $("#tasks");
        var li = $("<li></li>");
        var date = new Date(value.date);

        li.addClass("mdl-list__item mdl-list__item--two-line");

        var spanPrimary = $("<span></span>");
        var spanTitle = $("<span></span>");
        var spanDescription = $("<span></span>");

        spanPrimary.addClass("mdl-list__item-primary-content");

        spanTitle.addClass("span");

        var col1 = $("<span></span>");
        col1.addClass("col-1");
        col1.html(value.title);

        spanTitle.append(col1);

        var col2 = $("<span></span>");
        col2.addClass("col-2");
        col2.html(date.getMonth() + "/" + date.getDay() + "/" + date.getFullYear());

        spanTitle.append(col2);

        var col3 = $("<span></span>");
        col3.addClass("col-3");

        var hours = date.getHours();
        var time = "AM";

        if (hours > 11) {
            hours -= 12;
            time = "PM"
        }

        col3.html(hours + ":" + date.getMinutes() + " " + time);

        spanTitle.append(col3);

        spanDescription.addClass("mdl-list__item-sub-title");
        spanDescription.text(value.desc);

        spanPrimary.append(spanTitle);
        spanPrimary.append(spanDescription);

        li.append(spanPrimary);

        var spanSecondary = $("<span></span>");

        spanSecondary.addClass("mdl-list__item-secondary-action");

        if (value.status) {
            var icon = $("<i/>");
            icon.addClass("material-icons md-48");
            icon.html(value.status);
            spanSecondary.append(icon);
        }

        li.append(spanSecondary);

        tasks.append(li);

    };

    var _setUpDialog = function() {

        var dialog = document.querySelector('dialog');
        var showDialogButton = document.querySelector('#taskAdd');

        if (! dialog.showModal) {
            dialogPolyfill.registerDialog(dialog);
        }

        showDialogButton.addEventListener('click', function() {
            dialog.showModal();
        });

        dialog.querySelector('.close').addEventListener('click', function() {
            dialog.close();
        });

        dialog.querySelector('.add').addEventListener('click', function() {
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
            dialog.close();
        });

    };

    var _init = function() {
        _loadTasks();

        _setUpDialog();
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