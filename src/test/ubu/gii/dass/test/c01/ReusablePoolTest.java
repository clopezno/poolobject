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

/**
 * @author alumno
 *
 */
public class ReusablePoolTest {

	

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		
		
		
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
		ReusablePool reus = ReusablePool.getInstance();
		assert(reus==ReusablePool.getInstance());
		
	}

	/**
	 * Test method for {@link ubu.gii.dass.c01.ReusablePool#acquireReusable()}.
	 */
	@Test
	public void testAcquireReusable() {
		ReusablePool RPool=ReusablePool.getInstance();
		int cont=2;
		while(cont>0) {
		try{
			RPool.acquireReusable();
			
		}catch(NotFreeInstanceException ex){
			assert(true);
		}
		cont--;
		}
	}

	/**
	 * Test method for {@link ubu.gii.dass.c01.ReusablePool#releaseReusable(ubu.gii.dass.c01.Reusable)}.
	 */
	@Test
	public void testReleaseReusable() {
		ReusablePool RPool=ReusablePool.getInstance();
		Reusable r = new Reusable();
		Reusable a = new Reusable();
		int cont=3;
		while(cont>0) {
			try {
				RPool.releaseReusable(r);
				RPool.releaseReusable(a);
			} catch (DuplicatedInstanceException e) {
				// TODO Auto-generated catch block
				assert(true);
			}
		cont--;
		}
	}

}
