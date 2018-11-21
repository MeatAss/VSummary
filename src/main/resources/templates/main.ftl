<#import "parts/common.ftl" as common>

<#assign headerContent>
    <link rel="stylesheet" href="../static/styles/animateLoading.css">

    <script src="https://cdn.jsdelivr.net/npm/sockjs-client@1/dist/sockjs.min.js"></script>

    <link rel="stylesheet" href="../static/styles/headerStyle.css">
    <script src="../static/js/headerActions.js"></script>
</#assign>

<@common.common header=headerContent>
    <div class="m-2 w-75 justify-content-center container row">

        <div class="col-9">
            <div class="input-group">
                <input id="searchText" type="search" class="form-control" placeholder="Поиск" aria-label="Recipient's username" aria-describedby="button-addon2">
                <div class="input-group-append">
                    <button class="btn btn-outline-secondary" type="button" id="button-addon2" onclick="sendSearchData()">
                        <i class="fas fa-search"></i>
                    </button>
                </div>
            </div>
        </div>

        <div class="col-3" role="toolbar" aria-label="Toolbar with button groups">
            <div class="btn-group mr-2" role="group" aria-label="First group">
                <button type="button" class="btn btn-light border big-icon" onclick="showSimpleAuthentication()">Вход</button>
                <button type="button" class="btn btn-light border" onclick="showFacebookAuthentication()"><i class="fab fa-facebook-f small-icon"></i></button>
                <button type="button" class="btn btn-light border" onclick="showVKontakteAuthentication()"><i class="fab fa-vk small-icon"></i></button>
                <button type="button" class="btn btn-light border" onclick="showTwitterAuthentication()"><i class="fab fa-twitter small-icon"></i></button>
            </div>
        </div>

    </div>
</@common.common>