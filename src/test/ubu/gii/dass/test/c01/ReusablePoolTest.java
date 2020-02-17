/**
 * 
 */
package ubu.gii.dass.test.c01;

import static org.junit.Assert.*;

import java.lang.reflect.Field;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import ubu.gii.dass.c01.DuplicatedInstanceException;
import ubu.gii.dass.c01.NotFreeInstanceException;
import ubu.gii.dass.c01.Reusable;
import ubu.gii.dass.c01.ReusablePool;

/**
 * @author Daniel Setó
 */
public class ReusablePoolTest {

	/**
	 * Inicialización de test
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {		
		// Utilizando Reflection podemos asegurarnos de trabajar siempre
		// con una "nueva" instancia de Singleton en cada Test.
		// Esta solución no me gusta demasiado, pero no veo cómo
		// pueden hacerse las pruebas realmente independientes si no es así.
		Field instance = ReusablePool.class.getDeclaredField("instance");
		instance.setAccessible(true);
		instance.set(null, null);
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	/**
	 * Test method for {@link ubu.gii.dass.c01.ReusablePool#getInstance()}.
	 * 
	 */
	@Test
	public void testGetInstance() {
		ReusablePool pool = ReusablePool.getInstance();
		assertNotNull("Se ha devuelto un objeto nulo", pool);
		
		ReusablePool pool2 = ReusablePool.getInstance();
		assertNotNull("Se ha devuelto un objeto nulo", pool2);
		
		assertSame("Se espera la misma instancia para el Singleton", pool, pool2);
		
	}

	/**
	 * Test method for {@link ubu.gii.dass.c01.ReusablePool#acquireReusable()}.
	 * Test de adquisición de dos objetos reusables diferentes
	 * @throws NotFreeInstanceException 
	 */
	@Test
	public void testAcquireReusable01() throws NotFreeInstanceException {		
		ReusablePool pool = ReusablePool.getInstance();
		
		Reusable r1 = pool.acquireReusable();
		assertNotNull(r1);
		assertTrue(r1 instanceof Reusable);
		
		Reusable r2 = pool.acquireReusable();
		assertNotNull(r2);
		assertTrue(r2 instanceof Reusable);
		
		assertFalse("Se esperaban instancias diferentes de Reusable", r1.util().equals(r2.util()));
		
	}

	/**
	 * Test method for {@link ubu.gii.dass.c01.ReusablePool#acquireReusable()}.
	 * Comprueba la excepción NotFreeInstanceException si se solicitan más de dos objetos reusable
	 * @throws NotFreeInstanceException 
	 */
	@SuppressWarnings("unused")
	@Test(expected = NotFreeInstanceException.class)
	public void testAcquireReusable02() throws NotFreeInstanceException {	
		ReusablePool pool = ReusablePool.getInstance();	
		
		Reusable r1 = pool.acquireReusable();	
		Reusable r2 = pool.acquireReusable();
		Reusable r3 = pool.acquireReusable();
			
	}

	/**
	 * Test method for {@link ubu.gii.dass.c01.ReusablePool#releaseReusable(ubu.gii.dass.c01.Reusable)}.
	 * Comprueba que se liberan correctamente los objetos del pool y quedan a disposición de los clientes.
	 * @throws NotFreeInstanceException 
	 * @throws DuplicatedInstanceException 
	 */
	@Test
	public void testReleaseReusable01() throws NotFreeInstanceException, DuplicatedInstanceException {
		ReusablePool pool = ReusablePool.getInstance();
		Reusable r1 = pool.acquireReusable();
		Reusable r2 = pool.acquireReusable();
		
		pool.releaseReusable(r1);
		pool.releaseReusable(r2);
		
		Reusable r2b = pool.acquireReusable();
		Reusable r1b = pool.acquireReusable();
		
		assertSame("No se recupera el objeto liberado",r1,r1b);
		assertSame("No se recupera el objeto liberado",r2,r2b);
	}
	
	/**
	 * Test method for {@link ubu.gii.dass.c01.ReusablePool#releaseReusable(ubu.gii.dass.c01.Reusable)}.
	 * Comprueba que no se permite liberar un objeto ya liberado.
	 * @throws NotFreeInstanceException 
	 * @throws DuplicatedInstanceException 
	 */
	@Test(expected = DuplicatedInstanceException.class)
	public void testReleaseReusable02() throws NotFreeInstanceException, DuplicatedInstanceException {
		ReusablePool pool = ReusablePool.getInstance();
		
		Reusable r1 = pool.acquireReusable();
		
		pool.releaseReusable(r1);
		pool.releaseReusable(r1);
		
	}
	
}

