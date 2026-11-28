<#import "commons.ftlh" as c>

<@c.page>
    <#if post??>
        <@c.post post=post/>

        <div class="comments">
            <div class="caption">Comments</div>
            <#list post.comments as comment>
                <div class="comment">
                    <div class="info">
                        <span class="author">
                            ${comment.user.login}
                        </span>
                        <span class="time">
                            at ${comment.creation_time}
                        </span>
                    </div>
                    <div class="content">
                        ${comment.text}
                    </div>
                </div>
            </#list>
        </div>

        <#if user??>
            <div class="form">
                <div class="header">Add Comment</div>
                <div class="body">
                    <form method="post" action="">
                        <div class="field">
                            <div class="name">
                                <label for="text">Text</label>
                            </div>
                            <div class="value">
                                <textarea id="text" name="text">${comment.text!}</textarea>
                            </div>
                            <@c.error "comment.text"/>
                        </div>
                        <div class="button-field">
                            <input type="submit" value="Add">
                        </div>
                    </form>
                </div>
            </div>
        </#if>
    <#else>
        Post not found
    </#if>
</@c.page>
