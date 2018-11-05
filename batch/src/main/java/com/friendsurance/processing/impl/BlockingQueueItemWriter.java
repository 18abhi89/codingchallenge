package com.friendsurance.processing.impl;

import java.util.concurrent.BlockingQueue;

import com.friendsurance.backend.impl.EmailMessage;
import com.friendsurance.processing.ItemWriter;

public class BlockingQueueItemWriter implements ItemWriter<EmailMessage> {

	private BlockingQueue<EmailMessage> waitingMessages;

	public BlockingQueueItemWriter(BlockingQueue<EmailMessage> waitingMessages) {
		this.waitingMessages = waitingMessages;
	}

	@Override
	public void write(EmailMessage item) {
		try {
			waitingMessages.put(item);
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		}
	}
}
