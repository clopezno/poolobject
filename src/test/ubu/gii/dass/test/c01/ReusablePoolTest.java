/**
 * 
 */
package ubu.gii.dass.test.c01;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Field;

import ubu.gii.dass.c01.ReusablePool;
import ubu.gii.dass.c01.NotFreeInstanceException;
import ubu.gii.dass.c01.Reusable;

/**
 * @author alumno
 *
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
		// (Porque no es posible garantizar que una prueba conserve
		// el estado interno de una instancia Singleton)
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
	 * Comprueba el funcionamiento correcto del patrón Singleton
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
	 * Test method for {@link ubu.gii.dass.c01.ReusablePool#releaseReusable(ubu.gii.dass.c01.Reusable)}.
	 */
	@Test
	public void testReleaseReusable() {
		fail("Not yet implemented");
	}

}
