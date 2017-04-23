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

            var json = {
                title: $("#title").val(),
                desc: $("#desc").val(),
                date: new moment($("#date").val()).toDate(),
                status: "",
                creatorId: $("#owner").val(),
                ownerId: "",
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
                    if (data && data.length > 0) {

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
    };

    return {
        init: function() {
            _init();
        }
    }

}();

$( document ).ready( court.hack.createtask.init() );

//# sourceURL=createtask.js