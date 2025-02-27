/**
 * 
 */
package ubu.gii.dass.c01;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Disabled;



/**
 * @author  <A HREF="mailto:cvo0004@alu.ubu.es">Carlos Venero Ortega</A>
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
        //Declaro dos objetos ReusablePool y obtengo las instancias de esa clase para cada objeto.
        ReusablePool miPool = ReusablePool.getInstance();;
        ReusablePool miPool2 = ReusablePool.getInstance();;
        
        //Compruebo que ambas instancias no son null.
        assertNotNull(miPool, "miPool no es una null.");
        assertNotNull(miPool2, "miPool2 no es una null.");
        
        //Compruebo que ambas instancias son realmente instancias de ReusablePool.
        assertTrue(miPool instanceof ReusablePool, "miPool es una instancia de ReusablePool.");
        assertTrue(miPool2 instanceof ReusablePool, "miPool2 es una instancia de ReusablePool.");
        
        //Compruebo que ambas instancias son realmente la misma y apuntan al mismo objeto en memoria.
        assertSame(miPool, miPool2, "Ambas instancias apuntan al mismo objeto en memoria.");
	}

        /**
    	 * Test method for {@link ubu.gii.dass.c01.ReusablePool#acquireReusable()}.
    	 * @throws NotFreeInstanceException
    	 */
    	@Test
            @DisplayName("testAcquireReusable")
     
    	public void testAcquireReusable() throws NotFreeInstanceException {
    		//Se obtiene la instancia del pool.
            ReusablePool miPool3 = ReusablePool.getInstance();
            
            //Se intenta adquirir el primer objeto reusable del pool.
            Reusable r1 = miPool3.acquireReusable();
            assertNotNull(r1, "El objeto adquirido no debe ser null.");
            
            //Se intenta adquirir el segundo objeto reusable del pool.
            Reusable r2 = miPool3.acquireReusable();
            assertNotNull(r2, "El segundo objeto adquirido no debe ser null.");
            
            //Se verifica que los objetos adquiridos son diferentes
            assertNotSame(r1, r2, "Los objetos adquiridos deben ser diferentes.");
            
            //Trata de adquirir un tercer objeto reusable y como ya han sido adquiridos los dos existentes lanza la excepción.
            assertThrows(NotFreeInstanceException.class, () -> miPool3.acquireReusable(), "Ya se han adquirido los 2 objetos existentes, por lo que se lanza la excepción.");     
    	}

	/**
	 * Test method for {@link ubu.gii.dass.c01.ReusablePool#releaseReusable(ubu.gii.dass.c01.Reusable)}.
	 */
	@Test
        @DisplayName("testReleaseReusable")
        @Disabled("Not implemented yet")
	public void testReleaseReusable() {
	
	}

}
