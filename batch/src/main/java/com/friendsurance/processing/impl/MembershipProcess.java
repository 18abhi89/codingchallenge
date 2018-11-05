/**
 * 
 */
package com.friendsurance.processing.impl;

import java.io.Reader;
import java.util.SortedSet;

import com.friendsurance.backend.impl.EmailMessage;
import com.friendsurance.backend.impl.FriendsuranceUser;
import com.friendsurance.backend.impl.EmailMessage.PoisonPillPendingMessage;
import com.friendsurance.backend.impl.FriendsuranceUser.NullMember;
import com.friendsurance.processing.ItemProcessing;
import com.friendsurance.processing.ItemReader;
import com.friendsurance.processing.ItemWriter;
import com.friendsurance.ruleengine.impl.RegexRuleParser;
import com.friendsurance.ruleengine.impl.RuleEngine;
import com.friendsurance.ruleselection.Rule;

/**
 * item process implementer
 * 
 * @author durrah
 *
 */
public class MembershipProcess extends ItemProcessing<FriendsuranceUser, EmailMessage> {
	private RuleEngine ruleEngine;
	private Reader rulesReader;

	protected MembershipProcess(ItemReader<FriendsuranceUser> reader, ItemWriter<EmailMessage> writer, Reader rulesReader) {
		super(reader, writer);
		this.rulesReader = rulesReader;
	}

	public void setRulesReader(Reader rulesReader) {
		this.rulesReader = rulesReader;
		ruleEngine = new RuleEngine.Builder().setRulesContentReader(rulesReader).setRuleParser(new RegexRuleParser())
				.build();
		ruleEngine.start();
	}

	@Override
	protected EmailMessage process(FriendsuranceUser item) {
		/**
		 * 
		 */
		if (item instanceof NullMember)
			return new PoisonPillPendingMessage();
		SortedSet<Rule> appl = (SortedSet<Rule>) ruleEngine.applicableRules(item);
		if (appl.isEmpty()) {
			return null;
		}
		return new EmailMessage(item, appl.first().getApplyResult());
	}

	protected void stop() {
		ruleEngine.stop();
	}

}
