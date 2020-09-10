package com.challenge.entitys;

/**
 * 
 * @author Ricardo Braga
 *
 */
public class ProtocolEntity {
	
	private MessageEntity messageEntity;
	
	private UserInfoEntity userInfoEntity;
	
	private DateTimeEntity dateTimeEntity;

	public MessageEntity getMessageEntity() {
		return messageEntity;
	}

	public void setMessageEntity(MessageEntity messageEntity) {
		this.messageEntity = messageEntity;
	}

	public UserInfoEntity getUserInfoEntity() {
		return userInfoEntity;
	}

	public void setUserInfoEntity(UserInfoEntity userInfoEntity) {
		this.userInfoEntity = userInfoEntity;
	}

	public DateTimeEntity getDateTimeEntity() {
		return dateTimeEntity;
	}

	public void setDateTimeEntity(DateTimeEntity dateTimeEntity) {
		this.dateTimeEntity = dateTimeEntity;
	}

}
