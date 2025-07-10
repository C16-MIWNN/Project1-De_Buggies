package nl.miwn.ch16.buggies.buggyrecepten;

import jakarta.servlet.Filter;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class BuggyReceptenApplicationTests {

    @Autowired
    private Filter springSecurityFilterChain;

	@Test
	void contextLoads() {
	}

}
