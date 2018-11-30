<#macro login path isRegisterForm>
    <form id='subForm${isRegisterForm?string}' class='form-signin' action='${path}' method='post'>
        <h1 class='h3 mb-3 font-weight-normal'>
            <#if isRegisterForm>
                Please sign up
            <#else>
                Please sign in
            </#if>
        </h1>

        <#if isRegisterForm>
            <label for='inputEmail' class='sr-only'>Login</label>
            <input id='inputEmail' class='form-control' name='email' type='email' placeholder='some@some.com' required autofocus/>
        </#if>

        <label for='inputLogin' class='sr-only'>Login</label>
        <input id='inputLogin' class='form-control' name='username'  placeholder='Login' required autofocus/>


        <label for='inputPassword' class='sr-only'>Password</label>
        <input type='password' id='inputPassword' class='form-control' name='password' placeholder='Password' required/>

        <div id='errorText' class='mb-2 text-danger'>
            <#if Session?? && Session.SPRING_SECURITY_LAST_EXCEPTION?? && !isRegisterForm>
                Неверный логин или пароль
            </#if>
        </div>

        <button class='btn btn-lg btn-primary btn-block' type='submit' onclick="submitData()">
            <#if isRegisterForm>
                Sing up
            <#else>
                Sign in
            </#if>
        </button>
        <#nested>
    </form>
</#macro>