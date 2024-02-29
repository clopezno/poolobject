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
 * @author Victor Manuel Martinez Garcia
 * @author Lorena Bueno Porras
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
		ReusablePool pool2 = ReusablePool.getInstance();
		//No es nulo
		assertNotNull(pool);
		assertNotNull(pool2);
		//El objeto devuelto es una instancia de ReusablePool
		assertTrue(pool instanceof ReusablePool);
		assertTrue(pool2 instanceof ReusablePool);
		//Comprobamos que se cumple el metodo Singleton
		assertEquals(pool,pool2);
	}

	/**
	 * Test method for {@link ubu.gii.dass.c01.ReusablePool#acquireReusable()}.
	 */
	@Test
	public void testAcquireReusable() throws NotFreeInstanceException {
		// Como gestiona 2 objetos, creamos ambos.
		ReusablePool pool = ReusablePool.getInstance();
		try {
			Reusable pool1 = pool.acquireReusable();
			Reusable pool2 = pool.acquireReusable();
			
			assertNotNull(pool1);
			assertNotNull(pool2);
			pool1.util();	
			pool2.util();		
			
		}catch (Exception e){
			// Si no hay instancias de objetos reusables disponibles
			fail("Fallo del test");
			
		}
		
	}

	/**
	 * Test method for {@link ubu.gii.dass.c01.ReusablePool#releaseReusable(ubu.gii.dass.c01.Reusable)}.
	 */
	@Test
	public void testReleaseReusable() {
		//fail("Not yet implemented");
	}

}
