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

import ubu.gii.dass.c01.Reusable;

/**
 * @author Plamen Petyov Petkov, ...
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
		reusables = new ArrayList<Reusable>(2);
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
		
		for(Reusable r : reusables)
			pool.releaseReusable(r);
		pool = null;
	}

	/**
	 * Test method for {@link ubu.gii.dass.c01.ReusablePool#getInstance()}.
	 */
	@Test
	public void testGetInstance() {
		//TODO
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link ubu.gii.dass.c01.ReusablePool#acquireReusable()}.
	 */
	@Test
	public void testAcquireReusable() {
		//TODO
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link ubu.gii.dass.c01.ReusablePool#releaseReusable(ubu.gii.dass.c01.Reusable)}.
	 */
	@Test
	public void testReleaseReusable() {
		//TODO
		fail("Not yet implemented");
	}

}
