package co.minasegura.measurement.model;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

public record Zone(@NotNull String id, @NotNull String type, @NotNull @Valid Mine mine){}
