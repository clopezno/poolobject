/**
 * 
 */
package ubu.gii.dass.c01;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;



/**
 * @author Sofía Calavia
 * @author Andrés Puentes
 * @author Mario Cea
 * @author Alejandro García
 *
 */
public class ReusablePoolTest {

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeAll
	public static void setUp() throws Exception{
	}

	
	@AfterAll
	public static void tearDown() throws Exception {
	}

	/**
	 * Test method for {@link ubu.gii.dass.c01.ReusablePool#getInstance()}.
	 */
        @Test
        @DisplayName("testGetInstance")
	public void testGetInstance() {
		// Crear una instancia del pool
		ReusablePool instance1 = ReusablePool.getInstance();

			//Verifica que la instancia no sea nula
      		assertNotNull (instance1,"La instancia adquirida no debe ser nula");

			//Comprueba si la instancia 1 es igual que la instancia 2
      		ReusablePool instance2 = ReusablePool.getInstance();
      		assertSame(instance1, instance2);		
	}

	/**
	 * Test method for {@link ubu.gii.dass.c01.ReusablePool#acquireReusable()}.
	*/
		@Test
    	@DisplayName("testAcquireReusable")
	public void testAcquireReusable() {
		// Crear una instancia del pool
		ReusablePool pool = ReusablePool.getInstance();

		try {
			//Va a adquirir un objeto reusable del pool
			Reusable reusable = pool.acquireReusable();

			// Verifica que el objeto reusable adquirido no es nulo
			assertNotNull(reusable,"El objeto reusable adquirido no debería ser nulo");

			// Verifica que el objeto adquirido es una instancia de la clase Reusable
        	assertTrue(reusable instanceof Reusable, "El objeto adquirido debe ser una instancia de Reusable");


		} catch (NotFreeInstanceException e) {
			// Si hay excepción, imprimirá un mensaje de error
			fail("No se lanzaría una excepción si hemos adquirirido un objeto reusable del pool");
        }
		
	}

	/**
	 * Test method for {@link ubu.gii.dass.c01.ReusablePool#releaseReusable(ubu.gii.dass.c01.Reusable)}.
		 * @throws NotFreeInstanceException 
		*/ 
		 @Test
		 @DisplayName("testReleaseReusable")
		 public void testReleaseReusable() throws DuplicatedInstanceException, NotFreeInstanceException {
			// Crear una instancia del pool
	 		ReusablePool pool = ReusablePool.getInstance();

			//Crea un nuevo objeto reusable
			Reusable reusable = new Reusable();

		 try{
			//Libera este objeto del pool
			 pool.releaseReusable(reusable);

			//Intenta adquirir un nuevo objeto de este
			 Reusable acqReusable = pool.acquireReusable();

			// Verifica si el objeto adquirido es el mismo que el objeto liberado
			 assertEquals(reusable, acqReusable, "El objeto que ha sido adquirido debe ser el mismo que el liberado");

			//Verifica que el poll tiene un objeto antes de liberarlo
			 assertNotNull(pool.acquireReusable(), "El pool debería tener al menos un objeto reusable después de liberarlo");

		 } catch (DuplicatedInstanceException e) {
			//Si se lanza una excepción, imprime un mensaje de error
			fail("No debería saltar una excepcion al liberar un objeto reusable");

		 } catch (NotFreeInstanceException e) {
			//Si se lanza una excepción, imprime un mensaje de error
			fail("No se debe lanzar una excepción al adquirir un objeto reusable");

		 }
	 }
}
