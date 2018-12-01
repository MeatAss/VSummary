<#import "parts/common.ftl" as common>

<#assign headerContent>
    <link rel="stylesheet" href="../static/styles/headerStyle.css">
    <script src="../static/js/navbarActions.js"></script>
    <script src="https://cdn.jsdelivr.net/simplemde/latest/simplemde.min.js"></script>
    <script src="../static/js/markdownEditor.js"></script>
    <style>
        img {
            width: 100%;
        }
    </style>
    <script>
        $(document).ready(() => {
           $('.markdown_text').each((i, item)=> {
               $(item).html(getHTML($(item).html()));
           });
        });
    </script>
</#assign>

<@common.common header=headerContent>
    <#include "parts/navbar.ftl">

    <div class="w-75 mr-auto ml-auto mt-4 p-3">
        <#list summaries as summary>
            <div class="card mb-2">
                <div class="card-body">
                    <h5 class="card-title">${summary.nameSummary}</h5>
                    <h6 class="card-subtitle mb-2 text-muted">${summary.shortDescription}</h6>
                    <div class="card-text markdown_text">${summary.textSummary}</div>
                    <a href="#" class="card-link">Подробнее</a>
                </div>
            </div>
        <#else>
            No Summaries
        </#list>
    </div>
</@common.common>