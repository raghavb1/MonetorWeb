package com.champ.base.request;

import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import com.champ.base.dto.MessageDto;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SaveMessageRequest extends BaseRequest {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3236017209556694040L;

	List<MessageDto> messages;

	public List<MessageDto> getMessages() {
		return messages;
	}

	public void setMessages(List<MessageDto> messages) {
		this.messages = messages;
	}

	@Override
	public String toString() {
		return "SaveMessageRequest [messages=" + messages + "]";
	}

}
