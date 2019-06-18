/*
 * CondSelectCount - class to evaluate condition for query 'select' count
 * 
 * Created - Paul J. Wagner, 1-Oct-2018
 */
package edu.uwec.cs.wagnerpj.sqltest.sqltests;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import edu.uwec.cs.wagnerpj.sqltest.general.Query;
import edu.uwec.cs.wagnerpj.sqltest.util.Utilities;

public class CondSelectCount implements ISQLTest {
	public int sqlTest (Query givenQuery, String condition) {
		int result;						// result on scale 0 to 10
		int thisSelectCt = -1;			// select count returned from this query
		boolean compResult = false;		// result of condition evaluation
		
		// count number of selects in query
		thisSelectCt = Utilities.countMatches(givenQuery.toString(), "SELECT");
		//System.out.println("CondSelectCount thisSelectCt = " + thisSelectCt);
		
		// build full condition from string condition
		String fullCondition = (thisSelectCt + condition);
		//System.out.println("CondSelectCount full condition: >" + fullCondition + "<");
		
		// evaluate full condition
		ScriptEngineManager factory = new ScriptEngineManager();
		ScriptEngine engine = factory.getEngineByName("JavaScript");
		try {
			compResult = (Boolean)engine.eval(fullCondition);
		}
		catch (ScriptException se) {
			System.err.println("CondSelectCount - cannot evaluate condition");
		}
		
		// compare and generate result
		result = compResult ? 10 : 0;
		
		return result;
	}	// end - condSelectCount
	
	public String getName() {
		return "CondSelectCount";
	}
	
	public String getDesc() {
		return "Answer has appropriate number of SELECT keyword";
	}
}	// end - class CondSelectCount
