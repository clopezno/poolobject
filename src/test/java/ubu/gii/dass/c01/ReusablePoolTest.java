/**
 * 
 */
package ubu.gii.dass.c01;

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
	public static void setUp(){
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
	 
	@Test
        @DisplayName("testAcquireReusable")
        @Disabled("Not implemented yet")

	public void testAcquireReusable() {
		
	}

	/**
	 * Test method for {@link ubu.gii.dass.c01.ReusablePool#releaseReusable(ubu.gii.dass.c01.Reusable)}.
	
	@Test
        @DisplayName("testReleaseReusable")
        @Disabled("Not implemented yet")
	public void testReleaseReusable() {
		
	}
 */
}
