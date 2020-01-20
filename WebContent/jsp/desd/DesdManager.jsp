<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>獎懲資料管理</title>
<script src="/eis/inc/js/plugin/bootstrap-typeahead.js"></script>
<script src="/eis/inc/js/plugin/json2.js"></script>
<script src="/eis/inc/js/plugin/jquery-ui.js"></script>
<script src="/eis/inc/js/plugin/bootstrap-tooltip.js"></script>
<script src="/eis/inc/js/plugin/jquery-ui-timepicker-addon.js"></script>
<link rel="stylesheet" href="/eis/inc/css/wizard-step.css" />
<link rel="stylesheet" href="/eis/inc/css/jquery-ui.css" />
<script>
$(function() {	
	
	$(".cnt").keyup(function() {
		if(this.value>8)this.value=8;
		if(this.value<-8)this.value=-8;
	});
	
	$("input[id='idiot']").typeahead({
		remote:"#stdNo",
		source : [],
		items : 10,
		updateSource:function(inputVal, callback){			
			$.ajax({
				type:"POST",
				url:"/eis/autoCompleteStmd",
				dataType:"json",
				data:{length:10, nameno:inputVal},
				success:function(d){
					callback(d.list);
				}
			});
		}		
	});
	
});





$(document).ready(function() {	
	$('#info1').popover("show");
	$('#info2').popover("show");
	setTimeout(function() {
		$('#info1').popover("hide");
		$('#info2').popover("hide");
	}, 0);
	
	$("input[id='cause']").typeahead({
		remote:"#reason",
		source : [],
		items : 10,
		updateSource:function(inputVal, callback){			
			$.ajax({
				type:"POST",
				url:"/eis/autoCompleteCODE_DESD_CAUSE",
				dataType:"json",
				data:{length:20, nameno:inputVal},
				success:function(d){
					callback(d.list);
				}
			});
		}		
	});
	
});


</script>

</head>
<body>
    
<div id="dialog"></div>
<div class="bs-callout bs-callout-info">
<button type="button" class="close" data-dismiss="alert">&times;</button>
<strong>獎懲資料管理</strong></div>
    


<form action="DesdManager" method="post" class="form-inline">



<div class="panel panel-primary">
<div class="panel-heading">個別處理獎懲記錄</div>
<table class="table">
	<tr>
		<td>
		<input type="text" style="width:180px;" class="form-control" id="ddate" placeholder="點一下輸入日期" onClick="$(this).val('')" name="ddate" value="${ddate}"/>
		<input type="text" style="width:180px;"class="form-control" id=no placeholder="點一下輸入文號" onClick="$(this).val('')" name="no" value="${no}"/>
		<input type="text" class="form-control" autocomplete="off" onClick="$(this).val('')" placeholder="原因" value="${cause}" onClick="$(this).val(''), $('#reason').val('')" name="cause" id="cause"/>
		<input type="hidden" value="${reason}" id="reason" name="reason"/>
		
		</td>
	</tr>
	<tr>
		<td>
		<select name="kind1" class="selectpicker" data-width="auto">
			<option <c:if test="${kind1 eq ''}">selected</c:if> value="">種類1</option>
			<c:forEach items="${allDesd}" var="d">
			<option <c:if test="${kind1 eq d.id}">selected</c:if> value="${d.id}">${d.name}, ${d.deduct}</option>
			</c:forEach>
		</select>
		<select id="cnt1" name="cnt1" class="selectpicker" data-width="auto">
			<option <c:if test="${cnt1 eq ''}">selected</c:if> value="">次數</option>
			<option <c:if test="${cnt1 eq '1'}">selected</c:if> value="1">1次</option>
			<option <c:if test="${cnt1 eq '2'}">selected</c:if> value="2">2次</option>
			<option <c:if test="${cnt1 eq '3'}">selected</c:if> value="3">3次</option>
		</select>
		<select name="kind2" class="selectpicker" data-width="auto">
			<option <c:if test="${kind2 eq ''}">selected</c:if> value="">種類2</option>
			<c:forEach items="${allDesd}" var="d">
			<option <c:if test="${kind2 eq d.id}">selected</c:if> value="${d.id}">${d.name}, ${d.deduct}</option>
			</c:forEach>
		</select>
		<select id="cnt2" name="cnt2" class="selectpicker" data-width="auto">
			<option <c:if test="${cnt2 eq ''}">selected</c:if> value="">次數</option>
			<option <c:if test="${cnt2 eq '1'}">selected</c:if> value="1">1次</option>
			<option <c:if test="${cnt2 eq '2'}">selected</c:if> value="2">2次</option>
			<option <c:if test="${cnt2 eq '3'}">selected</c:if> value="3">3次</option>
		</select>
		
		<select name="act_illegal" class="selectpicker" data-width="auto">
			<option <c:if test="${act_illegal eq '1'}">selected</c:if> value="1">納入</option>
			<option <c:if test="${act_illegal eq '0'}">selected</c:if> value="0">不納入</option>
		</select>
		</td>
	</tr>
	<tr>
		<td>
		
			<input class="form-control" onClick="$('#idiot').val(''), $('#stdNo').val('');" autocomplete="off" type="text" id="idiot" value="${nameno}" name="nameno"
			 data-provide="typeahead" onClick="addStd()" placeholder="學號或姓名" />
			<input type="hidden" id="stdNo" value="${stdNo}" name="stdNo"/>
		    <button class="btn btn-default" name="method:search" type="submit">個別查詢</button>
		    <button class="btn btn-danger" name="method:add" type="submit">個別建立</button>
		
		<div id="info1" rel="popover" title="說明" data-content="個別建立或查詢" data-placement="right" class="btn btn-warning">?</div>	
		</td>
	</tr>

	<tr>
		<td width="100%" colspan="9">
		<%@ include file="/inc/jsp-kit/classSelector.jsp"%>
		</td>
	</tr>
	<tr>
		<td>	
		<button class="btn btn-default" name="method:print" type="submit">列印報表</button>
		<button class="btn btn-danger" onclick="return confirm('請問是否建立？');" name="method:addAll" type="submit">批次建立</button>
		
		<div id="info2" rel="popover" title="說明" data-content="整班建立或列表" data-placement="right" class="btn btn-warning">?</div>
		</td>
	</tr>
	
</table>
</div>


<c:if test="${!empty stds}">
<div class="panel panel-primary">
<div class="panel-heading">獎懲記錄列表</div>
<table class="table">
	<tr>
		<td nowrap>日期</td>
		<td nowrap>文號</td>
		<td nowrap>學生</td>
		<td nowrap>原因</td>
		<td nowrap>種類</td>
		<td nowrap>次數</td>
		<td nowrap>種類</td>
		<td nowrap>次數</td>
		<td nowrap>生活競賽</td>
		<td width="100%"></td>
	</tr>
	<c:forEach items="${stds}" var="s">
	<tr>
		<td nowrap>${s.ddate}</td>
		<td nowrap>${s.no}</td>
		<td nowrap>${s.student_no}, ${s.student_name}</td>
		<td nowrap>${s.reason}${s.name}</td>
		<td nowrap>
		<c:forEach items="${allDesd}" var="d">
			<c:if test="${s.kind1 eq d.id}">${d.name}</c:if>
		</c:forEach>		
		</td>
		<td nowrap>${s.cnt1}</td>
		<td nowrap>
		<c:forEach items="${allDesd}" var="d">
			<c:if test="${s.kind2 eq d.id}">${d.name}</c:if>
		</c:forEach>
		</td>
		<td nowrap>${s.cnt2}</td>
		<td nowrap>
		<c:if test="${s.act_illegal eq '1'}">納入</c:if>
		<c:if test="${s.act_illegal eq '0'}">不納入</c:if>		
		</td>
		<td width="100%">
		
		<button class="btn btn-danger btn-small" onClick="$('#delOid').val('${s.Oid}')" name="method:del" type="submit">刪除</button>
		</td>
	</tr>
	</c:forEach>
</table>
</div>
<input type="hidden" id="delOid" name="Oid" />

</c:if>


</form>
    
<script>
$("input[name='ddate'], input[name='endDate']").datepicker({
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