<!DOCTYPE html>
<%@ page language="java" pageEncoding="utf-8"%>
<%@ include file="/platform/common/jsp/taglibs.jsp" %>
<html>
	<head>  
		<script type="text/javascript">
			Namespace.register("com.yunt.order.index");
			com.yunt.order.index = {
				loadContent : function(href) {
					$("body").mask("数据正在加载中，请稍后···");
					$("#content").load(href,function(){
						$("body").unmask();
					});
				},
				input : function() {
					$.ajax({
					 	type: "POST",
					 	url: "${path}/order/saveOrUpdate?inputKind=save",
					  	processData:true,
					  	success: function(data){
					  		com.yunt.order.index.refresh();
					  	}
					 });
				},
				doExport : function() {
				 	window.location.href = "${path}/order/doExport?model.code="+$("#${pageId}code").val()+"&model.kind="+$("#${pageId}kind").val()+"&model.startDate="+ $("#${pageId}startDate").val()+"&model.endDate="+$("#${pageId}endDate").val()+"&model.state="+$("#${pageId}state").val();
				},
				refresh : function () {
					com.yunt.order.index.loadContent("${path}/order/list?model.code="+$("#${pageId}code").val()+"&model.kind="+$("#${pageId}kind").val()+"&model.startDate="+ $("#${pageId}startDate").val()+"&model.endDate="+$("#${pageId}endDate").val()+"&model.state="+$("#${pageId}state").val());
				}
			};
			$(document).ready(function(){
				com.yunt.order.index.refresh();
				var height=$(window).height()-160;
				$(".details").css("height",height);
			});
		</script>
	</head>
	<body>
		<div class="cont">
			<div class="title">订单管理</div>
			<div class="details">
				<div class="details_operation clearfix">
				    <%-- <div class="bui_select">
				    	<img src="${path}/platform/theme/distributionSystem/images/coin.png">
						<input type="button" value="自动添加" class="add" onclick="com.yunt.order.index.input();">
					</div> --%>
					<div class="bui_select ml10">
						<input type="button" value="导出excel" class="add" onclick="com.yunt.order.index.doExport();">
					</div>
					<div class="fr">
	                	<form method="" action="" class="box">
							<label style="line-height:30px;padding-left:10px;">订单号:</label>
	                    	<input id="${pageId}code" class="search" type="text" placeholder="订单号" />
	                    	<label style="line-height:30px;padding-left:10px;">类型:</label>
	                    	<uc:select id="${pageId}kind" list="{1:'首单消费',2:'返单消费'}" name="kind" cssClass="search" headerKey="" headerValue="请选择" />
							<label style="line-height:30px;">状态:</label>
	               			<uc:select id="${pageId}state" list="{0:'未支付',1:'未发货',2:'已发货',3:'已完成'}" name="state" cssClass="search" headerKey="" headerValue="请选择" />
							<label style="line-height:30px;">开始日期:</label>
							<input id="${pageId}startDate" class="Wdate search" type="text" placeholder="开始时间" onClick="WdatePicker()"/>
							<label style="line-height:30px;">结束日期:</label>
							<input id="${pageId}endDate" class="Wdate search" type="text" placeholder="结束时间" onClick="WdatePicker()"/>
	               			<input type="button" value="搜索" class="searchCoin" onclick="com.yunt.order.index.refresh();">
						</form>
					</div>
				</div>
				<div id="content"  />
		    </div>
		</div>
	</body>
</html>
