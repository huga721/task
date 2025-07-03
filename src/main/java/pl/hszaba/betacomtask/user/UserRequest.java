package pl.hszaba.betacomtask.user;

import jakarta.validation.constraints.NotBlank;

public record UserRequest(@NotBlank String login, @NotBlank String password) {
}
