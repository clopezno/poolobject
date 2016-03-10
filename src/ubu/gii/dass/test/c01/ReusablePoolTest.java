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
 * Test que prueba los distintos métodos de la clase ReusablePool
 * 
 * @author Óscar Eduardo Aguado Díaz
 * @author José Antonio Barbero Aparicio
 * @author Miguel Rodríguez Rico
 * @author Adrián Santamaría Leal
 *
 */
public class ReusablePoolTest {

	ReusablePool pool1;
	ReusablePool pool2;
	Reusable r1;
	Reusable r2;

	/**
	 * Método que incializa las variables usadas
	 * 
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		pool1 = null;
		pool2 = null;
	}

	/**
	 * Método que se encarga de liberar los distintos pool
	 * 
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
		if (r1 != null && r2 != null) {
			pool1.releaseReusable(r1);
			pool1.releaseReusable(r2);
		}
		pool1 = null;
		pool2 = null;
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
	 * 
	 * @throws NotFreeInstanceException
	 */
	@Test(expected = NotFreeInstanceException.class)
	public void testAcquireReusable() throws NotFreeInstanceException {

		pool1 = ReusablePool.getInstance();
		r1 = pool1.acquireReusable();
		r2 = pool1.acquireReusable();
		assertNotEquals(r1, null);
		assertNotEquals(r2, null);

		pool1.acquireReusable();

	}

	/**
	 * Test method for
	 * {@link ubu.gii.dass.c01.ReusablePool#releaseReusable(ubu.gii.dass.c01.Reusable)}
	 * .
	 * 
	 * @throws NotFreeInstanceException
	 * @throws DuplicatedInstanceException
	 */
	@Test(expected = NotFreeInstanceException.class)
	public void testReleaseReusable() throws NotFreeInstanceException, DuplicatedInstanceException {
		pool1 = ReusablePool.getInstance();
		r1 = pool1.acquireReusable();
		r2 = pool1.acquireReusable();
		assertNotEquals(r1, null);
		assertNotEquals(r2, null);

		pool1.acquireReusable();
		// existen dos instancias de reusable y las liberamos una
		pool1.releaseReusable(r1);
		Reusable instanciaNueva = pool1.acquireReusable();
		// ahora si puedo obtener un Reusable ya que he liberado
		assertNotEquals(instanciaNueva, null);

		// ahora compruebo que salta exception si intento liberar uno que ya
		// existe
		pool1.releaseReusable(instanciaNueva);
	}

}
