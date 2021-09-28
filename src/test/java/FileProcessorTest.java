import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import jdk.jfr.Description;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URL;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
@DisplayName("File Processing Test")
class FileProcessorTest {

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    @Description("Reading of the test file")
    void readLines() {
        ClassLoader classLoader = getClass().getClassLoader();
        URL url = classLoader.getResource("test-data.csv");
        assertNotNull(url);
        FileProcessor processor = new FileProcessor();
        assertTrue(processor.processFile(url.getPath()));
    }

    @Test
    @Description("Validation of traces")
    void testIsValid() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            ClassLoader classLoader = getClass().getClassLoader();
            URL url = classLoader.getResource("invalid-data.csv");
            assertNotNull(url);
            FileProcessor processor = new FileProcessor();
            assertTrue(processor.processFile(url.getPath()));
        });
    }
}