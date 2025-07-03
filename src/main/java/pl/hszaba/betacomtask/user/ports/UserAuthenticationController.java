package pl.hszaba.betacomtask.user.ports;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.hszaba.betacomtask.user.UserRequest;
import pl.hszaba.betacomtask.user.domain.UserAuthenticationService;

@RestController
@RequestMapping("/v1")
@RequiredArgsConstructor
public class UserAuthenticationController {

    private final UserAuthenticationService userAuthenticationService;

    @Operation(summary = "Login", description = "Authenticate with the platform.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful authentication")
    })
    @PostMapping("/login")
    ResponseEntity<String> login(@RequestBody @Valid UserRequest userRequest) {
        return ResponseEntity.ok(userAuthenticationService.loginUser(userRequest));
    }

    @Operation(summary = "Register", description = "Register to the platform.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Registering successful")
    })
    @PostMapping("/register")
    ResponseEntity<Void> register(@RequestBody @Valid UserRequest userRequest) {
        userAuthenticationService.registerUser(userRequest);
        return ResponseEntity.noContent().build();
    }
}
