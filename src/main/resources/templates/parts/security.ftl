<#assign
known = Session.SPRING_SECURITY_CONTEXT??
>

<#if known>
    <#assign
    user = Session.SPRING_SECURITY_CONTEXT.authentication.principal
    name = user.getFirstname()+" "+user.getLastname()
    currentUserId = user.getId()
    >
<#else>
    <#assign
    name = "unknown"
    currentUserId=-10
    >
</#if>