/**
 * Created by b888pcs on 4/22/2017.
 */
var court = court || {};
court.hack = court.hack || {};
court.hack.task = court.hack.task || {};

court.hack.task = function() {

    var _list = [
        {title: "Title 1", desc: "Description 1", date: "12/1/2017", time: "10:10pm"},
        {title: "Title 2", desc: "Description 2", date: "12/2/2017", time: "10:10pm"},
        {title: "Title 3", desc: "Description 3", date: "12/3/2017", time: "10:10pm"},
        {title: "Title 4", desc: "Description 4", date: "12/4/2017", time: "10:10pm"},
        {title: "Title 5", desc: "Description 5", date: "12/5/2017", time: "10:10pm"},
        {title: "Title 6", desc: "Description 6", date: "12/6/2017", time: "10:10pm"}
    ];

    var _loadTasks = function() {

        var tasks = $("#tasks");

        tasks.html(" ");

        _.each(_list, _loadTask);

    };

    var _loadTask = function(value, index) {

        var tasks = $("#tasks");
        var li = $("<li></li>");

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
        col2.html(value.date);

        spanTitle.append(col2);

        var col3 = $("<span></span>");
        col3.addClass("col-3");
        col3.html(value.time);

        spanTitle.append(col3);

        spanDescription.addClass("mdl-list__item-sub-title");
        spanDescription.text(value.desc);

        spanPrimary.append(spanTitle);
        spanPrimary.append(spanDescription);

        li.append(spanPrimary);

        var spanSecondary = $("<span></span>");

        spanSecondary.addClass("mdl-list__item-secondary-action");

        var label = $("<label></label>");

        label.attr("for", "input-" + index);
        label.addClass("mdl-checkbox mdl-js-checkbox mdl-js-ripple-effect");

        var input = $("<input/>");

        input.attr("id", "input-" + index);
        input.attr("type", "checkbox");
        input.addClass("mdl-checkbox__input");

        label.append(input);

        spanSecondary.append(label);

        li.append(spanSecondary);

        tasks.append(li);

        tasks.upgradeElement();

    };

    var _init = function() {
        _loadTasks();

        $("#taskAdd").click(function() {
            var inputs = $("#taskInputs input");
            var task =  {
                title: inputs.eq(0).val(),
                desc: inputs.eq(1).val(),
                date: inputs.eq(2).val(),
                time: inputs.eq(3).val()
            };
            _loadTask(task, $("#tasks li").length);
        });
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