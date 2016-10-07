<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">


<title>缺曠記錄管理</title>
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







<div class="bs-callout bs-callout-info" id="callout-helper-pull-navbar">	
    <button type="button" class="close" data-dismiss="alert">&times;</button>
    <strong>缺曠記錄管理</strong> 
    
</div>

<form action="TimeOffManager" class="form-inline" method="post">
<div class="panel panel-primary">
<div class="panel-heading">設定條件</div>
<ul class="list-group">
<li class="list-group-item"><span class="label label-as-badge label-warning">1</span> 輸入學號開始查詢，不輸入日期則顯示全部</li>
<li class="list-group-item"><span class="label label-as-badge label-warning">2</span> 建立缺課時會自動判斷學生<strong>「輸入期間內是否有課並建立缺曠或假單」</strong></li>
<li class="list-group-item"><span class="label label-as-badge label-danger">3</span> 修改不核准或審核中的假單，請先刪除原假單</li>
</ul>
<table class="table">
	<tr>
		<td nowrap>
		<div class="input-group">
	  		<span class="input-group-addon">開始日期</span>
	  		<input class="form-control" type="text" id="beginDate" placeholder="點一下輸入日期" name="beginDate" value="${beginDate}"/>
		
			<select name="begin" class="selectpicker" data-width="auto">
			<option value="0">開始節次</option>		
			<c:forEach begin="1" end="14" var="b">
			<option <c:if test="${b eq begin}">selected</c:if> value="${b}">第${b}節</option>
			</c:forEach>
		</select>
		</div>
		
	  	
		
		
		
		</td>
	</tr>
	<tr>
		<td class="text-info" nowrap>
		
		<div class="input-group">
	  		<span class="input-group-addon">結束日期</span>
	  		<input class="form-control" type="text" id="endDate" placeholder="點一下輸入日期" name="endDate" value="${endDate}"/>
			<select name="end" class="selectpicker" data-width="auto">
			<option value="0">結束節次</option>		
			<c:forEach begin="1" end="14" var="b">
			<option <c:if test="${b eq end}">selected</c:if> value="${b}">第${b}節</option>
			</c:forEach>
			</select>
		</div>
		
	  	
		
		
		</td>
	</tr>
	<tr>
		<td class="text-info" nowrap>
		
		
		
		<div class="input-group">
		<span class="input-group-addon">學號姓名</span>
      	<input class="form-control" onClick="$('#nameno').val(''), $('#student_no').val('');" autocomplete="off" type="text" id="nameno" value="${nameno}" name="nameno"
			 data-provide="typeahead" onClick="addStd()" placeholder="輸入學號或姓名再點選列表中的學生" />
      	
      	<select name="absType" class="selectpicker" data-width="auto">
			<option value="">缺曠型態</option>
			<c:forEach items="${CODE_DILG_RULES}" var="c">
			<option <c:if test="${absType eq c.id}">selected</c:if> value="${c.id}">${c.name}</option>
			</c:forEach>   			
  		</select>
        
      	
    	</div>
    	<button class="btn btn-default" name="method:search" type="submit">尋找學生缺曠</button>
    	<input type="hidden" id="student_no" value="${student_no}" name="student_no"/>
    	<button class="btn btn-danger" name="method:add" type="submit">建立學生缺曠</button>
		
		    
		</td>
	</tr>
</table>
</div>





<c:if test="${!empty student_no}">
<table width="100%"><tr><td width="60%" valign="top" nowrap>

<div class="panel panel-primary">
<div class="panel-heading">查詢範圍</div>
<div class="panel-body">
${nameno}<br>
<c:if test="${empty dilgs}">沒有缺曠記錄</c:if>
<c:if test="${!empty dilgs}">
<c:if test="${info.abs2>0||info.abs5>0||info.abs3>0||info.abs4>0}"> 
累計
<c:if test="${info.abs2>0}">曠課: ${info.abs2}節,</c:if> 	
<c:if test="${info.abs5>0}">遲到: ${info.abs5}節,</c:if>
<c:if test="${info.abs1>0}">重大傷病: ${info.abs1}節,</c:if>	
<c:if test="${info.abs3>0}">病假: ${info.abs3}節,</c:if>
<c:if test="${info.abs4>0}">事假: ${info.abs4}節,</c:if>	
<c:if test="${info.abs6>0}">公假: ${info.abs6}節,</c:if>
<c:if test="${info.abs7>0}">喪假: ${info.abs7}節,</c:if>
<c:if test="${info.abs8>0}">婚假: ${info.abs8}節,</c:if>
<c:if test="${info.abs9>0}">產假: ${info.abs9}節</c:if>
<c:if test="${!empty failSeld}"><br><p class="text-error">缺課達⅓<c:forEach items="${failSeld}" var="f">${f.chi_name},</c:forEach></c:if>
</c:if>
</div>
<table class="table table-bordered">
	<tr>
		<td>日期</td>
		<!--c:forEach begin="1" end="16" var="i"-->
		<c:forEach begin="${endStart.begin}" end="${endStart.end}" var="i">
		<td nowrap>第${i}節</td>
		</c:forEach>
	</tr>
<c:forEach items="${dilgs}" var="d">
	<tr>
		<td>${fn:substring(d.date, 5, 10)}</td>
		<!--c:forEach begin="1" end="16" var="i"-->	
		<c:forEach begin="${endStart.begin}" end="${endStart.end}" var="i">	
		<td>
		<c:forEach items="${d.dilgs}" var="dd">		
		<c:if test="${dd.cls==i}">
		<div onMouseOver="$(this).tooltip('show')"
		<c:if test="${dd.result eq '2'}">title="${dd.chi_name}, ${dd.cname}未核准"</c:if>
		<c:if test="${dd.result==null && dd.abs ne'2' && dd.abs ne'5'}">title="${dd.chi_name}, ${dd.cname}審核中"</c:if>
		<c:if test="${dd.result!=null && dd.result ne '2'}">readonly title="${dd.chi_name}, ${dd.cname}已核准"</c:if>data-toggle="tooltip" data-placement="bottom" title="${dd.chi_name}">
		<input type="hidden" name="Oid" id="Oid${dd.Oid}" value="">
		
		
		
		<select name="abs" class="selectpicker" data-width="auto" onChange="$('#Oid${dd.Oid}').val('${dd.Oid}')">
			<option value="">刪除</option>
			<c:forEach items="${CODE_DILG_RULES}" var="c">
			<option <c:if test="${dd.abs eq c.id}">selected</c:if> value="${c.id}">${c.name}</option>
			</c:forEach>   			
  		</select>
		
		
   		
   		</div>
   		</c:if>
		
		</c:forEach>
		</td>
		</c:forEach>
	</tr>
		

</c:forEach>
</table>
<div class="panel-body">
<c:if test="${empty just}"><button class="btn btn-danger" name="method:update" onClick="$('#Oid${d.Oid}').val('${d.Oid}')" type="submit">儲存學生缺曠記錄</button></c:if>
<c:if test="${!empty just}"><button class="btn btn-danger" disabled type="button">操行成績已結算: ${just}</button></c:if>
<a href="TimeOffManager" class="btn" >返回</a>
</div>
</div>
<center>

</center>


</td>
<td valign="top" nowrap style="padding-left:5px;"><%@ include file="timeOffManager/detail.jsp"%></td>
</tr>
</table>
</c:if>
</c:if>




</form>









    
<script>
$("input[name='beginDate'], input[name='endDate']" ).datepicker();
</script>

</body>
</html>