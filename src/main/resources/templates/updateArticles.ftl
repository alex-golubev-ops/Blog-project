<#import "parts/blog.ftl" as m>
<#include "parts/security.ftl">
<@m.page>
    <div class="form-group">
        <form method="post" action="/articles/${article.id}/update">

            <input required class="form-control mt-3" type="text" name="title" value="${article.title}"/>
            <input required class="form-control mt-3" type="text" name="tags" value="${article.tags}"/>

            <div class="form-floating mt-3">
                    <textarea class="form-control h-25" rows="5" name="text"
                              id="floatingTextarea">${article.text}</textarea>

            </div>
            <div class="form-check">
                <input class="form-check-input" type="radio" name="status" value="PUBLIC"
                       id="flexRadioDefault1"   >
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

            <button class="btn btn-primary mt-3" type="submit">Edit</button>

        </form>
    </div>
</@m.page>