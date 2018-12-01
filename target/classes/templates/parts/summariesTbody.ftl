<#macro tbody summaries originalUserId>
    <#list summaries as summary>
        <tr id="${summary.getId()}">
            <th scope="row">${summary.getNameSummary()}</th>
            <td>${summary.getShortDescription()}</td>
            <td>
                <form method="post" action="/updateSummary">
                    <input name="userId" type="hidden" value="${originalUserId}">
                    <input name="summaryId" type="hidden" value="${summary.getId()}">
                    <i class="update_icon fas fa-pencil-alt"></i>
                </form>
            </td>
            <td>
                <i class="delete_icon far fa-trash-alt"></i>
            </td>
        </tr>
    </#list>
</#macro>