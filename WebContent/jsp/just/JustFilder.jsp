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
<link rel="stylesheet" href="/eis/inc/css/jquery-ui.css" />
<link rel="stylesheet" href="/eis/inc/css/wizard-step.css" />
<script>


$(document).ready(function() {	
	$('#funbtn').popover("show");
	$('#idiotbtn').popover("show");
	setTimeout(function() {
		$('#funbtn').popover("hide");
		$('#idiotbtn').popover("hide");
	}, 10000);
	
	$("#reCount").mouseenter(function(){
		$('#reCount').popover("show");
		setTimeout(function() {
			$('#reCount').popover("hide");
		}, 10000);
	  });
	
});


</script>

</head>
<body>
    
<div id="dialog"></div>
<div class="alert">
<button type="button" class="close" data-dismiss="alert">&times;</button>
<strong>操行成績結算</strong> 請先列印試算並進行各項修正，結算動作同時確定1/3缺課名單，以及學期操行成績。</div>
    


<form action="JustFilder" method="post" class="form-horizontal">
<div class="wizard-steps">
  	<div><a href="#"><span>1</span> 列印試算成績</a></div>
  	<div><a href="#"><span>3</span> 修正獎懲缺曠</a></div>
  	<div><a href="#"><span>3</span> 結算成績</a></div>
  	<div><a href="#"><span>4</span> 列印結算總成績(操評會)</a></div>
  	<div><a href="#"><span>5</span> 操評會修正總成績</a></div>
  	<div><a href="#"><span>6</span> 列印結算總成績</a></div>
</div>
<table class="table">
	<tr>
		<td class="text-info" nowrap>班級範圍</td>
		<td class="control-group info" nowrap><%@ include file="/inc/jsp-kit/dhnSelector.jsp"%>
		<select name="grade">
			<option <c:if test="${grade eq ''}">selected</c:if> value="">全部</option>
			<option <c:if test="${grade eq '0'}">selected</c:if> value="0">非畢業班</option>
			<option <c:if test="${grade eq '1'}">selected</c:if> value="1">畢業班</option>
			<option <c:if test="${grade eq '2'}">selected</c:if> value="2">延修班</option>			
		</select>
		
		</td>
		<td width="100%"></td>
	</tr>
	<tr>
		<td class="text-info" nowrap>結算日期</td>
		<td class="control-group info" colspan="2">			
			<div class="input-append control-group info" style="float:left">		
				<input type="text" id="endDate" placeholder="點一下輸入日期" name="endDate" value="${endDate}"/>				
				<button class="btn btn-info" name="method:test" type="submit">列印試算總成績</button>				
				<button onClick="javascript: return(confirm('確定結算?')); void('')" class="btn btn-danger" name="method:count" type="submit">列印結算總成績</button>
			</div>
			&nbsp;
			<div id="funbtn" rel="popover" title="說明" 
			data-content="試算動作可進行多次，依部㓡人數不同約在5~10秒之內完成請耐心等候。為確保1/3缺課名單，結算僅可進行一次(除畢業班)，重複點選結算無任何作用。修正1/3缺課名單，請告知註冊及課務單位依規定辦理" 
			data-placement="right" class="btn btn-warning">?</div>
		</td>
		<td width="100%"></td>		
	</tr>
	
</table>


<div class="wizard-steps">
  	<div><a href="#"><span>1</span> 重新結算畢業班總成績</a></div>
  	<div><a href="#"><span>2</span> 列印結算總成績(操評會)</a></div>
  	<div><a href="#"><span>3</span> 操評會修正總成績</a></div>
  	<div><a href="#"><span>4</span> 列印結算總成績</a></div>
</div>
<table class="table">
	<tr>
		<td class="text-error" nowrap>畢業下修</td>
		<td class="control-group error">
			<input type="text" id="gradEndKillDate" placeholder="畢業班課程結算日期" name="gradEndKillDate" value="${gradEndKillDate}"/>
		</td>
		<td class="control-group error" nowrap>			
			<div class="input-append control-group danger" style="float:left;">
				<input type="text" id="gradEnd" placeholder="下修低年級課程結算日期" name="gradEnd" value="${gradEnd}"/>			
				<button class="btn btn-danger" name="method:reCount" type="submit">重新結算總成績</button>
			</div>
			&nbsp;
			<div id="idiotbtn" rel="popover" title="說明" 
			data-content="左側欄為「畢業班課程結算日期」右側欄為「下修低年級課程結算日期」,系統依左側欄計算畢業班開設課程的缺曠、依右側欄計算下修課程的缺曠、依左側欄排除畢業班結算後老師新增的曠缺記錄" 
			data-placement="bottom" class="btn btn-warning">?</div>		
		</td>
		<td width="100%"></td>		
	</tr>
	
</table>



</form>
    
<script>
$("input[name='beginDate'], input[name='endDate']" ).datepicker();
$("input[name='gradBegin'], input[name='gradEndKillDate']" ).datepicker();
$("input[name='gradEnd'], input[name='gradEnd']" ).datepicker();
</script>

</body>
</html>