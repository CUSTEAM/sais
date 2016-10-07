<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script src="/eis/inc/js/plugin/jquery-ui.js"></script>
<script src="/eis/inc/js/plugin/jquery-ui-timepicker-addon.js" type="text/javascript"></script>
<link href="/eis/inc/css/jquery-ui.css" rel="stylesheet"/>
<title>點名狀況查詢</title>
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

<div class="bs-callout bs-callout-info" id="callout-helper-pull-navbar">
    <button type="button" class="close" data-dismiss="alert">&times;</button>
    <strong>點名狀況查詢</strong> 請輸入下列欄位後按下「依範圍查詢」，不想看到過多結果請縮小查詢範圍，增加效率&nbsp;
    <div id="funbtn" rel="popover" title="說明" data-content="每個欄位均可排序" data-placement="bottom" class="btn btn-warning">?</div>
</div>
<form action="CallStatusView" method="post" class="form-inline">
<div class="panel panel-primary">
<div class="panel-heading">查詢條件</div>
<table class="table">
	<tr>
		
		<td>
		
		<%@ include file="/inc/jsp-kit/csdSelector.jsp"%>
		</td>
	</tr>
	<tr>
		<td>
			<input class="form-control" type="text" placeholder="點一下輸入日期" name="begin" value="${begin}"/>
		
			<input class="form-control" type="text" placeholder="點一下輸入日期" name="end" value="${end}"/>
			<button class="btn btn-info" name="method:search" type="submit">依範圍查詢</button>
		</td>
	</tr>
</table>
</div>
<c:if test="${!empty result}">
<div class="panel panel-primary">
<div class="panel-heading">查詢結果</div>
<display:table name="${result}" class="table table-striped table-bordered" sort="list" excludedParams="*" >
  	<display:column title="開課班級" property="ClassName" sortable="true" />
  	<display:column title="課程名稱" property="chi_name" sortable="true"/>  	
  	<display:column title="授課教師" property="cname" sortable="true" />
  	<display:column title="點名次數" property="logCnt" sortable="true" />
  	<display:column title="缺課節數" property="dilgCnt" sortable="true" />
  	<display:column title="每日細節" property="Oid" paramId="Oid" href="CallStatusView" value="查看"/>
</display:table>
</div>
</c:if>

<script>
$("input[name='begin'], input[name='end']" ).datepicker();
</script>
</body>
</html>