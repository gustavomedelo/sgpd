package br.com.medelo.sgpd.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum RiskEnum {
    BAIXO(1L, "Baixo"),
    MEDIO(2L, "Médio"),
    ALTO(3L, "Alto");

    private Long id;
    private String descricao;

    public static RiskEnum getById(Long id) {
        for (RiskEnum e : RiskEnum.values()) {
            if (e.getId().equals(id)) {
                return e;
            }
        }
        return null;
    }
}
