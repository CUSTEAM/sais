<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>缺曠查詢列印</title>
<script src="/eis/inc/js/plugin/bootstrap-typeahead.js"></script>
<script src="/eis/inc/js/plugin/json2.js"></script>
<script src="/eis/inc/js/plugin/jquery-ui.js"></script>
<script src="/eis/inc/js/plugin/bootstrap-tooltip.js"></script>
<script src="/eis/inc/js/plugin/jquery-ui-timepicker-addon.js"></script>
<link href="/eis/inc/css/jquery-ui.css" rel="stylesheet"/>
<script>  
$(document).ready(function() {	
	$('.help').popover("show");
	setTimeout(function() {
		$('.help').popover("hide");
	}, 0);
});
</script>  
</head>
<body> 
    
<div class="bs-callout bs-callout-info" id="callout-helper-pull-navbar">		
<button type="button" class="close" data-dismiss="alert">&times;</button>
<strong>缺曠查詢列印</strong>
</div>

<form action="TimeOffSearch" class="form-inline" method="post">
<div class="panel panel-primary">
<div class="panel-heading">查詢範圍</div>
	
<table class="table">
	<tr>
		
		<td colspan="2">
		<%@ include file="/inc/jsp-kit/classSelector.jsp"%>
		</td>
	</tr>
	<tr>
		
		<td width="100%" nowrap colspan="2">
		<div class="input-group">
      	<div class="input-group-addon">自</div>
		<input type="text" style="width:180px;" id="beginDate" class="form-control" placeholder="點一下輸入日期" name="beginDate" value="${beginDate}"/>
		</div>
		<div class="input-group">
      	<div class="input-group-addon">至</div>
		<input type="text" style="width:180px;" id="endDate" class="form-control" placeholder="點一下輸入日期" name="endDate" value="${endDate}"/>					
		</div>
		<div class="btn-group" role="group">
			<div class="btn btn-info help" data-toggle="popover" title="說明" 
			data-content="依據查詢範圍列出班級" data-placement="bottom">?</div>
		    <button class="btn btn-info" name="method:listCls" type="submit">班級統計表</button>
		    
		</div>
		</td>		
	</tr>
	<tr>
		<td nowrap>
		
		
		    
		</td>
	</tr>
	<tr>	
		<td nowrap>
		<select name="abs" class="selectpicker" data-width="auto">
			<option value="">所有種類</option>
			<c:forEach items="${CODE_DILG_RULES}" var="c">
			<option <c:if test="${c.id eq abs}">selected</c:if> value="${c.id}">${c.name}</option>
			</c:forEach>
		</select>
		
		<div class="input-group">
      	<div class="input-group-addon">多於或等於</div>
      	<input type="text" style="width:80px;" class="form-control" name="more" value="${more}"/>
    	</div>
		
		<div class="input-group">
      	<div class="input-group-addon">少於</div>
      	<input type="text" style="width:80px;" class="form-control" name="less" value="${less}"/>
    	</div>
		
		<div class="btn-group" role="group">
		    <div class="btn btn-warning help" data-toggle="popover" title="說明" 
			data-content="依據查詢範圍列出學生" data-placement="bottom">?</div>
			<button class="btn btn-warning" name="method:listStd" type="submit">個人統計表</button>
			
			</div>  
		    
		    
		    <div class="btn-group" role="group" style="width:420px;">
		    <button class="btn btn-success" name="method:listMail" type="submit">郵寄通知</button>
			<div class="btn btn-success help" data-toggle="popover" title="郵寄通知設定說明" 
			data-content="1.篩選開始日期前「多於」(已寄)學生 2.列出結束日期前「多於」(未寄)學生 3.篩選結束日期前「少於」(超標)學生 4.再次設定更高標準查詢" 
			data-placement="bottom">?</div>
			</div>
		</td>		
	</tr>
</table>
</div>
</form>
<script>
$("input[name='beginDate'], input[name='endDate']").datepicker({
	changeMonth: true,
	changeYear: true,
	//minDate: '@minDate'
	yearRange: "-100:+0"
	//showButtonPanel: true,
	//dateFormat: 'yy-MM-dd'
});


</script>
</body>
</html>