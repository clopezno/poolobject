/**
 * 
 */
package ubu.gii.dass.c01;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Disabled;

/**
 * @author alumno
 *
 */
public class ReusablePoolTest {

	@BeforeAll
	public static void setUp() {
	}

	@AfterAll
	public static void tearDown() throws Exception {
	}

	/**
	 * Test method for {@link ubu.gii.dass.c01.ReusablePool#getInstance()}.
	 */
	@Test
	@DisplayName("testGetInstance")
	@Disabled("Not implemented yet")
	public void testGetInstance() {

	}

	/**
	 * Test method for {@link ubu.gii.dass.c01.ReusablePool#acquireReusable()}.
	 */
	@Test
	@DisplayName("testAcquireReusable")
	@Disabled("Not implemented yet")

	public void testAcquireReusable() {

	}

	/**
	 * Test method for
	 * {@link ubu.gii.dass.c01.ReusablePool#releaseReusable(ubu.gii.dass.c01.Reusable)}.
	 * Realizado por: Luis Ignacio de Luna Gómez
	 * Fecha: 09/02/2025
		 * @throws DuplicatedInstanceException 
		 */
		@Test
		@DisplayName("git commit -m \"testReleaseReusable: Implementación del test para liberar y reutilizar objetos en ReusablePool\"\r\n" + //
						"")
		//@Disabled("Not implemented yet") //Desactivar para poder ejectuar la prueba
		public void testReleaseReusable() throws DuplicatedInstanceException {

		try {
			// Creo una instancia del pool
			ReusablePool pool = ReusablePool.getInstance();

			// Adquiero un objeto reutilizable
			Reusable objeto = pool.acquireReusable();

			assertNotNull(objeto, "El objeto adquirido no debe ser nulo");

			// Libero el objeto reutilizable
			pool.releaseReusable(objeto);

			// Adquiero un objeto reutilizable de nuevo
			Reusable objetoNuevo = pool.acquireReusable();
			assertSame(objeto, objetoNuevo, "El objeto adquirido debe ser el mismo que el liberado");

			//Verifico que el pool sigue permitiendonos la adquisición de objetos
			Reusable objetoVerificador = pool.acquireReusable();
			assertNotNull(objetoVerificador, "El objeto adquirido no debe ser nulo");
		}

		catch (NotFreeInstanceException e) {
			e.printStackTrace();
			assertTrue(false, "No debería lanzar excepción");
		}

	}

}
