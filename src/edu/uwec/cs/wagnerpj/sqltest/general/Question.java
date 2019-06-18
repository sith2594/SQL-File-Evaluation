/*
 * Question - class to hold an SQL assignment question
 * 
 * Created - Paul J. Wagner, 11-Sep-2018
 */
package edu.uwec.cs.wagnerpj.sqltest.general;

import java.util.ArrayList;

public class Question {
	// data
	String qNumStr;						// question string; e.g. 1b. or 1.d)
	int questionMarks;					// number of marks for that question
	Query desiredQuery;					// model answer
	ArrayList<EvalComponentInQuestion> tests; 		// list of tests to evaluate this question
	
	// methods
	// constructors
	// all-arg constructor
	public Question(String qNumStr, int questionMarks, Query desiredQuery, 
			ArrayList<EvalComponentInQuestion> tests) {
		super();
		this.qNumStr = qNumStr;
		this.questionMarks = questionMarks;
		this.desiredQuery = desiredQuery;
		this.tests = tests;
	}
	
	// default constructor
	public Question() {
		this(null, 0, null, null);
	}

	// getters and setters
	public String getQNumStr() {
		return qNumStr;
	}

	public void setQNumStr(String qNumStr) {
		this.qNumStr = qNumStr;
	}

	public int getQuestionMarks() {
		return questionMarks;
	}

	public void setQuestionMarks(int questionMarks) {
		this.questionMarks = questionMarks;
	}
	
	public Query getDesiredQuery() {
		return desiredQuery;
	}

	public void setDesiredQuery(Query desiredQuery) {
		this.desiredQuery = desiredQuery;
	}

	public ArrayList<EvalComponentInQuestion> getTests() {
		return tests;
	}

	public void setTests(ArrayList<EvalComponentInQuestion> tests) {
		this.tests = tests;
	}

	// hashCode
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((desiredQuery == null) ? 0 : desiredQuery.hashCode());
		result = prime * result + ((qNumStr == null) ? 0 : qNumStr.hashCode());
		result = prime * result + questionMarks;
		result = prime * result + ((tests == null) ? 0 : tests.hashCode());
		return result;
	}

	// equals
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Question other = (Question) obj;
		if (desiredQuery == null) {
			if (other.desiredQuery != null)
				return false;
		} else if (!desiredQuery.equals(other.desiredQuery))
			return false;
		if (qNumStr == null) {
			if (other.qNumStr != null)
				return false;
		} else if (!qNumStr.equals(other.qNumStr))
			return false;
		if (questionMarks != other.questionMarks)
			return false;
		if (tests == null) {
			if (other.tests != null)
				return false;
		} else if (!tests.equals(other.tests))
			return false;
		return true;
	}	// end - method equals

	// toString
	@Override
	public String toString() {
		return "Question [qNumStr=" + qNumStr + ", questionMarks=" + questionMarks + ", desiredQuery=" + desiredQuery
				+ ", tests=" + tests + "]";
	}
	
}	// end - class Question
