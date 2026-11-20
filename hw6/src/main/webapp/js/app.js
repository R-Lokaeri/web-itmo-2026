// Всплывающее уведомление в правом нижнем углу.
window.notify = function (message, isError) {
    $.notify(message, {
        position: "right bottom",
        className: isError ? "error" : "success"
    });
};

window.ajax = function (options) {
    const defaultOptions = {
        type: "POST",
        dataType: "json",
        success: function (response) {
            if (response.redirect) {
                location.href = response.redirect;
            } else if (options.successCallback) {
                options.successCallback(response);
            }
        },
        error: function (jqXHR, textStatus, errorThrown) {
            console.error("AJAX Error:", textStatus, errorThrown);
        }
    };

    $.ajax($.extend({}, defaultOptions, options));
};
