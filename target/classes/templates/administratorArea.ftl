<#import "parts/common.ftl" as common>
<#import "parts/personalCard.ftl" as card>

<#assign headerContent>
    <link rel="stylesheet" href="../static/styles/headerStyle.css">
    <script src="../static/js/navbarActions.js"></script>

    <script src="../static/js/bootstrap.bundle.js"></script>

    <link rel="stylesheet" href="../static/styles/bootstrap-editable.css">
    <script src="../static/js/bootstrap-editiable.min.js"></script>
    <script src="../static/js/administratorArea.js"></script>
</#assign>

<@common.common header=headerContent>
    <#include "parts/navbar.ftl">
    <div id="personalAreaCards" class="w-75 mr-auto ml-auto mt-5 p-3 card">
        <table id="usersTable" class="table">
            <thead>
            <tr>
                <th scope="col">First name</th>
                <th scope="col">Second name</th>
                <th scope="col">Is admin</th>
                <th scope="col">Is lock</th>
                <th scope="col">Show</th>
                <th scope="col">Delete</th>
            </tr>
            </thead>
            <tbody>
                    <#list users as otherUser>
                    <tr id="${otherUser.getId()}">
                        <td scope="row">${otherUser.getGivenName()}</td>
                        <td>
                            ${otherUser.getSecondName()}
                        </td>
                        <td>
                            <a href="#" class="select_role" data-type="select" data-pk="${otherUser.getId()}" data-url="administratorArea/changeRole" data-title="Select status"
                            data-value="${otherUser.getRoles()?seq_contains("ADMINISTRATOR")?string("ADMINISTRATOR","USER")}"></a>
                        </td>
                        <td>
                            <a href="#" class="select_status" data-type="select" data-pk="${otherUser.getId()}" data-url="administratorArea/changeStatus" data-title="Select status"
                               data-value="${otherUser.isEnable()?string("true","false")}"></a>
                        </td>
                        <td>
                            <i class="fas fa-search" onclick="showUser(this)"></i>
                        </td>
                        <td>
                            <i class="far fa-trash-alt"></i>
                        </td>
                    </tr>
                    </#list>
            </tbody>
        </table>
    </div>
</@common.common>