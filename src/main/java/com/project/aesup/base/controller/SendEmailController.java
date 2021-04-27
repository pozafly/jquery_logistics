package com.project.aesup.base.controller;


import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;



import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class SendEmailController extends AbstractController {
   protected final Log logger = LogFactory.getLog(this.getClass());
   PrintWriter out = null;
   private Multipart multipart;
	HashMap<String, Object> map = new HashMap<>();
		// gson 라이브러리
	Gson gson = new GsonBuilder().serializeNulls().create(); // 속성값이 null 인 속성도 json 변환
	
   public ModelAndView handleRequestInternal(HttpServletRequest request, HttpServletResponse response) {
      
      if (logger.isDebugEnabled()) {
            logger.debug(" SendEmailController : handleRequestInternal 시작 ");
        }
      
      String email=request.getParameter("sendemail");
      
      
 System.out.println("email  값확인 : "+email);
 
 
      String fileName = "test01.pdf";
      String savePath = "F:/물류프로젝트 준비/물류/세미나영상 및 프로젝트/신헌이 프로젝트/2차 스프링/SkyBlue 2차/src/main/webapp/resources/iReportForm";
      
      String host = "smtp.naver.com";
      final String user = "miss0625";
      final String password = "tmgmrzjf";

      String to = email;

      // Get the session object
      Properties props = new Properties();
      props.put("mail.smtp.host", host);
      props.put("mail.smtp.auth", "true");

      Session session = Session.getDefaultInstance(props, new javax.mail.Authenticator() {
         protected PasswordAuthentication getPasswordAuthentication() {
            return new PasswordAuthentication(user, password);
         }
      });

      // Compose the message
      try {
    	  out = response.getWriter();
         MimeMessage message = new MimeMessage(session);
         message.setFrom(new InternetAddress(user));
         message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

         // Subject
        
         message.setSubject("요청하신 견적서 입니다.");
         multipart = new MimeMultipart();
               
         // Text
         MimeBodyPart mbp1 = new MimeBodyPart();
            mbp1.setText("요청하신 견적서 입니다. ");
            multipart.addBodyPart(mbp1);

         // send the message
         if(fileName != null){
               DataSource source = new FileDataSource(savePath+"\\"+fileName);
               BodyPart messageBodyPart = new MimeBodyPart();
               messageBodyPart.setDataHandler(new DataHandler(source));
               messageBodyPart.setFileName(fileName);
               multipart.addBodyPart(messageBodyPart);
           }
         message.setContent(multipart);
            Transport.send(message);
         
            map.put("error_code", 0);
            map.put("error_msg", "Success!!!!");
            
            System.out.println("Success!!!!");
            
      } catch (MessagingException e) {
         e.printStackTrace();
         map.put("error_code", -1);
         map.put("error_msg", e.getMessage());
         
      }catch (Exception e) {
    	  map.put("error_code", -1);
          map.put("error_msg", e.getMessage());
	}finally {
		out.println(gson.toJson(map));
		out.close();
	}
      
      if (logger.isDebugEnabled()) {
            logger.debug(" ReportController : handleRequestInternal 종료 ");
        }
        return null;
   }
}
