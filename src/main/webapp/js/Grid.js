
function showCodeDialog(grid, gridName, rowid, iCol, divisionCodeNo, title){
					//this, "estimateGrid", rowid , iCol , "CL-01","거래처 코드 검색
	$("#codeDialog").dialog({
		//autoOpen : false,
		title : '코드 검색',
		width : 500,
		height : 500,
		modal : true   // 폼 외부 클릭 못하게
	});
	
	$.jgrid.gridUnload("#codeGrid");//gridUnload()는 JQgrid 의 내용을 초기화(날려버릴때) 사용
	console.log(gridName);
	console.log(divisionCodeNo);
	console.log(title);
	
	$("#codeGrid").jqGrid({
        url : "/aesup/base/codeList.do",
        datatype : "json",
        jsonReader : { root: "detailCodeList" },
        postData : { 
    		method : "findDetailCodeList" ,
    		divisionCode : divisionCodeNo 
    	},
		colNames : [ '상세코드번호' , '상세코드이름' , '사용여부' ],
		colModel : [
			{ name : 'detailCode', width:100, align : "center",editable:false},
			{ name : 'detailCodeName', width:100, align : "center", editable:false},
			{ name : 'codeUseCheck', width:100, align : "center",editable:false},
		],
		width : 450,
		height : 300,
		caption : title,
		align : "center",
		viewrecords : true,
		rownumbers : true,
		
		onSelectRow : function(id) {
				
			var detailCode=$("#codeGrid").getCell(id, 1);
			var detailName=$("#codeGrid").getCell(id, 2);
			var codeUseCheck=$("#codeGrid").getCell(id, 3);
				
			if(codeUseCheck != 'N' && codeUseCheck != 'n') {
					
				switch(gridName) {
					
					case "estimateGrid" :
							
						if(iCol == 1) {  // 견적에서 사업장 코드 셀 클릭시
							alert("견적 -> 거래처코드 -> 셀선택시, icol : "+iCol+", 그리드명 : "+gridName);
							$(grid).setCell(rowid, iCol, detailName);	
							$(grid).setCell(rowid, 2, detailCode);
						} 
						break;
					
					case "estimateDetailGrid" : 
							
						if(iCol == 1 || iCol == 2) {  // 견적 상세에서 품목코드, 품목명 셀 클릭시
								
							var ids = $(grid).getRowData();
							console.log(ids);
							var errorStatus = false;
								
							$(ids).each(function(index, obj) {
									
								var itemCodeInList = obj.itemCode;
								console.log(itemCodeInList);
								if(detailCode == itemCodeInList) {
									alertError("사용자 에러","견적 상세에 이미 있는 품목입니다");
									errorStatus = true;
									return false;
								} 
							})
								
							if(!errorStatus) {
								$(grid).setCell(rowid, 2, detailCode);					
								$(grid).setCell(rowid, 3, detailName);	
							}

						} else if(iCol == 4) {
							$(grid).setCell(rowid, iCol, detailCode);	
						}
						break;
						
					case "companyCodeGrid" :
						$("#companyCode").val(detailCode);
						$("#dialog-form").dialog("close"); // 폼 닫기
						break;
						
					case "workplaceCodeGrid" :
						$("#workplaceCode").val(detailCode);
						$("#dialog-form").dialog("close"); // 폼 닫기
						break;
						
					case "bomGrid1" :
						$("#itemCodeSearchBox").val(detailCode);
						$("#dialog-form").dialog("close"); // 폼 닫기
						break;
						
					case "bomGrid2" :
						$("#parentItemCodeSearchBox").val(detailCode);
						$("#dialog-form").dialog("close"); // 폼 닫기
						break;
						
					case "itemCodeSearchGrid" : 
						$("#itemCodeSearchBox").val(detailCode);
						$("#dialog-form").dialog("close"); // 폼 닫기
						break;
					
						
					default : 
				}
					
				$("#codeDialog").dialog("close");	

			} else {
				alertError("사용자 에러", "사용 가능한 코드가 아닙니다");		
			}
		}
	});
}