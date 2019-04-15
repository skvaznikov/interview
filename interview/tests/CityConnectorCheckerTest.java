
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import java.io.PrintStream;

import org.junit.jupiter.api.Test;

import interview.CityConnectionChecker;

class CityConnectorCheckerTest {

	@Test
	void checkNotConnected() {
		PrintStream out = mock(PrintStream.class);
		System.setOut(out);
		CityConnectionChecker.main(new String[] { "Cleveland", "Denver", "connected.txt" });
		verify(out).println("NOT CONNECTED");
	}

	@Test
	void checkConnected() {
		PrintStream out = mock(PrintStream.class);
		System.setOut(out);
		CityConnectionChecker.main(new String[] { "Cleveland", "New York", "connected.txt" });
		verify(out).println("CONNECTED");
	}

}
