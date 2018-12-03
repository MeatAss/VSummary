<#macro show timestamp isLike countLikes>
    <div class="comment_like font-weight-light">
        <p class="timestamp-like d-inline mb-auto">${timestamp}</p>
        <div class="float-right">
            <i class="like_icon <#if isLike>fas<#else>far</#if> fa-heart mr-1"></i>
            <p class="count_like d-inline mb-auto">${countLikes}</p>
        </div>
    </div>
</#macro>