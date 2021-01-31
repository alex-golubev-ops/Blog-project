<#assign
known = Session.SPRING_SECURITY_CONTEXT??
>

<#if known>
    <#assign
    userAuthentication = Session.SPRING_SECURITY_CONTEXT.authentication.principal
    name = userAuthentication.getFirstname()+" "+userAuthentication.getLastname()
    currentUserId = userAuthentication.getId()
    >
<#else>
    <#assign
    name = "unknown"
    currentUserId=-10
    >
</#if>