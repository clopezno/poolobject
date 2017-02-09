/**
 * 
 */
package ubu.gii.dass.test.c01;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import ubu.gii.dass.c01.ReusablePool;

import java.util.List;
import java.util.ArrayList;

import ubu.gii.dass.c01.DuplicatedInstanceException;
import ubu.gii.dass.c01.NotFreeInstanceException;
import ubu.gii.dass.c01.Reusable;

/**
 * @author Plamen Petyov Petkov, Rodrigo Martinez Bravo, Napoleón Cortés Mata, Maria Viyuela Fernandez
 *
 */
public class ReusablePoolTest {

	private ReusablePool pool;
	private List<Reusable> reusables;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {

		pool = ReusablePool.getInstance();
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {

		pool = null;
	}

	/**
	 * Test method for {@link ubu.gii.dass.c01.ReusablePool#getInstance()}.
	 * 
	 * Tests the following conditions:
	 * - if {@link ubu.gii.dass.c01.ReusablePool#getInstance()} returns not null
	 * - if two invocations of {@link ubu.gii.dass.c01.ReusablePool#getInstance()}
	 * 
	 */
	@Test
	public void testGetInstance() {

		ReusablePool localPool = ReusablePool.getInstance();

		assertNotNull(pool); // assert pool not null
		assertNotNull(localPool); // assert localPool not null
		assertEquals(pool, localPool); // assert getInstance returns the same
										// ReusablePool instance
	}

	/**
	 * Test method for {@link ubu.gii.dass.c01.ReusablePool#acquireReusable()}.
	 * 
	 * @throws NotFreeInstanceException
	 */
	@Test(expected = NotFreeInstanceException.class)
	public void testAcquireReusable() throws NotFreeInstanceException {
		
		Reusable r1 = pool.acquireReusable();	// acquire a Reusable instance
		assertNotNull(r1);	// assert first reusable not null
		assertTrue(r1 instanceof Reusable); // asserts first reusable is instance of Reusable

		Reusable r2 = pool.acquireReusable();	// acquire a Reusable instance
		assertNotNull(r2);	// assert second reusable not null
		assertTrue(r2 instanceof Reusable); // asserts second reusable is instance of Reusable
		
		assertNotEquals(r1, r2);	// assert r1 and r2 hold different instances of Reusable
		
		pool.acquireReusable();	// NotFreeInstanceException intentionally thrown
	}

	/**
	 * Test method for
	 * {@link ubu.gii.dass.c01.ReusablePool#releaseReusable(ubu.gii.dass.c01.Reusable)}
	 * .
	 * 
	 * @throws DuplicatedInstanceException
	 */
	@Test(expected = DuplicatedInstanceException.class)
	public void testReleaseReusable() throws DuplicatedInstanceException,
			NotFreeInstanceException {
		
		Reusable r1 = pool.acquireReusable();
		Reusable r2 = pool.acquireReusable();
			
		pool.releaseReusable(r1);	// release first Reusable instance
		pool.releaseReusable(r2);	// release second Reusable instance
		
		// ReusablePool behaves as a stack of Reusable instances
		// The last released Reusable instance is the first to be acquired 
		assertEquals(r2, pool.acquireReusable());	// assert acquired r2
		
		assertNotEquals(r1, r2);
				
		pool.releaseReusable(r1); // DuplicatedInstanceException intentionally thrown
	}
	
	@Test
	public void testReleaseNewReusable() throws NotFreeInstanceException,
		DuplicatedInstanceException {
		
		Reusable r1 = pool.acquireReusable();
		Reusable r2 = pool.acquireReusable();
		Reusable newReusable = new Reusable();
		
		pool.releaseReusable(r1);
		pool.releaseReusable(r2);
		pool.releaseReusable(newReusable);	// This is illegal and should not happen !!!
		
		fail("You should not be able to release external Reusable instances to the pool !!!");
	}

}
