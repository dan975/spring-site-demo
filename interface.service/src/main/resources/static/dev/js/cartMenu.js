$(document).ready(function () {
    $('#cartDropdown').on('show.bs.dropdown', function(e) {
        $('#cartMenu').stop(true, true).slideDown({
            complete: function(){
                $('#cartDropdown').removeClass('dropdown');
                $('#cartDropdown').addClass('dropup');
            }
        });
    });

    $('#cartDropdown').on('hide.bs.dropdown', function(e) {
        e.preventDefault();
        $('#cartMenu').stop(true, true).slideUp({
            complete: function(){
                $('#cartDropdown').removeClass('dropup');
                $('#cartDropdown').addClass('dropdown');
            }
        });
        $('#cartDropdown').removeClass('show');
        $('#cartMenu').removeClass('show');
    });

    $(document).on('click', '#cartMenu', function (e) {
      e.stopPropagation();
    });
});

