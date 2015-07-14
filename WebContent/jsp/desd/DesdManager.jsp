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
	}, 5000);
	
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
<div class="alert">
<button type="button" class="close" data-dismiss="alert">&times;</button>
<strong>獎懲資料管理</strong></div>
    


<form action="DesdManager" method="post" class="form-horizontal">




<table class="table">
	<tr>
		<td>日期</td>
		<td>文號</td>
		<td>原因</td>
		<td>種類</td>
		<td>次數</td>
		<td>種類</td>
		<td>次數</td>
		<td>生活競賽</td>
		<td width="100%"></td>
	</tr>
	<tr>
		<td class="control-group info"><input type="text" class="span2" id="ddate" placeholder="點一下輸入日期" onClick="$(this).val('')" name="ddate" value="${ddate}"/></td>
		<td class="control-group info"><input type="text" class="span2" id=no placeholder="點一下輸入文號" onClick="$(this).val('')" name="no" value="${no}"/></td>
		<td class="control-group info"><input type="text" class="span3" autocomplete="off" onClick="$(this).val('')" placeholder="代碼或名稱" value="${cause}" onClick="$(this).val(''), $('#reason').val('')" name="cause" id="cause"/>
		<input type="hidden" value="${reason}" id="reason" name="reason"/></td>
		<td class="control-group info">
		<select name="kind1">
			<option <c:if test="${kind1 eq ''}">selected</c:if> value=""></option>
			<c:forEach items="${allDesd}" var="d">
			<option <c:if test="${kind1 eq d.id}">selected</c:if> value="${d.id}">${d.name}, ${d.deduct}</option>
			</c:forEach>
		</select>
		</td>
		<td class="control-group info">
		<select id="cnt1" name="cnt1">
			<option <c:if test="${cnt1 eq ''}">selected</c:if> value=""></option>
			<option <c:if test="${cnt1 eq '1'}">selected</c:if> value="1">1次</option>
			<option <c:if test="${cnt1 eq '2'}">selected</c:if> value="2">2次</option>
			<option <c:if test="${cnt1 eq '3'}">selected</c:if> value="3">3次</option>
		</select>
		</td>
		<td class="control-group info">
		<select name="kind2">
			<option <c:if test="${kind2 eq ''}">selected</c:if> value=""></option>
			<c:forEach items="${allDesd}" var="d">
			<option <c:if test="${kind2 eq d.id}">selected</c:if> value="${d.id}">${d.name}, ${d.deduct}</option>
			</c:forEach>
		</select>
		</td>
		<td class="control-group info">
		<select id="cnt2" name="cnt2">
			<option <c:if test="${cnt2 eq ''}">selected</c:if> value=""></option>
			<option <c:if test="${cnt2 eq '1'}">selected</c:if> value="1">1次</option>
			<option <c:if test="${cnt2 eq '2'}">selected</c:if> value="2">2次</option>
			<option <c:if test="${cnt2 eq '3'}">selected</c:if> value="3">3次</option>
		</select>
		</td>
		<td class="control-group info">
		
		<select name="act_illegal">
			<option <c:if test="${act_illegal eq '1'}">selected</c:if> value="1">納入</option>
			<option <c:if test="${act_illegal eq '0'}">selected</c:if> value="0">不納入</option>
		</select>
		</td>
		<td width="100%"></td>
	</tr>
	<tr>
		<td width="100%" colspan="9" class="control-group info">
		<div class="input-append">
			<input class="span4" onClick="$('#idiot').val(''), $('#stdNo').val('');" autocomplete="off" type="text" id="idiot" value="${nameno}" name="nameno"
			 data-provide="typeahead" onClick="addStd()" placeholder="學號或姓名" />
			<input type="hidden" id="stdNo" value="${stdNo}" name="stdNo"/>
		    <button class="btn btn-info" name="method:search" type="submit">個別查詢</button>
		    <button class="btn btn-danger" name="method:add" type="submit">個別建立</button>
		</div>
		<div id="info1" rel="popover" title="說明" data-content="個別建立或查詢" data-placement="right" class="btn btn-warning">?</div>	
		</td>
	</tr>
	<tr>
		<td width="100%" colspan="9" class="control-group info">
		<%@ include file="/inc/jsp-kit/selector/fullSelector.jsp"%>
		<div class="input-append">			
		    <button class="btn btn-info" name="method:print" type="submit">列印報表</button>
		    <button class="btn btn-danger" name="method:addAll" type="submit">批次建立</button>
		</div>	
		<div id="info2" rel="popover" title="說明" data-content="整班建立或列表" data-placement="right" class="btn btn-warning">?</div>
		</td>
	</tr>
	
</table>

<c:if test="${!empty stds}">
<table class="table">
	<tr>
		<td nowrap>日期</td>
		<td nowrap>文號</td>
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
<input type="hidden" id="delOid" name="Oid" />

</c:if>


</form>
    
<script>
$("input[name='ddate'], input[name='endDate']" ).datepicker();
</script>

</body>
</html>