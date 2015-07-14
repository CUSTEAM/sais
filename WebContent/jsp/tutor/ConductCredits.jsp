<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script>  
$(document).ready(function() {	
	$('#funbtn').popover("show");
	setTimeout(function() {
		$('#funbtn').popover("hide");
	}, 3000);
	
});


</script>  
</head>
<body>


<form action="ConductCredits" method="post">
<div class="alert">
	<button type="button" class="close" data-dismiss="alert">&times;</button>
    <strong>操行成績加減分</strong> 以下列表為您管轄的班級。
    <div id="funbtn" rel="popover" title="說明" data-content="請點選班級旁的按鈕給予個別同學加減分" data-placement="right" class="btn btn-warning">?</div>
</div>


<table class="table">
	<tr>
		<td class="text-info" nowrap>權限</td>
		<td class="text-info" nowrap>班級</td>
		<td class="text-info" nowrap>人數</td>
		<td class="text-info" nowrap>狀態</td>
		<td class="text-info" nowrap></td>
		<td class="text-info" nowrap>
		<input type="hidden" id="ClassNo" name="ClassNo" />
		<input type="hidden" id="rule" name="rule" />
		</td>
	</tr>
	<c:forEach items="${myClass}" var="c">
	<c:if test="${c.cnt>0}">
	<tr>
		<td class="text-info" nowrap>
		<c:if test="${c.rule eq 'T'}"><span class="label label-success">導師</span></c:if>
		<c:if test="${c.rule eq 'D'}"><span class="label label-warning">主任</span></c:if>
		<c:if test="${c.rule eq 'M'}"><span class="label label-important">教官</span></c:if>
		</td>
		<td class="text-info" nowrap>${c.ClassName}</td>
		<td class="text-info" nowrap>${c.cnt}</td>
		<td class="text-info" nowrap>
		<c:if test="${c.score>0}"><p class="text-error">學務單位已結算缺曠獎懲</p></c:if>
		<c:if test="${c.score==0}"><p class="text-success">學務單位未結算缺曠獎懲</p></c:if>
		</td>
		<td class="text-info" nowrap>
		<c:if test="${c.score==0}">
		<button class="btn btn btn-small" name="method:edit" onClick="$('#ClassNo').val('${c.ClassNo}'), $('#rule').val('${c.rule}')" type="submit">修改加權分數</button></td>
		</c:if>
		<c:if test="${c.score>0}">
		<button class="btn btn btn-small" disabled name="method:view" onClick="$('#ClassNo').val('${c.ClassNo}'), $('#rule').val('${c.rule}')" type="submit">檢視結算成績</button></td>
		</c:if>
		<td class="text-info" width="100%"></td>
	</tr>
	</c:if>
	</c:forEach>
</table>
</form>


</body>
</html>