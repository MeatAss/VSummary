<#import "parts/common.ftl" as common>
<#import "parts/personalCard.ftl" as card>
<#import "parts/summariesTbody.ftl" as summariesTbody>

<#assign headerContent>
    <script src="https://cdn.jsdelivr.net/npm/sockjs-client@1/dist/sockjs.min.js"></script>
    <script src="/webjars/stomp-websocket/2.3.3/stomp.min.js"></script>
    <script src="../static/js/personalAreaWebService.js"></script>

    <link rel="stylesheet" href="../static/styles/headerStyle.css">
    <script src="../static/js/navbarActions.js"></script>

    <script src="https://cdn.jsdelivr.net/simplemde/latest/simplemde.min.js"></script>
    <script src="../static/js/markdownEditor.js"></script>

    <script src="../static/js/bootstrap.bundle.js"></script>
    <link rel="stylesheet" href="../static/styles/bootstrap-editable.css">
    <script src="../static/js/bootstrap-editiable.min.js"></script>
    <script src="../static/js/personalArea.js"></script>

    <style>
        img {
            width: 100%;
        }
    </style>
</#assign>

<@common.common header=headerContent>
    <#include "parts/navbar.ftl">
    <#assign
    mainData = [
            {"idA" : "givenName", "post" : "/personalArea/update?userId="+userId, "text" : "First name", "textA" : (givenName??)?then(givenName, 'Не указано')},
            {"idA" : "secondName", "post" : "/personalArea/update?userId="+userId, "text" : "Second name", "textA" : (secondName??)?then(secondName, 'Не указано')}
        ] />

    <div id="personalAreaCards" class="w-75 mr-auto ml-auto mt-4 p-3">
        <@card.mainCard cardTitle="Основное" cardsText=mainData/>

        <div class="card mb-2">
            <div class="card-body">
                <h5 class="card-title">Summary control</h5>
                <div class="card-text">

                    <div class="input-group mb-3">

                        <div class="input-group-prepend">
                            <span class="input-group-text">Filter</span>
                        </div>
                        <input type="text" class="form-control" id="filter_input" aria-describedby="basic-addon3" maxlength="100">

                        <div class="input-group-prepend ml-4">
                            <span class="input-group-text">Sorting</span>
                        </div>
                        <div id="dropdownSortingArea" class="dropdown">
                            <button class="btn btn-outline-secondary dropdown-toggle" type="button" id="dropdownSorting"
                                    value="0" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                                Standard
                            </button>
                            <div class="dropdown-menu" aria-labelledby="dropdownSorting">
                                <a class="dropdown-item active" href="#" value="0">Standard</a>
                                <a class="dropdown-item" href="#" value="1">Alphabetically</a>
                                <a class="dropdown-item" href="#" value="-1">Not alphabetically</a>
                            </div>
                        </div>

                        <form method="post" action="/createSummary">
                            <input class="btn btn-outline-secondary ml-4" type="submit" value="Create summary">
                            <input name="userId" type="hidden" <#if know> value="${userId}</#if>">
                        </form>
                    </div>
                </div>
            </div>

            <table id="summariesTable" class="table">
                <thead>
                <tr>
                    <th scope="col">Summary name</th>
                    <th scope="col">Short description</th>
                    <th scope="col">Edit</th>
                    <th scope="col">Delete</th>
                </tr>
                </thead>
                <tbody value="${originalUserId}">
                    <@summariesTbody.tbody summaries=summaries originalUserId=originalUserId/>
                </tbody>
            </table>
        </div>


    </div>
</@common.common>