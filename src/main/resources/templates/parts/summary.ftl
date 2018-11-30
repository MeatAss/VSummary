<#macro summaryEditor userId summaryId=0 name='' short='' number='' tags='' text=''>

    <form id="addSummaryForm" class="mx-auto border p-4 mt-2 w-75 h-75">
        <input id="userId" type="hidden" value = "${userId}">
        <input id="idSummary" type="hidden" value = "${summaryId}">

        <div class="form-group">
            <label for="nameSummary">Название</label>
            <input class="form-control has-error" id="nameSummary" type="text" placeholder="Введите название конспекта" autofocus required maxlength="100" value="${name}">
        </div>
        <div class="form-group">
            <label for="shortDescription">Краткое описание</label>
            <input class="form-control" id="shortDescription" type="text" placeholder="Краткое описание" required maxlength="200" value="${short}">
        </div>
        <div class="form-group">
            <label for="specialtyNumber">Номер специальности</label>
            <input class="form-control" id="specialtyNumber" type="text" placeholder="Номер специальности" required maxlength="50" value="${number}">
        </div>
        <div class="form-group position-relative">
            <label for="summaryTags">Тэги</label>
            <input class="form-control" id="summaryTags" type="text" placeholder="Тэги" required maxlength="200" value="${tags}">
            <ul id="tagsUL" class="position-absolute border nav flex-column bg-light w-100 invisible" style="z-index: 999">
            </ul>
        </div>
        <div class="form-group" id="divTextId">
            <label for="textSummary">Текст</label>
            <textarea class="form-control" name="desc" id="textSummary" required minlength="25" maxlength="1000">${text}</textarea>
        </div>

        <div class="form-group" id="divLoaderImage">

        </div>

        <button id="submit" class="btn btn-primary" onclick="sendData()">Submit</button>
    </form>

</#macro>