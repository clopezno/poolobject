package ubu.gii.dass.c01;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class ReusablePoolTest {
    private static ReusablePool pool;
    private static final int maxResources = 2;

    @BeforeEach
    public void resetPool() throws Exception {
        Field instanceField = ReusablePool.class.getDeclaredField("instance");
        instanceField.setAccessible(true);
        instanceField.set(null, null);
        pool = ReusablePool.getInstance();
    }
    
    @AfterAll
    public static void tearDown() throws Exception {
        List<Reusable> acquiredReusables = new ArrayList<>();
        // Adquirir todas las instancias disponibles del pool.
        while (true) {
            try {
                acquiredReusables.add(pool.acquireReusable());
            } catch (NotFreeInstanceException e) {
                break;
            }
        }
        // Liberar las instancias obtenidas.
        for (Reusable reusable : acquiredReusables) {
            try {
                pool.releaseReusable(reusable);
            } catch (DuplicatedInstanceException e) {
                System.err.println("Error al liberar una instancia: " + e.getMessage());
            }
        }
    }
    
    // Limpieza adicional tras cada prueba para restablecer el estado del pool
    @AfterEach
    public void clearPools() {
        try {
            while (true) {
                pool.acquireReusable();
            }
        } catch (NotFreeInstanceException e) {
            try {
                System.out.println("Intermedio: " + pool);
                for (int i = 0; i < maxResources; i++) {
                    pool.releaseReusable(new Reusable());
                }
            } catch (Exception ex) {
                // Se ignoran excepciones en la limpieza.
            }
        }
    }
    
    @Test
    @DisplayName("testGetInstance")
    public void testGetInstance() {
        try {
            ReusablePool pool1 = ReusablePool.getInstance();
            ReusablePool pool2 = ReusablePool.getInstance();
            assertNotNull(pool1, "La instancia pool1 no debería ser nula.");
            assertNotNull(pool2, "La instancia pool2 no debería ser nula.");
            assertSame(pool1, pool2, "Ambos deben ser idénticos");
            assertEquals(pool1, pool2, "Ambos deben ser iguales");
        } catch (Exception e) {
            fail("Excepción en testGetInstance: " + e.getMessage());
        }
    }
    
    @Test
    @DisplayName("testAcquireReusable")
    public void testAcquireReusable() {
        // Almacenar las instancias adquiridas para posteriores comprobaciones
        Reusable[] reusables = new Reusable[maxResources];
        
        // Adquirir el número máximo de instancias disponibles
        for (int i = 0; i < maxResources; i++) {
            try {
                reusables[i] = pool.acquireReusable();
                assertNotNull(reusables[i], "El reusable obtenido es nulo.");
            } catch (NotFreeInstanceException e) {
                fail("Error al adquirir una nueva instancia reusable: " + e.getMessage());
            }
        }
        
        // Verificar que cada instancia adquirida es única
        for (int i = 0; i < maxResources - 1; i++) {
            for (int j = i + 1; j < maxResources; j++) {
                assertNotEquals(reusables[i], reusables[j], "Los reusables deben ser distintos.");
            }
        }
        
        // Comprobar que una nueva adquisición lanza la excepción esperada
        assertThrows(NotFreeInstanceException.class, () -> {
            pool.acquireReusable();
        }, "No se lanzó NotFreeInstanceException al intentar adquirir más instancias.");
        
        // Caso límite: liberar una instancia y volver a adquirirla
        try {
            pool.releaseReusable(reusables[0]);
            Reusable reciclado = pool.acquireReusable();
            assertNotNull(reciclado, "El reusable reciclado es nulo.");
            assertSame(reusables[0], reciclado, "El recurso debe ser reutilizado tras liberarlo");
            pool.releaseReusable(reciclado);
        } catch (Exception e) {
            fail("Error en el caso límite adicional de testAcquireReusable: " + e.getMessage());
        }
    }
    
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
    
            // Caso límite: liberar null no debe lanzar excepción.
            assertDoesNotThrow(() -> pool.releaseReusable(null), "releaseReusable(null) no debe lanzar excepción.");
    
            // Caso límite: liberar un objeto no gestionado por el pool.
            Reusable fakeReusable = new Reusable();
            assertDoesNotThrow(() -> pool.releaseReusable(fakeReusable), "releaseReusable(fakeReusable) no debe lanzar excepción.");
    
            // Caso límite: intentar liberar nuevamente el mismo objeto debe lanzar excepción.
            assertThrows(DuplicatedInstanceException.class, () -> {
                pool.releaseReusable(obj2);
            }, "No se lanzó DuplicatedInstanceException al intentar liberar dos veces el mismo objeto.");
        } catch (Exception e) {
            fail("Excepción en testReleaseReusable: " + e.getMessage());
        }
    }
    
    @Test
    @DisplayName("testReusableUtil")
    public void testReusableUtil() {
        // Verificar que el método util() genera resultados individuales
        assertNotEquals((new Reusable()).util(), (new Reusable()).util(),
                "Las utilidades de reusables deben ser distintas.");
    }
    
    @Test
    @DisplayName("testClient")
    public void testClient() {
        // Comprobar que la clase Client se instancia y su método main se ejecuta sin errores
        assertNotNull(new Client(), "El cliente no debería ser nulo.");
        assertDoesNotThrow(() -> Client.main(null), "Client.main no debería lanzar excepción.");
    }
}