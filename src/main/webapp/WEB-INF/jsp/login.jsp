<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div id="userForm">
		<c:if test="${not empty requestScope.loginFailed}">
			<span class="redBold"><c:out value="${loginFailed}"/></span>
		</c:if>
    <form:form action="login.html" commandName="userCommand" method="POST">
        <table>
            <tbody>
            <tr>
                <th width="50%"/>
                <td><form:errors path="*" cssClass="redBold"/></td>
            </tr>
            <tr>
                <th><form:label path="username" cssErrorClass="redBold">Username</form:label></th>
                <td><form:input path="username"/></td>
            </tr>
            <tr>
                <th><form:label path="password" cssErrorClass="redBold">Password</form:label></th>
                <td><form:password path="password"/></td>
            </tr>
            <tr>
                <td/>
                <td><input type="submit" value="Login" class="button"/></td>
            </tr>
            <tr>
                <td/>
                <td><a href="register.html">Not registered?</a></td>
            </tr>
            </tbody>
        </table>
    </form:form>
</div>