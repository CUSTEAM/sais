<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>操行成績及評語管理</title>
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
<div class="bs-callout bs-callout-info" id="callout-helper-pull-navbar">
	<button type="button" class="close" data-dismiss="alert">&times;</button>
    <strong>操行成績加減分</strong> 以下列表為您管轄的班級
</div>

<div class="panel panel-primary">
<div class="panel-heading">點選班級旁的按鈕給予個別同學加減分</div>
<ul class="list-group">
<li class="list-group-item"><span class="label label-as-badge label-warning">1</span> 操評會前請參考即時計算成績建立加減分</li>
<li class="list-group-item"><span class="label label-as-badge label-warning">2</span> 操評會後可依據結算日固定成績建立加減分</li>
<li class="list-group-item"><span class="label label-as-badge label-danger">3</span> 操行結算後，修改成績需透過學務單位執行「結算後修正」管理</li>
</ul>
<table class="table">
	<tr>
		<td nowrap>權限</td>
		<td nowrap>班級</td>
		<td nowrap>人數</td>
		<td nowrap>狀態</td>
		<td nowrap></td>
		<td nowrap>
		<input type="hidden" id="ClassNo" name="ClassNo" />
		<input type="hidden" id="rule" name="rule" />
		</td>
	</tr>
	<c:forEach items="${myClass}" var="c">
	<c:if test="${c.cnt>0}">
	<tr>
		<td nowrap>
		<c:if test="${c.rule eq 'T'}"><span class="label label-success">導師</span></c:if>
		<c:if test="${c.rule eq 'D'}"><span class="label label-warning">主任</span></c:if>
		<c:if test="${c.rule eq 'M'}"><span class="label label-important">教官</span></c:if>
		</td>
		<td nowrap>${c.ClassName}</td>
		<td nowrap>${c.cnt}</td>
		<td nowrap>
		<c:if test="${c.score>0}"><p class="text-error">學務單位已結算缺曠獎懲</p></c:if>
		<c:if test="${c.score==0}"><p class="text-success">學務單位未結算缺曠獎懲</p></c:if>
		</td>
		<td nowrap>
		<c:if test="${c.score==0}">
		<button class="btn btn-default btn-sm" name="method:edit" onClick="$('#ClassNo').val('${c.ClassNo}'), $('#rule').val('${c.rule}')" type="submit">修改加權分數</button></td>
		</c:if>
		<c:if test="${c.score>0}">
		<button class="btn btn-default btn-sm" disabled name="method:view" onClick="$('#ClassNo').val('${c.ClassNo}'), $('#rule').val('${c.rule}')" type="submit">檢視結算成績</button></td>
		</c:if>
		<td width="100%"></td>
	</tr>
	</c:if>
	</c:forEach>
</table>
</div>
</form>


</body>
</html>