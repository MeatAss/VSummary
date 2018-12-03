<#macro show summary isAuthenticate=false isFull=false>

<#import "like.ftl" as like>

<div id="summary-area${summary.id}" class="summary-area mb-4" value="${summary.id}">
    <div class="list-group-item card">
        <h5 class="card-title">${summary.nameSummary}</h5>
        <h6 class="card-subtitle mb-2 text-muted">
            ${summary.shortDescription} | Speciality ${summary.specialtyNumber}
            <#if isAuthenticate>
                <div class="rating-area d-inline float-right">
                    <#list 1..5 as i>
                        <i class="fa-star ${(i <= summary.avgRatings)?string("fas","far")}" value="${i}"></i>
                    </#list>
                </div>
            </#if>
        </h6>
        <hr align="center" size="2"/>
        <div class="card-text markdown_text <#if !isFull>markdown-text-gradient</#if>">${summary.textSummary}</div>

        <#if !isFull>
            <button type="button" class="summary-more btn btn-outline-secondary card-link mt-3">Read more</button>
        </#if>

        <hr class="mb-1" align="center" size="2"/>
        <div class="card-text">
            <p class="m-auto d-inline">${summary.username} | ${summary.timestamp}</p>
            <#list summary.tags as tag>
                <span class="d-inline float-right ml-1 badge badge-pill badge-secondary">${tag}</span>
            </#list>
        </div>
    </div>
    <div class="list-group-item card">
        <#if isAuthenticate>
        <div>
            <p class="m-auto font-weight-light" id="count-comments">comments : ${summary.comments?size}</p>

            <div class="comment_area">
            <#list summary.comments as comment>
                <div class="comment_div" id="comment_div${comment.id}" value="${comment.id}">
                    <hr class="m-1" align="center" size="2"/>
                    <h5>${comment.userName}</h5>
                    <p>${comment.message}</p>
                    <@like.show timestamp=comment.timestamp isLike=comment.isLike countLikes=comment.countLikes/>
                </div>
            </#list>
            </div>
            <div class="input-group mb-2 mt-3">
                <input type="text" class="comment_input form-control" placeholder="Write comment" aria-label="Write comment" aria-describedby="button-addon2" maxlength="500" minlength="1">
                <div class="input-group-append">
                    <button class="comment_button btn btn-outline-secondary" type="button"><i class="far fa-share-square"></i></button>
                </div>
            </div>
        </div>
        </#if>

    </div>
</div>

</#macro>