<#import "parts/common.ftl" as common>
<#import "parts/mainSummary.ftl" as mainSummary>
<#import "parts/cloudTags.ftl" as cloudTags>

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

    <link rel="stylesheet" href="../static/styles/summary.css">
    <link rel="stylesheet" href="../static/styles/cloudTags.css">
</#assign>

<@common.common header=headerContent>
    <#include "parts/navbar.ftl">

    <div class="w-75 mr-auto ml-auto p-3">
        <@cloudTags.show tags=tags/>

        <#if recentSummaries??>
            <h2 class="mb-3 mt-3">Most rated summaries</h2>
            <#list recentSummaries as summary>
                <@mainSummary.show summary=summary isAuthenticate=isAuthenticate/>
            </#list>
            <#if !isRecentFull>
                <form method="get" action="/main/showRecent">
                    <input type="submit" class="btn btn-outline-secondary" value="Show more">
                </form>
            </#if>
        </#if>

        <#if ratedSummaries??>
            <h2 class="mb-3 mt-3">Most recent summaries</h2>
            <#list ratedSummaries as summary>
                <@mainSummary.show summary=summary isAuthenticate=isAuthenticate/>
            </#list>
            <#if !isRatedFull>
                <form method="get" action="/main/showRated">
                    <input type="submit" class="btn btn-outline-secondary" value="Show more">
                </form>
            </#if>
        </#if>

        <#if summaries??>
            <#list summaries as summary>
                <@mainSummary.show summary=summary isAuthenticate=isAuthenticate/>
            </#list>
        </#if>
    </div>
</@common.common>