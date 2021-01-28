    <#import "parts/blog.ftl" as l>
    <@l.page>
        <div>
            <#if message??>
                ${message}
            </#if>
        </div>
        <form class="m-auto" action="/login" method="post">
            <div class="mb-3 col-sm-6">
                <label class="form-label">Email:</label>
                <input required type="email" name="email" class="form-control">
                <label class="form-label">Password:</label>
                <input required type="password" name="password" class="form-control">
                <button  type="submit" class="btn btn-primary mt-3">Sign In</button>
                <a href="/registration" class="btn btn-primary mt-3 ml-5">Registration</a>
                <div>
                    <a href="/auth/forgot_password" class="btn btn-primary mt-3">Forgot Password</a>
                </div>
            </div>
        </form>
    </@l.page>