<#import "parts/common.ftl" as common>
<#import "parts/login.ftl" as login>

<#assign headerContent>
     <link rel="stylesheet" href="../static/styles/signin.css">

    <script>
        function onRegistration() {
            window.location.href = "/registration";
        }
    </script>
</#assign>

<@common.common header=headerContent>
    <@login.login path="/login" isRegisterForm=false>
        <button class="btn btn-lg btn-primary btn-block" onclick=onRegistration()>Registration</button>
    </@login.login>
</@common.common>