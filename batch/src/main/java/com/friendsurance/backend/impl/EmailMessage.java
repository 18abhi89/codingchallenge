/**
 * 
 */
package com.friendsurance.backend.impl;

import com.friendsurance.mail.EmailRecipient;

/**
 * the result of processing
 * 
 * @author durrah
 *
 */
public class EmailMessage {

	private EmailRecipient recipient;
	private int ruleOutput;

	public EmailMessage() {
	}

	public EmailMessage(EmailRecipient recipient, int ruleOutput) {
		super();
		this.recipient = recipient;
		this.ruleOutput = ruleOutput;
	}

	public EmailRecipient getRecipient() {
		return recipient;
	}

	public int getRuleOutput() {
		return ruleOutput;
	}

	public static class PoisonPillPendingMessage extends EmailMessage {
	}
}
