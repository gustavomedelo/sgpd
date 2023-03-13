package br.com.medelo.sgpd.builder;

import br.com.medelo.sgpd.dto.CreatePersonDTO;
import br.com.medelo.sgpd.model.Person;
import lombok.experimental.UtilityClass;

import static br.com.medelo.sgpd.util.Utils.formatStringToLocalDate;
import static br.com.medelo.sgpd.util.Utils.nonNullAndNonEmpty;

@UtilityClass
public class PersonBuilder {

    public static Person buildPersonEntity(CreatePersonDTO createProjetoDTO, boolean funcionario) {
        return Person.builder()
                .name(createProjetoDTO.getName())
                .birthDate(nonNullAndNonEmpty(createProjetoDTO.getBirthDate())
                        ? formatStringToLocalDate(createProjetoDTO.getBirthDate()) : null)
                .cpf(createProjetoDTO.getCpf())
                .employee(funcionario)
                .build();
    }
}
