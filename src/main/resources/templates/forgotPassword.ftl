<#import "parts/blog.ftl" as m>
<#include "parts/security.ftl">
<@m.page>
    <#if message??>
        ${message}
    </#if>
<#if begin??>
    <form action="/auth/forgot_password" method="post">
    Enter your email
    <input required class="form-control mt-3" type="email" name="email" placeholder="Email"/>
    <button type="submit" class="btn btn-primary mt-3">Reestablish</button>
    </form>
</#if>
    <#if code??>
        <form action="/auth/check_code" method="get">
            Enter your code from email
            <input required class="form-control mt-3" type="number" name="code" placeholder="Code"/>
            <input  type="hidden" name="email" value="${email}"/>
            <button type="submit" class="btn btn-primary mt-3">Check</button>
        </form>
    </#if>
    <#if newpassword??>
        <form action="/auth/reset" method="post">
            Enter new password
            <input required class="form-control mt-3" type="password" name="password" placeholder="password"/>
            <input  type="hidden" name="code" value="${codefromredis}"/>
            <button type="submit" class="btn btn-primary mt-3">Reset</button>
        </form>
    </#if>
</@m.page>