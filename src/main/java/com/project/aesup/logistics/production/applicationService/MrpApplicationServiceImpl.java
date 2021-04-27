package com.project.aesup.logistics.production.applicationService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.TreeSet;

import com.project.aesup.logistics.production.dao.MpsDAO;
import com.project.aesup.logistics.production.dao.MrpDAO;
import com.project.aesup.logistics.production.dao.MrpGatheringDAO;
import com.project.aesup.logistics.production.to.MrpGatheringTO;
import com.project.aesup.logistics.production.to.MrpTO;

public class MrpApplicationServiceImpl implements MrpApplicationService {

	// DAO 참조변수 선언
	private MpsDAO mpsDAO;
	private MrpDAO mrpDAO;
	private MrpGatheringDAO mrpGatheringDAO;
	
	public void setMpsDAO (MpsDAO mpsDAO) {
		this.mpsDAO = mpsDAO;
	}
	public void setMrpDAO (MrpDAO mrpDAO) {
		this.mrpDAO = mrpDAO;
	}
	public void setMrpGatheringDAO (MrpGatheringDAO mrpGatheringDAO) {
		this.mrpGatheringDAO = mrpGatheringDAO;
	}

	public ArrayList<MrpTO> searchMrpList(String mrpGatheringStatusCondition) {

		return mrpDAO.selectMrpList(mrpGatheringStatusCondition);
	}

	public ArrayList<MrpTO> searchMrpList(String dateSearchCondtion, String startDate, String endDate) {

		return mrpDAO.selectMrpList(dateSearchCondtion, startDate, endDate);

	}

	public ArrayList<MrpTO> searchMrpListAsMrpGatheringNo(String mrpGatheringNo) {

		return mrpDAO.selectMrpListAsMrpGatheringNo(mrpGatheringNo);

	}

	public ArrayList<MrpGatheringTO> searchMrpGatheringList(String dateSearchCondtion, String startDate,
			String endDate) {

		ArrayList<MrpGatheringTO> mrpGatheringList = null;

		mrpGatheringList = mrpGatheringDAO.selectMrpGatheringList(dateSearchCondtion, startDate, endDate);

		for(MrpGatheringTO bean : mrpGatheringList) 	{
			
			bean.setMrpTOList(  mrpDAO.selectMrpListAsMrpGatheringNo( bean.getMrpGatheringNo()) );
		
		}

		return mrpGatheringList;

	}

	public HashMap<String, Object> openMrp(ArrayList<String> mpsNoArr) {

		HashMap<String, Object> resultMap = new HashMap<>();

		String mpsNoList = mpsNoArr.toString().replace("[", "").replace("]", "");   //PS2019101702 주생산계획번호에, [PS2019101702] 괄호 씌워줌 배열형식으로.
			System.out.println("mpsNoList 값확인 : "+mpsNoList);
		resultMap = mrpDAO.openMrp(mpsNoList);

		return resultMap;

	}

	public HashMap<String, Object> registerMrp(String mrpRegisterDate, ArrayList<MrpTO> newMrpList) {

		HashMap<String, Object> resultMap = null;

		int i = mrpDAO.selectMrpCount(mrpRegisterDate);
        //
		// 새로운 MRP 일련번호 양식 생성 : 등록일자 '2018-01-01' => 일련번호 'RP20180101-'
		StringBuffer newMrpNo = new StringBuffer();
		newMrpNo.append("RP");
		newMrpNo.append(mrpRegisterDate.replace("-", ""));
		newMrpNo.append("-");

		// 주생산계획번호를 담을 HashSet : 중복된 번호도 하나만 입력됨
		HashSet<String> mpsNoList = new HashSet<>();

		for (MrpTO bean : newMrpList) {

			bean.setMrpNo(newMrpNo.toString() + String.format("%03d", i++));  //List에 새로운 일련번호를 여기서 세팅한다.
			//3자리로 일련번호 표현하고싶을때 사용.
			bean.setStatus("INSERT");
			mpsNoList.add(bean.getMpsNo());  //mps넘버를 얻어서 List에 추가시킴.

		}

		// 새로운 MRP 빈을 batchListProcess 로 테이블에 저장
		resultMap = batchMrpListProcess(newMrpList);

		// 생성된 MRP 일련번호를 저장할 TreeSet
		TreeSet<String> mrpNoSet = new TreeSet<>();

		// "INSERT" 목록에 새로 생성된 MRP 일련번호들이 있음, ArrayList 로 형변환
		@SuppressWarnings("unchecked")
		ArrayList<String> mrpNoArr = (ArrayList<String>) resultMap.get("INSERT");

		for (String mrpNo : mrpNoArr) {
			mrpNoSet.add(mrpNo); // ArrayList 의 MRP 일련번호들을 TreeSet 에 저장

		}

		resultMap.put("firstMrpNo", mrpNoSet.pollFirst()); // 최초 MRP 일련번호를 결과 Map 에 저장
		resultMap.put("lastMrpNo", mrpNoSet.pollLast()); // 마지막 MRP 일련번호 결과 Map 에 저장

		// MPS 테이블에서 해당 mpsNo 의 MRP 적용상태를 "Y" 로 변경
		for (String mpsNo : mpsNoList) {

			mpsDAO.changeMrpApplyStatus(mpsNo, "Y");

		}

		// MRP 적용상태를 "Y" 로 변경한 주생산계획번호들을 결과 Map 에 저장
		resultMap.put("changeMrpApplyStatus", mpsNoList.toString().replace("[", "").replace("]", ""));

		return resultMap;
	}

	//일괄처리 프로세스
	public HashMap<String, Object> batchMrpListProcess(ArrayList<MrpTO> mrpTOList) {


		HashMap<String, Object> resultMap = new HashMap<>();

		//상태에 따라 처리할 ArrayList를 만든다. 
		ArrayList<String> insertList = new ArrayList<>();
		ArrayList<String> updateList = new ArrayList<>();
		ArrayList<String> deleteList = new ArrayList<>();

		for (MrpTO bean : mrpTOList) {

			String status = bean.getStatus();

			switch (status) {

			case "INSERT":

				// dao 파트 시작
				mrpDAO.insertMrp(bean);
				// dao 파트 끝

				insertList.add(bean.getMrpNo());  //insert리스트에 mrp넘버를 List갯수만큰 좌좌좍 추가시킴.

				break;

			case "UPDATE":

				// dao 파트 시작
				mrpDAO.updateMrp(bean);
				// dao 파트 끝

				updateList.add(bean.getMrpNo());

				break;

			case "DELETE":

				// dao 파트 시작
				mrpDAO.deleteMrp(bean);
				// dao 파트 끝

				deleteList.add(bean.getMrpNo());

				break;

			}

		}

		resultMap.put("INSERT", insertList);  //맨 밑에서 return하는데 
		resultMap.put("UPDATE", updateList);  //이 친구들만 return 했다는 말은,
		resultMap.put("DELETE", deleteList);  //결과값 전체를 리턴한게 아니라 DAO에서 등록하고 추가한 일련번호만 리턴함.

		return resultMap;
	}

	public ArrayList<MrpGatheringTO> getMrpGathering(ArrayList<String> mrpNoArr) {

		ArrayList<MrpGatheringTO> mrpGatheringList = null;

		// mrp번호 배열 [mrp번호,mrp번호, ...] => "mrp번호,mrp번호, ..." 형식의 문자열로 변환
		String mrpNoList = mrpNoArr.toString().replace("[", "").replace("]", "");
		System.out.println("mrpNoArr = "+mrpNoArr);
		System.out.println("mrpNoList = "+mrpNoList);
		mrpGatheringList = mrpGatheringDAO.getMrpGathering(mrpNoList);

		return mrpGatheringList;

	}

	public HashMap<String, Object> registerMrpGathering(String mrpGatheringRegisterDate,
			ArrayList<MrpGatheringTO> newMrpGatheringList, HashMap<String, String> mrpNoAndItemCodeMap) {

		HashMap<String, Object> resultMap = null;

		// 소요량 취합일자로 새로운 소요량 취합번호 확인
		int i = mrpGatheringDAO.selectMrpGatheringCount(mrpGatheringRegisterDate);

		/*
		 * ( itemCode : 새로운 mrpGathering 일련번호 ) 키/값 Map => itemCode 로 mrpNo 와
		 * mrpGatheringNo 를 매칭
		 */
		HashMap<String, String> itemCodeAndMrpGatheringNoMap = new HashMap<>();

		// 새로운 mrpGathering 일련번호 양식 생성 : 등록일자 '2018-01-01' => 일련번호 'MG20180101-'
		StringBuffer newMrpGatheringNo = new StringBuffer();
		newMrpGatheringNo.append("MG");
		newMrpGatheringNo.append(mrpGatheringRegisterDate.replace("-", ""));
		newMrpGatheringNo.append("-");

		// 새로운 mrpGathering 빈에 일련번호 입력 / status 를 "INSERT" 로 변경
		for (MrpGatheringTO bean : newMrpGatheringList) {

			bean.setMrpGatheringNo(newMrpGatheringNo.toString() + String.format("%03d", i++));
			bean.setStatus("INSERT");

			// mrpGathering 빈의 itemCode 와 mrpGatheringNo 를 map 에 저장
			itemCodeAndMrpGatheringNoMap.put(bean.getItemCode(), bean.getMrpGatheringNo());

		}

		// 새로운 mrpGathering 빈을 batchListProcess 로 테이블에 저장, 결과 Map 반환
		resultMap = batchMrpGatheringListProcess(newMrpGatheringList);

		System.out.println("newMrpGatheringList :" + newMrpGatheringList);
		
		// 생성된 mrp 일련번호를 저장할 TreeSet
		TreeSet<String> mrpGatheringNoSet = new TreeSet<>();

		// "INSERT_LIST" 목록에 "itemCode - mrpGatheringNo" 키/값 Map 이 있음
		@SuppressWarnings("unchecked")
		HashMap<String, String> mrpGatheringNoList = (HashMap<String, String>) resultMap.get("INSERT_MAP");

		for (String mrpGatheringNo : mrpGatheringNoList.values()) {
			mrpGatheringNoSet.add(mrpGatheringNo); // ArrayList 의 mrpGathering 일련번호들을 TreeSet 에 저장

		}

		resultMap.put("firstMrpGatheringNo", mrpGatheringNoSet.pollFirst()); // 최초 mrpGathering 일련번호를 결과 Map 에 저장
		resultMap.put("lastMrpGatheringNo", mrpGatheringNoSet.pollLast()); // 마지막 mrpGathering 일련번호를 결과 Map 에 저장

		// MRP 테이블에서 해당 mrpNo 의 mrpGatheringNo 저장, 소요량취합 적용상태를 "Y" 로 변경
		// itemCode 로 mrpNo 와 mrpGatheringNo 를 매칭시킨다
		for (String mrpNo : mrpNoAndItemCodeMap.keySet()) {

			String itemCode = mrpNoAndItemCodeMap.get(mrpNo);
			String mrpGatheringNo = itemCodeAndMrpGatheringNoMap.get(itemCode);
			mrpDAO.changeMrpGatheringStatus(mrpNo, mrpGatheringNo, "Y");

		}

		// MRP 적용상태를 "Y" 로 변경한 MRP 번호들을 결과 Map 에 저장
		resultMap.put("changeMrpGatheringStatus",
				mrpNoAndItemCodeMap.keySet().toString().replace("[", "").replace("]", ""));

		return resultMap;
	}

	public HashMap<String, Object> batchMrpGatheringListProcess(ArrayList<MrpGatheringTO> mrpGatheringTOList) {

		HashMap<String, Object> resultMap = new HashMap<>();

		HashMap<String, String> insertListMap = new HashMap<>(); // "itemCode : mrpGatheringNo" 의 맵
		ArrayList<String> insertList = new ArrayList<>();
		ArrayList<String> updateList = new ArrayList<>();
		ArrayList<String> deleteList = new ArrayList<>();

		for (MrpGatheringTO bean : mrpGatheringTOList) {

			String status = bean.getStatus();

			switch (status) {

			case "INSERT":

				mrpGatheringDAO.insertMrpGathering(bean);

				insertList.add(bean.getMrpGatheringNo());

				insertListMap.put(bean.getItemCode(), bean.getMrpGatheringNo());

				break;

			case "UPDATE":

				mrpGatheringDAO.updateMrpGathering(bean);

				updateList.add(bean.getMrpGatheringNo());

				break;

			case "DELETE":

				mrpGatheringDAO.deleteMrpGathering(bean);

				deleteList.add(bean.getMrpGatheringNo());

				break;

			}

		}

		resultMap.put("INSERT_MAP", insertListMap);
		resultMap.put("INSERT", insertList);
		resultMap.put("UPDATE", updateList);
		resultMap.put("DELETE", deleteList);

		return resultMap;
	}

}
