<#import 'parts/common.ftl' as common>
<#import 'parts/login.ftl' as login>

<#assign headerContent>
    <link rel='stylesheet' href='../static/styles/signin.css'>
    <script src='../static/js/authentication.js'></script>
</#assign>

<@common.common header=headerContent>
    <@login.login path='/registration/Simple' isRegisterForm=true>
        <button class='btn btn-lg btn-primary btn-block' onclick=redirectLogin()>Go to login</button>
    </@login.login>
</@common.common>