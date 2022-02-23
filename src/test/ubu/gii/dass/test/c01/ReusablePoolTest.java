/**
 * 
 */
package ubu.gii.dass.test.c01;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import ubu.gii.dass.c01.DuplicatedInstanceException;
import ubu.gii.dass.c01.NotFreeInstanceException;
import ubu.gii.dass.c01.Reusable;
import ubu.gii.dass.c01.ReusablePool;
import ubu.gii.dass.c01.Client;

/**
 * @author Andoni Vianez Ulloa
 * @author Yobana Nido Álvarez
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
		assertNotNull("Devuelve un objeto nulo", pool);
		
		ReusablePool pool2 = ReusablePool.getInstance();
		assertNotNull("Devuelve un objeto nulo", pool2);
		
		assertSame("Misma instancia que el Singleton", pool, pool2);
	}

	/**
	 * Test method for {@link ubu.gii.dass.c01.ReusablePool#acquireReusable()}.
	 * @throws NotFreeInstanceException 
	 */
	@Test
	public void testAcquireReusable() {
		Reusable r1, r2, r3 = null;
		try {
			r1 = pool.acquireReusable();
			assertNotNull(r1);
			assertTrue(r1 instanceof Reusable);
			r2 = pool.acquireReusable();
			assertNotNull(r2);
			assertTrue(r2 instanceof Reusable);
			assertFalse(r1.util().equals(r2.util()));
			r3 = pool.acquireReusable();
		} catch (NotFreeInstanceException e) {
			assertNull(r3);
		}
	}

	/**
	 * Test method for {@link ubu.gii.dass.c01.ReusablePool#releaseReusable(ubu.gii.dass.c01.Reusable)}.
	 * @throws NotFreeInstanceException 
	 * @throws DuplicatedInstanceException 
	 */
	@Test
	public void testReleaseReusable() throws NotFreeInstanceException, DuplicatedInstanceException {
		Reusable r1 = null;
		
		r1 = pool.acquireReusable();
		pool.releaseReusable(r1); 

		try {
			pool.releaseReusable(r1); 
		} catch (DuplicatedInstanceException e) { 
			assertTrue(e instanceof DuplicatedInstanceException);
		}
	}
	
	/**
	 * Test method for  {@link ubu.gii.dass.c01.Client#main(ubu.gii.dass.c01.Client)}.
	 * @throws DuplicatedInstanceException 
	 * @throws NotFreeInstanceException 
	 */
	@SuppressWarnings("static-access")
	@Test
	public void testClient() throws NotFreeInstanceException, DuplicatedInstanceException {
		Client client = new Client();
		assertNotNull(client);
		assertTrue(client instanceof Client);
		
		client.main(null);
	}

}
