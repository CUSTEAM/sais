<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script src="/eis/inc/js/plugin/bootstrap-typeahead.js"></script>
<script src="/eis/inc/js/plugin/jquery-ui.js"></script>
<script src="/eis/inc/js/plugin/bootstrap-tooltip.js"></script>
<script src="/eis/inc/js/plugin/jquery-ui-timepicker-addon.js" type="text/javascript"></script>
<link href="/eis/inc/css/jquery-ui.css" rel="stylesheet"/>
<script>  
$(document).ready(function() {
		
	$("input[name='beginDate']").change(function(){
		//alert($("#beginDate").val());
		$("#endDate").val($("#beginDate").val());
	});	
});


</script>  
</head>
<body>
<form action="JustManager" method="post">
<input type="hidden" name="stdNo" value="${editStd}"/>

<div class="alert">
    <button type="button" class="close" data-dismiss="alert">&times;</button>
    <h4>編輯缺曠重新結算操行成績 <button class="btn btn-danger" name="method:reCount" type="submit">修改記錄並重新結算</button>
	<button class="btn" name="method:search" type="submit">返回</button></h4>
</div>
<c:if test="${!empty dilgs}">
<c:if test="${info.abs2>0||info.abs5>0||info.abs3>0||info.abs4>0}">
	<div class="alert">
 	<h4>${stdNo}累計 <c:if test="${info.abs2>0}">尚未請假或未核准(曠課): ${info.abs2}節,</c:if> 	
 	<c:if test="${info.abs5>0}">遲到: ${info.abs5}節,</c:if>
    <c:if test="${info.abs1>0}">重大傷病: ${info.abs1}節,</c:if>	
	<c:if test="${info.abs3>0}">病假: ${info.abs3}節,</c:if>
	<c:if test="${info.abs4>0}">事假: ${info.abs4}節,</c:if>	
	<c:if test="${info.abs6>0}">公假: ${info.abs6}節,</c:if>
	<c:if test="${info.abs7>0}">喪假: ${info.abs7}節,</c:if>
	<c:if test="${info.abs8>0}">婚假: ${info.abs8}節,</c:if>
	<c:if test="${info.abs9>0}">產假: ${info.abs9}節</c:if>
	<c:if test="${!empty failSeld}"><span class="text-error">缺課達⅓ - <c:forEach items="${failSeld}" var="f">${f.chi_name},</c:forEach></span></c:if>
    </h4>
    </div>
</c:if>

<table class="table">
	<tr>
		<td class="text-info" nowrap>開始日期</td>
		<td class="control-group info" width="1">
			<input type="text" id="beginDate" placeholder="點一下輸入日期" name="beginDate" value="${beginDate}"/>
		</td>		
		<td class="control-group info" width="100%">
		<select name="begin">
			<option value="0">開始節次</option>		
			<c:forEach begin="1" end="14" var="b">
			<option <c:if test="${b eq begin}">selected</c:if> value="${b}">第${b}節</option>
			</c:forEach>
		</select>
		</td>
	</tr>
	<tr>
		<td class="text-info" nowrap>結束日期</td>
		<td class="control-group info"><input type="text" id="endDate" placeholder="點一下輸入日期" name="endDate" value="${endDate}"/></td>		
		<td class="control-group info">
		<select name="end">
			<option value="0">結束節次</option>		
			<c:forEach begin="1" end="14" var="b">
			<option <c:if test="${b eq end}">selected</c:if> value="${b}">第${b}節</option>
			</c:forEach>
		</select>
		</td>
	</tr>
	<tr>
		<td class="text-info" nowrap>缺曠型態</td>
		<td width="100%" colspan="2">
		<div class="input-append control-group info">
		<select name="absType">
			<option value=""></option>
			<c:forEach items="${CODE_DILG_RULES}" var="c">
				<option <c:if test="${absType eq c.id}">selected</c:if> value="${c.id}">${c.name}</option>
			</c:forEach>
   			
   		</select>
   		<button class="btn btn-info" name="method:addDilg" type="submit">建立學生缺曠</button>
   		</div>
		</td>
	</tr>
	
</table>


<table width="100%"><tr><td width="60%" valign="top" nowrap>

<table class="table table-bordered">
	<tr>
		<td>日期</td>
		<c:forEach begin="${endStart.begin}" end="${endStart.end}" var="i">
		<td nowrap>第${i}節</td>
		</c:forEach>
	</tr>
<c:forEach items="${dilgs}" var="d">
	<tr>
		<td>${fn:substring(d.date, 5, 10)}</td>
		<c:forEach begin="${endStart.begin}" end="${endStart.end}" var="i">	
		<td>
		<c:forEach items="${d.dilgs}" var="dd">		
		<c:if test="${dd.cls==i}">
		<div data-toggle="tooltip" onMouseOver="$(this).tooltip('show')" 
		<c:if test="${dd.result eq '2'}">disabled title="${dd.chi_name}, ${dd.cname}未核准"</c:if>
		<c:if test="${dd.result==null && dd.abs ne'2' && dd.abs ne'5'}">disabled title="${dd.chi_name}, ${dd.cname}審核中"</c:if>
		<c:if test="${dd.result!=null && dd.result ne '2'}">readonly title="${dd.chi_name}, ${dd.cname}已核准"</c:if> data-placement="bottom" title="${dd.chi_name}">
		<input type="hidden" name="Oid" id="Oid${dd.Oid}" value="">
		<span <c:if test="${dd.result eq '2'}">class="control-group error"</c:if>
		<c:if test="${dd.result==null && dd.abs ne'2' && dd.abs ne'5'}">class="control-group info"</c:if>
		<c:if test="${dd.result!=null&&dd.result ne '2'}">class="control-group success"</c:if>>
		<select name="abs" onChange="$('#Oid${dd.Oid}').val('${dd.Oid}')">
			<option value="">刪除</option>
			<c:forEach items="${CODE_DILG_RULES}" var="c">
				<option <c:if test="${dd.abs eq c.id}">selected</c:if> value="${c.id}">${c.name}</option>
			</c:forEach>   			
   		</select>
   		</span>
   		</div>
   		</c:if>
		
		</c:forEach>
		</td>
		</c:forEach>
	</tr>
		

</c:forEach>
</table>
<center>
<button class="btn btn-danger" name="method:saveDilg" type="submit">修改記錄並重新結算</button>
<button class="btn" name="method:search" type="submit">返回</button>
</center>

</td><td valign="top" nowrap>

<%@ include file="/jsp/timeOffManager/detail.jsp"%></td></tr></table>

</c:if>
</form>


<script>
$("input[name='beginDate'], input[name='endDate']" ).datepicker();
</script>
</body>
</html>