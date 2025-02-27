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

/**
 * Clase de pruebas unitarias.
 *  @author Ibai Moya
 *  @author David Peñasco
 *  @author Nicolás Pérez
 *  @author Juan García
 * 
 */
public class ReusablePoolTest {
    private static ReusablePool pool;
    private static final int MAX_RESOURCES = 2;

    /**
     * Reinicia la instancia del pool antes de cada prueba.
     * @throws Exception si ocurre un error al reiniciar la instancia.
     */
    @BeforeEach
    public void resetPool() throws Exception {
        Field instanceField = ReusablePool.class.getDeclaredField("instance");
        instanceField.setAccessible(true);
        instanceField.set(null, null);
        pool = ReusablePool.getInstance();
    }
    
    /**
     * Libera todas las instancias del pool al finalizar todas las pruebas.
     * @throws Exception si ocurre un error al liberar las instancias.
     */
    @AfterAll
    public static void tearDown() throws Exception {
        List<Reusable> acquiredReusables = new ArrayList<>();
        /* Se adquieren todas las instancias disponibles del pool. */
        while (true) {
            try {
                acquiredReusables.add(pool.acquireReusable());
            } catch (NotFreeInstanceException e) {
                break;
            }
        }
        /* Se liberan las instancias obtenidas. */
        for (Reusable reusable : acquiredReusables) {
            try {
                pool.releaseReusable(reusable);
            } catch (DuplicatedInstanceException e) {
                System.err.println("Error al liberar una instancia: " + e.getMessage());
            }
        }
    }
    
    /**
     * Limpia el pool de reusables al finalizar cada prueba.
     */
    @AfterEach
    public void clearPools() {
        List<Reusable> tempList = new ArrayList<>();
        try {
            /* Acumula todas las instancias adquiribles. */
            while (true) {
                tempList.add(pool.acquireReusable());
            }
        } catch (NotFreeInstanceException e) {
            /* Cuando no se pueden adquirir más instancias se sale del bucle. */
        }
        /* Se devuelve todas las instancias acumuladas para restaurar el pool. */
        for (Reusable reusable : tempList) {
            try {
                pool.releaseReusable(reusable);
            } catch (Exception ex) {
                /* Se ignoran las excepciones durante la restauración del pool. */
            }
        }
    }
    
    /**
     * Método de prueba para la clase {@link ubu.gii.dass.c01.ReusablePool#getInstance()}.
     */
    @Test
    @DisplayName("testGetInstance")
    public void testGetInstance() {
        try {
            ReusablePool pool1 = ReusablePool.getInstance();
            ReusablePool pool2 = ReusablePool.getInstance();
            assertNotNull(pool1, "La instancia pool1 no debería ser nula.");
            assertNotNull(pool2, "La instancia pool2 no debería ser nula.");
            assertSame(pool1, pool2, "Ambos deben ser idénticos.");
            assertEquals(pool1, pool2, "Ambos deben ser iguales.");
        } catch (Exception e) {
            fail("Excepción en testGetInstance: " + e.getMessage());
        }
    }
    
    /**
     * Método de prueba para la clase {@link ubu.gii.dass.c01.ReusablePool#acquireReusable()}.
     */
    @Test
    @DisplayName("testAcquireReusable")
    public void testAcquireReusable() {
        /* Se almacenan las instancias adquiridas para posteriores comprobaciones. */
        Reusable[] reusables = new Reusable[MAX_RESOURCES];
        
        /* Se adquieren el número máximo de instancias disponibles. */
        for (int i = 0; i < MAX_RESOURCES; i++) {
            try {
                reusables[i] = pool.acquireReusable();
                assertNotNull(reusables[i], "El reusable obtenido es nulo.");
            } catch (NotFreeInstanceException e) {
                fail("Error al adquirir una nueva instancia reusable: " + e.getMessage());
            }
        }
        
        /* Se verifica que cada instancia adquirida es única. */
        for (int i = 0; i < MAX_RESOURCES - 1; i++) {
            for (int j = i + 1; j < MAX_RESOURCES; j++) {
                assertNotEquals(reusables[i], reusables[j], "Los reusables deben ser distintos.");
            }
        }
        
        /* Se comprueba que una nueva adquisición lanza la excepción esperada. */
        assertThrows(NotFreeInstanceException.class, () -> {
            pool.acquireReusable();
        }, "No se lanzó NotFreeInstanceException al intentar adquirir más instancias.");
        
        /* Se prueba el caso límite: liberar una instancia y volver a adquirirla. */
        try {
            pool.releaseReusable(reusables[0]);
            Reusable reciclado = pool.acquireReusable();
            assertNotNull(reciclado, "El reusable reciclado es nulo.");
            assertSame(reusables[0], reciclado, "El recurso debe ser reutilizado tras liberarlo.");
            pool.releaseReusable(reciclado);
        } catch (Exception e) {
            fail("Error en el caso límite adicional de testAcquireReusable: " + e.getMessage());
        }
    }
    
    /**
     * Método de prueba para la clase {@link ubu.gii.dass.c01.ReusablePool#releaseReusable(ubu.gii.dass.c01.Reusable)}.
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
    
            /* Se prueba el caso límite: liberar null no debe lanzar excepción. */
            assertDoesNotThrow(() -> pool.releaseReusable(null), "releaseReusable(null) no debe lanzar excepción.");
    
            /* Se prueba el caso límite: liberar un objeto no gestionado por el pool no debe lanzar excepción. */
            Reusable fakeReusable = new Reusable();
            assertDoesNotThrow(() -> pool.releaseReusable(fakeReusable), "releaseReusable(fakeReusable) no debe lanzar excepción.");
    
            /* Se prueba el caso límite: intentar liberar nuevamente el mismo objeto debe lanzar excepción. */
            assertThrows(DuplicatedInstanceException.class, () -> {
                pool.releaseReusable(obj2);
            }, "No se lanzó DuplicatedInstanceException al intentar liberar dos veces el mismo objeto.");
        } catch (Exception e) {
            fail("Excepción en testReleaseReusable: " + e.getMessage());
        }
    }
    
    /**
     * Método de prueba para la clase {@link ubu.gii.dass.c01.Reusable#util()}.
     */
    @Test
    @DisplayName("testUtilMethod")
    public void testUtilMethod() {

        /* Se comprueba que el método util() retorne valores diferentes para instancias distintas. */
        Reusable reusableA = new Reusable();
        Reusable reusableB = new Reusable();
        assertNotEquals(reusableA.util(), reusableB.util(), "Se esperaba que las salidas de util() fueran diferentes.");
    }
    
    /**
     * Método de prueba para la clase {@link ubu.gii.dass.c01.Client#main()}.
     */
    @Test
    @DisplayName("testClientMain")
    public void testClientMain() {

        /* Se valida que Client se inicializa correctamente y que su método main se ejecute sin incidencias. */
        Client clientInstance = new Client();
        assertNotNull(clientInstance, "Se requería que la instancia de Client no fuese nula.");
        assertDoesNotThrow(() -> Client.main(new String[]{}), "La ejecución de Client.main no debió lanzar excepción.");
    }
}