$(document).ready(() => {
    $('.markdown_text').each((i, item)=> {
        $(item).html(getHTML($(item).html()));
    });

    $('.comment_input').keypress((event) => {
        if (event.which === 13)
            sendComment($(event.currentTarget))
    });

    $('.comment_like').on('click', likeClick);
});

function likeClick(event) {
    $.ajax({
        type: 'POST',
        url: 'main/addLike',
        data: {
            "commentId" : $($(event.currentTarget).closest('.comment_div')).attr('value')
        },
        success: (object) => { changeLike(JSON.parse(object)) }
    });
}

function changeLike(object) {
    like = $('#comment_div' + object.commentId + ' .comment_like');
    object.isRemove ? changeClass(like.find('.like_icon'), 'fas', 'far') : changeClass(like.find('.like_icon'), 'far', 'fas');

    like.find('.count_like').text(object.countLikes);
}

function changeClass(object, removeClass, addClass) {
    object.removeClass(removeClass);
    object.addClass(addClass);
}

function sendComment(input) {
    if ((input.val().length > input.attr('maxlength')) || (input.val().length < input.attr('minlength')))
        return;

    $.ajax({
        type: 'POST',
        url: 'main/addComment',
        data: {
            "comment" : input.val(),
            "summaryId" : $(input.closest('.summary-area')).attr('value')
        }
    });

    input.val('');
}

function addComment(object) {
    divMain = $('<div></div>')
        .addClass('comment_div')
        .attr('id', 'comment_div' + object.commentId)
        .attr('value', object.commentId);
    divMain.append(
        $('<h5></h5>').text(object.username)
    );
    divMain.append(
        $('<p></p>').text(object.message)
    );

    divLike = $('<div></div>')
        .addClass('comment_like');
    divLike.append(
        $('<i></i>')
            .addClass('like_icon far fa-heart mr-1')
            .on('click', likeClick)
    );
    divLike.append(
        $('<p></p>')
            .addClass('count_like d-inline mb-auto')
            .text('0')
    );

    divMain.append(divLike);
    divMain.append(
        $('<hr>')
            .attr('align', 'center')
            .attr('size', '2')
    );

    $('#summary-area' + object.summaryId + ' .comment_area').append(divMain);
}