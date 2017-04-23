/**
 * Created by b888pcs on 4/22/2017.
 */
var court = court || {};
court.hack = court.hack || {};
court.hack.calendar = court.hack.calendar || {};

court.hack.calendar = function() {

    var _getEvents = function(start, end, timezone, callback) {

        $.ajax({
            url: "/jedi/api/task/data",
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

            var item = {
                title: event.title,
                start: date.format("YYYY-MM-DD"),
                className: event.status
            };

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
            eventLimit: true ,// allow "more" link when too many events
            displayEventTime: true
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

//# sourceURL=task.js