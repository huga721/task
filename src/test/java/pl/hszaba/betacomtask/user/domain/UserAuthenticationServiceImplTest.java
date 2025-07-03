package pl.hszaba.betacomtask.user.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import pl.hszaba.betacomtask.common.security.JwtManager;
import pl.hszaba.betacomtask.user.UserRequest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserAuthenticationServiceImplTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private JwtManager userJwtManager;

    private UserAuthenticationServiceImpl service;

    @BeforeEach
    void setUp() {
        service = new UserAuthenticationServiceImpl(userRepository, passwordEncoder, userJwtManager);
    }

    @Test
    void loginUser_successful() {
        // given
        var request = new UserRequest("hubert", "secret");
        var user = UserEntity.create("hubert", "hashed");

        when(userRepository.findByLogin("hubert")).thenReturn(Optional.of(user));
        when(passwordEncoder.matches("secret", "hashed")).thenReturn(true);
        when(userJwtManager.generateToken(user)).thenReturn("fake.jwt.token");

        // when
        String token = service.loginUser(request);

        // then
        assertEquals("fake.jwt.token", token);
    }

    @Test
    void loginUser_wrongPassword_shouldThrow() {
        var request = new UserRequest("hubert", "wrong");
        var user = UserEntity.create("hubert", "hashed");

        when(userRepository.findByLogin("hubert")).thenReturn(Optional.of(user));
        when(passwordEncoder.matches("wrong", "hashed")).thenReturn(false);

        assertThrows(BadCredentialsException.class, () -> service.loginUser(request));
    }

    @Test
    void loginUser_userNotFound_shouldThrow() {
        when(userRepository.findByLogin("hubert")).thenReturn(Optional.empty());

        var request = new UserRequest("hubert", "secret");

        assertThrows(UsernameNotFoundException.class, () -> service.loginUser(request));
    }

    @Test
    void registerUser_successful() {
        var request = new UserRequest("hubert", "secret");

        when(userRepository.existsByLogin("hubert")).thenReturn(false);
        when(passwordEncoder.encode("secret")).thenReturn("hashed");

        service.registerUser(request);

        verify(userRepository).save(any(UserEntity.class));
    }

    @Test
    void registerUser_userAlreadyExists_shouldThrow() {
        var request = new UserRequest("hubert", "secret");

        when(userRepository.existsByLogin("hubert")).thenReturn(true);

        assertThrows(RuntimeException.class, () -> service.registerUser(request));
        verify(userRepository, never()).save(any());
    }
}
