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
<link href="/eis/inc/css/jquery-ui.css" rel="stylesheet"/>
<link href="/eis/inc/css/wizard-step.css" rel="stylesheet"/>
<script>
$(document).ready(function() {
	//$('#funbtn').popover("show");
	//setTimeout(function() {
		//$('#funbtn').popover("hide");
	//}, 8000);
	
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
	
	$("input[id='nameno']").typeahead({		
		remote:"#"+this.name,
		source : [],
		items : 10,		
		updateSource:function(inputVal, callback){			
			$.ajax({
				type:"POST",
				url:"/eis/autoCompleteCode1",
				dataType:"json",
				data:{length:10, nameno:inputVal, name:(this).name},
				success:function(d){
					callback(d.list);
				}
			});
		}
	
	});
	
});
/*function ValidateNumber(e, pnumber){	
	if (!/^\d+$/.test(pnumber)){
		$(e).val(/^\d+/.exec($(e).val()));
	}
	return false;
}
onkeyup="return ValidateNumber($(this),value)"*/
</script>
</head>
<body>




<div class="alert alert-danger">
    <h4>操行成績管理</h4>
    <p style="margin-top:5px;">輸入班級或學號找尋學生開始編輯，請在成績結算後進行</p>
    <p>修改⅓缺課名單請先查詢學生，點選學號或姓名修正該科目缺課記錄</p>
</div>

<form action="JustManager" method="post">
<div class="wizard-steps">
  	<div><a href="#"><span>1</span> 操行成績結算</a></div>
  	<div class="completed-step"><a href="#"><span>2</span> 修改學生缺曠扣考及總分</a></div>
</div>
<table class="table">
	<tr>
		<td class="text-info" nowrap>班級</td>
		<td width="100%" class="control-group info" colspan="2">
		<%@ include file="/inc/jsp-kit/classSelector.jsp"%>
		</td>
	</tr>
	<tr>
		<td class="text-info" nowrap>學號姓名</td>
		<td width="100%" colspan="2">
		<div class="input-append control-group info">
			<input class="span4" onClick="$('#idiot').val(''), $('#stdNo').val('');" autocomplete="off" type="text" id="idiot" value="${nameno}" name="nameno"
			 data-provide="typeahead" placeholder="學號或姓名" />
			<input type="hidden" id="stdNo" value="${stdNo}" name="stdNo"/>
		    <button class="btn btn-info" name="method:search" type="submit">尋找學生</button>
		</div>	
		</td>
	</tr>
</table>



<input type="hidden" name="editStd" id="editStd" />

<c:if test="${!empty stds}">
<div class="alert alert-danger">
    <p>所有欄位自由修改，輸入時程式不會做任何自動計算</p>
</div>
<table class="table">
	<tr>
		<td class="text-info" nowrap>學號</td>
		<td class="text-info" nowrap>姓名</td>
		<td class="text-info" nowrap>導師</td>
		<td class="text-info" nowrap>主任</td>
		<td class="text-info" nowrap>教官</td>
		<td class="text-info" nowrap>缺曠</td>							
		<td class="text-info" nowrap>獎懲</td>
		<td class="text-info" nowrap>操評會</td>
		<td class="text-info" nowrap>成績</td>
		<td class="text-info" nowrap>評語一</td>
		<td class="text-info" nowrap>評語二</td>
		<td class="text-info" nowrap>評語三</td>
		<td class="text-info" width="100%"></td>
	</tr>
	<c:forEach items="${stds}" var="s">
		<tr>
			<td class="text-info" nowrap>
			<input type="hidden" name="student_no"  value="${s.student_no}" />
			<button class="btn btn-link" onMouseOver="$('#editStd').val('${s.student_no}')" name="method:edit" type="submit">${s.student_no}</button></td>
			<td class="text-info" nowrap><button onMouseOver="$('#editStd').val('${s.student_no}')" class="btn btn-link" name="method:edit" type="submit">${s.student_name}</button></td>
			
			<td class="control-group info">								
				<input id="teacher_score${s.student_no}" name="teacher_score" value="${s.teacher_score}" 
				type="text" class="span1" style="ime-mode:disabled" autocomplete="off" maxlength="2"/>				
			</td>
											
			<td class="control-group info">								
				<input id="deptheader_score${s.student_no}" name="deptheader_score" value="${s.deptheader_score}" 
				type="text" class="span1" style="ime-mode:disabled" autocomplete="off" maxlength="2"/>
			</td>
			
			<td class="control-group info">
				<input id="military_score${s.student_no}" name="military_score" value="${s.military_score}" 
				type="text" class="span1" style="ime-mode:disabled" autocomplete="off" maxlength="2"/>
			</td>			
			<td class="control-group info" nowrap>
				<input id="dilg_score${s.student_no}" value="${s.dilg_score}" autocomplete="off" name="dilg_score"type="text" class="span1"/>
			</td>																
			<td class="control-group info" nowrap>
				<input id="desd_score${s.student_no}" value="${s.desd_score}" autocomplete="off" name="desd_score" type="text" class="span1"/>
			</td>
			<td class="control-group info" nowrap>
				<input id="meeting_score${s.student_no}" value="${s.meeting_score}" name="meeting_score" 
				type="text" class="span1" style="ime-mode:disabled" autocomplete="off" maxlength="3" class="span1"/>
				</td>
			<td class="control-group info" nowrap>
				<input id="total_score${s.student_no}" name="total_score" value="${s.total_score}"type="text" maxlength="2" autocomplete="off" onkeyup="return ValidateNumber($(this),value)" class="span1"/>
			</td>
			<td class="control-group info" nowrap>
				<input name="com_code1" type="text" class="span2" value="${s.com_code1}" placeholder="輸入代碼或名稱片段" />								
			</td>
			<td class="control-group info" nowrap>
				<input name="com_code2" type="text" class="span2" value="${s.com_code2}" placeholder="輸入代碼或名稱片段" />
			</td>
			<td class="control-group info" nowrap>
				<input name="com_code3" type="text" class="span2" value="${s.com_code3}" placeholder="輸入代碼或名稱片段" />
			</td>
			<td nowrap width="100%"></td>
		</tr>
		
	</c:forEach>
		<tr>
			<td colspan="13" align="right">
			<button class="btn btn btn-success" name="method:save" type="submit">儲存</button>
			<a href="JustManager" class="btn">返回</a>
			</td>
		</tr>
	</table>
	
</c:if>
</form>
</body>
</html>