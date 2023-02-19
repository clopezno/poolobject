/**
 *
 */
package ubu.gii.dass.test.c01;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import ubu.gii.dass.c01.*;

/**
 * @author Montoya Ramírez José Jesús
 *
 */
public class ReusablePoolTest {

	private ReusablePool pool;

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
	 */
	@Test
	public void testGetInstance() {
		ReusablePool pool = ReusablePool.getInstance();

		assertNotNull(pool);

		assertTrue(pool instanceof ReusablePool);
	}

	/**
	 * Test method for {@link ubu.gii.dass.c01.ReusablePool#acquireReusable()}.
	 */
	@Test
	public void testAcquireReusable() {
		try {
			Reusable reu1 = pool.acquireReusable();
			Reusable reu2 = pool.acquireReusable();
			assertNotNull(reu1);
			assertNotNull(reu2);
			assertSame(reu1, reu2);
			assertTrue(reu1 instanceof Reusable);
			assertTrue(reu2 instanceof Reusable);

			Reusable reu3 = pool.acquireReusable();
			assertNotNull(reu3);

		} catch (Exception e) {

		}
	}

	/**
	 * Test method for {@link ubu.gii.dass.c01.ReusablePool#releaseReusable(ubu.gii.dass.c01.Reusable)}.
	 */
	@Test
	public void testReleaseReusable() {
		try {
			Reusable reu1 = pool.acquireReusable();
			Reusable reu2 = pool.acquireReusable();
			assertNotNull(reu1);
			assertNotNull(reu2);
			pool.releaseReusable(reu2);
			try {
				pool.releaseReusable(reu2);

			} catch (DuplicatedInstanceException e) {
				// al ejecutarse otra vez el pool.releaseReusable(reu2)
				assertTrue(true);
			}

		} catch (Exception e) {
			fail("Unexpected exception: " + e.getClass().getSimpleName());
		}
	}
}
