package ubu.gii.dass.c01;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Disabled;

/**
 * @author <A HREF="mailto:cvo0004@alu.ubu.es">Carlos Venero Ortega</A>
 * @author <A HREF="mailto:ifp1001@alu.ubu.es">Iván Fernández Pardo</A>
 */
public class ReusablePoolTest {

    @BeforeAll
    public static void setUp() {
        // Setup before all tests (if needed)
    }

    @AfterAll
    public static void tearDown() throws Exception {
        // Cleanup after all tests (if needed)
    }

    /**
     * Test method for {@link ubu.gii.dass.c01.ReusablePool#getInstance()}.
     */
    @Test
    @DisplayName("testGetInstance")
    public void testGetInstance() {
        // Obtener dos instancias del pool
        ReusablePool instance1 = ReusablePool.getInstance();
        ReusablePool instance2 = ReusablePool.getInstance();

        // Verificar que ambas referencias apuntan al mismo objeto (Singleton)
        assertNotNull(instance1, "La instancia no debería ser nula");
        assertSame(instance1, instance2, "Las dos instancias deberían ser la misma");
    }

    /**
     * Test method for {@link ubu.gii.dass.c01.ReusablePool#acquireReusable()}.
     */
    @Test
    @DisplayName("testAcquireReusable")
    public void testAcquireReusable() throws NotFreeInstanceException, DuplicatedInstanceException {
        // Obtener la instancia del pool (patrón Singleton)
        ReusablePool pool = ReusablePool.getInstance();
        
        // Intentar adquirir el primer objeto del pool
        Reusable result1 = pool.acquireReusable();  // Adquirir el primer objeto
        assertNotNull(result1, "El objeto adquirido no debe ser null.");
        
        // Intentar adquirir el segundo objeto del pool
        Reusable result2 = pool.acquireReusable();  // Adquirir otro objeto
        assertNotNull(result2, "El segundo objeto adquirido no debe ser null.");
        
        // Verificar que los objetos adquiridos son diferentes
        assertNotSame(result1, result2, "Los objetos adquiridos deben ser diferentes.");
        
        // Liberamos los objetos para que puedan ser reutilizados
        pool.releaseReusable(result1);
        pool.releaseReusable(result2);
    }

    /**
     * Test method for {@link ubu.gii.dass.c01.ReusablePool#releaseReusable(ubu.gii.dass.c01.Reusable)}.
     */
    @Test
    @DisplayName("testReleaseReusable")
    @Disabled("Not implemented yet")
    public void testReleaseReusable() {
        // Esta prueba aún no ha sido implementada
    }
}


