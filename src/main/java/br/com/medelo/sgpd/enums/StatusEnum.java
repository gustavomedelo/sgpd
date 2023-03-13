package br.com.medelo.sgpd.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum StatusEnum {

    EM_ANALISE(1L, "Em análise"),
    ANALISE_REALIZADA(2L, "Análise realizada"),
    ANALISE_APROVADA(3L, "Análise aprovada"),
    INICIADO(4L, "Iniciado"),
    PLANEJADO(5L, "Planejado"),
    EM_ANDAMENTO(6L, "Em andamento"),
    ENCERRADO(7L, "Encerrado"),
    CANCELADO(8L, "Cancelado");

    private Long id;
    private String descricao;

    public static StatusEnum getById(Long id) {
        for (StatusEnum e : StatusEnum.values()) {
            if (e.getId().equals(id)) {
                return e;
            }
        }
        return null;
    }
}
