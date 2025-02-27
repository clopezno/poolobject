package ubu.gii.dass.c01;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.ArrayList;

public class ReusablePoolTest {
    private static ReusablePool pool;
    private static final int maxResources = 2;

    @BeforeAll
    public static void setUp() {
        pool = ReusablePool.getInstance();
    }

    @AfterAll
    public static void tearDown() throws Exception {
        List<Reusable> acquiredReusables = new ArrayList<>();
            
        while (true) {
            try {
                acquiredReusables.add(pool.acquireReusable());
            } catch (NotFreeInstanceException e) {
                break; 
            }
        }
        
        for (Reusable reusable : acquiredReusables) {
            try {
                pool.releaseReusable(reusable);
            } catch (DuplicatedInstanceException e) {
                System.err.println("Error al liberar una instancia: " + e.getMessage());
            }
        }    
    }

    /**
     * Test method for {@link ubu.gii.dass.c01.ReusablePool#getInstanceReusable()}.
     */
    @Test
    @DisplayName("testGetInstance")
    public void testGetInstance() {
        try {
            ReusablePool pool1 = ReusablePool.getInstance();
            ReusablePool pool2 = ReusablePool.getInstance();
            assertNotNull(pool1);
            assertNotNull(pool2);
            assertEquals(pool1, pool2, "Ambas son identicas");
        } catch (Exception e) {
            fail("Exception en testGetInstance " + e.getMessage());
        }
    }

    /**
     * Test method for {@link ubu.gii.dass.c01.ReusablePool#acquireReusable()}.
     */
    @Test
    @DisplayName("testAcquireReusable")
    public void testAcquireReusable() {
        
        // Se almacenan los reusables adquiridos para compararlos
        Reusable[] reusables = new Reusable[ReusablePoolTest.maxResources];
        
        for (int numReusable = 0; numReusable < ReusablePoolTest.maxResources; numReusable++) {
            try {
                reusables[numReusable] = pool.acquireReusable();
                assertNotNull(reusables[numReusable], "El reusable obtenido es nulo.");
            } catch (NotFreeInstanceException ex) {
                fail("Error al adquirir una nueva instancia reusable: " + ex.getMessage());
            }
        }
        
        // Verifica que los reusables adquiridos sean distintos entre sí
        for (int i = 0; i < ReusablePoolTest.maxResources - 1; i++) {
            for (int j = i + 1; j < ReusablePoolTest.maxResources; j++) {
                assertNotEquals(reusables[i], reusables[j], "Los reusables deben ser distintos.");
            }
        }
            
        // Comprueba que una nueva adquisición lanza una excepción
        try {
            pool.acquireReusable();
            fail("Excepcion NotFreeInstanceException no se ha lanzado al adquirir una nueva instancia reusable");
        } catch (NotFreeInstanceException ex) {
            // Se espera una excepción, si llega hasta aquí, el código es correcto.
        }
    }

    /**
     * Test method for {@link ubu.gii.dass.c01.ReusablePool#releaseReusable()}.
     */
    @Test
    @DisplayName("testReleaseReusable")
    public void testReleaseReusable() {
        try {
            ReusablePool pool = ReusablePool.getInstance();
            Reusable obj1 = pool.acquireReusable();
            
            assertNotNull(obj1, "No deberia ser null");
            pool.releaseReusable(obj1);
            
            Reusable obj2 = pool.acquireReusable();
            assertSame(obj1, obj2, "Tiene que ser reusado");
            pool.releaseReusable(obj2);

            // Try to release the same object again to trigger DuplicatedInstanceException
            assertThrows(DuplicatedInstanceException.class, () -> {
                pool.releaseReusable(obj2);
            });
        } catch (Exception e) {
            fail("Excepcion en testReleaseReusable: " + e.getMessage());
        }
    }
}