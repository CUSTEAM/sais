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
<script src="/eis/inc/js/plugin/jquery-ui-timepicker-addon.js"></script>
<link href="/eis/inc/css/jquery-ui.css" rel="stylesheet"/>
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
    
<div class="alert">
<button type="button" class="close" data-dismiss="alert">&times;</button>
<strong>缺曠查詢列印</strong>
<div id="funbtn" rel="popover" title="說明" data-content="輸入查詢範圍.." data-placement="right" class="btn btn-warning">?</div>
</div>

<form action="TimeOffSearch" method="post">
<table class="table">
	<tr>
		<td class="text-info" nowrap>班級範圍..</td>
		<td class="control-group info" colspan="2">
		<%@ include file="/inc/jsp-kit/classSelector.jsp"%>
		</td>
	</tr>
	<tr>
		<td class="text-info" nowrap>日期範圍</td>
		<td class="control-group info" width="100%" nowrap colspan="2">
		<input type="text" id="beginDate" placeholder="點一下輸入日期" name="beginDate" value="${beginDate}"/>
		<input type="text" id="endDate" placeholder="點一下輸入日期" name="endDate" value="${endDate}"/>					
		</td>		
	</tr>
	<tr>
		<td class="text-info" nowrap>缺課節數</td>	
		<td class="control-group info">
		<select name="less">
			<option <c:if test="${less eq 'm'}">selected</c:if> value="m">多於</option>
			<option <c:if test="${less eq 'l'}">selected</c:if> value="l">少於</option>
		</select>
		</td>	
		<td class="control-group info" width="100%">				
		<div class="input-append control-group info">			
			<input class="span1" type="text" id="num" value="${fnum+0}" name="num" value="${endDate}"/>
		    <button class="btn btn-info" name="method:listCls" type="submit">班級統計表</button>
		    <button class="btn btn-warning" name="method:listStd" type="submit">個人統計表</button>
		    <button class="btn btn-success" name="method:listMail" type="submit">郵寄通知</button>
		</div>			
		</td>		
	</tr>
</table>
</table>
</form>
<script>
$("input[name='beginDate'], input[name='endDate']" ).datepicker();
</script>
</body>
</html>