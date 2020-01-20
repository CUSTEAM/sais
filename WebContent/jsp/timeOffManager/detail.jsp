<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>	
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<div class="panel-group" id="accordion" role="tablist" aria-multiselectable="true">
	
	<c:forEach items="${cs}" var="c">	
	<c:catch var ="catchException">	
	<fmt:formatNumber var="dilg" value="${c.dilg_period+c.elearn_dilg}" pattern="#0"/>
	<fmt:formatNumber var="thour" value="${c.thour*18/3}" pattern="#0"/>
	<fmt:formatNumber var="pro" value="${100-((dilg/thour)*100)}" pattern="#0"/>
	<div class="panel panel-primary">
    	<div class="panel-heading" role="tab" id="heading${c.Oid}">
	      	<table width="100%"><tr><td width="100%"nowrap>
	      		<h4 class="panel-title">
		        <a role="button" data-toggle="collapse" 
		        data-parent="#accordion" href="#collapse${c.Oid}" 
		        aria-expanded="true" aria-controls="collapse${c.Oid}">		        
				<span class="label label-danger">${dilg}/${thour}</span> ${c.chi_name}<c:if test="${c.status eq'1'}">(⅓)</c:if> 
		        </a>
		        
	      		</h4>
		        </td>
		        <td nowrap>
				<h4 class="panel-title">
				<a role="button" data-toggle="collapse" 
		        data-parent="#accordion" href="#collapse${c.Oid}" 
		        aria-expanded="true" aria-controls="collapse${c.Oid}">		        
				<span class="btn btn-default btn-xs">查看${dilg}節缺曠內容</span>
				
				
		        </a>
		        </h4>
		        </td>
		        </tr></table>		       
    		</div>
    		<div class="panel-body">
    		<p>
	        <c:if test="${c.opt eq '1'}">必修</c:if>
			<c:if test="${c.opt eq '2'}">選修</c:if>
			<c:if test="${c.opt eq '3'}">通識</c:if>
			, ${c.credit}學分, ${c.thour}時數, ${c.cname}老師
	        </p>
    		
    		</div>
	    	<div id="collapse${c.Oid}" class="panel-collapse collapse" role="tabpanel" aria-labelledby="heading${c.Oid}">
	    	<div class="panel-body">	        
	        
			<c:if test="${empty c.dilgs}">無記錄 :-)<br></c:if>
			<table class="table">
			<c:forEach items="${c.dilgs}" var="d">
			<tr><td>
			<c:if test="${d.abs eq '2'}">${d.date} 第 ${d.cls}節, <span class="label label-important">${d.name}</span></c:if>
			<c:if test="${d.abs eq '5'}">${d.date}, 第 ${d.cls}節, <span class="label label-warning">${d.name}</span></c:if>
			<c:if test="${d.abs ne '5'&&d.abs ne '2'}">${d.date}, 第 ${d.cls}節, <span class="label label-success">${d.name}</span></c:if>
			<c:if test="${d.result==null&& d.abs ne 2 && d.abs ne 5}"><span class="label label-warning">審核中</span></c:if>
			<c:if test="${d.result eq '1'}"><span class="label label-info">已核准</span></c:if>
			<c:if test="${d.result eq '2'}"><span class="label label-inverse">請假未核准</span></c:if>
			</td></tr>			
			</c:forEach>
			</table>
			<c:if test="${c.elearn_dilg>0}">遠距課程缺課時數: ${c.elearn_dilg}</c:if>
	        
	      	</div>
	    </div>
  	</div>
  	</c:catch>
  	</c:forEach>
  
</div>

<div class="collapse" id="collapseExample">
<div class="panel panel-primary">
<div class="panel-heading" >修改記錄</div>
<table class="table">
	<tr>
		<td>記錄</td>
		<td>修改者</td>
		<td>修改日期</td>
	</tr>
	<c:if test="${empty dilgHist}"><tr><td colspan="3">無修改記錄</td></tr></c:if>
	<c:forEach items="${dilgHist}" var="d">				
	<tr>
		<td nowrap>${d.comment}</td>
		<td nowrap>${d.cname}</td>
		<td nowrap>${fn:substring(d.exdate,5,16)}</td>
	</tr>
	</c:forEach>
</table>
</div>
</div>
<button class="btn btn-primary" type="button" data-toggle="collapse" data-target="#collapseExample" aria-expanded="false" aria-controls="collapseExample">檢視修改記錄</button>



