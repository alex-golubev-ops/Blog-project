<#import "parts/blog.ftl" as m>
<#include "parts/security.ftl">
<@m.page>
    <form class="d-flex" method="get" action="/articles">
        <input name=filter value="filter" class="form-control mr-2" type="text" placeholder="Search"
               aria-label="Search">

        <button class="btn btn-outline-success mr-5" type="submit">Search</button>
    </form>
    <#if user??>
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
    </#if>


    <div class="mt-5">Posts:</div>
    <#list articles as articl>
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

                <#if user?? && user == articl.getAuthor()>
                <a href="/update" class="btn btn-primary">Update</a>

                </#if>
            </div>

        </div>
    <#else>
        No articles
    </#list>
</@m.page>