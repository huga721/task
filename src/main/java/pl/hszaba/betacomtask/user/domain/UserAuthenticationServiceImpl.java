package pl.hszaba.betacomtask.user.domain;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.hszaba.betacomtask.common.exceptions.UserAlreadyExistsException;
import pl.hszaba.betacomtask.common.security.JwtManager;
import pl.hszaba.betacomtask.user.UserRequest;

import static java.lang.String.format;

@Slf4j
@Service
@RequiredArgsConstructor
class UserAuthenticationServiceImpl implements UserAuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtManager jwtManager;

    @Override
    public String loginUser(UserRequest request) {
        var userMaybe = userRepository.findByLogin(request.login());
        if (userMaybe.isEmpty()) {
            throw new UsernameNotFoundException(format("User with %s not found", request.login()));
        }
        var user = userMaybe.get();

        if (!passwordEncoder.matches(request.password(), user.getPassword())) {
            throw new BadCredentialsException("Password doesn't match");
        }

        return jwtManager.generateToken(user);
    }

    @Transactional
    @Override
    public void registerUser(UserRequest request) {
        if (userRepository.existsByLogin(request.login())) {
            throw new UserAlreadyExistsException(format("User with %s login already exists", request.login()));
        }

        var hashedPassword = passwordEncoder.encode(request.password());
        var user = UserEntity.create(request.login(), hashedPassword);

        userRepository.save(user);
        log.info("User '{}' registered successfully", request.login());
    }
}
