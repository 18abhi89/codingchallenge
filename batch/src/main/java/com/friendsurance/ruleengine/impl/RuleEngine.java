package com.friendsurance.ruleengine.impl;

import java.io.Reader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.concurrent.CopyOnWriteArraySet;

import com.friendsurance.backend.User;
import com.friendsurance.ruleselection.Rule;
import com.friendsurance.ruleselection.Rule.RuleMaxPriorityComparator;

/**
 * a simple rule engine, with a concurrent set of rules, we start the engine by
 * loading the initial rules, and also we start the WatchDog Service (if
 * provided by the builder)
 * 
 * @author durrah
 *
 */
public class RuleEngine {
	private final Set<Rule> rules = new CopyOnWriteArraySet<Rule>();
	private RulesLoader rulesLoader;
	private RuleParser ruleParser;
	private Reader rulesContentReader;

	public RuleEngine(Builder builder) {
		this.ruleParser = builder.ruleParser;
		this.rulesLoader = new RulesLoader(ruleParser);
		this.rulesContentReader = builder.rulesContentReader;
	}

	/**
	 * start the rule engine
	 */
	public void start() {
		loadRules();
	}

	public void stop() {
	}

	public Set<Rule> getRules() {
		return rules;
	}

	private void loadRules() {
		List<Rule> initialRules = new ArrayList<Rule>();
		try {
			initialRules = rulesLoader.loadRules(rulesContentReader);
		} catch (Exception e) {
		}

		rules.addAll(initialRules);
	}

	/**
	 * this is the most important method of the rule engine, the main function
	 * of it, for each rule of engine rules it checks if the rule applied for a
	 * given user, if so it will add it to a {@link SortedSet} based on
	 * {@link RuleMaxPriorityComparator} so the rule with highest priority will
	 * be the first rule, this approach of saving rules will be useful in
	 * logging or it may be used in other places if the logic changed.
	 * 
	 * @param user
	 * @return
	 */
	public Set<Rule> applicableRules(User user) {
		SortedSet<Rule> rules = new TreeSet<Rule>(new Rule.RuleMaxPriorityComparator());
		Iterator<Rule> iterator = getRules().iterator();
		while (iterator.hasNext()) {
			Rule rule = (Rule) iterator.next();
			if (rule.applies(user))
				rules.add(rule);
		}
		return rules;
	}

	/**
	 * the builder of the engine, as the engine may need some big init
	 * parameters this build class helps to make the initialization process
	 * easier
	 * 
	 * @author durrah
	 *
	 */
	public static class Builder {
		private RuleParser ruleParser;
		private Reader rulesContentReader;

		public RuleParser getRuleParser() {
			return ruleParser;
		}

		public Builder setRuleParser(RuleParser ruleParser) {
			this.ruleParser = ruleParser;
			return this;
		}

		public Builder setRulesContentReader(Reader rulesContentReader) {
			this.rulesContentReader = rulesContentReader;
			return this;
		}

		public Reader getRulesContentReader() {
			return rulesContentReader;
		}

		public RuleEngine build() {
			return new RuleEngine(this);
		};
	}
}
