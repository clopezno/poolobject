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

	ReusablePool pool1;
	ReusablePool pool2;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		pool1 = null;
		pool2 = null;
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
		assert pool1 == null;
		assert pool2 == null;
		pool1 = ReusablePool.getInstance();
		assert pool1 != null;
		pool2 = ReusablePool.getInstance();
		assert pool1.equals(pool1);
	}

	/**
	 * Test method for {@link ubu.gii.dass.c01.ReusablePool#acquireReusable()}.
	 * @throws NotFreeInstanceException 
	 */
	@Test(expected=NotFreeInstanceException.class)
	public void testAcquireReusable() throws NotFreeInstanceException {
		
		pool1 = ReusablePool.getInstance();
		Reusable r1 = pool1.acquireReusable();
		Reusable r2 = pool1.acquireReusable();
		assertNotEquals(r1, null);	
		assertNotEquals(r2, null);	
			
		pool1.acquireReusable();
			
	}

	/**
	 * Test method for {@link ubu.gii.dass.c01.ReusablePool#releaseReusable(ubu.gii.dass.c01.Reusable)}.
	 */
	@Test
	public void testReleaseReusable() {
		fail("Not yet implemented");
	}

}
