package br.com.medelo.sgpd.dto.form;

import br.com.medelo.sgpd.model.Person;
import lombok.Builder;
import lombok.Data;
import lombok.With;

@With
@Data
@Builder
public class ProjectForm {

    private Long id;
    private String name;
    private String description;
    private String initDate;
    private String endDateForecast;
    private String endDate;
    private String status;
    private Float budget;
    private String risk;
    private Person manager;
    private boolean allowDelete;
}
