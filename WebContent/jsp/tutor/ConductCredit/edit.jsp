<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>操行成績及評語管理</title>

<script src="/eis/inc/js/plugin/bootstrap-typeahead.js"></script>



<script src="/eis/inc/js/develop/stdinfo.js"></script>
<script src="/eis/inc/js/develop/timeInfo.js"></script>
<script src="/eis/inc/js/plugin/jquery.tinyMap.min.js"></script>



<link href="/eis/inc/css/jquery-ui.css" rel="stylesheet"/>
<script>
$(document).ready(function() {	
	$(".nameno").typeahead({		
		remote:"#"+this.id,
		source : [],
		items : 10,		
		updateSource:function(inputVal, callback){			
			$.ajax({
				type:"POST",
				url:"/eis/autoCompleteCode1",
				dataType:"json",
				//data:{length:10, nameno:inputVal, name:(this).name},
				data:{nameno:inputVal},
				success:function(d){
					callback(d.list);
				}
			});
		}	
	});
	
	
	
	/*$(".nameno").typeahead({
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
	});*/
	
	
	
});

function cnt(id){
	var sum=85+
	parseInt($("#teacher_score"+id).val())+
	parseInt($("#deptheader_score"+id).val())+
	parseInt($("#military_score"+id).val())+	
	parseInt($("#desd_score"+id).val())+
	parseInt($("#meeting_score"+id).val())-
	parseInt($("#dilg_score"+id).val());
	if(parseInt($("#dilg_score"+id).val())>0){
		sum=sum-3;
	}
	if(sum>95)sum=95;
	
	$("#total"+id).text(Math.round(sum));	
}

function getDilgInfo(no, name, Oid){
	$("#stdNameNo").text(no+" - "+name);
	$.get("/eis/getStdDilg",{student_no:no, Dtime_oid:Oid},
		function(data){
		$("#info").html(data.info);				
	}, "json");	
}

function getStdContectInfo(no, name){
	$("#stdNameNo").text(no+" - "+name);
	$.get("/eis/getStdContectInfo",{student_no:no},
		function(data){
		$("#info").html(data.info);				
	}, "json");	
}

function getStdScoreInfo(no, name){
	$("#stdNameNo").text(no+" - "+name);
	$.get("/eis/getStdScoreInfo",{student_no:no},
		function(data){
		$("#info").html(data.info);				
	}, "json");	
}
</script>
</head>
<body>



<!-- Modal -->
<div class="modal fade" id="codeInfo" tabindex="-1" role="dialog" aria-labelledby="myModalLabel1">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title" id="myModalLabel1">評語參考</h4>
      </div>
      <div class="modal-body">
        <c:forEach items="${codeInfo}" var="c">
		${c.no} ${c.name}<br>
		</c:forEach>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
        
      </div>
    </div>
  </div>
</div>



<form action="ConductCredits" method="post">
				
<div class="bs-callout bs-callout-info">
	<button type="button" class="close" data-dismiss="alert">&times;</button>
	<h4>操行成績加權</h4> 操評會、獎懲、缺曠，以及總成績，學期中均為估計值, 學期末由學務單位定期結算<br>
	<strong>完成後請點選最下方</strong> <button class="btn btn btn-danger btn-xs" name="method:save" type="submit">儲存</button>
	儲存完成不再編輯請按 <a href="ConductCredits" class="btn btn-xs btn-default">返回</a> 
</div>	

<div class="bs-callout bs-callout-warning">
	<h4>評語自動完成</h4>輸入代碼或任意文字均可接受，中文字詞提供評語參考，不限制文字內容。<br>若輸入代碼則必須與代碼表一致，點選儲存後轉換為文字。
</div>	
<div class="panel panel-primary">
<div class="panel-heading">學生列表</div>
<table class="table">
	<tr>
		<td nowrap>學號</td>
		<td nowrap>姓名</td>
		<td nowrap>資訊</td>
		<td nowrap>導師</td>
		<td nowrap>主任</td>
		<td nowrap>教官</td>
		<td nowrap>缺曠</td>							
		<td nowrap>獎懲</td>
		<td nowrap>操評會</td>
		<td nowrap>成績</td>
		<td nowrap>評語一</td>
		<td nowrap>評語二</td>
		<td nowrap>評語三</td>
		<td><input type="hidden" name="rule" value="${rule}" /></td>
	</tr>
	<c:forEach items="${stds}" var="s">
		<tr>
			<td nowrap>
			<input type="hidden" name="student_no" value="${s.student_no}" />${s.student_no}</td>
			<td nowrap>${s.student_name}</td>
			<td>
			<div class="btn-group btn-default">
					<button type="button" class="btn btn-default btn-xs dropdown-toggle" data-toggle="dropdown" aria-haspopup="true"><i class="glyphicon glyphicon-align-justify" style="margin-top: 1px;"></i></button>
					<ul class="dropdown-menu">
						<li><a href="#stdInfo" data-toggle="modal" onClick="getStudentTime('${s.student_no}', '${s.student_name}')">本學期課表</a></li>
						<li><a href="#stdInfo" data-toggle="modal" onClick="getDilgInfo('${s.student_no}', '${s.student_name}', '${Oid}')">本課程缺課記錄</a></li>
						<li><a href="#stdInfo" data-toggle="modal" onClick="getDilgInfo('${s.student_no}', '${s.student_name}', '')">所有課程缺課記錄</a></li>
						<li><a href="#stdInfo" data-toggle="modal" onClick="getStdContectInfo('${s.student_no}', '${s.student_name}')">連絡資訊</a></li>
						<li><a href="#stdInfo" data-toggle="modal" onClick="getStdScoreInfo('${s.student_no}', '${s.student_name}')">歷年成績</a></li>
						<li><a href="/CIS/Portfolio/ListMyStudents.do" target="_blank">學習歷程檔案</a></li>									
					</ul>
				</div>
			</td>
			<td nowrap onClick="cnt('${s.student_no}')">								
				<c:if test="${rule eq 'T'}">		
				<select class="selectpicker" data-width="auto" id="teacher_score${s.student_no}" name="teacher_score">		
					<option <c:if test="${s.teacher_score eq'4'}">selected</c:if> value="4">＋4</option>
					<option <c:if test="${s.teacher_score eq'3'}">selected</c:if> value="3">＋3</option>
					<option <c:if test="${s.teacher_score eq'2'}">selected</c:if> value="2">＋2</option>
					<option <c:if test="${s.teacher_score eq'1'}">selected</c:if> value="1">＋1</option>
					<option <c:if test="${s.teacher_score eq'0'}">selected</c:if> value="0">± 0</option>
					<option <c:if test="${s.teacher_score eq'-1'}">selected</c:if> value="-1">－1</option>
					<option <c:if test="${s.teacher_score eq'-2'}">selected</c:if> value="-2">－2</option>
					<option <c:if test="${s.teacher_score eq'-3'}">selected</c:if> value="-3">－3</option>
					<option <c:if test="${s.teacher_score eq'-4'}">selected</c:if> value="-4">－4</option>
				</select>
				</c:if>
				<c:if test="${rule ne 'T'}"><input type="hidden" id="teacher_score${s.student_no}" name="teacher_score" value="${s.teacher_score}" />${s.teacher_score}</c:if>
			</td>
											
			<td nowrap onClick="cnt('${s.student_no}')">								
				<c:if test="${rule eq 'D'}">				
				<select class="selectpicker" data-width="auto" id="deptheader_score${s.student_no}" name="deptheader_score">
					<option <c:if test="${s.deptheader_score eq'2'}">selected</c:if> value="2">＋2</option>
					<option <c:if test="${s.deptheader_score eq'1'}">selected</c:if> value="1">＋1</option>
					<option <c:if test="${s.deptheader_score eq'0'}">selected</c:if> value="0">± 0</option>
					<option <c:if test="${s.deptheader_score eq'-1'}">selected</c:if> value="-1">－1</option>
					<option <c:if test="${s.deptheader_score eq'-2'}">selected</c:if> value="-2">－2</option>
				</select>
				</c:if>
				<c:if test="${rule ne 'D'}"><input type="hidden" id="deptheader_score${s.student_no}" name="deptheader_score" value="${s.deptheader_score}" />${s.deptheader_score}</c:if>
			</td>
			
			<td nowrap onClick="cnt('${s.student_no}')">
				<c:if test="${rule eq 'M'}">
				<select class="selectpicker" data-width="auto" id="military_score${s.student_no}" name="military_score">
					<option <c:if test="${s.military_score eq'4'}">selected</c:if> value="4">＋4</option>
					<option <c:if test="${s.military_score eq'3'}">selected</c:if> value="3">＋3</option>
					<option <c:if test="${s.military_score eq'2'}">selected</c:if> value="2">＋2</option>
					<option <c:if test="${s.military_score eq'1'}">selected</c:if> value="1">＋1</option>
					<option <c:if test="${s.military_score eq'0'}">selected</c:if> value="0">± 0</option>
					<option <c:if test="${s.military_score eq'-1'}">selected</c:if> value="-1">－1</option>
					<option <c:if test="${s.military_score eq'-2'}">selected</c:if> value="-2">－2</option>
					<option <c:if test="${s.military_score eq'-3'}">selected</c:if> value="-3">－3</option>
					<option <c:if test="${s.military_score eq'-4'}">selected</c:if> value="-4">－4</option>
				</select>
				</c:if>
				<c:if test="${rule ne 'M'}"><input type="hidden" id="military_score${s.student_no}" name="military_score" value="${s.military_score}" />${s.military_score}</c:if>
			</td>
			
			<td nowrap><input type="hidden" id="dilg_score${s.student_no}" value="${s.dilg_score}" />-${s.dilg_score}</td>																
			<td nowrap><input type="hidden" id="desd_score${s.student_no}" value="${s.desd_score}" />${s.desd_score}</td>
			<td nowrap><input type="hidden" id="meeting_score${s.student_no}" value="${s.meeting_score}" />${s.meeting_score}</td>
			<td nowrap id="total${s.student_no}">
			
			<fmt:formatNumber var="total" value="${(85+(s.teacher_score+s.deptheader_score+s.military_score+s.desd_score+s.meeting_score))-s.dilg_score}"   pattern="##"/>
			<c:if test="${s.dilg_score>0}"><c:set var="total" value="${total-3}"/></c:if>
			<c:if test="${total>95}">95</c:if>
			<c:if test="${total<=95}">${total}</c:if>
			</td>
			<td nowrap>
			<div class="input-group">
			<input name="com_code1" id="c1${s.student_no}" type="text" class="form-control nameno" onClick="$(this).val(''), $('#com_code1${s.student_no}').val('')" autocomplete="off" onpaste="return true"
			value="<c:if test="${!empty s.com_code1}">${s.com_code1}</c:if>" data-provide="typeahead" placeholder="評語1" />
			<span class="input-group-btn">
			<button type="button" class="btn btn-default" data-toggle="modal" data-target="#codeInfo">?</button>
			</span>
			</div>
			</td>
			<td nowrap>
			<div class="input-group">
			<input name="com_code2" id="c2${s.student_no}" type="text" class="form-control nameno" onClick="$(this).val(''), $('#com_code2${s.student_no}').val('');" autocomplete="off" onpaste="return true"
			value="<c:if test="${!empty s.com_code2}">${s.com_code2}</c:if>" data-provide="typeahead" placeholder="評語2" />
			<span class="input-group-btn">
			<button type="button" class="btn btn-default" data-toggle="modal" data-target="#codeInfo">?</button>
			</span>
			</div>
			</td>
			
			
			<td nowrap>
			<div class="input-group">
			<input name="com_code3" id="c3${s.student_no}" type="text" class="form-control nameno" onClick="$(this).val(''), $('#com_code3${s.student_no}').val('');" autocomplete="off" onpaste="return true"
			value="<c:if test="${!empty s.com_code3}">${s.com_code3}</c:if>" data-provide="typeahead" placeholder="評語3" />
			<span class="input-group-btn">
			<button type="button" class="btn btn-default" data-toggle="modal" data-target="#codeInfo">?</button>
			</span>
			</div>
			</td>
			<td></td>
		</tr>
	</c:forEach>
</table>
<div class="panel-body">
    <c:if test="${empty view}"><button class="btn btn btn-danger" name="method:save" type="submit">儲存</button></c:if>
	
	<a href="ConductCredits" class="btn btn-default">返回</a>
  </div>
</div>
	
</form>
	
		

<!-- Modal -->
<div class="modal fade" id="stdInfo" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
  <div class="modal-dialog modal-lg" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal"
			aria-hidden="true">×</button>
		<h3 id="stdNameNo"></h3>
      </div>
      <div class="modal-body" id="info"></div>
      <center><div class="modal-body" style="width:80%; height:400px;" id="stdMap"></div></center>
	<div class="modal-footer">
		<button class="btn btn-primary" data-dismiss="modal" aria-hidden="true">關閉</button>
	</div>
    </div>
  </div>
</div>

</body>
</html>