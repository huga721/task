package pl.hszaba.betacomtask.user.domain;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public UserEntity findUserByLogin(String login) {
        var currentUserMaybe = userRepository.findByLogin(login);
        if (currentUserMaybe.isEmpty()) {
            throw new UsernameNotFoundException("Unexpected error");
        }
        return currentUserMaybe.get();
    }
}
