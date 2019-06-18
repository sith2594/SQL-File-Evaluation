/*
 * CondFromCountTests - JUnit test case class to test CondFromCount class
 *
 * Created - Paul J. Wagner, 1-Oct-2018
 */
package edu.uwec.cs.wagnerpj.sqltest.junit;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import edu.uwec.cs.wagnerpj.sqltest.sqltests.CondFromCount;

public class CondFromCountTests extends AbstractTest {
	// data
	CondFromCount condFC = null;
	
	@Before
	public void setUp() throws Exception {
		condFC = new CondFromCount();
	}

	@After
	public void tearDown() throws Exception {
		condFC = null;
	}

	@Test
	public void testSqlCond() {
		// valid query with one from
		assertEquals(condFC.sqlTest(creatureAllQuery, " >= 1"), 10);
		// valid nested query with two froms
		assertEquals(condFC.sqlTest(nestedQuery, " == 2"), 10);
		// null query has no froms
		assertEquals(condFC.sqlTest(nullQuery, " >= 1"), 0);
		// bad query with select/improper table has no froms
		assertEquals(condFC.sqlTest(badQuery, " >= 1"), 0);
		// bad query with garbage has no froms
		assertEquals(condFC.sqlTest(garbageQuery, " >= 1"), 0);
	}

}	// end - class CondFromCountTests
