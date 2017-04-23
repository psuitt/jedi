ajax({
    url: "/jedi/api/users",
    type: "GET",
    success: function (data) {
        alert(JSON.stringify(data));
        var userList = $("#users");
        for(var i = 0; i < data.length; i++) {
            var userItem = $('<li>', {
                class: 'mdl-list__item mdl-list__item--two-line'
            });
            userItem.attr("user_id", data[i].url);
            userItem.click(function () {
                alert("I was Clicked.");
            });
            userItem.appendTo($('<i>', {
                text: 'person',
                class: 'material-icons mdl-list__item-avatar'
            }));
            userList.append(userItem);
        }
    }
});