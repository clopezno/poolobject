/**
 * 
 */
package ubu.gii.dass.c01;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.lang.reflect.Field;
import java.util.Vector;

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
	 * 
	 * @author Ahmad Mareie Pascual
	 * Fecha: 09/02/2025
	 * @throws Exception 
	 * 
	 */
	@Test
	@DisplayName("testAcquireReusable")
	public void testAcquireReusable() throws Exception {
		ReusablePool pool = ReusablePool.getInstance();
		Reusable external = new Reusable(); // Asumiendo constructor público
		int initialSize = getPoolSize(pool);
		pool.releaseReusable(external); // Agregar nueva instancia externa
		assertEquals(initialSize + 1, getPoolSize(pool), "Superado el límite de tamaño del pool");
		// Adquirimos todas las instancias (originales + externa)
		for (int i = 0; i < initialSize + 1; i++) {
			pool.acquireReusable();
		}
		try {
			pool.acquireReusable();
			fail("Debería lanzar NotFreeInstanceException");
		} catch (NotFreeInstanceException e) {
			System.err.println(e.getMessage());
		}
	}

    // Método helper para obtener el tamaño del pool usando reflection
    private int getPoolSize(ReusablePool pool) throws Exception {
        Field reusablesField = ReusablePool.class.getDeclaredField("reusables");
        reusablesField.setAccessible(true);
        Vector<Reusable> reusables = (Vector<Reusable>) reusablesField.get(pool);
        return reusables.size();
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
