/**
 * Created by b888pcs on 4/22/2017.
 */
var court = court || {};
court.hack = court.hack || {};
court.hack.calendar = court.hack.calendar || {};

court.hack.calendar = function() {

    var _getEvents = function(start, end, timezone, callback) {

        var accountString = Cookies.get("account");
        if (accountString == null) {
            window.location.href='/jedi/api/login';
            return;
        }

        var account = JSON.parse(accountString);

        $.ajax({
            url: "/jedi/api/task/data/" + account.email,
            method: "GET",
            success: function (data) {
                if (data && data.length > 0) {
                    _loadEvents(data, callback);
                }
            },
            error: function (xhr, status, error) {

            },
            complete: function () {

            }
        });

    };

    var _loadEvents = function(data, callback) {

        var events = [];

        _.each(data, function (event, index) {

            var date = new moment(event.date);
            var title = event.title;
            var typeIndex =  title.indexOf(":");
            var type = "";

            if (typeIndex > -1) {
                type = event.title.substr(0, typeIndex).toLowerCase();
                title = event.title.substr(typeIndex + 1);
            }

            var item;

            if (type) {
                if (type == "appt") {
                	item = {
                             title: title,
                             start: date.format("YYYY-MM-DDTHH:mm:ss"),
                             className: event.status + " " + type,
                             color: "#4CAF50"
                         };
                	item.color = "#F44336";
                } else {
                	item = {
                            title: title,
                            start: date.format("YYYY-MM-DD"),
                            className: event.status + " " + type,
                            color: "#4CAF50"
                        };
                   item.color = "#3F51B5";
                }
            } else {
            	item = {
                        title: title,
                        start: date.format("YYYY-MM-DDTHH:mm:ss"),
                        className: event.status,
                        color: "#4CAF50"
                    };
            }

            if (index == 5) {
                item.title = "Must Watch",
                item.url = "https://www.youtube.com/watch?v=oHg5SJYRHA0"
            }

            events.push(item);
        });

        callback(events);

    };

    var _init = function() {
        $('#calendar').fullCalendar({
            events: _getEvents,
            timeFormat: 'h(:mm)t',
            header: {
                left: 'prev,next today',
                center: 'title',
                right: 'month,agendaWeek,agendaDay'
            },
            defaultDate: '2017-04-12',
            navLinks: true, // can click day/week names to navigate views
            eventLimit: true// allow "more" link when too many events
        });

        $(".fc-toolbar button").addClass("mdl-button mdl-js-button mdl-button--raised mdl-button--colored");
    };

    return {
        init: function() {
            _init();
        }
    }

}();

$( document ).ready( court.hack.calendar.init() );

//# sourceURL=calendar.js