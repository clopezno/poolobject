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
     * Método de prueba para {@link ubu.gii.dass.c01.ReusablePool#getInstanceReusable()}.
     */
    @Test
    @DisplayName("testGetInstance")
    public void testGetInstance() {
        try {
            ReusablePool pool1 = ReusablePool.getInstance();
            ReusablePool pool2 = ReusablePool.getInstance();
            assertNotNull(pool1, "La instancia pool1 no debería ser nula.");
            assertNotNull(pool2, "La instancia pool2 no debería ser nula.");
            assertEquals(pool1, pool2, "Ambos deben ser idénticos");
        } catch (Exception e) {
            fail("Excepción en testGetInstance: " + e.getMessage());
        }
    }

    /**
     * Método de prueba para {@link ubu.gii.dass.c01.ReusablePool#acquireReusable()}.
     */
    @Test
    @DisplayName("testAcquireReusable")
    public void testAcquireReusable() {
        
        // Se almacenan los reusables adquiridos para compararlos
        Reusable[] reusables = new Reusable[ReusablePoolTest.maxResources];
        
        // Adquirir el máximo de instancias disponibles
        for (int numReusable = 0; numReusable < ReusablePoolTest.maxResources; numReusable++) {
            try {
                reusables[numReusable] = pool.acquireReusable();
                assertNotNull(reusables[numReusable], "El reusable obtenido es nulo.");
            } catch (NotFreeInstanceException ex) {
                fail("Error al adquirir una nueva instancia reusable: " + ex.getMessage());
            }
        }
        
        // Verificar que los reusables adquiridos sean distintos entre sí
        for (int i = 0; i < ReusablePoolTest.maxResources - 1; i++) {
            for (int j = i + 1; j < ReusablePoolTest.maxResources; j++) {
                assertNotEquals(reusables[i], reusables[j], "Los reusables deben ser distintos.");
            }
        }
            
        // Comprueba que una nueva adquisición lanza una excepción
        assertThrows(NotFreeInstanceException.class, () -> {
            pool.acquireReusable();
        }, "No se lanzó NotFreeInstanceException al intentar adquirir más instancias.");

        // Caso límite adicional: Liberar una instancia y volver a adquirirla
        try {
            pool.releaseReusable(reusables[0]);
            Reusable reciclado = pool.acquireReusable();
            assertNotNull(reciclado, "El reusable reciclado es nulo.");
            assertSame(reusables[0], reciclado, "El recurso debe ser reutilizado tras liberarlo");
            pool.releaseReusable(reciclado);
        } catch (Exception ex) {
            fail("Error en el caso límite adicional de testAcquireReusable: " + ex.getMessage());
        }
    }

    /**
     * Método de prueba para {@link ubu.gii.dass.c01.ReusablePool#releaseReusable()}.
     */
    @Test
    @DisplayName("testReleaseReusable")
    public void testReleaseReusable() {
        try {
            ReusablePool pool = ReusablePool.getInstance();
            Reusable obj1 = pool.acquireReusable();
            
            assertNotNull(obj1, "El reusable no debería ser nulo.");
            pool.releaseReusable(obj1);
            
            Reusable obj2 = pool.acquireReusable();
            assertSame(obj1, obj2, "El reusable debe ser reutilizado.");
            pool.releaseReusable(obj2);

            // Caso límite: Intentar liberar null debe lanzar excepción
            assertThrows(IllegalArgumentException.class, () -> {
                pool.releaseReusable(null);
            }, "No se lanzó IllegalArgumentException al intentar liberar null.");

            // Caso límite: Liberar un objeto no gestionado por el pool
            // Suponemos que crear un objeto Reusable directamente no está permitido
            Reusable fakeReusable = new Reusable();
            assertThrows(IllegalArgumentException.class, () -> {
                pool.releaseReusable(fakeReusable);
            }, "No se lanzó IllegalArgumentException al intentar liberar un objeto no adquirido previamente.");

            // Caso límite adicional: Intentar liberar nuevamente el mismo objeto
            pool.releaseReusable(obj2);
            assertThrows(DuplicatedInstanceException.class, () -> {
                pool.releaseReusable(obj2);
            }, "No se lanzó DuplicatedInstanceException al intentar liberar dos veces el mismo objeto.");
        } catch (Exception e) {
            fail("Excepción en testReleaseReusable: " + e.getMessage());
        }
    }
}