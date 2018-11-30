<#assign
    know = Session.SPRING_SECURITY_CONTEXT??
>

<#if know>
    <#assign
        user = Session.SPRING_SECURITY_CONTEXT.authentication.principal
        userId = user.getId()
        name = user.getGivenName()
        isAdmin = user.getRoles()?seq_contains("ADMINISTRATOR")
    >
<#else>
    <#assign
        name = "guest"
        isAdmin = false
        userId = -1
    >
</#if>