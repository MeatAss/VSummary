<#import "parts/common.ftl" as common>
<#import "parts/like.ftl" as like>

<#import "parts/mainSummary.ftl" as mainSummary>

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
</#assign>

<@common.common header=headerContent>
    <#include "parts/navbar.ftl">

    <div class="w-75 mr-auto ml-auto mt-4 p-3">
        <@mainSummary.show summary=summary isAuthenticate=isAuthenticate isFull=true/>
    </div>
</@common.common>