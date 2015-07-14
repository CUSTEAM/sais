<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script src="/eis/inc/js/plugin/bootstrap-typeahead.js"></script>
<script src="/eis/inc/js/plugin/json2.js"></script>
<script src="/eis/inc/js/plugin/jquery-ui.js"></script>
<script src="/eis/inc/js/plugin/bootstrap-tooltip.js"></script>
<script src="/eis/inc/js/plugin/jquery-ui-timepicker-addon.js" type="text/javascript"></script>
<link href="/eis/inc/css/jquery-ui.css" rel="stylesheet"/>
</head>
<body>
    
<div id="dialog"></div>
<div class="alert">
<button type="button" class="close" data-dismiss="alert">&times;</button>
<strong>生活教育競賽</strong></div>
    


<form action="Contest" method="post" class="form-horizontal">
<table class="table control-group info">
	<tr>
		<td nowrap>班級範圍</td>
		<td nowrap>
		<%@ include file="/inc/jsp-kit/classSelector.jsp"%>
		</td>
		<td width="100%"><button class="btn btn-danger" name="method:print" type="submit">列印</button></td>
	</tr>
	<tr>
		<td>日期範圍</td>
		<td>
		<input type="text" id="begin" placeholder="點一下輸入日期" name="begin" value="${begin}"/>
		<input type="text" id="end" placeholder="點一下輸入日期" name="end" value="${end}"/>
		</td>
	</tr>
	
</table>

</form>
    
<script>
$("input[name='begin'], input[name='end']" ).datepicker();
</script>
</body>
</html>