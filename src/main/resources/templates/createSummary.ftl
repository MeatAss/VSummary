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

    <script>

    </script>
</#assign>

<@common.common header=headerContent>
    <#--<div class="m-2 w-50 h-50">-->
        <#--<div class="input-group mb-3">-->
            <#--<input id="searchText" type="search"  class="form-control" placeholder="Поиск" aria-label="Recipient's username" aria-describedby="button-addon2">-->
            <#--<div class="input-group-append">-->
                <#--<button class="btn btn-outline-secondary" type="button" id="button-addon2" onclick="sendSearchData()">-->
                    <#--<i class="fas fa-search"></i>-->
                <#--</button>-->
            <#--</div>-->
        <#--</div>-->
    <#--</div>-->
    <#include "parts/navbar.ftl">
    <@summary.summaryEditor userId=userId />


</@common.common>