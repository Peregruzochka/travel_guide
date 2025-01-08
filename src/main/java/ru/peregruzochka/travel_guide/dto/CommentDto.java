package ru.peregruzochka.travel_guide.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CommentDto {
    private UUID id;

    @NotNull(message = "sightId can`t be null")
    private UUID sightId;

    private String content;

    private UUID userId;

    @NotNull(message = "grade can`t be null")
    @Min(value = 1)
    @Max(value = 5)
    private Integer grade;

    private LocalDateTime createdAt;
}
