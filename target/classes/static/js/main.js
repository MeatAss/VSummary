$(document).ready(() => {
    $('.markdown_text').each((i, item)=> {
        $(item).html(getHTML($(item).html()));
    });

    $('.comment_input').keypress((event) => {
        if (event.which === 13)
            sendComment($(event.currentTarget))
    });

    $('.comment_like i')
        .on('click', likeClick)
        .on('mouseover', (event) => {inverseClass($(event.currentTarget), 'fas', 'far')})
        .on('mouseleave', (event) => {inverseClass($(event.currentTarget), 'fas', 'far')});

    $('.rating-area')
        .on('mousemove', ratingAreaMove)
        .on('mouseleave', ratingAreaLeave);

    $('.rating-area i').on('click', ratingClick);

    $('.summary-more').on('click', showMoreClick);
});

function ratingAreaMove(event) {
    marks = $(event.currentTarget).find('i');
    marks.removeClass('text-info');

    number = Math.ceil(
        (event.clientX - event.currentTarget.getBoundingClientRect().x) /
        (marks.length > 1 ?
            (marks[1].offsetLeft - (marks[0].offsetLeft + marks[0].clientWidth)) + marks[1].clientWidth :
            marks[0].clientWidth)
    );

    $(marks.slice(0, number)).addClass('text-info');
}

function ratingAreaLeave(event) {
    $(event.currentTarget).find('i').removeClass('text-info');
}

function inverseClass(object, first, second) {
    object.hasClass(first) ?
        changeClass(object, first, second) :
        changeClass(object, second, first)
}

function showMoreClick(event) {
    window.location.href = "/main/summary?summaryId=" + $(event.currentTarget).closest('.summary-area').attr('value');
}

function ratingClick(event) {
    $.ajax({
        type: 'POST',
        url: '/main/addRating',
        data: {
            "rating" : $(event.currentTarget).attr('value'),
            "summaryId" : $($(event.currentTarget).closest('.summary-area')).attr('value')
        }
    });
}

function changeRating(object) {
    rating = $('#summary-area' + object.summaryId + ' .rating-area i');

    rating.each((i, item) => {
        changeClass($(item), 'fas', 'far');
        if (i < object.avgRatings)
            changeClass($(item), 'far', 'fas');
    });
}

function likeClick(event) {
    $(event.currentTarget)
        .off('mouseleave')
        .on('mouseleave', (e) => {
            $(e.currentTarget)
                .off('mouseleave')
                .on('mouseleave', (event) => {inverseClass($(event.currentTarget), 'fas', 'far')});
        });


    $.ajax({
        type: 'POST',
        url: '/main/addLike',
        data: {
            "commentId" : $($(event.currentTarget).closest('.comment_div')).attr('value')
        },
        success: (object) => { changeLike(JSON.parse(object)) }
    });
}

function changeLike(object) {
    like = $('#comment_div' + object.commentId + ' .comment_like');

    object.isRemove ?
        changeClass(like.find('.like_icon'), 'fas', 'far') :
        changeClass(like.find('.like_icon'), 'far', 'fas');

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
        url: '/main/addComment',
        data: {
            "comment" : input.val(),
            "summaryId" : $(input.closest('.summary-area')).attr('value')
        }
    });

    input.val('');
}

function addComment(object) {
    $('#summary-area' + object.summaryId + ' .comment_area').append(createCOmmentDiv(object));
}

function createCOmmentDiv(object) {
    divMain = $('<div></div>')
        .addClass('comment_div')
        .attr('id', 'comment_div' + object.commentId)
        .attr('value', object.commentId);
    divMain.append(
        $('<hr>')
            .attr('align', 'center')
            .attr('size', '2')
    );
    divMain.append(
        $('<h5></h5>').text(object.username)
    );
    divMain.append(
        $('<p></p>').text(object.message)
    );

    divMain.append(createLikeDiv(object.timestamp));
    divMain.append(
        $('<hr>')
            .attr('align', 'center')
            .attr('size', '2')
    );

    return divMain;
}

function createLikeDiv(timestamp) {
    divLike = $('<div></div>')
        .addClass('comment_like font-weight-light');
    divLike.append(
        $('<p></p>')
            .addClass('timestamp-like d-inline mb-auto')
            .text(timestamp)
    );

    divFloatLike = $('<div></div>')
        .addClass('float-right');
    divFloatLike.append(
        $('<i></i>')
            .addClass('like_icon far fa-heart mr-1')
            .on('click', likeClick)
    );
    divFloatLike.append(
        $('<p></p>')
            .addClass('count_like d-inline mb-auto')
            .text('0')
    );

    divLike.append(divFloatLike);

    return divLike;
}