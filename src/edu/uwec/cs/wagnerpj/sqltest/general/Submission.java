/*
 * Submission - class to hold a single submission for an assignment
 * 
 * Created - Paul J. Wagner, 12-Sep-2018
 */
package edu.uwec.cs.wagnerpj.sqltest.general;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import edu.uwec.cs.wagnerpj.sqltest.sqltests.ISQLTest;
import edu.uwec.cs.wagnerpj.sqltest.util.Utilities;

public class Submission {
	// data
	private String submissionFileName;				// name of file submission came from
	private String submissionName;					// name of assignment as submitted - from template
	private String studentName;						// name of the student
	private String studentID;						// student id code

	private ArrayList <QuestionAnswer> answers;		// list of answers as submitted
	private ArrayList <QueryEvaluation> queryEvals;	// list of query evals for each answer
	private double totalMarks;						// total number of marks for assignment
	
	// methods
	// constructors
	// all-arg constructor
	public Submission(String submissionFileName, String studentName, String studentID,
			String submissionName, ArrayList<QuestionAnswer> answers, 
			ArrayList<QueryEvaluation> queryEvals, double totalMarks) {
		super();
		this.submissionFileName = submissionFileName;
		this.studentName = studentName;
		this.studentID = studentID;
		this.submissionName = submissionName;
		this.answers = answers;
		this.queryEvals = queryEvals;
		this.totalMarks = totalMarks;
	}
	
	// default constructor
	public Submission() {
		this(null, null, null, null, null, null, 0.0);
	}

	// getters and setters
	public String getSubmissionFileName() {
		return submissionFileName;
	}

	public void setSubmissionFileName(String submissionFileName) {
		this.submissionFileName = submissionFileName;
	}

	public String getStudentName() {
		return studentName;
	}

	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}
	
	public String getStudentID() {
		return studentID;
	}

	public void setStudentID(String studentID) {
		this.studentID = studentID;
	}
	
	public String getSubmissionName() {
		return submissionName;
	}

	public void setSubmissionName(String submissionName) {
		this.submissionName = submissionName;
	}

	public ArrayList<QuestionAnswer> getAnswers() {
		return answers;
	}

	public void setAnswers(ArrayList<QuestionAnswer> answers) {
		this.answers = answers;
	}
	
	public ArrayList<QueryEvaluation> getQueryEvals() {
		return queryEvals;
	}

	public void setQueryEvals(ArrayList<QueryEvaluation> queryEvals) {
		this.queryEvals = queryEvals;
	}
	
	public double getTotalMarks() {
		return totalMarks;
	}

	public void setTotalMarks(double totalMarks) {
		this.totalMarks = totalMarks;
	}

	// hashCode
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((answers == null) ? 0 : answers.hashCode());
		result = prime * result + ((queryEvals == null) ? 0 : queryEvals.hashCode());
		result = prime * result + ((studentID == null) ? 0 : studentID.hashCode());
		result = prime * result + ((studentName == null) ? 0 : studentName.hashCode());
		result = prime * result + ((submissionFileName == null) ? 0 : submissionFileName.hashCode());
		result = prime * result + ((submissionName == null) ? 0 : submissionName.hashCode());
		result = prime * result + (int)totalMarks;
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
		Submission other = (Submission) obj;
		if (answers == null) {
			if (other.answers != null)
				return false;
		} else if (!answers.equals(other.answers))
			return false;
		if (queryEvals == null) {
			if (other.queryEvals != null)
				return false;
		} else if (!queryEvals.equals(other.queryEvals))
			return false;
		if (studentID == null) {
			if (other.studentID != null)
				return false;
		} else if (!studentID.equals(other.studentID))
			return false;
		if (studentName == null) {
			if (other.studentName != null)
				return false;
		} else if (!studentName.equals(other.studentName))
			return false;
		if (submissionFileName == null) {
			if (other.submissionFileName != null)
				return false;
		} else if (!submissionFileName.equals(other.submissionFileName))
			return false;
		if (submissionName == null) {
			if (other.submissionName != null)
				return false;
		} else if (!submissionName.equals(other.submissionName))
			return false;
		if (totalMarks != other.totalMarks)
			return false;
		return true;
	}	// end - method equals

	// toString
	@Override
	public String toString() {
		return "Submission [submissionFileName=" + submissionFileName + ", studentName=" + studentName + ", studentID="
				+ studentID + ", submissionName=" + submissionName + ", answers=" + answers + ", queryEvals="
				+ queryEvals + ", totalMarks=" + totalMarks + "]";
	}
	
	// readSubmission - read one submission from a file
	// TODO: read in student name and id - is submission name generally there/needed?
	public void readSubmission(String submissionFileName, PrintWriter commWriter) {
		FileReader fr = null;
		BufferedReader br = null;
		String answerQueryStr = "";	// each answer string given in assignment
		boolean moreLinesForAnswer = false;	// are there more query answer lines coming?
		String qNumStr = "";				// question number as a string; e.g. 1.c)
		String regexp = "--- \\d+[a-z]*[.]";	// regular expression for question, e.g. >---1a. or ---23.<
		Pattern pattern = Pattern.compile(regexp);	// pattern for regexp pattern matching
		Matcher matcher = null;				// matcher for regexp pattern matching		
		
		try {
			fr = new FileReader(submissionFileName);
			br = new BufferedReader(fr);
			String line;
			this.submissionFileName = submissionFileName;
			System.out.println("\nReading in file " + submissionFileName);
			// TODO - remove --- from these three lines before writing out
			// TODO - make how many lines are in an assignment customizable? e.g. add student id
			line = br.readLine(); 					// get first line
			line = Utilities.skipBlankLines(br, line); // skip blanks if any
			submissionName = line.substring(4);		 // first line = assignment name, strip off >--- <
			//System.out.println("submission name: " + submissionName);
			line = br.readLine();					// second line = student name
			studentName = line.substring(10);		// strip of the >--- Name: <
			//System.out.println("student name: " + studentName);
			line = br.readLine(); 					// read third line

			// skip any additional instructions for assignment
			line = Utilities.skipBlankLines(br, line);
			//System.out.println("after any blanks, next line is: >" + line + "<");
			line = Utilities.skipInstructorComments(br, line);
			//System.out.println("after instructor comments, next line is: >" + line + "<");
			line = Utilities.skipBlankLines(br, line);			
			//System.out.println("after second set of blanks, next line is: >" + line + "<");
			
			// initialize answers and total marks
			if (answers == null) {					// initialize questions list
				answers = new ArrayList<QuestionAnswer>();
			}
			totalMarks = 0;

			// process student's answers
			int loopCount = 0;						// for debugging
			//line = br.readLine();               	// get first answer line - assume at least one question number on temmplate
			while (line != null && loopCount < 10) {					// more answers to process  
				loopCount++;
				// skip white lines before/between/after questions
				// TODO: need general way of detecting non-query text; e.g. comments, garbage
				//System.out.println("line before skipping blanks is: >" + line + "<");
				line = Utilities.skipBlankLines(br, line);
				line = Utilities.processUserComments(br, line, commWriter, submissionFileName);
				line = Utilities.skipBlankLines(br, line);
				System.out.println("next line to analyze is: >" + line + "<");				
				
				// if not at end of file, process as answer or non-answer text to be skipped
				if (line != null) {
			        matcher = pattern.matcher(line);
			        if (matcher.find()) {				// start of new question
			        	//System.out.println("found new question...");
						// process the first line to get question number and desired query
			        	// TODO: need to generalize to support . or ) as in pattern
						int periodPos = line.indexOf('.');
						// check if beginning of new question
						if (periodPos >= 0) {
							qNumStr = line.substring(4, periodPos);			// skip past --- and space
							System.out.println("\nqNumStr is: >" + qNumStr + "<");
							
							// skip remaining instructor comments with question text
							line = Utilities.skipInstructorComments(br, line);
							//System.out.println("line after skipping instructor comments is: >" + line + "<");
							
							// skip any blank lines after instructor comments
							line = Utilities.skipBlankLines(br, line);
							
							// process any user comments above the answer
							line = Utilities.processUserComments(br, line, commWriter, submissionFileName);
							
							// skip any remaining blank lines before answer
							line = Utilities.skipBlankLines(br, line);
							
							// next line should be start of answer (possibly complete on one line)
							//   unless no answer present, then make answerQueryStr blank
							if (line != null && !Utilities.isInstructorComment(line)) {
								answerQueryStr = line;
							} else if (Utilities.isInstructorComment(line)) {	// if found next question - no answer submitted
								answerQueryStr = "";
							} else {											//  if line is null
								answerQueryStr = "";
							}
							//System.out.println("start of answerQueryStr is: >" + answerQueryStr + "<");
											
							// process the remaining lines for that answer to get the complete query
							if (line != null && !Utilities.isInstructorComment(line)) {
								moreLinesForAnswer = true;
							} else {
								moreLinesForAnswer = false;
							}
							while (line != null && moreLinesForAnswer) {
								line = br.readLine();					// get next line
								if (line != null) {						// if not at end of file...
							        matcher = pattern.matcher(line);
							        // TODO - need to generally check for . or )
							        // TODO - need better new question check than period at less than hard-coded position
							        if (matcher.find() && line.indexOf('.') < 8) {	// if find start of next question
							        	//System.out.println("found start of next question");
							        	moreLinesForAnswer = false;
							        }
							        else if (line.indexOf(';') == -1) {	// look for terminating semicolon,						
							        	//System.out.println("found additional answer line");
							        	answerQueryStr += ("\n" + line); //  if not found, still part of answer
							        }
							        else if (line.indexOf(';') != -1) { // found semicolon, is end of answer								// 
							        	//System.out.println("found last question line, with semicolon");
							        	answerQueryStr += ("\n" + line);
							        	moreLinesForAnswer = false;
										line = br.readLine();			// start toward next question
							        }
							        else {
							        	System.err.println("unexpected answer line condition");
							        }
							        //System.out.println("next line is: >" + line + "<");
								}	// end - if line is not null
							}	// end - while more lines for answer
							//System.out.println("final answer before blank/comment check for " + qNumStr + " is: >" + answerQueryStr.trim() + "<\n");
							
							// process any remaining lines, looking for user comments, possibly surrounded by blank lines
							line = Utilities.skipBlankLines(br, line);
							line = Utilities.processUserComments(br, line, commWriter, submissionFileName);
							line = Utilities.skipBlankLines(br, line);
						}	// end - if period
					
						// remove any trailing semicolon from the answer
						int semiPos = answerQueryStr.indexOf(';');
						if (semiPos != -1) {
							answerQueryStr = answerQueryStr.substring(0, semiPos);
						}
						
						// build the entire question answer
						answerQueryStr = answerQueryStr.trim();
						System.out.println("\nfinal answer for " + qNumStr + " is: >" + answerQueryStr + "<");
						QuestionAnswer answer = 
								new QuestionAnswer(qNumStr, new Query(answerQueryStr), 0.0);
						answers.add(answer);
						
					}	// end - if matcher found the start of a question
				}	// end - if line not null
			}	// end - while more answers to process
		} catch (FileNotFoundException e) {
			System.err.println("Cannot find file " + submissionFileName);
		} catch (IOException ioe) {
			System.err.println("Cannot read from file " + submissionFileName);
		}

	}	// end - method readSubmission
	
	// writeSubmission - write a submission out to file
	public void writeSubmission(String evaluationFolderPath) {
		PrintWriter outWriter = null;						// output file writer
															// output file name, including path
		String outFileName = evaluationFolderPath + submissionFileName + ".out";
		DecimalFormat df = new DecimalFormat();				// decimal format for number display
		df.setMaximumFractionDigits(2);
		
		try {
			outWriter = new PrintWriter(outFileName, "UTF-8");

			// output general information
			outWriter.println("Assignment  : " + submissionName);
			outWriter.println("Student Name: " + studentName);
			//outWriter.println("Student ID  : " + studentID);
			outWriter.println("Answer File : " + submissionFileName);
			outWriter.println("Total Points: " + df.format(totalMarks));
			outWriter.println();
			outWriter.println("Your answers, evaluation and points follow.");
			outWriter.println();
			
			// output answer information for each question answered
			for (int aIndex = 0; aIndex < answers.size(); aIndex++) {
				// output submitted answer information
				QuestionAnswer a = answers.get(aIndex);
				outWriter.println(a.getQNumStr() + ": " + a.getActualQuery());
				outWriter.println();
				
				// output testing information for that answer
				QueryEvaluation qe = queryEvals.get(aIndex);
				outWriter.print("Marks given: " + df.format(qe.getQueryScore()) );
				double maxQuestionMarks = qe.getMaxMarks();
				outWriter.println(" of maximum " + df.format(maxQuestionMarks));
				outWriter.println();
				ArrayList<ISQLTest> tests = qe.getAllTests();
				ArrayList<Integer>  testMarks = qe.getAllTestsResults();
				ArrayList<Integer>  testPcts = qe.getAllTestsPercents();
				for (int testIndex = 0; testIndex < tests.size(); testIndex++) {
					ISQLTest test = tests.get(testIndex);
					int marks = testMarks.get(testIndex);
					int pct = testPcts.get(testIndex);
					// TODO: mark conversion to utility method
					outWriter.println(test.getDesc() + ": " + df.format ( (marks / 100.0) * (qe.getMaxMarks() / 10.0) )
							+ " / " + df.format( (pct / 100.0) * qe.getMaxMarks() ) );
				}
				outWriter.println();
			}

		}
		catch (IOException ioe) {
			System.out.println("IOException in writing to file " + outFileName);
		}
		finally {
			outWriter.close();
		}
	}	// end - method writeSubmission

}	// end - class Submission
