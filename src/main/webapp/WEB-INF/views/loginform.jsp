<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Third Project</title>
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/scripts/jqueryUI/jquery-ui.min.css" />
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/scripts/jqGrid/css/ui.jqgrid.css" />
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/scripts/jqGrid/plugins/ui.multiselect.css" />
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/scripts/jqGrid/css/ui.jqgrid.css" />
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/scripts/jqGrid/plugins/ui.multiselect.css" />
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/loginform_util.css" />
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/loginform_main.css" />
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/loginform_modified.css" />
<script src="${pageContext.request.contextPath}/scripts/jquery/jquery-3.3.1.min.js"></script>
<script src="${pageContext.request.contextPath}/scripts/jqueryUI/jquery-ui.min.js"></script>
<script src="${pageContext.request.contextPath}/scripts/jqGrid/js/jquery.jqGrid.min.js"></script>
<script src="${pageContext.request.contextPath}/scripts/jqGrid/js/i18n/grid.locale-kr.js"></script>

 
<!-- 모듈화를 위해 따로 빼둔 자바스크립트 파일 -->
<script src="${pageContext.request.contextPath}/js/Grid.js?ver=1"></script>


<style>
input[type=text], input[type=password] {
	display: inline;
	width: 350px;
	padding-left: 1%;
	margin-bottom: 10px;
	transition: 0.6s;
	outline: none;
	height: 50px;
}

.center {
	text-align : center;
}

.ui-jqgrid .ui-jqgrid-hdiv {
	font-size: 0.8em;
	height: 33px;
}

.ui-jqgrid .ui-widget-header {
	height: 33px;
	font-size: 0.9em;
}

.ui-jqgrid .ui-jqgrid-bdiv {
	overflow-x: auto;
	overflow-y: scroll;
}

body {
	background-image: url("${pageContext.request.contextPath}/scripts/images/a.jpg");
	background-size: cover;
	background-attachment: scroll;
}

#insertSource {
	margin-top: 12%;
	margin-left: 40%;
}
</style>
<script>

<%session.invalidate();%>
	// session 초기화
	// 여기서는 안쓰지만, 그냥 넣은 변수들
	var lastSelected_CompanyCodeGrid_Id; // 회사코드 grid 에서 마지막 선택한 row 의 id
	var lastSelected_WorkplaceCodeGrid_Id; // 사업장코드 grid 에서 마지막 선택한 row 의 id
	var lastSelected_CompanyCodeGrid_RowValue; // 회사코드 grid 에서 마지막 선택한 row 의 data
	var lastSelected_WorkplaceCodeGrid_RowValue; // 사업장코드 grid 에서 마지막 선택한 row 의 data

	
	$(document).ready(function() {
		
		// 저장된 쿠키값을 가져와서 ID 칸에 넣어준다. 없으면 공백으로 들어감.
		var userInputId = getCookie("userInputId");
		$("input[name='userId']").val(userInputId);

		if ($("input[name='userId']").val() != "") { // 그 전에 ID를 저장해서 처음 페이지 로딩 시, 입력 칸에 저장된 ID가 표시된 상태라면,
				$("#ckb1").attr("checked", true); // ID 저장하기를 체크 상태로 두기.
			}
		
			$("#ckb1").change(function() { // 체크박스에 변화가 있다면,
				if ($("#ckb1").is(":checked")) { // ID 저장하기 체크했을 때,
					var userInputId = $("input[name='userId']").val();
					setCookie("userInputId",userInputId, 7); // 7일 동안 쿠키 보관
				} else { // ID 저장하기 체크 해제 시,
					deleteCookie("userInputId");
				}
			});
				
			// ID 저장하기를 체크한 상태에서 ID를 입력하는 경우, 이럴 때도 쿠키 저장.
			$("input[name='userId']").keyup(function() { // ID 입력 칸에 ID를 입력할 때,
				if ($("#ckb1").is(":checked")) { // ID 저장하기를 체크한 상태라면,
					var userInputId = $("input[name='userId']").val();
					setCookie("userInputId",userInputId, 7); // 7일 동안 쿠키 보관
				}
			});

				// jqueryUI 버튼 위젯 적용
			$("input[type=button], input[type=submit], input[type=reset]").button(); 

				// MemberLogInController 의 LogInCheck 메서드에서 보낸 errorCode 가 음수인 경우, 즉 로그인 실패
				if ("${requestScope.errorCode}" < 0) {
					alertError("로그인 에러","로그인을 실패하였습니다. </br> ${requestScope.errorMsg}");
				} 
				else{
				// $("#error-dialog").attr("style", "display:none");
				}
				// 회사코드, 사업장코드 보여주는 dialog-form 에 jqueryUI dialog 위젯 적용
				$("#dialog-form").dialog({
					title : '코드 검색',
					autoOpen : false, // 자동으로 열리지 않게
					width : 630,
					height : 570,
					modal : true, // 폼 외부 클릭 못하게
					buttons : { // 버튼 이벤트 적용
					"확인" : function() {},
					"취소" : function() {
						$("#dialog-form").dialog("close");
					}
				}
				
			});
		initEvent(); // 이벤트 적용 함수
		//showCompanyCodeGrid(); // 회사 코드 grid 세팅 함수
	});

	
	function setCookie(cookieName, value, exdays) {
		var exdate = new Date();
		exdate.setDate(exdate.getDate() + exdays);
		var cookieValue = escape(value) + ((exdays == null) ? "" : "; expires=" + exdate.toGMTString());
		document.cookie = cookieName + "=" + cookieValue;
	}

	function deleteCookie(cookieName) {
		var expireDate = new Date();
		expireDate.setDate(expireDate.getDate() - 1);
		document.cookie = cookieName + "= " + "; expires=" + expireDate.toGMTString();
	}
	function getCookie(cookieName) {
		cookieName = cookieName + '=';
		var cookieData = document.cookie;
		var start = cookieData.indexOf(cookieName);
		var cookieValue = '';
		if (start != -1) {
			start += cookieName.length;
			var end = cookieData.indexOf(';', start);
			if (end == -1)
				end = cookieData.length;
			cookieValue = cookieData.substring(start, end);
		}
		return unescape(cookieValue);
	}

	// $(document).ready(function(){
	// initEvent();
	// }) 이렇게 되어있다.
	function initEvent() {
		$("#companyCodeSearchButton").on("mouseover" , function(){
			$("#companyCodeSearchButton").css("background","skyblue")
		});
		$("#workplaceCodeSearchButton").on("mouseover" , function(){
			$("#workplaceCodeSearchButton").css("background","skyblue")
		});
		$("#logInButton").on("mouseover" , function(){
			$("#logInButton").css("background","skyblue")
		});
		
		$("#resetButton").on("mouseover" , function(){
			$("#resetButton").css("background","skyblue")
		});
		
		$("#apiButton").on("mouseover" , function(){
			$("#apiButton").css("background","skyblue")
		});
		
	
		
		$("input[type=button], input[type=submit], input[type=reset]").on("mouseout" , function(){
			$("input[type=button], input[type=submit], input[type=reset]").css("background","#D5D5D5")
		});
		
		
		
		
		
		
		


		// 회사코드 검색 버튼 클릭시
		$("#companyCodeSearchButton").on("click", function() {

			//$("#companyCodeGridDiv").show(); // 회사코드 grid 있는 div 보여주고,
			//$("#workplaceCodeGridDiv").hide(); // 사업장 코드 grid 있는 div 숨김
			
			showCodeDialog(this, "companyCodeGrid", null , null , "CO-01", "회사 코드 검색");
			$("#openapiGridDiv").hide(); // 회사코드 grid 있는 div 숨기고,
			$("#codeDialog").dialog("open");
			//$("#dialog-form").dialog("open"); // 코드 검색 창 열기
		});

		// 사업장코드 검색 버튼 클릭시
		$("#workplaceCodeSearchButton").on("click", function() {

			// companyCode 텍스트박스에 입력된 값이 없음 : 사업장 검색 불가능 => 에러 띄우기
			if ($("#companyCode").val() == "") {
				alertError("입력 에러", "회사 코드를 먼저 입력하세요 ~_~");

			} else {

				$("#openapiGridDiv").hide(); // 회사코드 grid 있는 div 숨기고,
				//$("#companyCodeGridDiv").hide(); // 회사코드 grid 있는 div 숨기고,
				$("#codeDialog").dialog("open");
				//$("#workplaceCodeGridDiv").show(); // 사업장 grid 코드 있는 div 보여주기

				// 사업장 코드 grid 보여주기
				//showWorkplaceCodeGrid();
				showCodeDialog(this, "workplaceCodeGrid", null , null , "CO-02", "사업장 코드 검색");
				
			}
		});
		
		//박스오피스
		$("#apiButton").on("click", function() {
			openapiCodeGrid();
			$("#dialog-form").dialog("open"); // 코드 검색 창 열기
		});
		

	}

	// 에러 메시지 폼인 error-dialog 를 전담하여 보여주는 함수
	function alertError(title, message) {

		// error-dialog 보이게 하기
		$("#error-dialog").attr("style", "display:block");

		$("#error-dialog").dialog({ // jqueryUI dialog 위젯 적용
			autoOpen : true, // 자동으로 열리도록
			modal : true, // 외부 클릭 못하게
			title : title, // error-dialog 폼 제목
			width : 'auto',
			height : 'auto',
			position : { // 폼 열릴 때 위치
				my : "center center",
				at : "center-70 center-50" // 폼 열릴 때, 대강 화면 중앙에 오도록
			},
			buttons : { // 버튼 이벤트 적용
				"확인" : function() {
					$("#error-dialog").attr("style", "display:none");
					$("#error-dialog").dialog("close");
				}
			}
		});
		// error-dialog 안의 errorMsg p 태그에 에러 메시지 적용
		$("#error-dialog #errorMsg").html(message);
	}
	
	
		// $(document).ready(function(){
		// showCompanyCodeGrid();	
		// }) 이렇게 되어있다.
	function showCompanyCodeGrid() {
		
			
		/*
		// 회사코드 ajax 시작
		$.ajax({
			type : 'POST',
			url : '${pageContext.request.contextPath}/basicInfo/searchCompany.do',
			data : {
				// MultiActionController : 여기서는 MemberLoginController 의 searchCompanyCode 메서드 호출
				method : 'searchCompanyList',
			},
			dataType : 'json',
			cache : false, //저장을 하지 않겠다
			success : function(dataSet) {
				console.log(dataSet);
				// gridRowJson : 그리드에 넣을 json 형식의 data	
				var gridRowJson = dataSet.gridRowJson; // 회사코드 grid 시작
				//alert(JSON.stringify(gridRowJson[0].companyTelNumber));
				//alert();
				$('#companyCodeGrid').jqGrid({
					mtype : 'POST',
					datatype : 'local',
					colNames : [ "회사코드", " 회사명", " 회사구분", " 사업자번호" ],
					colModel : [ 
						{name : "companyCode", width : "90", resizable : true, align : "center"}, 
						{name : "companyName", width : "200", resizable : true, align : "center"}, 
						{name : "companyDivision", width : "90", resizable : true, align : "center"}, 
						{name : "businessLicenseNumber", width : "120", resizable : true, align : "center"}, 
					],
					caption : '회사코드 검색',
					sortname : 'companyCode',
					multiselect : false,
					multiboxonly : false,
					viewrecords : true,
					rownumWidth : 30,
					height : 300,
					width : 580,
					autowidth : false,
					shrinkToFit : true,
					cellEdit : false,
					rowNum : 10, // -1 : 모든 로우 한번에 표시, 그런데 잘 안먹히는 경우 많음
					scrollerbar : true,
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

					// grid 의 로우 선택시 이벤트 : 여기서는 안쓰지만, 그냥 적용해 봤음
					onSelectRow : function(id) {
						if (lastSelected_CompanyCodeGrid_Id != id) {
							lastSelected_CompanyCodeGrid_Id = id;
							lastSelected_CompanyCodeGrid_RowValue = $('#companyCodeGrid').jqGrid('getRowData', id);
						}
					}
				}); // 회사코드 grid 끝

				// 회사코드 Data 넣기
				$('#companyCodeGrid').jqGrid('setGridParam', {
					datatype : 'local',
					data : gridRowJson
				}).trigger('reloadGrid');

			}
		}); // 회사코드 ajax 끝
		*/
		//showCodeDialog(this, "companyCodeGrid", null , null , "CO-01", "회사 코드 검색");
		
	}

		
		
	/*
	function showWorkplaceCodeGrid() {

		$.jgrid.gridUnload("#workplaceCodeGrid"); // 사업장 코드 grid 완전 초기화

		// 사업장코드 ajax 시작
		$.ajax({
			type : 'POST',
			url : '${pageContext.request.contextPath}/basicInfo/searchWorkplace.do',
			data : {
				companyCode : $("#companyCode").val(), // 주의, 변수의 값을 넘길 때는 "" 나 '' 있으면 안됨!!

				// MultiActionController : 여기서는 MemberLoginController 의 searchWorkplaceCode 메서드 호출
				method : 'searchWorkplaceList'
			},
			dataType : 'json',
			cache : false,
			success : function(dataSet) {
						
				console.log(dataSet);
				var gridRowJson = dataSet.gridRowJson;
					
				// 사업장코드 grid 시작
				$('#workplaceCodeGrid').jqGrid({
					mtype : 'POST',
					datatype : 'local',
					colNames : [ "회사코드", " 사업장코드", " 사업장명", " 사업장번호" ],
					colModel : [ 
						{name : "companyCode", width : "90", resizable : true, align : "center" }, 
						{name : "workplaceCode", width : "110", resizable : true, align : "center"}, 
						{name : "workplaceName", width : "200", resizable : true, align : "center"}, 
						{name : "businessLicenseNumber", width : "110", resizable : true, align : "center"} 
					],
					caption : '사업장코드 검색',
					sortname : 'workplaceCode',
					multiselect : false,
					multiboxonly : false,
					viewrecords : true,
					rownumWidth : 30,
					height : 300,
					width : 580,
					autowidth : false,
					shrinkToFit : true,
					cellEdit : false, // true가되어야 수정가능하다
					rowNum : 10,
					scrollerbar : true,
					// rowList : [ 10, 20, 30 ],
					viewrecords : true,
					editurl : 'clientArray',
					cellsubmit : 'clientArray',
					rownumbers : true,
					autoencode : true,
					resizable : true,
					loadtext : '로딩중...',
					emptyrecords : '데이터가 없습니다.',
					cache : false,
					pager : '#workplaceCodeGridPager', // pager 적용시에는 반드시 이 옵션 있어야 함
					onSelectRow : function(id) {
						if (lastSelected_WorkplaceCodeGrid_Id != id) {
							lastSelected_WorkplaceCodeGrid_Id = id;
							lastSelected_WorkplaceCodeGrid_RowValue = $('#workplaceCodeGrid').jqGrid('getRowData',id);
						}
					}
				}); // 사업장코드 grid 끝
	
				// 사업장코드 pager 설정
				$('#workplaceCodeGrid').navGrid(
					'#workplaceCodeGridPager', {
						add : false,
						del : false,
						edit : false,
						search : true,
						refresh : true,
						view : true
				});
	
				// 사업장코드 Data 넣기
				$('#workplaceCodeGrid').jqGrid('setGridParam', {
					datatype : 'local',
					data : gridRowJson
				}).trigger('reloadGrid');

			}
		}); // 사업장코드 ajax 끝

	}
	*/
	
	
	
	//박스오피스
	function openapiCodeGrid() {
		
		$.jgrid.gridUnload("#openapiCodeGrid"); // 공공api 코드 grid 완전 초기화

		// 공공api ajax시작
		$.ajax({
			type : 'POST',
			url : '${pageContext.request.contextPath}/base/openapi.do',
			dataType : 'json',
			cache : false, //저장을 하지 않겠다
			success : function(obj) {
				console.log(obj);
				let dataSet=JSON.parse(obj.gridRowJson);
				let gridRowJson = dataSet.boxOfficeResult.dailyBoxOfficeList;			
	 
				// 공공api grid 시작
				$('#openapiCodeGrid').jqGrid({
					mtype : 'POST',
					datatype : 'local',
					colNames : [  "박스오피스순위", "영화명", "개봉일", "누적관객수" , "매출액" ],
					colModel : [ 
						{name : "rank", width : "50", resizable : true, align : "center"}, 
						{name : "movieNm", width : "200", resizable : true, align : "center"}, 
						{name : "openDt", width : "180", resizable : true, align : "center"}, 
						{name : "audiAcc", width : "110", resizable : true, align : "center"},
						{name : "salesAmt", width : "140", resizable : true, align : "center"}
					],
					caption : 'BoxOffice 순위',
					sortname : 'districtName',
					multiselect : false,
					multiboxonly : false,
					viewrecords : true,
					rownumWidth : 50,
					height : 450,
					width : 580,
					autowidth : true,
					shrinkToFit : true,
					cellEdit : false, // true가되어야 수정가능하다
					rowNum : 10,
					scrollerbar : true,
					// rowList : [ 10, 20, 30 ],
					viewrecords : true,
					editurl : 'clientArray',
					cellsubmit : 'clientArray',
					rownumbers : true,
					autoencode : true,
					resizable : true,
					loadtext : '로딩중...',
					emptyrecords : '데이터가 없습니다.',
					cache : false,
					pager : '#openapiGridPager', // pager 적용시에는 반드시 이 옵션 있어야 함
				}); // 공공api grid 끝
		
				// 공공api pager 설정
				$('#openapiCodeGrid').navGrid(
					'#openapiCodeGridPager', {
						add : false,
						del : false,
						edit : false,
						search : true,
						refresh : true,
						view : true
				});
			
				// 공공api Data 넣기
				$('#openapiCodeGrid').jqGrid('setGridParam', {
					datatype : 'local',
					data : gridRowJson,
					sortname : 'rank',
					sortorder : 'asc'
				}).trigger('reloadGrid');
			
			}
		}); // 공공 ajax 끝

	}

</script>
</head>
<body>

	<div id="insertSource">
		<h1 style="margin-left: 115px">
			<font color="white">Third Project</font>
		</h1>
		<br>
		<form method="post" action="${pageContext.request.contextPath}/login.do?method=LogInCheck">
			
			<h3><input type="text" placeholder="회사코드" class="center" name="companyCode" id="companyCode"> 
			&nbsp; 
			<input type="button" value="회사코드검색" id="companyCodeSearchButton" class="ev"><br></h3>
			
			<h3><input type="text" placeholder="사업장코드" class="center" name="workplaceCode" id="workplaceCode"> 
			&nbsp; 
			<input type="button" value="사업장코드검색" id="workplaceCodeSearchButton" class="ev"><br></h3>
			
			<input type="text" placeholder="사원ID" class="center" name="userId"><br>
			<input type="password" placeholder="비밀번호" class="center" name="userPassWord"><br>
			<h3>
			<input type="submit" value="로그인" id="logInButton" class="ev" style="margin-left: 15px">
			<input type="reset" value="초기화" id="resetButton" class="ev">
			<input id="ckb1" type="checkbox" name="rmb"> 
			<label for="ckb1"><font color="white">ID 기억하기</font></label>
			<br/><br/>
			<input type="button" value="박스오피스 정보 - 어제자 영화" id="apiButton" class="ev"></h3>
		
		
		
		</form>
	</div>
	<!-- 코드 grid 보여주는 div -->
	<div id="dialog-form">

		<div id="companyCodeGridDiv">
			<table id="companyCodeGrid"></table>
			<!-- 회사 코드  보여주는 grid 적용-->
		</div>

		<div id="workplaceCodeGridDiv">
			<table id="workplaceCodeGrid"></table>
			<!-- 사업장 코드  보여주는 grid 적용-->
			<div id="workplaceCodeGridPager"></div>
		</div>

		<div id="codeDialog">
			<table id="codeGrid"></table>
		</div>



		<div id="openapiGridDiv">
			<table id="openapiCodeGrid"></table>
			<!-- 공공  보여주는 grid 적용-->
			<div id="openapiGridPager"></div>
		</div>

	</div>
	
	

	<!-- 에러 메시지 보여주는 div -->
	<div id="error-dialog" style="display: none;">
		<p id="errorMsg" style="font-size: 1.1em; color: black"></p>
	</div>
</body>
</html>