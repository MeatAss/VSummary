<#import "parts/common.ftl" as common>

<#assign headerContent>
    <#if isAuthenticate>
        <script src="https://cdn.jsdelivr.net/npm/sockjs-client@1/dist/sockjs.min.js"></script>
        <script src="/webjars/stomp-websocket/2.3.3/stomp.min.js"></script>
        <script src="../static/js/mainWebService.js"></script>
    </#if>
    <link rel="stylesheet" href="../static/styles/headerStyle.css">
    <script src="../static/js/navbarActions.js"></script>

    <script src="https://cdn.jsdelivr.net/simplemde/latest/simplemde.min.js"></script>
    <script src="../static/js/markdownEditor.js"></script>
    <script src="../static/js/main.js"></script>

    <style>
        img {
            width: 100%;
        }
        .comment_like {
            color: #495057 !important;
        }
    </style>
</#assign>

<@common.common header=headerContent>
    <#include "parts/navbar.ftl">

    <div class="w-75 mr-auto ml-auto mt-4 p-3">
        <#list summaries as summary>
            <div id="summary-area${summary.id}" class="summary-area card mb-2" value="${summary.id}">
                <ul class="list-group list-group-flush">
                    <li class="list-group-item">
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
                        <div class="card-text markdown_text">${summary.textSummary}</div>
                        <a href="#" class="card-link">Подробнее</a>
                    </li>
                    <#if isAuthenticate>
                    <li class="list-group-item pr-3 pl-3">
                        <div class="comment_area">
                            <#list summary.comments as comment>
                                <div class="comment_div" id="comment_div${comment.id}" value="${comment.id}">
                                    <h5>${comment.userName}</h5>
                                    <p>${comment.message}</p>
                                    <div class="comment_like">
                                        <i class="like_icon <#if comment.isLike>fas<#else>far</#if> fa-heart mr-1"></i>
                                        <p class="count_like d-inline mb-auto">${comment.countLikes}</p>
                                    </div>
                                    <hr align="center" size="2"/>
                                </div>
                            </#list>
                        </div>
                        <div class="input-group mb-2">
                            <input type="text" class="comment_input form-control" placeholder="Write comment" aria-label="Write comment" aria-describedby="button-addon2" maxlength="500" minlength="1">
                            <div class="input-group-append">
                                <button class="comment_button btn btn-outline-secondary" type="button"><i class="far fa-share-square"></i></button>
                            </div>
                        </div>
                    </li>
                    </#if>
                </ul>
            </div>
        <#else>
            No Summaries
        </#list>
    </div>
</@common.common>