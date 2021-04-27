package com.project.aesup.common.dao;

import org.springframework.context.MessageSource;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

@SuppressWarnings("deprecation")
public class IbatisSupportDAO extends SqlMapClientDaoSupport{

	@SuppressWarnings("unused")
	private MessageSource messageSource;
		
    public void setMessageSource(MessageSource messageSource) {
        this.messageSource = messageSource;
    }
}