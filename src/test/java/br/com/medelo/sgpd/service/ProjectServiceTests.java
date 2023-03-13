package br.com.medelo.sgpd.service;

import br.com.medelo.sgpd.dto.form.OperationProjectForm;
import br.com.medelo.sgpd.dto.form.ProjectForm;
import br.com.medelo.sgpd.exception.BusinessException;
import br.com.medelo.sgpd.helper.MessageHelper;
import br.com.medelo.sgpd.model.Person;
import br.com.medelo.sgpd.model.Project;
import br.com.medelo.sgpd.repository.PersonRepository;
import br.com.medelo.sgpd.repository.ProjectRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static br.com.medelo.sgpd.enums.RiskEnum.ALTO;
import static br.com.medelo.sgpd.enums.StatusEnum.ANALISE_APROVADA;
import static br.com.medelo.sgpd.enums.StatusEnum.EM_ANDAMENTO;
import static br.com.medelo.sgpd.enums.StatusEnum.ENCERRADO;
import static br.com.medelo.sgpd.enums.StatusEnum.INICIADO;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class ProjectServiceTests {

    @InjectMocks
    private ProjectService projectService;
    @Mock
    private MessageHelper messageHelper;
    @Mock
    private PersonRepository personRepository;
    @Mock
    private ProjectRepository projectRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldCreateProject() {
        Long managerId = 1L;
        OperationProjectForm operationProjectForm = OperationProjectForm.builder()
                .name("Project 1")
                .description("Description")
                .status(1L)
                .risk(ALTO.getId())
                .manager(managerId)
                .build();

        Person manager = new Person();
        manager.setId(managerId);

        when(personRepository.findById(managerId)).thenReturn(Optional.of(manager));
        projectService.createProject(operationProjectForm);
        verify(projectRepository, times(1)).save(any());
    }

    @Test
    void shouldUpdateProject() {
        Long managerId = 1L;
        Long projectId = 1L;

        OperationProjectForm operationProjectForm = OperationProjectForm.builder()
                .name("Project 1")
                .description("Description")
                .status(1L)
                .risk(ALTO.getId())
                .manager(managerId)
                .build();

        Person manager = new Person();
        manager.setId(managerId);

        when(projectRepository.findById(projectId)).thenReturn(Optional.of(Project.builder().build()));
        when(personRepository.findById(managerId)).thenReturn(Optional.of(manager));
        projectService.updateProject(1L, operationProjectForm);
        verify(projectRepository, times(1)).save(any());
    }

    @Test
    void shouldDeleteProject() {
        when(projectRepository.findById(any()))
                .thenReturn(Optional.ofNullable(Project.builder().status(ANALISE_APROVADA).build()));
        projectService.deleteProject(any());

        verify(projectRepository, times(1)).deleteById(any());
    }

    @Test
    void shouldThrow_whenProjetoIniciado() {
        when(projectRepository.findById(any()))
                .thenReturn(Optional.ofNullable(Project.builder().status(INICIADO).build()));

        Assertions.assertThrows(BusinessException.class,
                () -> projectService.deleteProject(1L));
    }

    @Test
    void shouldThrow_whenProjetoEmAndamento() {
        when(projectRepository.findById(any()))
                .thenReturn(Optional.ofNullable(Project.builder().status(EM_ANDAMENTO).build()));

        Assertions.assertThrows(BusinessException.class,
                () -> projectService.deleteProject(1L));
    }

    @Test
    void shouldThrow_whenProjetoEncerrado() {
        when(projectRepository.findById(any()))
                .thenReturn(Optional.ofNullable(Project.builder().status(ENCERRADO).build()));

        Assertions.assertThrows(BusinessException.class,
                () -> projectService.deleteProject(1L));
    }

    @Test
    void testFindAllProject() {
        Project project1 = new Project();
        project1.setId(1L);
        project1.setName("Project 1");
        project1.setStatus(INICIADO);

        Project project2 = new Project();
        project2.setId(2L);
        project2.setName("Project 2");
        project2.setStatus(INICIADO);

        List<Project> projectList = new ArrayList<>();
        projectList.add(project1);
        projectList.add(project2);

        when(projectRepository.findAll()).thenReturn(projectList);

        List<ProjectForm> projectFormList = projectService.findAllProject();

        assertEquals(2, projectFormList.size());
        assertEquals("Project 1", projectFormList.get(0).getName());
        assertFalse(projectFormList.get(0).isAllowDelete());
        assertEquals("Project 2", projectFormList.get(1).getName());
        assertFalse(projectFormList.get(1).isAllowDelete());

        verify(projectRepository, times(1)).findAll();
    }
}
