<#macro main path isRegisterForm onclick clickText>
    <#import '../parts/common.ftl' as common>
    <#import '../parts/login.ftl' as login>

    <#assign headerContent>
        <link rel='stylesheet' href='../../static/styles/signin.css'>
        <script src='../../static/js/authentication.js'></script>
    </#assign>

    <@common.common header=headerContent>
        <@login.login path=path isRegisterForm=isRegisterForm>
            <button class='btn btn-lg btn-primary btn-block' onclick="${onclick}">${clickText}</button>
        </@login.login>
    </@common.common>
</#macro>