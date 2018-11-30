<#import "parts/common.ftl" as common>
<#import "parts/summary.ftl" as summary>

<#assign headerContent>
     <link rel="stylesheet" href="../static/styles/animateLoading.css">

    <script src="https://cdn.jsdelivr.net/npm/sockjs-client@1/dist/sockjs.min.js"></script>

    <script src="/webjars/stomp-websocket/2.3.3/stomp.min.js"></script>
    <script src="../static/js/createSummaryWebService.js"></script>
    <script src="../static/js/createSummaryApp.js"></script>
    <script src="../static/js/createSummaryDragAndDrop.js"></script>

    <link rel="stylesheet" href="https://cdn.jsdelivr.net/simplemde/latest/simplemde.min.css">
    <script src="https://cdn.jsdelivr.net/simplemde/latest/simplemde.min.js"></script>

    <script src="../static/js/markdownEditor.js"></script>

    <style>
        #tagsUL > li:hover {
            background-color: #e6e6e6;
        }
    </style>
</#assign>

<@common.common header=headerContent>
    <#include "parts/navbar.ftl">

    <@summary.summaryEditor
        userId=originalUserId
        summaryId=summaryId
        name=nameSummary
        short=shortDescription
        number=specialtyNumber
        tags=summaryTags
        text=textSummary
    />


</@common.common>