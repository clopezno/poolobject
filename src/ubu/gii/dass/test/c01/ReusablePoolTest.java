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

import ubu.gii.dass.c01.NotFreeInstanceException;
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
		//Comprobamos que tanto el contenido como la instancia no sea null
		  assertNotNull(pool);
		  //Comprobamos si creando otro objeto tienen el mismo contenido
		  ReusablePool prueba2= ReusablePool.getInstance();
		  assertTrue(pool==prueba2);
		  assertTrue(pool.equals(prueba2));
		  }

	/**
	 * Test method for {@link ubu.gii.dass.c01.ReusablePool#acquireReusable()}.
	 * @throws NotFreeInstanceException 
	 */
	@Test(expected= NotFreeInstanceException.class)
	public void testAcquireReusable() throws NotFreeInstanceException {
		//TODO
		reusables.add(pool.acquireReusable());
		//Me tiene que devolver un valor nulo
		assertNotNull("El objeto es null", reusables.get(0));
		//El reusable es del tipo Reusable
		assertTrue("No es del tipo reusable.", reusables.get(0) instanceof Reusable);
		//Hago dos llamadas mas para comprobar que salta la excepcion
		reusables.add(pool.acquireReusable());
		reusables.add(pool.acquireReusable());
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
