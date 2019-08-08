/*
 * CondMaxCountTests - JUnit test case class to test CondMaxCount class
 *
 * Created - Paul J. Wagner, 31-Mar-2019
 */
package edu.uwec.cs.wagnerpj.sqltest.junit;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import edu.uwec.cs.wagnerpj.sqltest.sqltests.CondMaxCount;

public class CondMaxCountTests extends AbstractTest {
	// data
	CondMaxCount condMC = null;
	
	@Before
	public void setUp() throws Exception {
		condMC = new CondMaxCount();
	}

	@After
	public void tearDown() throws Exception {
		condMC = null;
	}

	@Test
	public void testSqlCond() {
		// valid query with one max
		assertEquals(condMC.sqlTest(maxQuery, " == 1"), 10);		
		// valid query displaying count with one group by but no max
		assertEquals(condMC.sqlTest(groupByQuery, " >= 1"), 0);
		// valid nested query with two selects but no maxs
		assertEquals(condMC.sqlTest(nestedQuery, " == 0"), 10);
		// null query has no maxs
		assertEquals(condMC.sqlTest(nullQuery, " >= 1"), 0);
		// bad query with select but little else has no maxs
		assertEquals(condMC.sqlTest(badQuery, " >= 1"), 0);
		// bad query with garbage has no maxs
		assertEquals(condMC.sqlTest(garbageQuery, " >= 1"), 0);
	}

}	// end - class CondMaxCountTests
