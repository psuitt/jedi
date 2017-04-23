$(document).ready(function () {
    init();
});

function init(){
    ajax({
        url: "/jedi/api/home/menu",
        type: "GET",
        success: function (data) {
            var menu = $("#menu");
            for(var i = 0; i < data.length; i++){
                var menuItem = $('<A>',{
                    text: data[i].name,
                    href: "#" + data[i].name,
                    id: data[i].name,
                    class: 'mdl-navigation__link mdl-typography--text-uppercase'
                });
                menuItem.attr("url", data[i].url);
                menuItem.click(function(){
                    renderView($(this).attr("url"));
                });
                menuItem.appendTo(menu);
            }
            initView();
        }
    });
}

function initView(){
    renderView(getUrlFromName(window.location.href.split('#')[1]));
}

function renderView(url){
    if(url){
        $("#viewer").load(url);
    }
}

function getUrlFromName(name){
    var menuItem = $("#"+name);
    var url = $(menuItem).attr("url");
    return url ? url : "";
}

function ajax(ajaxOptions) {
    "use strict";
    var defaultOptions = {
        type: "GET",
        dataType: "json",
        context: this,
        headers: {
            'Content-Type': 'application/json',
            'Accept': 'application/json'
        },
        cache: false,
        blockElement: undefined,
        onStatus401: function(){ window.location.reload(); },
    };
    var options = $.extend(defaultOptions, ajaxOptions);

    if(options["blockElement"]){
        $(options["blockElement"]).block({
            message: options["blockContent"],
            overrideJPODMessage: true
        });
    }

    $.ajax({
        type: options["type"],
        url: options["url"],
        data: options["data"],
        dataType: options["dataType"],
        context: options["context"],
        cache: options["cache"],
        headers: options["headers"],
        success: function (data) {
            if(options["success"]){
                options["success"].call(options["context"], data);
            }
        },
        error: function (xhr, status, error) {
            if (xhr.responseJSON) {
                xhr.responseJSON.ErrorMessage = xhr.responseJSON.ErrorMessage || "Server responded with out custom ErrorMessage";
            } else if (xhr.status === 401){
                options.onStatus401.call(options.context);
            } else if (xhr.status === 405){
                console.error(xhr);
            }
            if(options["error"]) {
                options["error"].call(options["context"], xhr, status, error);
            }
        },
        complete: function () {
            if(options["complete"]){
                options["complete"].call(options["context"]);
            }
            if(options["blockElement"]){
                $(options["blockElement"]).unblock();
            }
        }
    });
}

