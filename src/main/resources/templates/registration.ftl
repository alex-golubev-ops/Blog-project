<#import "parts/blog.ftl" as l>

<@l.page>
    <form class="m-auto" action="/registration"  method="post">
        <div class="mb-3 col-sm-6">
            <label class="form-label"> Firstname : </label>
            <input required class="form-control" type="text" name="firstname"/>
            <label class="form-label"> Lastname : </label>
            <input required class="form-control" type="text" name="lastname"/>
            <label class="form-label"> Password: </label>
            <input required class="form-control" type="password" name="password"/>
            <label class="form-label"> Email: </label>
            <input required class="form-control" type="email" name="email" placeholder="email@your.there"/>

            <#if emailmessage??>
                <div >
                    ${emailmessage}
                </div>
            </#if>

            <button type="submit" class="btn btn-primary mt-3">Registration</button>
        </div>

    </form>
</@l.page>