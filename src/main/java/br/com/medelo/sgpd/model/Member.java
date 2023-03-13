package br.com.medelo.sgpd.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.With;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@With
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "membros")
public class Member {

    @Id
    @Column(name = "idprojeto")
    private Long idProjeto;
    @Column(name = "idpessoa")
    private Long idPessoa;
}
