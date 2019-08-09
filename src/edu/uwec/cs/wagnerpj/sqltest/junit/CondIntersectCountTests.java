/*
 * CondIntersectCountTests - JUnit test case class to test CondIntersectCount class
 *
 * Created - Paul J. Wagner, 08-Aug-2019
 */
package edu.uwec.cs.wagnerpj.sqltest.junit;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import edu.uwec.cs.wagnerpj.sqltest.sqltests.CondIntersectCount;

public class CondIntersectCountTests extends AbstractTest {
	// data
	CondIntersectCount condIC = null;
	
	@Before
	public void setUp() throws Exception {
		condIC = new CondIntersectCount();
	}

	@After
	public void tearDown() throws Exception {
		condIC = null;
	}

	@Test
	public void testSqlCond() {
		// valid query with one intersect
		assertEquals(condIC.sqlTest(intersectQuery, " == 1"), 10);		
		// valid query displaying count with one group by but no intersect
		assertEquals(condIC.sqlTest(groupByQuery, " >= 1"), 0);
		// valid nested query with two selects but no intersects
		assertEquals(condIC.sqlTest(nestedQuery, " == 0"), 10);
		// null query has no intersects
		assertEquals(condIC.sqlTest(nullQuery, " >= 1"), 0);
		// bad query with select but little else has no intersects
		assertEquals(condIC.sqlTest(badQuery, " >= 1"), 0);
		// bad query with garbage has no intersects
		assertEquals(condIC.sqlTest(garbageQuery, " >= 1"), 0);
	}

}	// end - class CondIntersectCountTests
