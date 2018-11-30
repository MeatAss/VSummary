<#include "security.ftl">

<header class="navbar navbar-expand-lg navbar-light bg-light w-75 mr-auto ml-auto flex-column flex-md-row position-sticky justify-content-between" style="z-index: 1000; top: 0">

    <span class="navbar-brand mb-0 h1">VSummary</span>

    <div class="collapse navbar-collapse" id="navbarNav">
        <ul class="navbar-nav">
            <li class="nav-item">
                <a class="nav-link" href="/main">Home <span class="sr-only">(current)</span></a>
            </li>
            <li class="nav-item">
                <a class="nav-link text-nowrap" href="/personalArea?userId=${userId}">Personal area</a>
            </li>
            <#if know && isAdmin>
                <li class="nav-item">
                    <a class="nav-link text-nowrap" href="/administratorArea">Administrator area</a>
                </li>
            </#if>
        </ul>
    </div>

    <div class="ml-3 my-auto d-inline w-100">
        <div class="input-group">

            <div class="input-group w-50 m-auto">
                <input id="searchText" type="search" class="form-control mr-auto ml-auto" placeholder="Поиск" aria-label="Recipient's username" aria-describedby="button-addon2">
                <div class="input-group-append">
                    <button class="btn btn-outline-secondary" type="button" id="button-addon2" onclick="sendSearchData()">
                        <i class="fas fa-search"></i>
                    </button>
                </div>
            </div>

            <#if !know>
                <div class="btn-group" role="group" aria-label="First group">
                    <button type="button" class="btn btn-light border big-icon" onclick="showAuthentication('SIMPLE')">Вход</button>
                    <button type="button" class="btn btn-light border" onclick="showAuthentication('FACEBOOK')"><i class="fab fa-facebook-f small-icon"></i></button>
                    <button type="button" class="btn btn-light border" onclick="showAuthentication('VKONTAKTE')"><i class="fab fa-vk small-icon"></i></button>
                    <button type="button" class="btn btn-light border" onclick="showAuthentication('TWITTER')"><i class="fab fa-twitter small-icon"></i></button>
                </div>
            <#else>
                <div class="justify-content-end" role="group">
                    <label class="col-form-label d-inline mr-2">${name}</label>
                    <form class="d-inline ml-2" action="/logout" method="post">
                        <input class="btn btn-outline-secondary" type="submit" value="Sing out">
                    </form>
                </div>
            </#if>

        </div>

    </div>
</header>