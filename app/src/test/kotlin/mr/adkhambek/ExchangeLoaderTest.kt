package mr.adkhambek

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow


internal class ExchangeLoaderTest {

    @Test
    fun `sample test 1`() {
        assertDoesNotThrow {
            ExchangeLoader.invoke()
        }
    }
}