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
		ReusablePool instance1 = ReusablePool.getInstance();
      		assertNotNull (instance1,"La instancia adquirida no debe ser nula");
      		ReusablePool instance2 = ReusablePool.getInstance();
      		assertSame(instance1, instance2);		
	}
	
	/**
	 * Test method for {@link ubu.gii.dass.c01.ReusablePool#releaseReusable(ubu.gii.dass.c01.Reusable)}.
	 * @throws NotFreeInstanceException 
	*/ 
	 @Test
	 @DisplayName("testReleaseReusable")
	 public void testReleaseReusable() throws DuplicatedInstanceException, NotFreeInstanceException {	 		ReusablePool pool = ReusablePool.getInstance();
			Reusable reusable = new Reusable();
		 try{
			 pool.releaseReusable(reusable);
			 Reusable acqReusable = pool.acquireReusable();
			 
			 assertEquals(reusable, acqReusable, "El objeto que ha sido adquirido debe ser el mismo que el liberado");
			 assertNotNull(pool.acquireReusable(), "El pool debería tener al menos un objeto reusable después de liberarlo");
		 } catch (DuplicatedInstanceException e) {
			fail("No debería saltar una excepcion al liberar un objeto reusable");
		 } catch (NotFreeInstanceException e) {
			fail("No se debe lanzar una excepción al adquirir un objeto reusable");
		 }
	 }

	
	/**
	 * Test method for exception when no free instances are available.
	 */
	@Test
	@DisplayName("testAcquireReusableWhenNoFreeInstances")
	public void testAcquireReusableWhenNoFreeInstances() {
		ReusablePool pool = ReusablePool.getInstance();
		
		try {
			Reusable r1 = pool.acquireReusable();
			Reusable r2 = pool.acquireReusable();
			
			assertNotNull(r1, "First reusable should not be null");
			assertNotNull(r2, "Second reusable should not be null");

			try {
				pool.acquireReusable();
				fail("Should have thrown NotFreeInstanceException");
			} catch (NotFreeInstanceException e) {
				assertEquals("No hay más instancias reutilizables disponibles. Reintentalo más tarde", e.getMessage());
			}

			pool.releaseReusable(r1);
			pool.releaseReusable(r2);
			
		} catch (Exception e) {
			fail("Unexpected exception: " + e.getMessage());
		}
	}

	
}
