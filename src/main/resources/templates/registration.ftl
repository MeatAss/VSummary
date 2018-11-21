<#import "parts/common.ftl" as common>
<#import "parts/login.ftl" as login>

<#assign headerContent>
     <link rel="stylesheet" href="../static/styles/signin.css">

    <script>
        function onLogin() {
            window.location.href = "/login";
        }
    </script>
</#assign>

<@common.common header=headerContent>
    <@login.login path="/registration/Simple" isRegisterForm=true>
        <button class="btn btn-lg btn-primary btn-block" onclick=onLogin()>Go to login</button>
    </@login.login>
</@common.common>