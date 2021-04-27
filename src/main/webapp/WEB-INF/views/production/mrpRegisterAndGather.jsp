<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>소요량 전개 / 취합 등록</title>
<style>

#mpsStartDatePicker, #mpsEndDatePicker {
	display: inline;
	width: 115px;
	transition: 0.6s;
	outline: none;
	height: 25px;
	font-size: 16px;
	text-align : center;
	
}

#mrpDateInputPicker, #mrpGatheringDateInputPicker {
	display: inline;
	width: 140px;
	transition: 0.6s;
	outline: none;
	height: 20px;
	font-size: 15px;
	text-align : center;
}

#tabs{
	font-size:15px;
}

#tabs table{
	font-size:11px;
}


#tabs .ui-jqgrid .ui-widget-header {
	height: 25px;
	font-size: 1.1em;
}

.ui-datepicker{
	z-index: 10000 !important;
}

.ui-dialog { 
	z-index: 9999 !important ; 
	font-size:12px;
}


</style>
<script>

var lastSelected_mpsGrid_Id;   // 가장 나중에 선택한 MPS grid 의 행 id 
var lastSelected_mpsGrid_RowValue;   // 가장 나중에 선택한 MPS grid 의 행 값 

var lastSelected_mrpSimulationGrid_Id;   // 가장 나중에 선택한 mrpSimulation grid 의 행 id 
var lastSelected_mrpSimulationGrid_RowValue;   // 가장 나중에 선택한 mrpSimulation grid 의 행 값 

var lastSelected_mrpSearchGrid_Id;   // 가장 나중에 선택한 mrpSearch grid 의 행 id 
var lastSelected_mrpSearchGrid_RowValue;   // 가장 나중에 선택한 mrpSearch grid 의 행 값 

var lastSelected_mrpGatheringGrid_Id;   // 가장 나중에 선택한 mrpGathering grid 의 행 id 
var lastSelected_mrpGatheringGrid_RowValue;   // 가장 나중에 선택한 mrpGathering grid 의 행 값 

var previousCellValue;  // 수정 가능한 셀에서 수정 전의 셀 값 
var resultList = [];  // 최종적으로 컨트롤러로 보내는 JS 객체 배열 

var mpsNoList = [];  // mps (주생산계획) 그리드에서 선택한 주생산계획번호 배열
var mrpNoList = [];  // mrpSearch (소요량취합되지 않은 mrp) 그리드의 모든 mpsNo 를 담을 배열
var mrpNoAndItemCodeList = {};   // ( mrpNo : itemCode ) 키/값을 담을 객체
													   // 나중에 mrpNo 별로 mrpGatheringNo 를 저장할 때 쓰임

$(document).ready(function() { 
	
	$("input[type=button], input[type=submit]").button();   // jqueryUI Button 위젯 적용
	$( "input[type=radio]" ).checkboxradio();   // jqueryUI Checkboxradio 위젯 적용
	
	
	$.datepicker.setDefaults({
		dateFormat : 'yy-mm-dd',
		prevText : '이전 달',
		nextText : '다음 달',
		monthNames : [ '1월', '2월', '3월', '4월', '5월', '6월', '7월',
									'8월', '9월', '10월', '11월', '12월' ],
		monthNamesShort : [ '1월', '2월', '3월', '4월', '5월', '6월',
											'7월', '8월', '9월', '10월', '11월', '12월' ],
		dayNames : [ '일', '월', '화', '수', '목', '금', '토' ],
		dayNamesShort : [ '일', '월', '화', '수', '목', '금', '토' ],
		dayNamesMin : [ '일', '월', '화', '수', '목', '금', '토' ],
		showMonthAfterYear : true,
		yearSuffix : '년'
	}); /*한글로 달력 보이기 위해서 */

	$("#mpsStartDatePicker").datepicker({
		changeMonth : true,
		numberOfMonths : 1,
		onClose : function(selectedDate) {
			$( "#mpsEndDatePicker" ).datepicker( "option", "minDate", selectedDate );
		}
	});		
	
	$("#mpsEndDatePicker").datepicker({
		changeMonth : true,
		numberOfMonths : 1
	});		

	$("#mrpSimulation-dialog").dialog({
		title : '소요량 전개 시뮬레이션',
		autoOpen : false, 
		width : 1260,
		height : 570,
		modal : true,   // 폼 외부 클릭 못하게
		buttons : {  // 버튼 이벤트 적용
			"확인" : function() {
				$("#mrpSimulation-dialog").dialog("close");

			}
		},	
	
		open : function(event, ui) {
					
			$('#mrpDateInputPicker').datepicker({ 
        		changeMonth : true,
				numberOfMonths : 1,
				minDate : new Date()      //처음 날짜는 오늘날짜.
        	});		

			$('#mrpDateInputPicker').hide();
		}
	});
	
	$("#mrpGathering-dialog").dialog({
		title : '소요량 취합 시뮬레이션',
		autoOpen : false, 
		width : 950,
		height : 550,
		modal : true,   // 폼 외부 클릭 못하게
		buttons : {  // 버튼 이벤트 적용
			"확인" : function() {
				$("#mrpGathering-dialog").dialog("close");
			}
		},	
	
		open : function(event, ui) {
					
			$('#mrpGatheringDateInputPicker').datepicker({ 
        		changeMonth : true,
				numberOfMonths : 1,
				minDate : new Date()
        	});		

			$('#mrpGatheringDateInputPicker').hide();
		}	
	});
	
	$( "#tabs" ).tabs({
		//event: "mouseover" ,
	    collapsible: true      //탭에, 접을 수 있는 기능을 넣는다.
	});
	
	initGrid();
	initEvent();
	
});

function initGrid() {

	// mps 그리드 시작
	$('#mpsGrid').jqGrid({ 
		mtype : 'POST', 
		datatype : 'local',
		colNames : [ "선택", "주생산계획번호", "계획구분", "일련번호(수주/판매)", "수주상세일련번호", 
			"판매계획일련번호", "품목코드", "품목명", "단위", "계획일자", "계획수량", "납기일", 
			"예정마감일자", "MRP 적용상태", "비고" ], 
		colModel : [ 
			{ name: "mpsCheck", width: "35", resizable: true, align: "center" ,
				formatter : function (cellvalue, options, rowObj) {
					
					if( rowObj.mrpApplyStatus != 'Y' ) {      //MRP적용상태가 Y가 아니라면,
						var mpsCheck = "<input type='radio' name='mpsCheck'  value=" + 
			     		JSON.stringify(options.rowId) +" />";      //로우아이디를 넣으면서 라디오 선택기를 넣는다.
					     return mpsCheck;
					} else {
						return "";   //Y라면, 라디오를 넣지 않음.
					}
				}
			},
			{ name: "mpsNo", width: "100", resizable: true, align: "center"} ,
			{ name: "mpsPlanClassification", width: "65", resizable: true, align: "center" } ,
			{ name: "no", width: "120", resizable: true, align: "center" ,    //일련번호(수주/판매)
				formatter : function (cellvalue, icol, rowObj) {
					
					var value = "";
					
					if( rowObj.contractDetailNo != null ) {     
					    value = rowObj.contractDetailNo;
					} else {
						value = rowObj.salesPlanNo;
					}
					
					return value;
					
				}
			},
			
			{ name: "contractDetailNo", width: "120", resizable: true, align: "center", hidden: true} ,
			{ name: "salesPlanNo", width: "120", resizable: true, align: "center", hidden: true} ,
			{ name: "itemCode", width: "70", resizable: true, align: "center"} ,
			{ name: "itemName", width: "120", resizable: true, align: "left"} ,
			{ name: "unitOfMps", width: "40", resizable: true, align: "center"} ,
			{ name: "mpsPlanDate", width: "75", resizable: true, align: "center", editable: true,
				formatter: 'date',  
				formatoptions: { srcformat: 'ISO8601Long', newformat: 'Y-m-d' }
			} ,
			{ name: "mpsPlanAmount", width: "65", resizable: true, align: "center"} ,
			{ name: "dueDateOfMps", width: "75", resizable: true, align: "center", editable: true,
				formatter: 'date',   
				formatoptions: { srcformat: 'ISO8601Long', newformat: 'Y-m-d' }
			} ,
			{ name: "scheduledEndDate", width: "90", resizable: true, align: "center",
				formatter: 'date',
				formatoptions: { srcformat: 'ISO8601Long', newformat: 'Y-m-d' }
			} ,
			{ name: "mrpApplyStatus", width: "90", resizable: true, align: "center"} ,
			{ name: "description", width: "80", resizable: true, align: "center" } 

		], 
		caption : '소요량 전개 대기중인 주생산계획 (MPS)', 
		sortname : 'mpsNo', 
		multiselect : false, 
		multiboxonly : false,
		viewrecords : false, 

		rownumWidth : 30, 
		height : 180, 
		width : 1150,
		autowidth : true, 
		shrinkToFit : false, 
		cellEdit : false,
		rowNum : 50,   // -1 : 모든 로우 한번에 표시, 그런데 잘 안먹히는 경우 많음
		scrollerbar: true, 
		//rowList : [ 10, 20, 30 ],
		viewrecords : true, 
		editurl : 'clientArray', 
		cellsubmit : 'clientArray',
		rownumbers : true, 
		autoencode : true, 
		resizable : true,
		loadtext : '로딩중...', 
		emptyrecords : '데이터가 없습니다.', 
		cache : false, 
		pager : '#mpsGridPager',

		onSelectRow: function(rowid) {   
	
			if( lastSelected_mpsGrid_Id != rowid ){
				lastSelected_mpsGrid_Id = rowid;
				lastSelected_mpsGrid_RowValue = $(this).jqGrid('getRowData', rowid); 
			}
		},
		
		onCellSelect : function(rowid, iCol, previousCellValue, e) {

			if( lastSelected_mpsGrid_Id != rowid ){
				lastSelected_mpsGrid_Id = rowid;
				lastSelected_mpsGrid_RowValue = $(this).jqGrid('getRowData', rowid); 
			}
	
		}
	}); // mps 그리드 끝
	
	// mps 그리드의 페이저 생성
	$('#mpsGrid').navGrid("#mpsGridPager", {
		add : false,
		del : false,
		edit : false,
		search : true,
		refresh : true,
		view: true
	});
	
	

	// mrpSimulation 그리드 시작
	$('#mrpSimulationGrid').jqGrid({
		mtype : 'POST', 
		datatype : 'local',
		colNames : [ "주생산계획번호", "BOM 번호", "품목구분", "품목코드", "품목명", 
			"발주/작업지시기한", "발주/작업지시완료기한", "계획수량", "누적손실율", "계산수량", "필요수량", "단위"], 
		colModel : [ 
			{ name: "mpsNo", width: "110", resizable: true, align: "center"} ,
			{ name: "bomNo", width: "100", resizable: true, align: "center" } ,
			{ name: "itemClassification", width: "80", resizable: true, align: "center"} ,
			{ name: "itemCode", width: "80", resizable: true, align: "center"} ,
			{ name: "itemName", width: "130", resizable: true, align: "left"} ,
			{ name: "orderDate", width: "120", resizable: true, align: "center", editable: true,
				formatter: 'date',  
				formatoptions: { srcformat: 'ISO8601Long', newformat: 'Y-m-d' }
			} ,
			{ name: "requiredDate", width: "120", resizable: true, align: "center", editable: true,
				formatter: 'date',  
				formatoptions: { srcformat: 'ISO8601Long', newformat: 'Y-m-d' }
			} ,
			{ name: "planAmount", width: "90", resizable: true, align: "center"} ,
			{ name: "totalLossRate", width: "90", resizable: true, align: "center", 
					formatter : function (cellvalue, icol, rowObj) {

						return parseFloat(cellvalue);
					
				}	
			} ,
			{ name: "caculatedAmount", width: "90", resizable: true, align: "center"} ,
			{ name: "requiredAmount", width: "70", resizable: true, align: "center"} ,
			{ name: "unitOfMrp", width: "50", resizable: true, align: "center"} 
		], 
		caption : '소요량 전개 ( MRP ) 시뮬레이션 결과', 
		//sortname : 'mpsNo',   // 여기를 지정하면 쿼리에서 정렬한 결과와 순서 달라짐
		multiselect : false, 
		multiboxonly : false,
		viewrecords : false, 
		rownumWidth : 30, 
		height : 245, 
		width : 1230,
		autowidth : false, 
		shrinkToFit : false, 
		cellEdit : false,
		rowNum : 50,   // -1 : 모든 로우 한번에 표시, 그런데 잘 안먹히는 경우 많음
		scrollerbar: true, 
		//rowList : [ 10, 20, 30 ],
		viewrecords : true, 
		editurl : 'clientArray', 
		cellsubmit : 'clientArray',
		rownumbers : true, 
		autoencode : true, 
		resizable : true,
		loadtext : '로딩중...', 
		emptyrecords : '데이터가 없습니다.', 
		cache : false, 
		pager : '#mrpSimulationGridPager',

		onSelectRow: function(rowid) {   
	
			if( lastSelected_mrpSimulationGrid_Id != rowid ){
				lastSelected_mrpSimulationGrid_Id = rowid;
				lastSelected_mrpSimulationGrid_RowValue = $(this).jqGrid('getRowData', rowid); 
			}
		},
		
		onCellSelect : function(rowid, iCol, previousCellValue, e) {

			if( lastSelected_mrpSimulationGrid_Id != rowid ){
				lastSelected_mrpSimulationGrid_Id = rowid;
				lastSelected_mrpSimulationGrid_RowValue = $(this).jqGrid('getRowData', rowid); 
			}
	
		}
	}); // mrpSimulation 그리드 끝
	
	// mrpSimulation 그리드의 페이저 생성
	$('#mrpSimulationGrid').navGrid("#mrpSimulationGridPager", {
		add : false,
		del : false,
		edit : false,
		search : true,
		refresh : true,
		view: true
	});
	
	

	// mrpSearchGrid 그리드 시작
	$('#mrpSearchGrid').jqGrid({ 
		mtype : 'POST', 
		datatype : 'local', 
		colNames : [ "소요량전개번호", "주생산계획번호", "품목분류", "품목코드", "품목명", 
			"발주/작업지시 기한", "발주/작업지시완료기한", "필요수량", "단위"], 
		colModel : [
			{ name: "mrpNo", width: "110", resizable: true, align: "center"} ,
			{ name: "mpsNo", width: "110", resizable: true, align: "center" } ,
			{ name: "itemClassification", width: "70", resizable: true, align: "center"} ,
			{ name: "itemCode", width: "80", resizable: true, align: "center"} ,
			{ name: "itemName", width: "130", resizable: true, align: "left"} ,
			{ name: "orderDate", width: "120", resizable: true, align: "center", editable: true,
				formatter: 'date',  
				formatoptions: { srcformat: 'ISO8601Long', newformat: 'Y-m-d' }
			} ,
			{ name: "requiredDate", width: "120", resizable: true, align: "center", editable: true,
				formatter: 'date',  
				formatoptions: { srcformat: 'ISO8601Long', newformat: 'Y-m-d' }
			} ,
			{ name: "requiredAmount", width: "70", resizable: true, align: "center"} ,
			{ name: "unitOfMrp", width: "50", resizable: true, align: "center"} 
			
		], 
		caption : '취합 대기중인 소요량 전개 목록', 
		//sortname : 'mpsNo',   // 여기를 지정해버리면 쿼리에서 이미 정렬한 결과와 순서 달라짐
		multiselect : false, 
		multiboxonly : false,
		viewrecords : false, 
		rownumWidth : 30, 
		height : 200, 
		width : 1400,
		autowidth : false, 
		shrinkToFit : false, 
		cellEdit : false,
		rowNum : 50,  
		scrollerbar: true, 
		//rowList : [ 10, 20, 30 ],
		viewrecords : true, 
		editurl : 'clientArray', 
		cellsubmit : 'clientArray',
		rownumbers : true, 
		autoencode : true, 
		resizable : true,
		loadtext : '로딩중...', 
		emptyrecords : '데이터가 없습니다.', 
		cache : false, 
		pager : '#mrpSearchGridPager',

		onSelectRow: function(rowid) {   
	
			if( lastSelected_mrpSearchGrid_Id != rowid ){
				lastSelected_mrpSearchGrid_Id = rowid;
				lastSelected_mrpSearchGrid_RowValue = $(this).jqGrid('getRowData', rowid); 
			}
		},
		
		onCellSelect : function(rowid, iCol, previousCellValue, e) {

			if( lastSelected_mrpSearchGrid_Id != rowid ){
				lastSelected_mrpSearchGrid_Id = rowid;
				lastSelected_mrpSearchGrid_RowValue = $(this).jqGrid('getRowData', rowid); 
			}
	
		}
	}); // mrpSearch 그리드 끝

	// mrpSearch 그리드의 페이저 생성
	$('#mrpSearchGrid').navGrid("#mrpSearchGridPager", {
		add : false,
		del : false,
		edit : false,
		search : true,
		refresh : true,
		view: true
	});

	// mrpGatheringGrid 그리드 시작
	$('#mrpGatheringGrid').jqGrid({ 
		mtype : 'POST', 
		datatype : 'local', 
		colNames : [ "구매 및 생산여부", "품목코드", "품목명", "단위", "발주/작업지시기한", "발주/작업지시완료기한", "필요수량"], 
		colModel : [
			{ name: "orderOrProductionStatus", width: "110", resizable: true, align: "center"} ,
			{ name: "itemCode", width: "80", resizable: true, align: "center"} ,
			{ name: "itemName", width: "130", resizable: true, align: "left"} ,
			{ name: "unitOfMrpGathering", width: "50", resizable: true, align: "center"} ,
			{ name: "claimDate", width: "120", resizable: true, align: "center", editable: true,
				formatter: 'date',  
				formatoptions: { srcformat: 'ISO8601Long', newformat: 'Y-m-d' }
			} ,
			{ name: "dueDate", width: "120", resizable: true, align: "center", editable: true,
				formatter: 'date',  
				formatoptions: { srcformat: 'ISO8601Long', newformat: 'Y-m-d' }
			} ,
			{ name: "necessaryAmount", width: "70", resizable: true, align: "center"}
			
		],
		caption : '소요량 취합 결과', 
		multiselect : false, 
		multiboxonly : false,
		viewrecords : false, 
		rownumWidth : 30, 
		height : 220, 
		width : 935,
		autowidth : false, 
		shrinkToFit : false, 
		cellEdit : false,
		rowNum : 50,  
		scrollerbar: true, 
		//rowList : [ 10, 20, 30 ],
		viewrecords : true, 
		editurl : 'clientArray', 
		cellsubmit : 'clientArray',
		rownumbers : true, 
		autoencode : true, 
		resizable : true,
		loadtext : '로딩중...', 
		emptyrecords : '데이터가 없습니다.', 
		cache : false, 
		pager : '#mrpGatheringGridPager',

		onSelectRow: function(rowid) {   
	
			if( lastSelected_mrpGatheringGrid_Id != rowid ){
				lastSelected_mrpGatheringGrid_Id = rowid;
				lastSelected_mrpGatheringGrid_RowValue = $(this).jqGrid('getRowData', rowid); 
			}
		},
		
		onCellSelect : function(rowid, iCol, previousCellValue, e) {

			if( lastSelected_mrpGatheringGrid_Id != rowid ){
				lastSelected_mrpGatheringGrid_Id = rowid;
				lastSelected_mrpGatheringGrid_RowValue = $(this).jqGrid('getRowData', rowid); 
			}
	
		}
	}); // mrpGathering 그리드 끝
	
	// mrpGathering 그리드의 페이저 생성
	$('#mrpGatheringGrid').navGrid("#mrpGatheringGridPager", {
		add : false,
		del : false,
		edit : false,
		search : true,
		refresh : true,
		view: true
	});
	
}

function initEvent() {
	
	$('#mpsSearchButton').on( "click", function() {
		
		showMpsGrid();
		
	});
	
	$('#showMrpSimulationButton').on( "click", function() {
		
		showMrpSimulationGrid();
		
	});
	
	$('#todayAsMrpDateRadio, #userInputAsMrpDateRadio').on("click", function() {
		
		var mrpDateCondition = $(':input:radio[name=mrpDateCondition]:checked').val();
		
		if( mrpDateCondition == 'todayAsMrpDate' ) {

			// 오늘 일자 생성
			var now = new Date();
			var today = now.getFullYear() + "-" +('0' + (now.getMonth() +1 )).slice(-2) + "-" + now.getDate();
			
			$('#mrpDateInputPicker').hide();
			$('#mrpDateInputPicker').val(today);
			
			
		} else if ( mrpDateCondition == 'userInputAsMrpDate' ) {
			$('#mrpDateInputPicker').val('소요량 전개 일자');
			$('#mrpDateInputPicker').show();
			
		}
		
	});
	
	
	$('#todayAsMrpGatheringDateRadio, #userInputAsMrpGatheringDateRadio').on("click", function() {
		
		var mrpGatheringDateCondition = $(':input:radio[name=mrpGatheringDateCondition]:checked').val();
		
		if( mrpGatheringDateCondition == 'todayAsMrpGatheringDate' ) {

			// 오늘 일자 생성
			var now = new Date();
			var today = now.getFullYear() + "-" +('0' + (now.getMonth() +1 )).slice(-2) + "-" + now.getDate();
			
			$('#mrpGatheringDateInputPicker').hide();
			$('#mrpGatheringDateInputPicker').val(today);
			
			
		} else if ( mrpGatheringDateCondition == 'userInputAsMrpGatheringDate' ) {
			$('#mrpGatheringDateInputPicker').val('소요량 취합 일자');
			$('#mrpGatheringDateInputPicker').show();
			
		}
		
	});
	
	
	$('#registerMrpButton').on("click", function() {
		
		if( $('#mrpDateInputPicker').val() == '소요량 전개 일자' ) {
			alertError("사용자 에러", "소요량 전개를 등록할 일자를 선택하세요");
			return;
		}
		
		
		// mrpSimulationGrid 의 전체 데이터를 가져옴
		var mrpList = $('#mrpSimulationGrid').getRowData();

		var confirmMsg = "선택한 주생산계획 ( 총 "+ mpsNoList.length +" 건 ) : " + mpsNoList  + "\r\n" + 
			"소요량전개 등록일자 : " + $('#mrpDateInputPicker').val()  + "\r\n" +
			"위 목록에 대한 소요량전개 결과 총 " + mrpList.length + 
			" 건을 저장합니다. \r\n계속하시겠습니까?"
		
		var confirmStatus = confirm(confirmMsg);
		
		if( confirmStatus == true ) {  //confirmStatus는 '확인'을 누르면 true, '취소'를 누르면 false임.
			
			$.ajax({
				type : 'POST',
				url : '${pageContext.request.contextPath}/production/registerMrp.do' ,
				data : {
					method : 'registerMrp',
					batchList : JSON.stringify(mrpList),     //mrp 시뮬레이션 한 결과 String처리되어 모두 다 넘어감.
					mrpRegisterDate : $('#mrpDateInputPicker').val()
				},
				dataType : 'json', 
				cache : false, 
				success : function(dataSet) {
					
					console.log(dataSet);
					
					if(dataSet.errorCode < 0){
						alertError('실패',dataSet.errorMsg);
						return;
					}
					
					var resultMsg = 
						"<  주생산계획 -> MRP 등록 내역 >   <br/><br/>"
						+ ( ( dataSet.result.INSERT.length != 0 ) ? 
								
							"MRP 일련번호 : " + dataSet.result.firstMrpNo +" 부터 " 
							+ dataSet.result.lastMrpNo  +" 까지 " 
							+ dataSet.result.INSERT.length + " 건 등록 완료 </br></br>"
							+ "주생산계획번호 : " + dataSet.result.changeMrpApplyStatus 
							+ " 의 MRP 적용상태  \"Y\" 로 변경 완료"
									
									: "없음" ) + "</br></br>"
									
									
						+ "위와 같이 작업이 처리되었습니다";
																
					showMpsGrid();
					
					$("#mrpSimulation-dialog").dialog("close");
					
					alertError("성공", resultMsg);

				}
			}); // ajax 끝
			
		}
		
	});
	
	
	$('#registerMrpGatheringButton').on("click", function() { 
		
		if( $('#mrpGatheringDateInputPicker').val() == '소요량 취합 일자' ) {
			alertError("사용자 에러", "소요량 취합을 등록할 일자를 선택하세요");
			return;
		}
		

		// mrpGatheringGrid 의 전체 데이터
		var mrpGatheringList = $('#mrpGatheringGrid').getRowData();
		
		var confirmMsg = "소요량전개  : 총 "+ mrpNoList.length +" 건" + "\r\n" + 
			"소요량취합 등록일자 : " + $('#mrpGatheringDateInputPicker').val()  + "\r\n" +
			"위 목록에 대한 소요량취합 결과 총 " + mrpGatheringList.length + 
			" 건을 저장합니다. \r\n계속하시겠습니까?"
		
		var confirmStatus = confirm(confirmMsg);
		
		if( confirmStatus == true ) {
			
			$.ajax({
				type : 'POST',
				url : '${pageContext.request.contextPath}/production/registerMrpGathering.do' ,
				data : {
					method : 'registerMrpGathering',
					batchList : JSON.stringify(mrpGatheringList),
					mrpGatheringRegisterDate : $('#mrpGatheringDateInputPicker').val(),
					mrpNoList : JSON.stringify(mrpNoList),
					mrpNoAndItemCodeList : JSON.stringify(mrpNoAndItemCodeList)
				},
				dataType : 'json', 
				cache : false, 
				success : function(dataSet) {
					
					console.log(dataSet);
					/*  여기까지오면 소요량취합 인설트 완료. 데이터값에 인설트한 취합번호 담겨져있음. */
					if(dataSet.errorCode < 0){
						alertError('실패',dataSet.errorMsg);
						return;
					}
					
					showMrpSearchGrid();
					
					var resultMsg = 
						"<  소요량전개 -> 소요량취합 등록 내역 >   <br/>"
						+ ( ( dataSet.result.INSERT.length != 0 ) ? 
								
							"소요량취합 일련번호 : " + dataSet.result.firstMrpGatheringNo +" 부터 </br>" 
							+ dataSet.result.lastMrpGatheringNo  +" 까지 </br>" 
							+ dataSet.result.INSERT.length + " 건 등록 완료 </br>"
							+ "소요량전개 일련번호 : " + dataSet.result.changeMrpGatheringStatus 
							+ "</br> 의 소요량취합 적용상태  \"Y\" 로 변경 완료"
									
									: "없음" ) + "</br>"
									
									
						+ "위와 같이 작업이 처리되었습니다";
					
					$("#mrpGathering-dialog").dialog("close");
					
					alertError("성공", resultMsg);

				}
			}); // ajax 끝
			
		} else {
			alertError("^^" , "취소되었습니다 ^^");
			
		}

	});

	$('#resetMrpSimulationButton').on("click", function() {
		
		$("input[type=radio][name=mpsCheck]").prop("checked",false);

		//
		
	});
	
	
	$('#showMrpGatheringButton').on("click", function() {
		
		var mrpSearchGridRecordCount = $('#mrpSearchGrid').getGridParam("reccount");
			//대충 소요량 취합 데이터가 그리드 안에 있는가를 알기위해 변수에 담은듯?

		if( mrpSearchGridRecordCount != 0 ) { //취합 데이터가 있다면 0이 아니므로 그리드 뽑고
			
			showMrpGatheringGrid();
			
		} else {							//취합 데이터가 없으면 얼럿에러 출력
			
			alertError("^^", "현재 취합 대기중인 소요량 전개 결과가 없습니다.");
			
		}
		
	});
	
	
	$('#tab2').on("click", function() {

		showMrpSearchGrid()
		
	});
	
}


function showMrpSimulationGrid() {
	
	$('#mrpSimulationGrid').jqGrid('clearGridData'); //그리드 초기화 해주고
	
	//mpsNoList = [];   // mpsNoList 초기화    //지운거
	
	var checkedMpsRowIdList = $('input[type=radio][name=mpsCheck]:checked'); //체크된 라디오 확인
	if(checkedMpsRowIdList.length == 0 ) { //체크된거  없으면
		alertError("사용자 에러","전개할 주생산계획을 선택하지 않았습니다");
		return;
	}
	//console.log 찍어봤을 때, List로 받지 않고, String 값 1개만 받는다.
	//$(checkedMpsRowIdList).each( function() {  // 체크된 주생산계획들에 대해 반복문 시작.  //지운거
		//그래서 .each할 필요도 없고, mpsNoList 배열을 만들 필요도 없다. 따라서 지우겠다.
		//var rowId = $(this).val();   //선택한 row id가 나온다.  //지운거
		var rowId = $(checkedMpsRowIdList).val(); 
		var mpsNo = $('#mpsGrid').getRowData(rowId).mpsNo ;  // 체크한 행의 주생산계획 번호
		//mpsNoList.push(mpsNo);   // 체크한 행의 주생산계획 번호를 mpsNoList 에 저장   //지운거
		
	//});   //지운거
	
	$.ajax({
		type : 'POST',
		url : '${pageContext.request.contextPath}/production/openMrp.do' ,
		data : {
			method : 'openMrp', 
			mpsNoList : "["+mpsNo+"]"  //단지 넘겨줄 때, 배열형식으로 넘겨주기로 뒷단에 약속이 되어있기 때문에 배열형식으로 넘겨줬다.
			//mpsNoList : mpsNo      //지운거
		},
		dataType : 'json', 
		cache : false, 
		success : function(dataSet) {
			console.log(dataSet);
			if(dataSet.errorCode < 0){
				
				alertError('에러',dataSet.errorMsg);
				return;
				
			}

			var gridRowJson = dataSet.gridRowJson;
			console.log(gridRowJson);
			// 소요량 전개 시뮬레이션 Data 넣기
			$('#mrpSimulationGrid')
				.jqGrid('setGridParam',{ datatype : 'local', data : gridRowJson })
				.trigger('reloadGrid');
			
			$("#mrpSimulation-dialog").dialog("open");

		}					
	}); // ajax 끝
		
}

function showMrpSearchGrid() {

	$.ajax({
		type : 'POST',
		url : '${pageContext.request.contextPath}/production/getMrpList.do' ,
		data : {
			method : 'getMrpList',
			mrpGatheringStatusCondition : "null"
		},
		dataType : 'json', 
		cache : false, 
		success : function(dataSet) {
			
			console.log(dataSet);
			
			if(dataSet.errorCode < 0){
				alertError('실패',dataSet.errorMsg);
				return;
			}
			
			var gridRowJson = dataSet.gridRowJson;
			
			$('#mrpSearchGrid').clearGridData();
			
			// 소요량 전개 시뮬레이션 Data 넣기
			$('#mrpSearchGrid')
				.jqGrid('setGridParam',{ datatype : 'local', data : gridRowJson })
				.trigger('reloadGrid');
			
		}					
	}); // ajax 끝
	
	
}

function showMrpGatheringGrid() {

	mrpNoList = [];  // mrpNoList 초기화
	mrpNoAndItemCodeList = {}  // mrpNoAndItemCodeList 초기화
	
	var mrpListWithNotGathering = $('#mrpSearchGrid').getRowData();  // mrpSearchGrid 의 전체 데이터
	
	$( mrpListWithNotGathering ).each( function( index, obj )   {  // 소요량 취합되지 않는 전체 데이터에 대해 반복문 시작

		var mrpNo = obj.mrpNo; 
	alert(mrpNo); //소요량전개번호가 찍힘
	
		mrpNoList.push(mrpNo);   //소요량전개번호를 배열로 담고

		mrpNoAndItemCodeList[obj.mrpNo] = obj.itemCode;  
		//mrpNoAndItemCodeList[소요량전개번호] = 전개번호를 가진 obj객체의 itemcode
		//즉 키(소요량전개번호)에 대한 값으로 품목코드를 넣는다.
	});
		
	$.ajax({
		type : 'POST',
		url : '${pageContext.request.contextPath}/production/getMrpGatheringList.do' ,
		data : {
			method : 'getMrpGatheringList',
			mrpNoList : JSON.stringify(mrpNoList),//소요량전개번호를 전부 담아서 가지고 감
		},
		dataType : 'json', 
		cache : false, 
		success : function(dataSet) {
			
			console.log(dataSet);
			
			if(dataSet.errorCode < 0){
				alertError('실패',dataSet.errorMsg);
				return;
			}
			
			var gridRowJson = dataSet.gridRowJson;
			
			// 소요량 취합 Data 넣기
			$('#mrpGatheringGrid')
				.jqGrid('setGridParam',{ datatype : 'local', data : gridRowJson })
				.trigger('reloadGrid');
			
			$("#mrpGathering-dialog").dialog("open");

			
		}					
	}); // ajax 끝
	

}


function showMpsGrid() {
	
	// ajax 시작
	$.ajax({ 
		type : 'POST',
		url : '${pageContext.request.contextPath}/production/searchMpsInfo.do' ,
		data : {
			method : 'searchMpsInfo', 
			startDate : $("#mpsStartDatePicker").val() , //시작일
			endDate : $("#mpsEndDatePicker").val() , //종료일
			includeMrpApply : $(':input:radio[name=includeMrpApplyCondition]:checked').val() 
								//체크된 라디오 밸류. 포함 = includeMrpApply, 미포함 = excludeMrpApply
		},
		dataType : 'json', 
		cache : false, 
		success : function(dataSet) { 
			console.log(dataSet);
			
			if(dataSet.errorCode < 0){
				alertError('실패',dataSet.errorMsg);
				return;
			}
			
			var gridRowJson = dataSet.gridRowJson;  // gridRowJson : 그리드에 넣을 json 형식의 data
			
			$("#mpsGrid").clearGridData();//그리드 초기화 한번 해주고
			
			// mps Data 넣기
			$('#mpsGrid')
				.jqGrid('setGridParam',{ datatype : 'local', data : gridRowJson })
				.trigger('reloadGrid');
		
	}});  // ajax 끝
	
}

</script>
<title>소요량 전개 / 취합</title>
</head>
<body>

<div id="tabs">
	<ul>
    	<li><a href="#tabs-1">주생산계획으로 소요량전개 시뮬레이션 / MRP 로 등록</a></li>
    	<li><a href="#tabs-2" id="tab2" >취합 대기중인 소요량전개 검색 / 취합</a></li>
  	</ul>
  
	<div id="tabs-1">
		<fieldset style="display: inline; height: 50px">
		    <legend>MRP 적용된 주생산계획 포함</legend>
    			<label for="includeMrpApplyRadio">포함</label>
				<input type="radio" name="includeMrpApplyCondition" value="includeMrpApply" id="includeMrpApplyRadio">
    			<label for="excludeMrpApplyRadio">미포함</label>
    			<input type="radio" name="includeMrpApplyCondition" value="excludeMrpApply" id="excludeMrpApplyRadio">
		</fieldset>

		<fieldset style="display: inline;">
		    <legend>주생산계획 검색 (BY 계획일자)</legend>
			<input type="text" value="시작일" id="mpsStartDatePicker" />
			<input type="text" value="종료일" id="mpsEndDatePicker" />
			<input type="button" value="MPS 조회" id="mpsSearchButton" />
		</fieldset>
	
		<fieldset style="display: inline;">
		    <legend>소요량 전개 ( MRP ) 시뮬레이션</legend>
			<input type="button" value="선택한 MPS 로 모의 전개" id="showMrpSimulationButton" />
  			<input type="button" value="선택 초기화" id="resetMrpSimulationButton" />
	 	</fieldset>
	
		<table id="mpsGrid" ></table>
		<div id="mpsGridPager"></div>
	</div>
	
	<div id="tabs-2">
		<fieldset style="display: inline;">
		    <legend>소요량 취합</legend>
			 <input type="button" value="소요량 취합 결과 조회" id="showMrpGatheringButton" />
	 	</fieldset>
	 	
		<table id="mrpSearchGrid"></table>
		<div id="mrpSearchGridPager"></div>
	</div>
</div>  


<div id = "mrpSimulation-dialog">
	<fieldset style="display: inline;">
		<legend>소요량 전개 ( MRP ) 등록 일자</legend>
    	<label for="todayAsMrpDateRadio">현재일자</label>
    	<input type="radio" name="mrpDateCondition" value="todayAsMrpDate" id="todayAsMrpDateRadio">
    	<label for="userInputAsMrpDateRadio">직접입력</label>
    	<input type="radio" name="mrpDateCondition" value="userInputAsMrpDate" id="userInputAsMrpDateRadio">
		<input type="text" value="소요량 전개 일자" id="mrpDateInputPicker" />
	</fieldset>
	
	<fieldset style="display: inline;">
		<legend>소요량 전개 ( MRP ) 등록</legend>
  		<input type="button" value="현재 전개된 결과를 MRP 에 등록" id="registerMrpButton" />
	</fieldset>
	
	<table id = "mrpSimulationGrid"></table>
	<div id = "mrpSimulationGridPager"></div>
</div>


<div id = "mrpGathering-dialog">
	<fieldset style="display: inline;">
		<legend>소요량 취합 등록 일자</legend>
    	<label for="todayAsMrpGatheringDateRadio">현재일자</label>
    	<input type="radio" name="mrpGatheringDateCondition" value="todayAsMrpGatheringDate" id="todayAsMrpGatheringDateRadio">
    	<label for="userInputAsMrpGatheringDateRadio">직접입력</label>
    	<input type="radio" name="mrpGatheringDateCondition" value="userInputAsMrpGatheringDate" id="userInputAsMrpGatheringDateRadio">
		<input type="text" value="소요량 취합 일자" id="mrpGatheringDateInputPicker" />
	</fieldset>
	
	<fieldset style="display: inline;">
		<legend>소요량 취합 등록</legend>
  		<input type="button" value="현재 취합된 결과를 등록" id="registerMrpGatheringButton" />
	</fieldset>
	
	<table id = "mrpGatheringGrid"></table>
	<div id = "mrpGatheringGridPager"></div>
</div>

</body>
</html>