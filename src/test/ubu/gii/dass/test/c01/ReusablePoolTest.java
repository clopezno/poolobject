/**
 *
 */
package ubu.gii.dass.test.c01;

import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;

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
	 * @throws NotFreeInstanceException
	 */
	@Test
	public void testAcquireReusable() throws NotFreeInstanceException {

		Reusable reu1 = pool.acquireReusable();
		Reusable reu2 = pool.acquireReusable();
		assertNotNull(reu1);
		assertNotNull(reu2);

		assertTrue(reu1 instanceof Reusable);
		assertTrue(reu2 instanceof Reusable);

		assertTrue(reu1.util().contains("Uso del objeto Reutilizable"));


		// Si no queda espacio libre el ReusablePool devuelve una excepcion
		try {
			Reusable reu3 = pool.acquireReusable();
		} catch (Exception e) {

			assertTrue(e instanceof NotFreeInstanceException);
		}

	}

	/**
	 * Test method for {@link ubu.gii.dass.c01.ReusablePool#releaseReusable(ubu.gii.dass.c01.Reusable)}.
	 */
	@Test
	public void testReleaseReusable() {
		ReusablePool pool = ReusablePool.getInstance();
		try {
			Reusable reu1 = pool.acquireReusable();

			assertNotNull(reu1);

			pool.releaseReusable(reu1);
			try {
				pool.releaseReusable(reu1);

			} catch (DuplicatedInstanceException e) {
				// al ejecutarse otra vez el pool.releaseReusable(reu2)
				assertTrue(true);
			}

		} catch (Exception e) {
			fail("Unexpected exception: " + e.getClass().getSimpleName());
		}
	}


	@Test
	public void testClient() {

		// Redirect standard output to capture logging messages
		ByteArrayOutputStream outContent = new ByteArrayOutputStream();

		try {
			Client.main(new String[]{});
		} catch (Exception e) {
			fail("Client threw an unexpected exception.");
		}
	}

}
