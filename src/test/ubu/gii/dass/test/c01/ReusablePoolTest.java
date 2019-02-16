/**
 * 
 */
package ubu.gii.dass.test.c01;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import ubu.gii.dass.c01.DuplicatedInstanceException;
import ubu.gii.dass.c01.NotFreeInstanceException;
import ubu.gii.dass.c01.Reusable;
import ubu.gii.dass.c01.ReusablePool;

/**
 * @author Roberto Luquero
 *
 */
public class ReusablePoolTest {

	private ReusablePool pool;
	private Reusable r1, r2, r3;

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
		r1 = null;
		r2 = null;
		r3 = null;
	}

	/**
	 * Test method for {@link ubu.gii.dass.c01.ReusablePool#getInstance()}.
	 */
	@Test
	public void testGetInstance() {
		// No es nulo
		assertNotNull(pool);

		// El objeto devuelto es instancia de ReusablePool
		assertTrue(pool instanceof ReusablePool);
	}

	/**
	 * Test method for {@link ubu.gii.dass.c01.ReusablePool#acquireReusable()}.
	 */
	@Test
	public void testAcquireReusable() throws NotFreeInstanceException {
		// No es nulo el pool
		assertNotNull(pool);

		// Son nulos los Reusables en un inicio
		assertNull(r1);
		assertNull(r2);
		assertNull(r3);

		// Obtengo los 2 primeros Reusables
		r1 = pool.acquireReusable();
		r2 = pool.acquireReusable();

		// Los 2 Reusables no son nulos
		assertNotNull(r1);
		assertNotNull(r2);

		// Los 2 Reusables son instancia de Reusable
		assertTrue(r1 instanceof Reusable);
		assertTrue(r2 instanceof Reusable);

		// El tercero lanza excepcion
		try {
			r3 = pool.acquireReusable();
		} catch (NotFreeInstanceException e) {
			assertTrue(e.getMessage()
					.contentEquals("No hay más instancias reutilizables disponibles. Reintentalo más tarde"));
		}
	}

	/**
	 * Test method for
	 * {@link ubu.gii.dass.c01.ReusablePool#releaseReusable(ubu.gii.dass.c01.Reusable)}.
	 * 
	 * @throws NotFreeInstanceException
	 * @throws DuplicatedInstanceException
	 */
	@Test
	public void testReleaseReusable() throws NotFreeInstanceException, DuplicatedInstanceException {
		// Los 2 Reusables no son nulos en un principio
		assertNull(r1);
		assertNull(r2);

		// Obtengo los 2 Reusables
		r1 = pool.acquireReusable();
		r2 = pool.acquireReusable();

		// Los 2 Reusables no son nulos
		assertNotNull(r1);
		assertNotNull(r2);

		// Libero los reusables
		pool.releaseReusable(r1);
		pool.releaseReusable(r2);

		// Intentar liberar un reusable libre lanza excepcion.
		try {
			pool.releaseReusable(r1);
		} catch (DuplicatedInstanceException e) {
			assertTrue(e.getMessage().contentEquals("Ya existe esa instancia en el pool."));
		}
	}
}
