<#import "parts/blog.ftl" as m>
<#include "parts/security.ftl">
<#import "parts/pager.ftl" as p>
<@m.page>

    <h2 class="mt-5">Post:</h2>
    <div class="card mt-2 ">
        <div class="card-header d-flex">
            <div class="mr-auto p-2">${article.author.getFirstname()+" "+article.getAuthor().getLastname()}
            </div>
            <div class="p-2">
                ${article.updateAt}
            </div>
        </div>

        <div class="card-body">
            <h5 class="card-title">${article.title}</h5>
            <p class="card-text">

            <p class="card-text">${article.text}</p>

        </div>
    </div>
    <h2 class="mt-5">Comments:</h2>
    <@p.pager url page/>
    <div class="container mt-3">

        <div class="row" id="addcomment" style="display: none;">
            <form>
                <textarea class="form-control" placeholder="Comment content..."></textarea><br/>
                <button class="btn btn-primary">Send</button>
            </form>
        </div>
        <#list page.content as comment>
            <div class="card mt-2">
                <h5 class="card-header">${comment.getCreateAt()}</h5>
                <div class="card-body">
                    <h5 class="card-title">${comment.getAuthor().getFirstname()} ${comment.getAuthor().getLastname()}</h5>
                    <p class="card-text">${comment.getMessage()}</p>
                </div>
                <#if comment.getAuthor()==userAuthentication || article.getAuthor().equals(userAuthentication)>
                    <form action="/articles/${article.id}/comments/${comment.id}" method="post">
                        <button class="btn btn-primary" type="submit">Delete</button>
                    </form>
                </#if>
            </div>
        <#else>
            No comments
        </#list>
    </div>
    <a class="btn btn-primary mt-5" data-toggle="collapse" href="#collapseExample" role="button"
       aria-expanded="false"
       aria-controls="collapseExample">
        Add Comment
    </a>

    <div class="collapse" id="collapseExample">
        <div class="form-group">
            <form method="post" action="/articles/${article.id}/comments">
                <div class="form-floating mt-3">
                    <textarea required class="form-control h-25" rows="5" name="message"
                              placeholder="Enter message here"
                              id="floatingTextarea"></textarea>
                </div>
                <button class="btn btn-primary mt-3" type="submit">Comment</button>
            </form>
        </div>
    </div>






</@m.page>