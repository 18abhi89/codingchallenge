package com.friendsurance.backend.impl;

import com.friendsurance.backend.User;
import com.friendsurance.exception.InvalidMemberSyntaxException;
import com.friendsurance.mail.EmailRecipient;

/**
 * member is a class that combines {@link User} and {@link EmailRecipient}
 * functionalities
 * 
 * @author abhishek
 *
 */
public class FriendsuranceUser extends User implements EmailRecipient {

	public FriendsuranceUser(String email, boolean hasContract, int friendsNumber, int sentInvitationsNumber) {
		super(email, hasContract, friendsNumber, sentInvitationsNumber);
	}

	/**
	 * create a member from a string input
	 * 
	 * @param line
	 * @return
	 * @throws Exception
	 *             either {@link InvalidMemberSyntaxException} or
	 *             {@link NumberFormatException}
	 */
	public static FriendsuranceUser fromString(String line) throws Exception {
		String[] items = line.split(",");
		if (items.length < 4)
			throw new InvalidMemberSyntaxException();
		String email = items[0].trim();
		String contractString = items[1].trim();
		boolean hasContract = contractString.equalsIgnoreCase("true");
		String friendsNumberString = items[2].trim();
		int friendsNumber = Integer.parseInt(friendsNumberString);
		String sentInvitationsNumberString = items[2].trim();
		int sentInvitationsNumber = Integer.parseInt(sentInvitationsNumberString);
		return new FriendsuranceUser(email, hasContract, friendsNumber, sentInvitationsNumber);
	}

	/**
	 * indicate that the input data read finished
	 * 
	 * @author abhishek
	 *
	 */
	public static class NullMember extends FriendsuranceUser {

		public NullMember(String email, boolean hasContract, int friendsNumber, int sentInvitationsNumber) {
			super(email, hasContract, friendsNumber, sentInvitationsNumber);
		}
	}

}
