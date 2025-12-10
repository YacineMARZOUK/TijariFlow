package org.example.tjariflow.dto.response;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ErrorResponseDTO {
    @JsonFormat(pattern = "dd MMMM yyyy HH:mm", locale = "fr")
    private LocalDateTime timestamp;
    private int status;
    private String error;
    private String message;
    private String path;
}