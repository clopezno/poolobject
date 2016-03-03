/**
 * 
 */
package ubu.gii.dass.test.c01;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import ubu.gii.dass.c01.NotFreeInstanceException;
import ubu.gii.dass.c01.Reusable;
import ubu.gii.dass.c01.ReusablePool;

/**
 * @author alumno
 *
 */
public class ReusablePoolTest {

	ReusablePool pool = null;
	ReusablePool pool2;
	ReusablePool pool3;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		pool = ReusablePool.getInstance();
		pool2 = null;
		pool3 = null;
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	/**
	 * Test method for {@link ubu.gii.dass.c01.ReusablePool#getInstance()}.
	 */
	@Test
	public void testGetInstance() {
		assert pool2 == null;
		assert pool3 == null;
		pool2 = ReusablePool.getInstance();
		assert pool2 != null;
		pool3 = ReusablePool.getInstance();
		assert pool2.equals(pool2);
	}

	/**
	 * Test method for {@link ubu.gii.dass.c01.ReusablePool#acquireReusable()}.
	 * @throws NotFreeInstanceException 
	 */
	@Test(expected=NotFreeInstanceException.class)
	public void testAcquireReusable() throws NotFreeInstanceException {
		//boolean SaltaException =false;
		
			Reusable r1 = pool.acquireReusable();
			Reusable r2 = pool.acquireReusable();
			
			assertNotEquals(r1, null);	
			assertNotEquals(r2, null);	
			
			 pool.acquireReusable();
			
	}

	/**
	 * Test method for {@link ubu.gii.dass.c01.ReusablePool#releaseReusable(ubu.gii.dass.c01.Reusable)}.
	 */
	@Test
	public void testReleaseReusable() {
		fail("Not yet implemented");
	}

}
