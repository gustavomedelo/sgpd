package br.com.medelo.sgpd.exception;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.Value;
import lombok.With;

import java.time.LocalDateTime;
import java.util.List;

import static java.time.LocalDateTime.now;

@Value
@With
@JsonDeserialize(builder = ErrorMessageDTO.JacksonBuilder.class)
@Builder(builderClassName = "JacksonBuilder")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorMessageDTO {

    @Builder.Default
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    LocalDateTime timestamp = now();
    String error;
    List<String> message;
    String path;

    @JsonPOJOBuilder(withPrefix = "")
    public static class JacksonBuilder {

    }
}
