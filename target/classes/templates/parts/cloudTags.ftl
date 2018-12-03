<#macro show tags>

<div id="teg-cloud" class="col-lg-offset-4 col-lg-4 card">
    <#list tags as tag>
        <a class="teg-${tag.tagValue}" href="/main/showTag?tagId=${tag.tagId}">${tag.tagName}</a>
    </#list>
</div>

</#macro>