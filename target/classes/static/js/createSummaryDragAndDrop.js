const maxFileSize = 3 * 1024 * 1024;
var lastImageId = 0;

$(document).ready(function() {
    dropZone = $('#divTextId');
    //fileForm = $('#fileUploadForm');

    dropZone[0].ondragover = function(event) {
        //dropZone.addClass('hover');
        return false;
    };

    dropZone[0].ondragleave = function(event) {
        //dropZone.removeClass('hover');
        return false;
    };

    dropZone[0].ondrop = onDropAction;
});

function onDropAction(event) {
    event.preventDefault();
    // dropZone.removeClass('hover');
    // dropZone.addClass('drop');

    if (event.dataTransfer.files.length > 0)
        dropActionFile(event.dataTransfer.files);
}

function dropActionFile(allFiles) {
    filtredFiles = filterFile(allFiles);

    idFiles = createFilesIcons(filtredFiles);

    data = new FormData();
    data.append('username', userName);
    jQuery.each(filtredFiles, function(i, file) {
        data.append('files', file);
        data.append('idFiles', idFiles[i]);
    });

    sendAjax(data);
}

function createFilesIcons(files) {
    idFiles = [];

    $(files).each((i, file) => {
        img = createFileIcon(file);
        idFiles.push(img.attr('id'));
        $('#divLoaderImage').append(
            $('<div></div>').addClass('imageLoader m-3 position-relative d-inline-block')
                .append(img)
                .append(createAnimateLoad())
        );
    });

    return idFiles;
}

function createFileIcon(file) {
    let img = $('<img>');

    img.addClass('img-fluid h-100');
    img.attr('id', 'imageLoader' + lastImageId++);
    img.attr('title', file.name);
    img[0].ondragstart = (event) => {
        event.dataTransfer.setData("text", '![](' + img.attr('value') + ')');
    };

    reader = new FileReader();
    reader.onload = function(event) {
        img.attr('src', event.target.result);
    };
    reader.readAsDataURL(file);

    return img;
}

function createAnimateLoad() {
    animateDiv = $('<div></div>');
    animateDiv.addClass('fixed-top position-absolute w-100 h-100');
    animateDiv.attr('name', 'animateLoading');
    animateDiv.css('background-color', 'rgba(0, 0, 0, 0.5)');

    animate = $('<div></div>');
    animate.addClass('lds-ellipsis');
    for (i = 0; i < 4; i++) {
        animate.append($('<div></div>'));
    }
    animateDiv.append(animate);

    return animateDiv;
}

function filterFile(files) {
    spis = [];
    regex = RegExp('([/|.|\w|\s|-])*\.(?:jpg|gif|png)');
    $(files).each((i, file)=> {
       if ((file.size < maxFileSize) && (regex.test(file.name)))
           spis.push(file);
    });

    return spis;
}

function sendAjax(data) {
    $.ajax({
        type: "POST",
        enctype: 'multipart/form-data',
        url: "/main/uploadImg",
        data: data,
        processData: false,
        contentType: false,
        cache: false,
        success: (response) => {
            startLoadImg(JSON.parse(response).files)
        },
        error: () => {
            alert("error");
        }
    });
}

function startLoadImg(files) {
    $(files).each((i, file) => {
        // $('#' + file.id).attr("value", '![](' + file.fullPath + ')');
        imgItem = $('#' + file.id);
        imgItem.attr('value', file.fullPath);
        imgItem.attr('servername', file.name);
        imgItem.parent().append(
            $('<div></div>')
                .addClass('align-top position-absolute')
                .attr('title', 'Удалить')
                .css('background-color', 'rgba(0,0,0,0.6)')
                .css('top', '0')
                .css('right', '0')
                .append(
                    $('<i></i>')
                        .addClass('far fa-times-circle text-white')
                        .attr('value', file.id)
                        .on('click', (elem) => {
                            img = $('#' + $(elem.currentTarget).attr('value'));
                            sendToServer('/app/main/deleteImg', img.attr('servername'));
                            img.parent().remove();
                        })
                )
        )
    });
}

function dropLoadInimate(id) {
    $('#' + id).parent().find('div[name=animateLoading]').remove();
}