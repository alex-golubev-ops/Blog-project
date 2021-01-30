<#include "security.ftl">
<nav class="navbar navbar-expand-lg navbar-light bg-light">
    <div class="container-fluid">
        <a class="navbar-brand" href="/articles">MyBlog</a>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent"
                aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarSupportedContent">
            <ul class="navbar-nav mr-auto">
                <li class="nav-item">
                    <a class="nav-link" href="/articles">Posts</a>
                </li>
                <#if userAuthentication??>
                <li class="nav-item">
                    <a class="nav-link" href="/my">MyPosts</a>
                </li>
                </#if>
            </ul>
            <div class="navbar-text mr-2">${name}</div>
            <#if userAuthentication??>
                <form action="/logout" >

                    <button class="btn btn-primary">Sign out</button>
                </form>
                <#else>
                    <form action="/login" >
                        <button class="btn btn-primary">Sign in</button>
                    </form>

            </#if>

        </div>
    </div>
</nav>
