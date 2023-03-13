package br.com.medelo.sgpd.service;

import br.com.medelo.sgpd.builder.ProjectBuilder;
import br.com.medelo.sgpd.enums.RiskEnum;
import br.com.medelo.sgpd.enums.StatusEnum;
import br.com.medelo.sgpd.exception.BusinessException;
import br.com.medelo.sgpd.dto.form.OperationProjectForm;
import br.com.medelo.sgpd.dto.form.ProjectForm;
import br.com.medelo.sgpd.helper.MessageHelper;
import br.com.medelo.sgpd.model.Person;
import br.com.medelo.sgpd.model.Project;
import br.com.medelo.sgpd.dto.form.SelectForm;
import br.com.medelo.sgpd.repository.PersonRepository;
import br.com.medelo.sgpd.repository.ProjectRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static br.com.medelo.sgpd.builder.ProjectBuilder.buildOperationProjectForm;
import static br.com.medelo.sgpd.exception.ErrorCodeEnum.ERROR_MANAGER_NOT_FOUND;
import static br.com.medelo.sgpd.exception.ErrorCodeEnum.ERROR_PROJECT_DELETE;
import static br.com.medelo.sgpd.exception.ErrorCodeEnum.ERROR_PROJECT_NOT_FOUND;
import static java.lang.Boolean.FALSE;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@Slf4j
@Service
public class ProjectService {

    private MessageHelper messageHelper;
    private PersonRepository personRepository;
    private ProjectRepository projetoRepository;

    @Autowired
    public ProjectService(MessageHelper messageHelper,
                          PersonRepository personRepository,
                          ProjectRepository projetoRepository) {
        this.messageHelper = messageHelper;
        this.personRepository = personRepository;
        this.projetoRepository = projetoRepository;
    }

    public List<ProjectForm> findAllProject() {
        List<Project> projects = projetoRepository.findAll();
        return projects.stream().map(p -> ProjectBuilder.buildProjectForm(p)
                        .withAllowDelete(!notAllowDelete(p.getStatus())))
                .collect(Collectors.toList());
    }

    public OperationProjectForm getCreateProjetoForm() {
        return OperationProjectForm.builder()
                .selectStatus(getSelectStatus())
                .selectRisk(getSelectRisk())
                .selectManager(getSelectManager())
                .build();
    }

    private List<SelectForm> getSelectStatus() {
        return Arrays.asList(StatusEnum.values()).stream().map(s ->
            SelectForm.builder()
                .key(s.getId().toString())
                .label(s.getDescricao())
                .build())
            .collect(Collectors.toList());
    }

    private List<SelectForm> getSelectRisk() {
        return Arrays.asList(RiskEnum.values()).stream().map(s ->
            SelectForm.builder()
                .key(s.getId().toString())
                .label(s.getDescricao())
                .build())
            .collect(Collectors.toList());
    }

    private List<SelectForm> getSelectManager() {
        List<Person> personList = findManagers();
        return personList.stream().map(s ->
            SelectForm.builder()
                .key(s.getId().toString())
                .label(s.getName())
                .build())
            .collect(Collectors.toList());
    }

    private List<Person> findManagers() {
        return personRepository.findByEmployee(FALSE);
    }

    public void createProject(final OperationProjectForm operationProjectForm) {
        Person gerente = getPersonById(operationProjectForm.getManager());
        projetoRepository.save(ProjectBuilder.buildProjectEntity(operationProjectForm, gerente));
    }

    public OperationProjectForm findById(final Long id) {
        return buildOperationProjectForm(getProjectById(id))
                .withSelectStatus(getSelectStatus())
                .withSelectRisk(getSelectRisk())
                .withSelectManager(getSelectManager());
    }

    private Project getProjectById(final Long id) {
        Optional<Project> project = projetoRepository.findById(id);
        if (!project.isPresent()) {
            log.error(messageHelper.get(ERROR_PROJECT_NOT_FOUND, id));
            throw BusinessException.builder()
                    .status(NOT_FOUND)
                    .message(messageHelper.get(ERROR_PROJECT_NOT_FOUND, id))
                    .build();
        }
        return project.get();
    }

    public void updateProject(final Long id, final OperationProjectForm updateProjetoDTO) {
        getProjectById(id);
        Person gerente = getPersonById(updateProjetoDTO.getManager());
        this.projetoRepository.save(ProjectBuilder.buildProjectEntity(updateProjetoDTO, gerente).withId(id));
    }

    private Person getPersonById(final Long id) {
        Optional<Person> person = personRepository.findById(id);
        if (!person.isPresent()) {
            log.info(messageHelper.get(ERROR_MANAGER_NOT_FOUND, id));
            throw BusinessException.builder()
                    .status(NOT_FOUND)
                    .message(messageHelper.get(ERROR_MANAGER_NOT_FOUND, id))
                    .build();
        }
        return person.get();
    }

    public void deleteProject(final Long id) {
        Project project = getProjectById(id);
        if (notAllowDelete(project.getStatus())) {
            log.info(messageHelper.get(ERROR_PROJECT_DELETE, project.getStatus()));
            throw BusinessException.builder()
                    .status(NOT_FOUND)
                    .message(messageHelper.get(ERROR_PROJECT_DELETE, project.getStatus()))
                    .build();
        }
        this.projetoRepository.deleteById(id);
    }

    private boolean notAllowDelete(final StatusEnum status) {
        return Arrays.asList(StatusEnum.INICIADO, StatusEnum.EM_ANDAMENTO, StatusEnum.ENCERRADO).contains(status);
    }
}
