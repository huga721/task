package pl.hszaba.betacomtask.item;

import jakarta.validation.constraints.NotBlank;

public record ItemCreateRequest(@NotBlank String name) {
}
