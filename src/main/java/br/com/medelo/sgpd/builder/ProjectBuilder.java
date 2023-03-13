package br.com.medelo.sgpd.builder;

import br.com.medelo.sgpd.enums.RiskEnum;
import br.com.medelo.sgpd.enums.StatusEnum;
import br.com.medelo.sgpd.dto.form.OperationProjectForm;
import br.com.medelo.sgpd.dto.form.ProjectForm;
import br.com.medelo.sgpd.model.Person;
import br.com.medelo.sgpd.model.Project;
import lombok.experimental.UtilityClass;

import java.time.LocalDate;

import static br.com.medelo.sgpd.util.Utils.formatLocalDateToString;
import static br.com.medelo.sgpd.util.Utils.nonNullAndNonEmpty;
import static java.util.Objects.nonNull;

@UtilityClass
public class ProjectBuilder {

    public static ProjectForm buildProjectForm(Project project) {
        return ProjectForm.builder()
                .id(project.getId())
                .name(project.getName())
                .initDate(nonNull(project.getInitDate()) ? formatLocalDateToString(project.getInitDate()) : null)
                .description(nonNull(project.getDescription()) ? project.getDescription() : null)
                .status(nonNull(project.getStatus()) ? project.getStatus().getDescricao() : null)
                .budget(nonNull(project.getBudget()) ? project.getBudget() : null)
                .risk(nonNull(project.getRisk()) ? project.getRisk().name() : null)
                .manager(project.getManager())
                .build();
    }

    public static Project buildProjectEntity(OperationProjectForm createProjetoDTO, Person gerente) {
        return Project.builder()
                .name(createProjetoDTO.getName())
                .description(createProjetoDTO.getDescription())
                .initDate(nonNullAndNonEmpty(createProjetoDTO.getInitDate()) ? LocalDate.parse(createProjetoDTO.getInitDate()) : null)
                .endDateForecast(nonNullAndNonEmpty(createProjetoDTO.getEndDateForecast()) ? LocalDate.parse(createProjetoDTO.getEndDateForecast()) : null)
                .endDate(nonNullAndNonEmpty(createProjetoDTO.getEndDate()) ? LocalDate.parse(createProjetoDTO.getEndDate()) : null)
                .status(StatusEnum.getById(createProjetoDTO.getStatus()))
                .budget(createProjetoDTO.getBudget())
                .risk(RiskEnum.getById(createProjetoDTO.getRisk()))
                .manager(gerente)
                .build();
    }

    public static OperationProjectForm buildOperationProjectForm(Project project) {
        return OperationProjectForm.builder()
                .id(project.getId())
                .name(project.getName())
                .description(project.getDescription())
                .initDate(nonNull(project.getInitDate()) ? project.getInitDate().toString() : null)
                .endDateForecast(nonNull(project.getEndDateForecast()) ? project.getEndDateForecast().toString() : null)
                .endDate(nonNull(project.getEndDate()) ? project.getEndDateForecast().toString() : null)
                .status(nonNull(project.getStatus()) ? project.getStatus().getId() : null)
                .budget(project.getBudget())
                .risk(nonNull(project.getRisk()) ? project.getRisk().getId() : null)
                .manager(nonNull(project.getManager()) ? project.getManager().getId() : null)
                .build();
    }
}
