/**
 * 
 */
package com.friendsurance.processing.impl;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.logging.Logger;

import com.friendsurance.backend.impl.EmailMessage;
import com.friendsurance.exception.JobExecutionException;
import com.friendsurance.mail.impl.FriendsuranceEmailService;

/**
 * @author abhishek
 *
 */
public class BatchMailSchedule {
	final static Logger LOGGER = Logger.getLogger(BatchMailSchedule.class.getCanonicalName());

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		BlockingQueue<EmailMessage> messages = new LinkedBlockingQueue<EmailMessage>();

		FriendsuranceEmailService emailService = new FriendsuranceEmailService(messages);
		Thread emailServiceThread = new Thread(emailService, "EmailService Thread");
		emailServiceThread.start();

		Job job = new Job(messages);
		try {
			job.execute();
		} catch (JobExecutionException e1) {
			LOGGER.severe("error executing job, to retry");
			System.exit(-1);
		}

		try {
			emailServiceThread.join();
		} catch (InterruptedException e) {
			// ignore
		}
	}

}
