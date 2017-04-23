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

    var _init = function() {
        componentHandler.upgradeAllRegistered();
    };

    return {
        init: function() {
            _init();
        }
    }

}();

$( document ).ready( court.hack.createtask.init() );

//# sourceURL=createtask.js