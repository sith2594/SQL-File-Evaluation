/*
 * CondCountCountTests - JUnit test case class to test CondCountCount class
 *
 * Created - Paul J. Wagner, 5-Oct-2018
 */
package edu.uwec.cs.wagnerpj.sqltest.junit;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import edu.uwec.cs.wagnerpj.sqltest.sqltests.CondCountCount;

public class CondCountCountTests extends AbstractTest {
	// data
	CondCountCount condCC = null;
	
	@Before
	public void setUp() throws Exception {
		condCC = new CondCountCount();
	}

	@After
	public void tearDown() throws Exception {
		condCC = null;
	}

	@Test
	public void testSqlCond() {
		// valid query displaying count with one group by has one count
		assertEquals(condCC.sqlTest(groupByQuery, " >= 1"), 10);
		// valid nested query with two selects but no counts, has count equal to zero
		assertEquals(condCC.sqlTest(nestedQuery, " == 0"), 10);
		// null query has no selects
		assertEquals(condCC.sqlTest(nullQuery, " >= 1"), 0);
		// bad query with select but little else has no counts
		assertEquals(condCC.sqlTest(badQuery, " >= 1"), 0);
		// bad query with garbage has no counts
		assertEquals(condCC.sqlTest(garbageQuery, " >= 1"), 0);
	}

}	// end - class CondSelectCountTests
