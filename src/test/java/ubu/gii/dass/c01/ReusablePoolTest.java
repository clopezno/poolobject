package ubu.gii.dass.c01;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
/**
 * @author Sofía Calavia
 * @author Andrés Puentes
 * @author Mario Cea
 * @author Alejandro García
 *
 */
public class ReusablePoolTest {

    /**
     * Configuración inicial antes de todas las pruebas
     *
     * @throws java.lang.Exception
     */
    @BeforeAll
    public static void setUp() throws Exception {
    }

    /**
     * Limpieza después de todas las pruebas
     *
     * @throws java.lang.Exception
     */
    @AfterAll
    public static void tearDown() throws Exception {
    }

    /**
     * Método de prueba para {@link ubu.gii.dass.c01.ReusablePool#getInstance()}.
     */
    @Test
    @DisplayName("testGetInstance")
    public void testGetInstance() {
        // Obtener dos instancias del singleton ReusablePool
        ReusablePool instance1 = ReusablePool.getInstance();
        assertNotNull(instance1, "La instancia adquirida no debe ser nula");
        ReusablePool instance2 = ReusablePool.getInstance();
        assertSame(instance1, instance2);
    }

    /**
     * Método de prueba para {@link ubu.gii.dass.c01.ReusablePool#acquireReusable()}.
     */
    @Test
    @DisplayName("testAcquireReusable")
    public void testAcquireReusable() {
        ReusablePool pool = ReusablePool.getInstance();
        try {
            // Adquirir un objeto reusable del pool
            Reusable reusable = pool.acquireReusable();
            assertNotNull(reusable, "El objeto reusable adquirido no debería ser nulo");
            assertTrue(reusable instanceof Reusable, "El objeto adquirido debe ser una instancia de Reusable");
        } catch (NotFreeInstanceException e) {
            fail("No se lanzaría una excepción si hemos adquirido un objeto reusable del pool");
        }
    }

    /**
     * Método de prueba para {@link ubu.gii.dass.c01.ReusablePool#releaseReusable(ubu.gii.dass.c01.Reusable)}.
     *
     * @throws NotFreeInstanceException
     */
    @Test
    @DisplayName("testReleaseReusable")
    public void testReleaseReusable() throws DuplicatedInstanceException, NotFreeInstanceException {
        ReusablePool pool = ReusablePool.getInstance();
        Reusable reusable = new Reusable();
        try {
            // Liberar el objeto reusable en el pool
            pool.releaseReusable(reusable);
            Reusable acqReusable = pool.acquireReusable();
            assertEquals(reusable, acqReusable, "El objeto que ha sido adquirido debe ser el mismo que el liberado");
            assertNotNull(pool.acquireReusable(), "El pool debería tener al menos un objeto reusable después de liberarlo");
        } catch (DuplicatedInstanceException e) {
            fail("No debería saltar una excepción al liberar un objeto reusable");
        } catch (NotFreeInstanceException e) {
            fail("No se debe lanzar una excepción al adquirir un objeto reusable");
        }
    }

    /**
     * Método de prueba para excepción cuando no hay instancias libres disponibles.
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
                assertEquals("No hay más instancias reutilizables disponibles. Reinténtalo más tarde", e.getMessage());
            }

            pool.releaseReusable(r1);
            pool.releaseReusable(r2);

        } catch (Exception e) {
            fail("Unexpected exception: " + e.getMessage());
        }
    }

    /**
     * Método de prueba para excepción al liberar una instancia duplicada.
     */
    @Test
    @DisplayName("testReleaseReusableDuplicated")
    public void testReleaseReusableDuplicated() {
        ReusablePool pool = ReusablePool.getInstance();
        try {
            Reusable reusable = pool.acquireReusable();
            pool.releaseReusable(reusable);

            try {
                pool.releaseReusable(reusable);
                fail("Should have thrown DuplicatedInstanceException");
            } catch (DuplicatedInstanceException e) {
                assertEquals("Ya existe esa instancia en el pool.", e.getMessage());
            }

        } catch (Exception e) {
            fail("Unexpected exception: " + e.getMessage());
        }
    }

    /**
     * Prueba para el correcto funcionamiento del pool con múltiples operaciones.
     */
    @Test
    @DisplayName("testPoolCycleOperations")
    public void testPoolCycleOperations() {
        ReusablePool pool = ReusablePool.getInstance();
        try {
            Reusable r1 = pool.acquireReusable();
            Reusable r2 = pool.acquireReusable();
            assertNotNull(r1, "First reusable should not be null");
            assertNotNull(r2, "Second reusable should not be null");
            assertTrue(r1 != r2, "Should be different objects");

            pool.releaseReusable(r2);
            pool.releaseReusable(r1);

            Reusable r3 = pool.acquireReusable();
            Reusable r4 = pool.acquireReusable();
            assertSame(r1, r3, "First released object should be first acquired");
            assertSame(r2, r4, "Second released object should be second acquired");

            pool.releaseReusable(r3);
            pool.releaseReusable(r4);

        } catch (Exception e) {
            fail("Unexpected exception: " + e.getMessage());
        }
    }

    /**
     * Método de prueba para la funcionalidad Reusable.util().
     */
    @Test
    @DisplayName("testReusableUtil")
    public void testReusableUtil() {
        Reusable reusable = new Reusable();
        String utilString = reusable.util();
        assertTrue(utilString.contains(String.valueOf(reusable.hashCode())),
                "Util string should contain the object's hashcode");
        assertTrue(utilString.contains(":Uso del objeto Reutilizable"),
                "Util string should contain the expected message");
    }

    /**
     * Método de prueba para las clases de excepción.
     */
    @Test
    @DisplayName("testExceptionMessages")
    public void testExceptionMessages() {
        NotFreeInstanceException notFreeEx = new NotFreeInstanceException();
        assertEquals("No hay más instancias reutilizables disponibles. Reinténtalo más tarde",
                notFreeEx.getMessage(),
                "NotFreeInstanceException should have correct message");

        DuplicatedInstanceException dupEx = new DuplicatedInstanceException();
        assertEquals("Ya existe esa instancia en el pool.",
                dupEx.getMessage(),
                "DuplicatedInstanceException should have correct message");
    }
}
