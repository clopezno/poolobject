/**
 * 
 */
package ubu.gii.dass.c01;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * @author alumno
 *
 */
public class ReusablePoolTest {
	private static final int num = 2;

	/*
	* Funcionalidad: Se ejecuta después de cada prueba e intenta adquirir todas las instancias reutilizables
	* del pool y luego libera nuevas instancias para restablecer el estado del pool.
	* 
	* @throws NotFreeInstanceException si no hay instancias libres disponibles en el pool.
	* @throws DuplicatedInstanceException si una instancia se libera más de una vez.
	*/
	@AfterEach
	public void despues() {
		ReusablePool pool = ReusablePool.getInstance();
		System.out.println("Antes" + pool);
		try {
			while (true) {
				pool.acquireReusable();
			}
		} catch (NotFreeInstanceException e) {
			try {
				System.out.println("Med" + pool);

				for (int i = 0; i < ReusablePoolTest.num; i++) {
					pool.releaseReusable(new Reusable());
				}
			} catch (Exception e1) {

			}
		}
		System.out.println("Despues" + pool);

	}

	@AfterAll
	public static void tearDown() {
	}

	/**
	 * Test method for {@link ubu.gii.dass.c01.ReusablePool#getInstance()}.
	 */
	@Test
	@DisplayName("testGetInstance")
	public void testGetInstance() {
		ReusablePool pool1 = ReusablePool.getInstance();
		ReusablePool pool2 = ReusablePool.getInstance();
		assertNotNull(pool1);
		assertNotNull(pool2);
		assertSame(pool1, pool2);
		assertEquals(pool1, pool2);
	}

	/**
	 * Test method for {@link ubu.gii.dass.c01.ReusablePool#acquireReusable()}.
	 */
	@Test
	@DisplayName("testAcquireReusable")
	public void testAcquireReusable() {
		ReusablePool pool = ReusablePool.getInstance();
		System.out.println(pool);
		try {
			for (int i = 0; i < ReusablePoolTest.num; i++) {
				pool.acquireReusable();
			}
		} catch (NotFreeInstanceException e) {
			fail("No deberia de llegar aqui");
		}
		assertThrows(NotFreeInstanceException.class, () -> {
			pool.acquireReusable();
		});
	}

	/**
	 * Test method for
	 * {@link ubu.gii.dass.c01.ReusablePool#releaseReusable(ubu.gii.dass.c01.Reusable)}.
	 */
	@Test
	@DisplayName("testReleaseReusable")
	public void testReleaseReusable() {
		ReusablePool pool = ReusablePool.getInstance();
		Reusable reusable = null;
		try {
			reusable = pool.acquireReusable();
		} catch (NotFreeInstanceException e) {
			fail("No debería llegar aquí");
		}
		try {
			pool.releaseReusable(reusable);
		} catch (DuplicatedInstanceException e) {
			fail("No debería llegar aquí");
		}
		Reusable finalReusable = reusable;
		assertThrows(DuplicatedInstanceException.class, () -> pool.releaseReusable(finalReusable));
	}

	/**
	 * Test method for {@link ubu.gii.dass.c01.Reusable#util}.
	 */
	@Test
	@DisplayName("testReusableUtil")
	public void testReusableUtil() {
		assertNotEquals((new Reusable()).util(), (new Reusable()).util());
	}

	/**
	 * Test method for {@link ubu.gii.dass.c01.Client}.
	 */
	@Test
	@DisplayName("testClient")
	public void testClient() {
		assertNotNull(new Client());
		assertDoesNotThrow(() -> Client.main(null));
	}
}
