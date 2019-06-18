/*
 * QueryEvaluation - class to hold the evaluation functionality for an SQL query
 * 
 * Created - Paul J. Wagner, 05-Sep-2018
 */
package edu.uwec.cs.wagnerpj.sqltest.general;

import java.util.ArrayList;

import edu.uwec.cs.wagnerpj.sqltest.sqltests.ISQLTest;

public class QueryEvaluation {
	// data
	private Query givenQuery;							// the submission query to evaluate
	private Query desiredQuery;						// the model query for evaluation
	private int maxMarks;								// maximum number of marks for question
	private ArrayList<ISQLTest> allTests = new ArrayList<ISQLTest>(); // list of tests to apply
	private ArrayList<Integer> allTestsPercents;		// parallel list of percentage weights
	private ArrayList<String> allTestsConditions;		// parallel list of test conditions
	private ArrayList<Integer> allTestsResults;			// parallel list of test results
	private double queryScore;							// overall score for that query
	
	// methods
	// constructors
	// all-arg constructor
	public QueryEvaluation(Query givenQuery, Query desiredQuery, int maxMarks,
			ArrayList<ISQLTest> allTests, ArrayList<Integer> allTestsPercents, 
			ArrayList<String> allTestsConditions,  ArrayList<Integer> allTestsResults, 
			double queryScore) {
		super();
		this.givenQuery = givenQuery;
		this.desiredQuery = desiredQuery;
		this.maxMarks = maxMarks;
		this.allTests = allTests;
		this.allTestsPercents = allTestsPercents;
		this.allTestsConditions = allTestsConditions;
		this.allTestsResults = allTestsResults;
		this.queryScore = queryScore;
	}

	// default constructor
	public QueryEvaluation () {
		this(null, null, 0, null, null, null, null, 0.0);
	}

	// getters and setters
	public Query getGivenQuery() {
		return givenQuery;
	}

	public void setGivenQuery(Query givenQuery) {
		this.givenQuery = givenQuery;
	}

	public Query getDesiredQuery() {
		return desiredQuery;
	}

	public void setDesiredQuery(Query desiredQuery) {
		this.desiredQuery = desiredQuery;
	}

	public int getMaxMarks() {
		return maxMarks;
	}

	public void setMaxMarks(int maxMarks) {
		this.maxMarks = maxMarks;
	}
	
	public ArrayList<ISQLTest> getAllTests() {
		return allTests;
	}

	public void setAllTests(ArrayList<ISQLTest> allTests) {
		this.allTests = allTests;
	}

	public ArrayList<Integer> getAllTestsPercents() {
		return allTestsPercents;
	}

	public void setAllTestsPercents(ArrayList<Integer> allTestsPercents) {
		this.allTestsPercents = allTestsPercents;
	}

	public ArrayList<String> getAllTestsConditions() {
		return allTestsConditions;
	}

	public void setAllTestsConditions(ArrayList<String> allTestsConditions) {
		this.allTestsConditions = allTestsConditions;
	}
	
	public ArrayList<Integer> getAllTestsResults() {
		return allTestsResults;
	}

	public void setAllTestsResults(ArrayList<Integer> allTestsResults) {
		this.allTestsResults = allTestsResults;
	}
	
	public double getQueryScore() {
		return queryScore;
	}
	
	public void setQueryScore(double queryScore) {
		this.queryScore = queryScore;
	}
	// end - methods for getters and setters

	// hashCode
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((allTests == null) ? 0 : allTests.hashCode());
		result = prime * result + ((allTestsConditions == null) ? 0 : allTestsConditions.hashCode());
		result = prime * result + ((allTestsPercents == null) ? 0 : allTestsPercents.hashCode());
		result = prime * result + ((allTestsResults == null) ? 0 : allTestsResults.hashCode());
		result = prime * result + ((desiredQuery == null) ? 0 : desiredQuery.hashCode());
		result = prime * result + ((givenQuery == null) ? 0 : givenQuery.hashCode());
		result = prime * result + maxMarks;
		long temp;
		temp = Double.doubleToLongBits(queryScore);
		result = prime * result + (int) (temp ^ (temp >>> 32));
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
		QueryEvaluation other = (QueryEvaluation) obj;
		if (allTests == null) {
			if (other.allTests != null)
				return false;
		} else if (!allTests.equals(other.allTests))
			return false;
		if (allTestsConditions == null) {
			if (other.allTestsConditions != null)
				return false;
		} else if (!allTestsConditions.equals(other.allTestsConditions))
			return false;
		if (allTestsPercents == null) {
			if (other.allTestsPercents != null)
				return false;
		} else if (!allTestsPercents.equals(other.allTestsPercents))
			return false;
		if (allTestsResults == null) {
			if (other.allTestsResults != null)
				return false;
		} else if (!allTestsResults.equals(other.allTestsResults))
			return false;
		if (desiredQuery == null) {
			if (other.desiredQuery != null)
				return false;
		} else if (!desiredQuery.equals(other.desiredQuery))
			return false;
		if (givenQuery == null) {
			if (other.givenQuery != null)
				return false;
		} else if (!givenQuery.equals(other.givenQuery))
			return false;
		if (maxMarks != other.maxMarks)
			return false;
		if (Double.doubleToLongBits(queryScore) != Double.doubleToLongBits(other.queryScore))
			return false;
		return true;
	}	// end - method equals

	// toString
	@Override
	public String toString() {
		return "QueryEvaluation [givenQuery=" + givenQuery + ", desiredQuery=" + desiredQuery + ", maxMarks=" + maxMarks
				+ ", allTests=" + allTests + ", allTestsPercents=" + allTestsPercents + ", allTestsConditions="
				+ allTestsConditions + ", allTestsResults=" + allTestsResults + ", queryScore=" + queryScore + "]";
	}
	
	// evaluate = do the evaluation for this query evaluation
	public double evaluate() {
		double result = 0.0;				// result of all test for this query evaluation
		int marksTotal = 0;					// raw marks total
		ArrayList<Integer> results = new ArrayList<Integer>();	// list of results
		int testIndex = 0;					// index for each test in list
		for (ISQLTest aTest: allTests) {
			String testString = null;		// test string to evaluate
			String aTestName = aTest.getName();
			if (aTestName.substring(0, 4).equals("Test")) {
				testString = desiredQuery.toString();
			}
			else if (aTestName.substring(0, 4).equals("Cond")) {
				testString = allTestsConditions.get(testIndex);
			}
			//System.out.println("QueryEvaluation-evaluate() - testString is: >" +  testString + "<");
			int testResult = aTest.sqlTest(givenQuery, testString); // or some condition string
			//System.out.println("testResult is: " + testResult);
			int currentPercent = allTestsPercents.get(testIndex);
			//System.out.println("currentPercent is: " + currentPercent);
			int currentMarks = testResult * currentPercent;
			results.add(currentMarks);
			//System.out.println(aTestName + ": " + "testResult: " + testResult + ", curr%: " + currentPercent +
			//	 ", curr marks: " + currentMarks);
			marksTotal += currentMarks;
			testIndex++;
		}	//	end - for each test 
		
		result = marksTotal / 100.0;		// convert from percent to raw marks
		result = result * (maxMarks / 10.0);	// convert from raw marks to marks for this question
		setQueryScore(result);				// store the total marks result internally		
		setAllTestsResults(results);		// store the list of test results internally
		
		return result;
	}
	
}	// end - class QueryEvaluation
