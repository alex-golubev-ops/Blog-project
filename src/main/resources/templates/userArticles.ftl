<#import "parts/blog.ftl" as m>
<#include "parts/security.ftl">
    <#import "parts/pager.ftl" as p>
<@m.page>
    <a class="btn btn-primary mt-3" data-toggle="collapse" href="#collapseExample" role="button"
       aria-expanded="false"
       aria-controls="collapseExample">
        Add new Articles
    </a>
    <div class="collapse" id="collapseExample">
        <div class="form-group">
            <form method="post" action="/articles">

                <input class="form-control mt-3" type="text" name="title" placeholder="Title"/>
                <input class="form-control mt-3" type="text" name="tags" placeholder="Tags"/>

                <div class="form-floating mt-3">
                    <textarea class="form-control h-25" rows="5" name="text" placeholder="Enter text here"
                              id="floatingTextarea"></textarea>

                </div>
                <div class="form-check">
                    <input class="form-check-input" type="radio" name="status" value="PUBLIC"
                           id="flexRadioDefault1">
                    <label class="form-check-label" for="flexRadioDefault1">
                        Public
                    </label>
                </div>

                <div class="form-check">
                    <input class="form-check-input" type="radio" name="status" value="DRAFT" id="flexRadioDefault2"
                           checked>
                    <label class="form-check-label" for="flexRadioDefault2">
                        Draft
                    </label>
                </div>

                <button class="btn btn-primary mt-3" type="submit">Добавить</button>

            </form>
        </div>
    </div>



    <div class="mt-5">My Posts:</div>
    <@p.pager url page/>
    <#list page.content as articl>
        <div class="card mt-2 ">

            <div class="card-header d-flex">
                <div class="mr-auto p-2">${articl.author.getFirstname()+" "+articl.getAuthor().getLastname()}
                </div>
                <div class="p-2">
                    ${articl.updateAt}
                </div>

            </div>

            <div class="card-body">
                <h5 class="card-title">${articl.title}</h5>
                <p class="card-text">

                <p class="card-text">${articl.text}</p>
                <a href="#" class="btn btn-primary">Comments</a>


                <a href="/articles/${articl.id}" class="btn btn-primary">Edit</a>


            </div>

        </div>
    <#else>
        No articles
    </#list>
</@m.page>