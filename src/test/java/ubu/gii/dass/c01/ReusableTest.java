package ubu.gii.dass.c01;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;

/**
 * @author Adrián Zamora Sánchez
 *
 */
public class ReusableTest {

    @Test
    @DisplayName("testUtil")
    public void testUtil() {
        // Se genera una isntancia de Reusable para hacer la prueba
        Reusable reusable = new Reusable();

        // Llamamos al método util() y obtenemos el resultado
        String result = reusable.util();

        // Verificamos que el resultado no sea nulo
        assertNotNull(result, "El resultado no debe ser nulo");

        // Verificamos que el resultado contenga el hashCode() del objeto
        assertTrue(result.contains(String.valueOf(reusable.hashCode())), "El resultado debe contener el hashCode del objeto");
    }
}
