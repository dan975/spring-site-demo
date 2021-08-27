// Adds active class to the main menu option for which current relative url matches.
// Login nav omitted intentionally.
$(function(){
    $('#mainLinks a.nav-link').each(function(){
        if ($(this).prop('href') == window.location.href) {
            $(this).addClass('active');
        }
    });
});

// Hide expanded menu when clicking anything else other than navbar toggler
$(document).ready(function () {
    $(document).click(function (event) {
        var click = $(event.target);
        var _open = $("#navbarCol").hasClass("show");
        if (_open === true && !click.hasClass("navbar-toggler")) {
            $(".navbar-toggler").click();
        }
    });
});
