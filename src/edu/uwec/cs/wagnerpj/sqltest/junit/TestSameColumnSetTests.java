/*
 * TestSameColumnSetTests - JUnit test case class to test TestSameColumnSet class
 * 
 * Created - Paul J. Wagner, 01-Nov-2017
 */
package edu.uwec.cs.wagnerpj.sqltest.junit;

import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import edu.uwec.cs.wagnerpj.sqltest.sqltests.TestSameColumnSet;

public class TestSameColumnSetTests extends AbstractTest {
	// data
	TestSameColumnSet testSCS = null;

	@Before
	public void setup() {
		testSCS = new TestSameColumnSet();
	}

	@Test
	public void testSameColumnSet() {
		// queries of same tables with same number of columns are equal
		assertEquals(testSCS.sqlTest(creatureAllQuery, creatureAllQuery.toString()), 10);
		// queries of same tables with * and columns listed are equal
		assertEquals(testSCS.sqlTest(creatureAllQuery, creatureAllQueryBC.toString()), 10);
		// queries of same tables with same columns but in different orders are equal
		assertEquals(testSCS.sqlTest(creatureAllQueryBC, creatureAllQueryBC2.toString()), 10);
		// queries of different tables with same number of columns are not equal
		assertEquals(testSCS.sqlTest(creatureAllQuery, achievementAllQuery.toString()), 0);
		// queries of same table with different column selects are not equal in column count
		assertEquals(testSCS.sqlTest(creatureAllQuery, creatureOneColQuery.toString()), 0);
		// queries of different tables with different column counts are not equal in column count
		assertEquals(testSCS.sqlTest(creatureAllQuery, skillAllQuery.toString()), 0);
		// valid table query and null query are not equal in column count
		assertEquals(testSCS.sqlTest(creatureAllQuery, nullQuery.toString()), 0);
		// valid table query and bad query are not equal in column count
		assertEquals(testSCS.sqlTest(creatureAllQuery, badQuery.toString()), 0);
	}
	
	@After
	public void teardown () {
		testSCS = null;
	}
	
}	// end - test case class TestSameColumnSetTests