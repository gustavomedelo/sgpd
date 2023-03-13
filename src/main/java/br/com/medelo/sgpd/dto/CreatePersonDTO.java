package br.com.medelo.sgpd.dto;

import br.com.medelo.sgpd.enums.AssignmentEnum;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.Value;
import lombok.With;
import org.hibernate.validator.constraints.br.CPF;

import javax.validation.Valid;
import java.util.Date;

@Value
@With
@JsonDeserialize(builder = CreatePersonDTO.JacksonBuilder.class)
@Builder(builderClassName = "JacksonBuilder")
public class CreatePersonDTO {

    private String name;
    private String birthDate;
    @CPF
    private String cpf;
    @Valid
    private AssignmentEnum assignment;

    @JsonPOJOBuilder(withPrefix = "")
    public static class JacksonBuilder {
    }
}
