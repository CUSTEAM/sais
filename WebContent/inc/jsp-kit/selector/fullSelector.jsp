<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<select name="cno" class="selectpicker" data-width="auto">
	<c:forEach items="${allCampus}" var="c">
	<option <c:if test="${c.idno eq cno}">selected</c:if> value="${c.idno}">${c.name}</option>
	</c:forEach>
</select>
<select name="tno" class="selectpicker" data-width="auto">
	<option value="">選擇部制</option>	
	<option <c:if test="${tno eq 'D'}">selected</c:if> value="D">日間部</option>
	<option <c:if test="${tno eq 'N'}">selected</c:if> value="N">進修部</option>
	<option <c:if test="${tno eq 'H'}">selected</c:if> value="H">進修學院</option>
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
<select name="gno" class="selectpicker" data-width="auto">
	<option value="">選擇年級</option>
	<c:forEach var="g" begin="1" end="6">
	<option <c:if test="${gno eq g}">selected</c:if> value="${g}">${g}年級</option>
	</c:forEach>
</select>
<select name="zno" class="selectpicker" data-width="auto">
	<option value="">選擇班級</option>
	<option <c:if test="${zno eq '1'}">selected</c:if> value="1">甲班</option>
	<option <c:if test="${zno eq '2'}">selected</c:if> value="2">乙班</option>
	<option <c:if test="${zno eq '3'}">selected</c:if> value="3">丙班</option>
</select>