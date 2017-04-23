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

            var json = {
                title: $("#owner").val(),
                desc: $("#owner").val(),
                date: $("#owner").val(),
                status: "",
                creatorId: $("#owner").val(),
                ownerId: $("#owner").val(),
                //eventId,
                reminderDate,
                sentFlag: "N"
            };

            $.ajax({
                url: "/jedi/api/createtask",
                method: "POST",
                data: JSON.stringify(json),
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