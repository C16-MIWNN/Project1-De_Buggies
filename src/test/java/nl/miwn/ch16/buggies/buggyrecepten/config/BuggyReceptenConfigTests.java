package nl.miwn.ch16.buggies.buggyrecepten.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.io.IOException;
import java.util.List;

import static org.mockito.Mockito.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class BuggyReceptenConfigTests {

	private BuggyReceptenConfig handler;
	private HttpServletRequest request;
	private HttpServletResponse response;
	private Authentication authentication;

	@BeforeEach
	void setUp() {
		handler = new BuggyReceptenConfig();
		request = mock(HttpServletRequest.class);
		response = mock(HttpServletResponse.class);
		authentication = mock(Authentication.class);
	}

	@Test
	void testRedirectsToAdminPageWhenAdminRole() throws IOException {
		// Arrange
		GrantedAuthority adminAuthority = () -> "ROLE_ADMIN";
		when(authentication.getAuthorities()).thenAnswer(invocation -> List.of(adminAuthority));

		// Act
		handler.onAuthenticationSuccess(request, response, authentication);

		// Assert
		verify(response).sendRedirect("/user/overview");
	}

	@Test
	void testRedirectsToHomePageWhenNonAdminRole() throws IOException {
		// Arrange
		GrantedAuthority userAuthority = () -> "ROLE_USER";
		when(authentication.getAuthorities()).thenAnswer(invocation -> List.of(userAuthority));

		// Act
		handler.onAuthenticationSuccess(request, response, authentication);

		// Assert
		verify(response).sendRedirect("/");
	}
}
