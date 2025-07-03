package pl.hszaba.betacomtask.user.domain;

public interface UserService {
    UserEntity findUserByLogin(String login);
}
