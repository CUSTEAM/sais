<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script src="/eis/inc/js/plugin/bootstrap-typeahead.js"></script>
<script src="/eis/inc/js/plugin/jquery-ui.js"></script>
<script src="/eis/inc/js/plugin/bootstrap-tooltip.js"></script>
<script type="text/javascript" src="/eis/inc/js/plugin/jquery-ui-timepicker-addon.js"></script>
<link rel="stylesheet" href="/eis/inc/css/jquery-ui.css" />
<title>假單管理</title>
<script src="/eis/inc/js/develop/stdinfo.js"></script>
<script src="/eis/inc/js/develop/timeInfo.js"></script>
<script src="/eis/inc/js/plugin/jquery.tinyMap.min.js"></script>
<script>  
$(document).ready(function() {
	$("input[id='nameno']").typeahead({
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
	
	$('#funbtn').popover("show");
	setTimeout(function() {
		$('#funbtn').popover("hide");
	}, 8000);
});

function showApp(filename){
    $.blockUI({ 
    	onOverlayClick: $.unblockUI,
        message: "<a href='/eis/getFtpFile?path=DilgImage&file="+filename+"'><img width=480 src='/eis/getFtpFile?path=DilgImage&file="+
        filename+"'/></a><br><br><a href='/eis/getFtpFile?path=DilgImage&file="+filename+"' class='btn btn-default'>點選查看原始檔案</a>", 
        css: { 
            top:  ($(window).height() - 480) /2 + 'px', 
            left: ($(window).width() - 480) /2 + 'px', 
            width: '510px',
            border: 'none', 
            padding: '15px', 
            backgroundColor: '#000', 
            '-webkit-border-radius': '10px', 
            '-moz-border-radius': '10px', 
            
            color: '#fff' 
        }
    });
}
</script>  
</head>
<body>
<form action="DilgAppManager" method="post" class="form-inline">
<div class="bs-callout bs-callout-info" id="callout-helper-pull-navbar">		
	<div class="btn-group" role="group" aria-label="...">
	<a href="DilgApp" class="btn btn-danger">未審核假單</a>
	<a href="DilgApp?type=pass" class="btn btn-default">已審核假單</a>
	<a href="DilgApp?type=redirect" class="btn btn-default">已轉呈假單</a>
	</div>
	<a href="/eis/Calendar"class="btn btn-default">返回</a>
	<div id="funbtn" rel="popover" title="說明" data-content="已處理假單過多影響列表效能時，可利用查詢欄位尋找假單" data-placement="right" class="btn btn-warning">?</div>
</div>	
<div class="alert alert-error">
<table class="table">
	<tr>
		<td>
		<div class="input-group">
		    <span class="input-group-addon">查詢假單範圍自</span>
		    <input class="form-control" type="text" id="begin" onClick="this.value='';" placeholder="查詢開始範圍" name="begin" value="${begin}"/>
	  	</div>			
		<div class="input-group">
		    <span class="input-group-addon">至</span>
		    <input class="form-control" type="text" id="end" onClick="this.value='';" placeholder="查詢結束範圍" name="end" value="${end}"/>
	  	</div>			
		</td>			
	</tr>
	<tr>
		<td>
		<div class="input-group">
		    <span class="input-group-addon">依學號或姓名查詢假單</span>
		    <input class="form-control" onClick="$('#nameno').val(''), $('#stdNo').val('');" autocomplete="off" type="text" id="nameno" value="${nameno}" name="nameno"
			 data-provide="typeahead" onClick="addStd()" placeholder="學號姓名文字片段" />
			
	  	</div>
		<input type="hidden" id="stdNo" value="${stdNo}" name="stdNo"/>				
		<button class="btn btn-danger" name="method:search" type="submit">尋找已審核假單</button>			
		</td>
	</tr>
</table>
</div>
	
	
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

<c:if test="${!empty dilgs}">
<div class="panel panel-primary">
	<div class="panel-heading">假單列表</div>	
	<div id="collapseH" class="panel-collapse collapse" role="tabpaneH" aria-labelledby="headingH">
			
			<ul class="list-group">
			<li class="list-group-item"><span class="label label-as-badge label-warning">1</span> 跨日/夜間修課請點選日/夜間課表查詢, 或直接以課程名稱查詢</li>
			<li class="list-group-item"><span class="label label-as-badge label-warning">2</span> 管制加/退選的規則是由各系或各部制權責單位設定, 程式按照其設定提供同學進行選課</li>
			<li class="list-group-item"><span class="label label-as-badge label-danger">3</span> 選課後的篩選工作由各部制權責單位決定是否進行, 若該單位決定採用第1階段選課, 表示該單位將執行人數過多的課程篩選</li>
			</ul>
			
	    </div>
    <display:table pagesize="10" name="${dilgs}" id="row" class="table" sort="list" excludedParams="*" >
  	<display:column title="學生資訊"  sortable="true">
  	${row.ClassName}<br>${row.student_no}<br>${row.student_name}<br>
  	
    <div class="btn-group btn-default">
		<button type="button" class="btn btn-default btn-xs dropdown-toggle" data-toggle="dropdown" aria-haspopup="true"><i class="glyphicon glyphicon-align-justify" style="margin-top: 1px;"></i> 顯示細節</button>
		<ul class="dropdown-menu">
			<li><a href="#stdInfo" data-toggle="modal" onClick="getStudentTime('${row.student_no}', '${row.student_name}')">本學期課表</a></li>
			<li><a href="#stdInfo" data-toggle="modal" onClick="getDilgInfo('${row.student_no}', '${row.student_name}', '${Oid}')">本課程缺課記錄</a></li>
			<li><a href="#stdInfo" data-toggle="modal" onClick="getDilgInfo('${row.student_no}', '${row.student_name}', '')">所有課程缺課記錄</a></li>
			<li><a href="#stdInfo" data-toggle="modal" onClick="getStdContectInfo('${row.student_no}', '${row.student_name}')">連絡資訊</a></li>
			<li><a href="#stdInfo" data-toggle="modal" onClick="getStdScoreInfo('${row.student_no}', '${row.student_name}')">歷年成績</a></li>
			<li><a href="/CIS/Portfolio/ListMyStudents.do" target="_blank">學習歷程檔案</a></li>									
		</ul>
	</div>    
    
  	
  	</display:column>
  	<display:column title="假別" property="name" sortable="true" />
	<display:column title="細節">
		<display:table name="${row.abss}" class="table table-striped table-condensed">
			<display:column property="chi_name" style="width:150px;" title="課程名稱" />
			<display:column property="date" style="width:100px;" title="日期" />
			<display:column property="cls" style="text-align:right; width:80px;" title="節次"/>
		</display:table>
	</display:column>
	<display:column title=" 附件">
	<c:if test="${!empty row.file}">
	<button type="button" class="btn btn-primary" onClick="showApp('${row.file}')">附件</button>
	
	</c:if>
	
	</display:column>
  	<display:column title="審核">
  	<p>原因: ${row.reason}, ${row.note}</p>
  	<input type="hidden" value="${row.Oid}" name="Oid"/>
	<select class="form-control" <c:if test="${param.type eq 'redirect'}">disabled</c:if> name="result" style="float:left; margin-right:5px;">
			<option value="">是否核准</option>
			<option <c:if test="${row.result eq'1'}">selected</c:if> value="1">核准</option>
			<option <c:if test="${row.result eq'2'}">selected</c:if> value="2">不核准</option>
		</select>
  	<div class="input-group"> 		
    	<input class="form-control" name="reply" value="${row.reply}" type="text">
    	<span class="input-group-btn">    
	    <c:if test="${param.type eq 'redirect'}">
	    	<button disabled class="btn btn btn-info">已轉呈:${row.cname}</button>
	    </c:if>    
	    <c:if test="${param.type eq 'pass'}">
	    	<button name="method:checkout" type="submit" class="btn btn-primary">修改審核</button>
	    </c:if>
    
	    <c:if test="${param.type==null}">
	    	<button name="method:checkout" type="submit" class="btn btn-primary">評語並送出審核</button>
	    </c:if>
	    </span>
    </div>
  	</display:column>
</display:table>
</div>
</c:if>
</form>
<script>
$("input[name='begin'], input[name='end']" ).datepicker({
	changeMonth: true,
	changeYear: true
});
</script>
</body>
</html>