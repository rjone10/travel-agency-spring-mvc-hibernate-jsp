<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Add hotel</title>
</head>
<body>
<h2>Add hotel</h2>
<form:form action="/hotels/add" modelAttribute="hotel" method="post">
    <label for="name">Hotel name</label>
    <form:input path="name" type="text" id="name" placeholder="Hotel name" required="true"/>
    <p>
        <label for="countryId">Country</label>
        <select name="countryId" id="countryId">
            <c:forEach var="c" items="${countries}">
                <option value="${c.id}">${c.name}</option>
            </c:forEach>
        </select>
    </p>
    <p>
        <label for="type">Accommodation Type</label>
        <form:select path="type">
            <form:options items="${types}"/>
        </form:select>
    </p>
    <p>
        <label for="rate">Hotel rate</label>
        <form:input path="rate" type="number" id="rate" placeholder="Rate" min="1" max="5" required="true"/>
    </p>
    <form:checkbox path="hasWiFi" value="true" label="WiFi"/>
    <form:checkbox path="hasPool" value="true" label="Pool"/>
    <form:checkbox path="isPetsAllowed" value="true" label="Allow pets"/>
    <form:checkbox path="canSmoke" value="true" label="Smoking"/>
    <p>
        <input type="submit" value="Add hotel">
    </p>
</form:form>
</body>
</html>