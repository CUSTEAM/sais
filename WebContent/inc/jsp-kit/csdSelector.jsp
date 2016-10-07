<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<select name="cno" class="selectpicker" data-width="auto">
	<c:forEach items="${allCampus}" var="c">
	<option <c:if test="${c.idno eq cno}">selected</c:if> value="${c.idno}">${c.name}</option>
	</c:forEach>
</select>

<select name="sno" class="selectpicker" data-width="auto">
	<option value="">選擇學制</option>
	<c:forEach items="${allSchool}" var="c">
	<option <c:if test="${c.idno eq sno}">selected</c:if> value="${c.idno}">${c.name}</option>
	</c:forEach>
</select>

<select name="dno" class="selectpicker" data-width="auto">
	<option value="">選擇科系</option>
	<c:forEach items="${allDept}" var="c">
	<option <c:if test="${c.idno eq dno}">selected</c:if> value="${c.idno}">${c.name}</option>
	</c:forEach>
</select>		