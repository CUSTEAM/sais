<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>	
<div class="accordion" id="myCs">		
	<div class="accordion-group">			
		
		<c:forEach items="${cs}" var="c">		
		<c:catch var ="catchException">	
		<div class="accordion-heading">
			<div class="accordion-inner" data-toggle="collapse" data-parent="#myCs" href="#cs${c.Oid}"> 
			<fmt:formatNumber var="dilg" value="${c.dilg_period+c.elearn_dilg}" pattern="#00"/>
				<fmt:formatNumber var="thour" value="${c.thour*18/3}" pattern="#00"/>
				<fmt:formatNumber var="pro" value="${100-((dilg/thour)*100)}" pattern="#00"/>
			<table width="100%">
				<tr>
					<td nowrap><div class="btn btn-small">?</div></td>
					<td width="80%">
					${c.chi_name} <c:if test="${c.status eq'1'}">(⅓)</c:if>
					</td>	
					
					<td width="20%" nowrap><br>						
					<div class="progress">
						<c:if test="${pro>=90}"><div class="bar bar-success" style="width:${pro}%" nowrap>${c.dilg_period+c.elearn_dilg}/<fmt:formatNumber value="${(c.thour*18)/3}" type="number"/>節</div></c:if>
						<c:if test="${pro>=75&&pro<90}"><div class="bar bar-info" style="width:${pro}%" nowrap>${c.dilg_period+c.elearn_dilg}/<fmt:formatNumber value="${(c.thour*18)/3}" type="number"/>節</div></c:if>
						<c:if test="${pro>=50&&pro<75}"><div class="bar bar-warning" style="width:${pro}%" nowrap>${c.dilg_period+c.elearn_dilg}/<fmt:formatNumber value="${(c.thour*18)/3}" type="number"/>節</div></c:if>
						<c:if test="${pro<50}"><div class="bar bar-danger" style="width:${pro}%" nowrap>${c.dilg_period+c.elearn_dilg}/<fmt:formatNumber value="${(c.thour*18)/3}" type="number"/>節</div></c:if>
				    </div>					    
					</td>
				</tr>
			</table>
			</div>
		</div>
		
		<div id="cs${c.Oid}" class="accordion-body collapse">				
			<div class="accordion-inner">
			<p>
			<c:if test="${c.opt eq '1'}">必修</c:if>
			<c:if test="${c.opt eq '2'}">選修</c:if>
			<c:if test="${c.opt eq '3'}">通識</c:if>
			, ${c.credit}學分 / ${c.thour}時數, ${c.cname}老師</p>
			<c:if test="${empty c.dilgs}">無記錄 :-)<br></c:if>
			<c:forEach items="${c.dilgs}" var="d">
			<c:if test="${d.abs eq '2'}">${d.date} 第 ${d.cls}節, <span class="label label-important">${d.name}</span></c:if>
			<c:if test="${d.abs eq '5'}">${d.date}, 第 ${d.cls}節, <span class="label label-warning">${d.name}</span></c:if>
			<c:if test="${d.abs ne '5'&&d.abs ne '2'}">${d.date}, 第 ${d.cls}節, <span class="label label-success">${d.name}</span></c:if>
			<c:if test="${d.result==null&& d.abs ne 2 && d.abs ne 5}"><span class="label label-warning">審核中</span></c:if>
			<c:if test="${d.result eq '1'}"><span class="label label-info">已核准</span></c:if>
			<c:if test="${d.result eq '2'}"><span class="label label-inverse">請假未核准</span></c:if>
			<br>
			</c:forEach>
			<c:if test="${c.elearn_dilg>0}">遠距課程缺課時數: <span class="label label-important">${c.elearn_dilg}</span></c:if>
			</div>
		</div>			
		</c:catch>
		</c:forEach>
		
		
		<div class="accordion-heading">
			<div class="accordion-inner" data-toggle="collapse" data-parent="#myCsxx" href="#csxx">
			<table>
				<tr>					
					<td><div class="btn btn-small">全部記錄</div></td>	
					</td>
				</tr>
			</table>
			</div>
			<div id="csxx" class="accordion-body collapse">				
			<div class="accordion-inner">
			<c:forEach items="${cs}" var="c">
			<c:forEach items="${c.dilgs}" var="d">
			<c:if test="${d.abs eq '2'}">${d.date}, 第 ${d.cls}節, <span class="label label-important">${d.name}</span></c:if>
			<c:if test="${d.abs eq '5'}">${d.date}, 第 ${d.cls}節, <span class="label label-warning">${d.name}</span></c:if>
			<c:if test="${d.abs ne '5'&&d.abs ne '2'}">${d.date}, 第 ${d.cls}節, <span class="label label-success">${d.name}</span></c:if>
			<c:if test="${d.result==null&& d.abs ne 2 && d.abs ne 5}"><span class="label label-warning">審核中</span></c:if>
			<c:if test="${d.result eq '1'}"><span class="label label-info">已核准</span></c:if>
			<c:if test="${d.result eq '2'}"><span class="label label-inverse">請假未核准</span></c:if>
			<br>
			</c:forEach>
			</c:forEach>
			</div>
		</div>
		
	</div>
</div>	

<div class="accordion" id="dilgHist">
	<div class="accordion-group">
		<div class="accordion-heading">
			<p class="accordion-toggle" data-toggle="collapse" data-parent="#dilgHist" href="#hist"><button class="btn btn-small">檢視修改記錄</button></p>
		</div>
		<div id="hist" class="accordion-body collapse">
			<div class="accordion-inner">
			<table class="table">
				<tr>
					<td>記錄</td>
					<td>修改者</td>
					<td>修改日期</td>
				</tr>
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
	</div>
</div>