<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

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
<script>  
$(document).ready(function() {
	$("input[id='nameno']").typeahead({
		remote:"#student_no",
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
	
	
	$('#funbtn').popover("show");
	setTimeout(function() {
		$('#funbtn').popover("hide");
	}, 3000);
	
	$("input[name='beginDate']").change(function(){
		//alert($("#beginDate").val());
		$("#endDate").val($("#beginDate").val());
	});	
});


</script>  
</head>
<body>







<div class="alert">
    <button type="button" class="close" data-dismiss="alert">&times;</button>
    <strong>請假資料維護</strong> 請輸入學號開始查詢，不輸入日期則顯示全部。建立缺課程式會判斷學生<strong>「輸入期間內是否有課並建立缺曠或假單」</strong>
    
    <div id="funbtn" rel="popover" title="說明" data-content="不核准或審核中欲變更,請直接刪除即可,修改假別後立即為核准狀態,您將成為核准人" data-placement="right" class="btn btn-warning">?</div>
    </div>

<form action="TimeOffManager" method="post">
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
		<td width="100%" colspan="2" class="control-group info">
		<select name="absType">
			<option value=""></option>
   			<option <c:if test="${absType eq '1'}">selected</c:if> value="1">住院</option>
   			<option <c:if test="${absType eq '2'}">selected</c:if> value="2">曠課</option>
   			<option <c:if test="${absType eq '3'}">selected</c:if> value="3">病假</option>
   			<option <c:if test="${absType eq '4'}">selected</c:if> value="4">事假</option>
   			<option <c:if test="${absType eq '5'}">selected</c:if> value="5">遲到</option>
   			<option <c:if test="${absType eq '6'}">selected</c:if> value="6">公假</option>
   			<option <c:if test="${absType eq '7'}">selected</c:if> value="7">喪假</option>
   			<option <c:if test="${absType eq '8'}">selected</c:if> value="8">婚假</option>
   			<option <c:if test="${absType eq '9'}">selected</c:if> value="9">產假</option>
   		</select>
		</td>
	</tr>
	<tr>
		<td class="text-info" nowrap>學號姓名</td>
		<td width="100%" colspan="2">
		<div class="input-append control-group info">
			<input class="span4" onClick="$('#nameno').val(''), $('#student_no').val('');" autocomplete="off" type="text" id="nameno" value="${nameno}" name="nameno"
			 data-provide="typeahead" onClick="addStd()" placeholder="輸入學號或姓名再點選列表中的學生" />
			<input type="hidden" id="student_no" value="${student_no}" name="student_no"/>
		    <button class="btn btn-info" name="method:search" type="submit">尋找學生缺曠</button>
		    <button class="btn btn-warning" name="method:add" type="submit">建立學生缺曠</button>
		</div>	
		</td>
	</tr>
</table>
<c:if test="${empty dilgs}"><div class="alert"><p>沒有缺曠記錄</p></div></c:if>
<c:if test="${!empty dilgs}">
<c:if test="${info.abs2>0||info.abs5>0||info.abs3>0||info.abs4>0}">
	<div class="alert">
 	<p>
 	累計 	
 	<c:if test="${info.abs2>0}">尚未請假或未核准(曠課): ${info.abs2}節,</c:if> 	
 	<c:if test="${info.abs5>0}">遲到: ${info.abs5}節,</c:if>
    <c:if test="${info.abs1>0}">重大傷病: ${info.abs1}節,</c:if>	
	<c:if test="${info.abs3>0}">病假: ${info.abs3}節,</c:if>
	<c:if test="${info.abs4>0}">事假: ${info.abs4}節,</c:if>	
	<c:if test="${info.abs6>0}">公假: ${info.abs6}節,</c:if>
	<c:if test="${info.abs7>0}">喪假: ${info.abs7}節,</c:if>
	<c:if test="${info.abs8>0}">婚假: ${info.abs8}節,</c:if>
	<c:if test="${info.abs9>0}">產假: ${info.abs9}節</c:if>
	<c:if test="${!empty failSeld}"><br><p class="text-error">缺課達⅓<c:forEach items="${failSeld}" var="f">${f.chi_name},</c:forEach></p></c:if>
    </div>
	</c:if>





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
		<div onMouseOver="$(this).tooltip('show')"
		<c:if test="${dd.result eq '2'}">title="${dd.chi_name}, ${dd.cname}未核准"</c:if>
		<c:if test="${dd.result==null && dd.abs ne'2' && dd.abs ne'5'}">title="${dd.chi_name}, ${dd.cname}審核中"</c:if>
		<c:if test="${dd.result!=null && dd.result ne '2'}">readonly title="${dd.chi_name}, ${dd.cname}已核准"</c:if>data-toggle="tooltip" data-placement="bottom" title="${dd.chi_name}">
		<input type="hidden" name="Oid" id="Oid${dd.Oid}" value="">
		<span <c:if test="${dd.result eq '2'}">class="control-group error"</c:if>
		<c:if test="${dd.result==null && dd.abs ne'2' && dd.abs ne'5'}">class="control-group info"</c:if>
		<c:if test="${dd.result!=null&&dd.result ne '2'}">class="control-group success"</c:if>>
		<select name="abs" onChange="$('#Oid${dd.Oid}').val('${dd.Oid}')">
			<option value="">刪除</option>
   			<option <c:if test="${dd.abs eq '1'}">selected</c:if> value="1">住院</option>
   			<option <c:if test="${dd.abs eq '2'}">selected</c:if> value="2">曠課</option>
   			<option <c:if test="${dd.abs eq '3'}">selected</c:if> value="3">病假</option>
   			<option <c:if test="${dd.abs eq '4'}">selected</c:if> value="4">事假</option>
   			<option <c:if test="${dd.abs eq '5'}">selected</c:if> value="5">遲到</option>
   			<option <c:if test="${dd.abs eq '6'}">selected</c:if> value="6">公假</option>
   			<option <c:if test="${dd.abs eq '7'}">selected</c:if> value="7">喪假</option>
   			<option <c:if test="${dd.abs eq '8'}">selected</c:if> value="8">婚假</option>
   			<option <c:if test="${dd.abs eq '9'}">selected</c:if> value="9">產假</option>
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
<c:if test="${empty just}"><button class="btn btn-danger" name="method:update" onClick="$('#Oid${d.Oid}').val('${d.Oid}')" type="submit">修改記錄</button></c:if>
<c:if test="${!empty just}"><button class="btn btn-danger" disabled type="button">操行成績已結算: ${just}</button></c:if>
<a href="TimeOffManager" class="btn" >返回</a>
</center>


</td>
<td td valign="top" nowrap><%@ include file="timeOffManager/detail.jsp"%></td>
</tr></table>

</c:if>




</form>









    
<script>
$("input[name='beginDate'], input[name='endDate']" ).datepicker();
</script>

</body>
</html>