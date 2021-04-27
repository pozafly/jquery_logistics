package com.project.aesup.base.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.ui.ModelMap;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

public class ImgFileController extends MultiActionController {


	// 톰캣 서버의 이미지 저장 경로
	private static String serverUploadFolderPath = "ImgServer/empPhoto/";

	// 이클립스 워크스페이스의 이미지 저장경로 => 반드시 Full Path 로 지정할 것!
	private static String workspaceUploadFolderPath = "F:/물류프로젝트 준비/물류/세미나영상 및 프로젝트/신헌이 프로젝트/2차 스프링/SkyBlue 2차/src/main/webapp/ImgServer/empPhoto/";

	// 뷰에서 file 태그로 전송할 때 name 속성의 값
	private static String multipartFileName = "uploadImgFile";
	
	private ModelMap modelMap = new ModelMap();
	private ModelAndView modelAndView = new ModelAndView();
	
	public ModelAndView imgFileUpload(HttpServletRequest request, HttpServletResponse response) {
	

		// 톰캣에 배포된 프로젝트 경로 => (예) C:/tomcat/webapps/uniproduct_second/
		String root = request.getSession().getServletContext().getRealPath("/");

		// 파일 업로드 경로 : C:/tomcat/webapps/uniproduct_second/ImgServer/empPhoto/
		String uploadPath = root + serverUploadFolderPath;

		/*
		 * MultipartHttpServletRequest : 스프링 제공 인터페이스 => 멀티파트 요청시 내부적으로 원본
		 * HttpServletRequest 대신 사용됨
		 */
		MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest) request;

		// multipartFileName 에 해당하는 업로드 파일 정보
				MultipartFile multipartFile = multipartHttpServletRequest.getFile(multipartFileName);

				if (!multipartFile.isEmpty()) {

					// 업로드된 이미지의 원본 파일명
					String originalFileName = multipartFile.getOriginalFilename();

					// 업로드된 이미지의 확장자 : jqg, png, gif 등등..
					String formatName = originalFileName.substring(originalFileName.lastIndexOf(".") + 1);

					// 저장 파일명에 사용할 랜덤 UID 생성 => 파일명 중복 방지
					UUID uid = UUID.randomUUID();

					//  UID + "." + 확장자 => 저장 파일명 ( storedFileName )

					
					
			
					String storedFileName = uid.toString() + "." + formatName;

					System.out.println("------------- 이미지 업로드 start -------------");
					System.out.println("   @ file 태그 name 속성값 : " + multipartFile.getName());
					System.out.println("   @ 원본 파일명 : " + multipartFile.getOriginalFilename());
					System.out.println("   @ 저장 파일명 : " + storedFileName);
					System.out.println("   @ 파일 크기 : " + multipartFile.getSize());

					// 파일 객체 생성
					File file = new File(uploadPath, storedFileName);

					try {

						// 업로드된 파일 객체를 transferTo() 메소드로 저장
						multipartFile.transferTo(file);

						// 톰캣 서버에 업로드된 파일 경로 + 저장 파일명
						Path from = Paths.get(uploadPath + storedFileName);
						System.out.println("   @ 서버 저장경로 : " + from.toString());

						// 이클립스 워크스페이스의 업로드 폴더 + 저장 파일명
						Path to = Paths.get(workspaceUploadFolderPath + storedFileName);

						// 톰캣 서버에 업로드된 파일을 워크스페이스의 업로드 폴더에 복사
						Files.copy(from, to, StandardCopyOption.REPLACE_EXISTING);

						System.out.println("   @ 워크스페이스 저장경로 : " + to.toString());
						System.out.println("-------------- 이미지 업로드 end --------------\n");

					} catch (IllegalStateException | IOException e) {
						e.printStackTrace();
					}

					// modelMap 의 이전 데이터 clear
					modelMap.clear();

					modelMap.put("imgUrl", "/" + serverUploadFolderPath + storedFileName);
					modelMap.put("errorCode", 0);
					modelMap.put("errorMsg", "성공");

				} // if 문 끝

				// modelAndView 의 이전 데이터 clear
				modelAndView.clear();

				// modelAndView 에 뷰 이름 설정 : 여기서는 imgUrl 을 json 으로 리턴 => "jsonView"
				modelAndView.setViewName("jsonView");

				// modelMap 의 모든 데이터를 modelAndView 에 등록
				modelAndView.addAllObjects(modelMap);

				return modelAndView;
	}
}
