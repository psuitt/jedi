/**
 * Created by b888pcs on 4/22/2017.
 */
// Passing a named function instead of an anonymous function.

court = court || {};
court.hack = court.hack || {};
court.hack.task = court.hack.task || {};

court.hack.task = function() {

    var _loadTasks = function() {

        

    };

    return {
        init: function() {
            _loadTasks();
        }
    }

}();
function readyFn( jQuery ) {
    // Code to run when the document is ready.
}

$( document ).ready( court.hack.task.init() );