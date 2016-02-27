/**
 * 
 */
package ubu.gii.dass.test.c01;

import static org.junit.Assert.*;
import ubu.gii.dass.c01.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Clase Test que prueba la clase ReusablePool con 100% de cobertura.
 * 
 * @author CUEVAS D�EZ JOS� RAM�N
 * @author L�PEZ MAR�N LAURA
 * @author CUADRADO GARC�A IRENE
 * @author EPIKHIN ANTON
 */
public class ReusablePoolTest {

	/**
	 * Pool que contendrá los objetos reusables
	 */
	private ReusablePool pool;
	/**
	 * Objetos reusables;
	 */
	private Reusable reusable1, reusable2;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		pool = ReusablePool.getInstance();
		reusable1 = pool.acquireReusable();
		reusable2 = pool.acquireReusable();
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
		pool.releaseReusable(reusable1);
		pool.releaseReusable(reusable2);
	}

	/**
	 * Test method for {@link ubu.gii.dass.c01.ReusablePool#getInstance()}.
	 */
	@Test
	public void testGetInstance() {
		// Adquiere instancia de ReusablePool.
		ReusablePool poolTest = ReusablePool.getInstance();

		// Comprueba que pool y poolTest son instancia de ReusablePool.
		assertTrue(pool instanceof ReusablePool);
		assertTrue(poolTest instanceof ReusablePool);

		// Compara los dos objetos son de la misma instancia.
		// Debido a que el metodo 'equals' no es sobreescrito, 'equals' y '=='
		// dar�n el mismo resultado.
		assertTrue(pool == poolTest);
		assertTrue(pool.equals(poolTest));
	}

	/**
	 * Test method for {@link ubu.gii.dass.c01.ReusablePool#acquireReusable()}.
	 */
	@Test
	public void testAcquireReusable() {
		try {
			// acquirir correctamente
			pool.releaseReusable(reusable2);
			Reusable reusable3 = pool.acquireReusable();

			// acquirir sin posibilidad de hacerlo, NotFreeInstance
			Reusable reusable4 = pool.acquireReusable();

		} catch (NotFreeInstanceException e) {
			assertEquals("No hay m�s instancias reutilizables disponibles. Reintentalo m�s tarde", e.getMessage());
		} catch (DuplicatedInstanceException e) {
			assertEquals("Ya existe esa instancia en el pool.", e.getMessage());
		}

	}

	/**
	 * Test method for
	 * {@link ubu.gii.dass.c01.ReusablePool#releaseReusable(ubu.gii.dass.c01.Reusable)}
	 * .
	 * 
	 * @throws NotFreeInstanceException
	 */
	@Test
	public void testReleaseReusable() throws NotFreeInstanceException {
		try {
			// se libera del pool un objeto reusable, correcto
			pool.releaseReusable(reusable1);
			// se intenta liberar el mismo objeto reusable,
			// DuplicatedInstanceException
			pool.releaseReusable(reusable1);
		} catch (DuplicatedInstanceException e) {
			assertEquals("Ya existe esa instancia en el pool.", e.getMessage());
		}

		// se a�ade el objeto reusable para dejar el pool vacio (estado inicial)
		reusable1 = pool.acquireReusable();
	}

}
