$(document).ready(function() {
    var dropZone = $('#textSummary'),
        fileForm = $('#fileUploadForm'),
        maxFileSize = 1000000;

    dropZone[0].ondragover = function() {
        //dropZone.addClass('hover');
        return false;
    };

    dropZone[0].ondragleave = function() {
        //dropZone.removeClass('hover');
        return false;
    };

    dropZone[0].ondrop = function(event) {
        event.preventDefault();
        // dropZone.removeClass('hover');
        // dropZone.addClass('drop');

        // if (event.dataTransfer.files.size > maxFileSize) {
        //     dropZone.text('Файл слишком большой!');
        //     dropZone.addClass('error');
        //     return false;
        // }

        data = new FormData();
        jQuery.each(event.dataTransfer.files, function(i, file) {
            data.append('file', file);
        });

        console.log(data);

        $.ajax({
            type: "POST",
            enctype: 'multipart/form-data',
            url: "/main/uploadImg",
            data: data,
            processData: false,
            contentType: false,
            cache: false,
            success: (data) => {
                alert("success" + data);
            },
            error: (data) => {
                alert("error" + data);
            }
        });

    };
});



