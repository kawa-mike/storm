
<%@ page import="systemadministration.log.AccessLog" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'accessLog.label', default: 'AccessLog')}" />
        <title><g:message code="default.edit.label" args="[entityName]" /></title>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLink(uri: '/')}">Home</a></span>
            <span class="menuButton"><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></span>
            <span class="menuButton"><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></span>
        </div>
        <div class="body">
            <h1><g:message code="default.edit.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${accessLogInstance}">
            <div class="errors">
                <g:renderErrors bean="${accessLogInstance}" as="list" />
            </div>
            </g:hasErrors>
            <g:form method="post" >
                <g:hiddenField name="id" value="${accessLogInstance?.id}" />
                <g:hiddenField name="version" value="${accessLogInstance?.version}" />
                <div class="dialog">
                    <table>
                        <tbody>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="typeAccess"><g:message code="accessLog.typeAccess.label" default="Type Access" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: accessLogInstance, field: 'typeAccess', 'errors')}">
                                    <g:textField name="typeAccess" value="${fieldValue(bean: accessLogInstance, field: 'typeAccess')}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="user"><g:message code="accessLog.user.label" default="User" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: accessLogInstance, field: 'user', 'errors')}">
                                    <g:select name="user.id" from="${systemadministration.usermanagement.User.list()}" optionKey="id" value="${accessLogInstance?.user?.id}"  />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="module"><g:message code="accessLog.module.label" default="Module" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: accessLogInstance, field: 'module', 'errors')}">
                                    <g:select name="module.id" from="${systemadministration.modulmanagement.Module.list()}" optionKey="id" value="${accessLogInstance?.module?.id}"  />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="article"><g:message code="accessLog.article.label" default="Article" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: accessLogInstance, field: 'article', 'errors')}">
                                    <g:select name="article.id" from="${ReportEditorMain.ArticleEditor.Article.list()}" optionKey="id" value="${accessLogInstance?.article?.id}"  />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="dateCreated"><g:message code="accessLog.dateCreated.label" default="Date Created" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: accessLogInstance, field: 'dateCreated', 'errors')}">
                                    <g:datePicker name="dateCreated" precision="day" value="${accessLogInstance?.dateCreated}"  />
                                </td>
                            </tr>
                        
                        </tbody>
                    </table>
                </div>
                <div class="buttons">
                    <span class="button"><g:actionSubmit class="save" action="update" value="${message(code: 'default.button.update.label', default: 'Update')}" /></span>
                    <span class="button"><g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" /></span>
                </div>
            </g:form>
        </div>
    </body>
</html>
