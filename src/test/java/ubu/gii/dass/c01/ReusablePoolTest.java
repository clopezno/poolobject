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
			//Verifica que el pool tiene un objeto antes de liberarlo
			 assertNotNull(pool.acquireReusable(), "El pool debería tener al menos un objeto reusable después de liberarlo");
		 } catch (DuplicatedInstanceException e) {
			//Si se lanza una excepción, imprime un mensaje de error
			fail("No debería saltar una excepcion al liberar un objeto reusable");
		 } catch (NotFreeInstanceException e) {
			//Si se lanza una excepción, imprime un mensaje de error
			fail("No se debe lanzar una excepción al adquirir un objeto reusable");
		 }
	 }

	/**
	 * Test method for exception when no free instances are available.
	 */
	@Test
	@DisplayName("testAcquireReusableWhenNoFreeInstances")
	public void testAcquireReusableWhenNoFreeInstances() {
		// Instancia un pool
		ReusablePool pool = ReusablePool.getInstance();
		
		try {
			// Adquiere 2 pools disponibles
			Reusable r1 = pool.acquireReusable();
			Reusable r2 = pool.acquireReusable();
			
			// Verifica que ambos objetos no son nulls y que estan instanciados
			assertNotNull(r1, "First reusable should not be null");
			assertNotNull(r2, "Second reusable should not be null");
			
			// Trata de adquirir un tercer reusable (deberia hacer un throw NotFreeInstanceException)
			try {
				pool.acquireReusable();
				fail("Should have thrown NotFreeInstanceException");
			} catch (NotFreeInstanceException e) {
				// Excepcion esperada
				assertEquals("No hay más instancias reutilizables disponibles. Reintentalo más tarde", e.getMessage());
			}
			// Limpia y devuelve los objetos al pool
			pool.releaseReusable(r1);
			pool.releaseReusable(r2);
			
		} catch (Exception e) {
			fail("Unexpected exception: " + e.getMessage());
		}
	}

	/**
	 * Test method for exception when releasing a duplicate instance.
	 */
	@Test
	@DisplayName("testReleaseReusableDuplicated")
	public void testReleaseReusableDuplicated() {
		// Instancia un pool
		ReusablePool pool = ReusablePool.getInstance();
		
		try {
			// Adquiere a reusable
			Reusable reusable = pool.acquireReusable();
			pool.releaseReusable(reusable);
			
			// Intenta hacerlo de nuevo (sino throw DuplicatedInstanceException)
			try {
				pool.releaseReusable(reusable);
				fail("Should have thrown DuplicatedInstanceException");
			} catch (DuplicatedInstanceException e) {
				// Excepcion esperada
				assertEquals("Ya existe esa instancia en el pool.", e.getMessage());
			}
			
		} catch (Exception e) {
			fail("Unexpected exception: " + e.getMessage());
		}
	}

	/**
	 * Test for proper functioning of the pool with multiple operations.
	 */
	@Test
	@DisplayName("testPoolCycleOperations")
	public void testPoolCycleOperations() {
		// Consigue la instancia pool
		ReusablePool pool = ReusablePool.getInstance();
		
		try {
            // Paso 1: adquiere todas las variables reusables disponibles
			Reusable r1 = pool.acquireReusable();
			Reusable r2 = pool.acquireReusable();
			
			// Paso 2: Verifica que sean objetos diferentes
			assertNotNull(r1, "First reusable should not be null");
			assertNotNull(r2, "Second reusable should not be null");
			assertTrue(r1 != r2, "Should be different objects");
			
            // Paso 3: Cambia el orden
			pool.releaseReusable(r2);
			pool.releaseReusable(r1);
			
            // Paso 4: Readquiere y verifica el orden LIFO
			Reusable r3 = pool.acquireReusable();
			Reusable r4 = pool.acquireReusable();
			
            // El pool deberia de retornar objetos siguiendo el orden LIFO
			assertSame(r1, r3, "First released object should be first acquired");
			assertSame(r2, r4, "Second released object should be second acquired");
			
			pool.releaseReusable(r3);
			pool.releaseReusable(r4);
			
		} catch (Exception e) {
			fail("Unexpected exception: " + e.getMessage());
		}
	}

	/**
	 * Test method for Reusable.util() functionality.
	 */
	@Test
	@DisplayName("testReusableUtil")
	public void testReusableUtil() {
		// Crea un objeto reusable y un string
		Reusable reusable = new Reusable();
		String utilString = reusable.util();
		
		//  Verifica si contiene lo esperado
		assertTrue(utilString.contains(String.valueOf(reusable.hashCode())), 
				   "Util string should contain the object's hashcode");
		assertTrue(utilString.contains(":Uso del objeto Reutilizable"), 
				   "Util string should contain the expected message");
	}

	/**
	 * Test method for exception classes.
	 */
	@Test
	@DisplayName("testExceptionMessages")
	public void testExceptionMessages() {
		// Test NotFreeInstanceException
		NotFreeInstanceException notFreeEx = new NotFreeInstanceException();
		assertEquals("No hay más instancias reutilizables disponibles. Reintentalo más tarde", 
					 notFreeEx.getMessage(), 
					 "NotFreeInstanceException should have correct message");
		
		// Test DuplicatedInstanceException
		DuplicatedInstanceException dupEx = new DuplicatedInstanceException();
		assertEquals("Ya existe esa instancia en el pool.", 
					 dupEx.getMessage(), 
					 "DuplicatedInstanceException should have correct message");
	}
}
