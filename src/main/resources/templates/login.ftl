    <#import "parts/blog.ftl" as l>
    <@l.page>
        <form class="m-auto" action="/login" method="post">
            <div class="mb-3 col-sm-6">
                <label class="form-label">Email:</label>
                <input required type="email" name="email" class="form-control">
                <label class="form-label">Password:</label>
                <input required type="password" name="password" class="form-control">
                <button  type="submit" class="btn btn-primary mt-3">Sign In</button>
                <a href="/registration" class="btn btn-primary mt-3 ml-5">Registration</a>
            </div>
        </form>
    </@l.page>