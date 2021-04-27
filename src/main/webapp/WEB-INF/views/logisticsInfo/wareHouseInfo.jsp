<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>창고 관리</title>

<style>
#tabs table {
	font-size: 11px;
}

#tabs .ui-jqgrid .ui-widget-header {
	height: 30px;
	font-size: 1em;
}

.small_Btn {
	width: auto;
	height: auto;
	font-size: 15px;
}

.ui-jqgrid-view {
	font-size: 0.8em;
}
</style>

<script type="text/javascript">
	var gridRowJson;
	
	$(document).ready(function() {

		$("input[type=button], input[type=submit]").button(); // jqueryUI Button 위젯 적용

		$("input[type=radio]").checkboxradio(); // jqueryUI Checkboxradio 위젯 적용

		$("#tabs").tabs({
			//event: "mouseover" ,
			collapsible : false
		
		});
		initGrid();
		initevent();	


		
	
	});

	function initGrid() {
		$('#warehouseListGrid').jqGrid({
			   url:"local",
			   datatype : 'json',
			   datastr : gridRowJson,
			   colNames:["창고코드","창고이름","창고사용여부","설명서"],
			   colModel:[
			    {name:"WAREHOUSE_CODE",width:50, editable:false},
			    {name:"WAREHOUSE_NAME",width:50, editable:true},
			    {name:"WAREHOUSE_USE_OR_NOT",width:50, editable:true},
			    {name:"DESCRIPTION",width:50, editable:true}
			   ], 
			   width:500, 
			   caption:"창고조회",
			   editurl : 'clientArray', 
				cellsubmit : 'clientArray',
			   onSelectRow: function(id){
				   alert(gridRowJson);
			     alert($(this).getRowData(id).id);
			   }
  
	});

	
	}

	

	function showWarehouseGrid() {

	

	}

	function initevent() {

	$('#showWarehouseListButton').on("click",function() {
		$.ajax({
			   type : 'POST',
			   url : '${pageContext.request.contextPath}/logisticsInfo/warehouseInfo.do',
			   data : {
			    method : "getWarehouseList",
			   },
			   async : false,
			   dataType : 'json',
			   cache : false,
			   success : function(dataSet) {
				   
			    console.log(dataSet);
			    
			    var gridRowJson = dataSet.gridRowJson;

				// 그리드 초기화
				$('#warehouseListGrid').jqGrid('clearGridData');		
			
				// 작업지시 시뮬레이션 Data 넣기
				$('#warehouseListGrid')
					.jqGrid('setGridParam',{ datatype : 'local', data : gridRowJson })
					.trigger('reloadGrid'); 
			    
			   	}
			  
			  });// ajax 끝
		
		
	});
}
</script>
</head>

<body>
	<div id="tabs">
			<fieldset style="display: inline;">
				<legend>조회</legend>
				<input type="button" value="창고목록조회" id="showWarehouseListButton">
			</fieldset>

			<fieldset style="display: inline;">
				<legend>등록</legend>
				<input type="button" value="창고등록하기" id="WarehouseRegistration">
			</fieldset>
			<table id="warehouseListGrid"></table>
			<table id="WarehouseGird"></table>
		</div>
		<div id="codeDialog">
			<table id="codeGrid"></table>
		</div>

</body>

</html>