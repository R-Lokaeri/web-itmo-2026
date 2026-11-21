<#import "commons.ftlh" as c>

<@c.page>
    <div class="user-profile">
        <div class="caption">User profile</div>
        <#if profileUser??>
            <div class="user-info">
                <div class="user-id">Id : ${profileUser.id}</div>
                <div class="user-login">Login : ${profileUser.login}</div>
                <div class="user-creation-time">Creation time : ${profileUser.creationTime}</div>
            </div>
        <#else>
            No such user
        </#if>
    </div>
</@c.page>
