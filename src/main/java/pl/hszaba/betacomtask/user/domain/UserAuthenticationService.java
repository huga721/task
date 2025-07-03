package pl.hszaba.betacomtask.user.domain;

import pl.hszaba.betacomtask.user.UserRequest;

public interface UserAuthenticationService {

    String loginUser(UserRequest userLoginRequest);
    void registerUser(UserRequest userLoginRequest);
}
