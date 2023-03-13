package br.com.medelo.sgpd.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCodeEnum {

    ERROR_MANAGER_NOT_FOUND("error.manager.not.found"),
    ERROR_PROJECT_NOT_FOUND("error.project.not.found"),
    ERROR_PROJECT_DELETE("error.project.delete"),
    ERROR_PERSON_NOT_FOUND("error.person.not.found"),
    ERROR_PERSON_ALREADY_EXISTS("error.person.already.exists");

    private final String messageKey;
}
