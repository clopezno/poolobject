package ubu.gii.dass.c01;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Disabled;

public class ReusablePoolTest {

    @BeforeAll
    public static void setUp(){
    }

    @AfterAll
    public static void tearDown() throws Exception {
    }

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

    @Test
    @DisplayName("testAcquireReusable")
    public void testAcquireReusable() {
        try {
            ReusablePool pool = ReusablePool.getInstance();
            Reusable obj1 = pool.acquireReusable();
            Reusable obj2 = pool.acquireReusable();

            assertNotNull(obj1, "El primero no tiene que ser null");
            assertNotNull(obj2, "El segundo no tiene que ser null");
            assertNotSame(obj1, obj2, "Los objetos tienen que ser diferentes");
        } catch (Exception e) {
            fail("Excepcion en testAcquireReusable: " + e.getMessage());
        }
    }

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
        } catch (Exception e) {
            fail("Excepcion en testReleaseReusable: " + e.getMessage());
        }
    }
}