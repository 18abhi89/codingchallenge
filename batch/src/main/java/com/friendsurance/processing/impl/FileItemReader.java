/**
 * 
 */
package com.friendsurance.processing.impl;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import com.friendsurance.backend.impl.FriendsuranceUser;
import com.friendsurance.processing.ItemReader;

/**
 * read user information from a flat file line by line
 * 
 * @author abhishek
 *
 */
public class FileItemReader implements ItemReader<FriendsuranceUser> {
	private BufferedReader reader;

	public FileItemReader(String filePath) throws IOException {
		reader = new BufferedReader(new FileReader(filePath));
	}

	@Override
	public FriendsuranceUser read() {
		FriendsuranceUser member = null;
		try {
			String line = reader.readLine();
			if (line == null) {
				reader.close();
				return new FriendsuranceUser.NullMember("", false, 0, 0);
			}
			member = FriendsuranceUser.fromString(line);
		} catch (Exception e) {
			// ignore
		}
		return member;
	}
}