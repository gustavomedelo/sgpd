package br.com.medelo.sgpd.service;

import br.com.medelo.sgpd.dto.CreatePersonDTO;
import br.com.medelo.sgpd.enums.AssignmentEnum;
import br.com.medelo.sgpd.exception.BusinessException;
import br.com.medelo.sgpd.helper.MessageHelper;
import br.com.medelo.sgpd.model.Member;
import br.com.medelo.sgpd.model.Person;
import br.com.medelo.sgpd.repository.MemberRepository;
import br.com.medelo.sgpd.repository.PersonRepository;
import br.com.medelo.sgpd.repository.ProjectRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static br.com.medelo.sgpd.builder.PersonBuilder.buildPersonEntity;
import static br.com.medelo.sgpd.enums.AssignmentEnum.FUNCIONARIO;
import static br.com.medelo.sgpd.exception.ErrorCodeEnum.ERROR_PERSON_ALREADY_EXISTS;
import static br.com.medelo.sgpd.exception.ErrorCodeEnum.ERROR_PERSON_NOT_FOUND;
import static java.lang.Boolean.TRUE;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@Slf4j
@Service
public class MemberService {

    private MessageHelper messageHelper;
    private PersonRepository personRepository;
    private MemberRepository memberRepository;

    private static final String ID = "id";

    @Autowired
    public MemberService(MessageHelper messageHelper,
                         PersonRepository personRepository,
                         MemberRepository memberRepository,
                         ProjectRepository projetoRepository) {
        this.messageHelper = messageHelper;
        this.personRepository = personRepository;
        this.memberRepository = memberRepository;
    }

    public List<Person> findAllEmployee() {
        return personRepository.findByEmployee(TRUE);
    }

    public void create(final CreatePersonDTO createPersonDTO) {
        getByCpf(createPersonDTO.getCpf());
        personRepository.save(
                buildPersonEntity(createPersonDTO, haveEmployeeAssignment(createPersonDTO.getAssignment())));
    }

    private Person getByCpf(final String cpf) {
        Optional<Person> person = personRepository.findByCpf(cpf);
        if (person.isPresent()) {
            log.error(messageHelper.get(ERROR_PERSON_ALREADY_EXISTS, cpf));
            throw BusinessException.builder()
                    .status(NOT_FOUND)
                    .message(messageHelper.get(ERROR_PERSON_ALREADY_EXISTS, cpf))
                    .build();
        }
        return null;
    }

    private boolean haveEmployeeAssignment(final AssignmentEnum assignment) {
        return FUNCIONARIO.equals(assignment);
    }

    public List<Person> findAllPerson() {
        return personRepository.findAll();
    }

    public void associateMemberProject(final Long idMember, final Long idProject) {
        getFuncionarioById(idMember);
        memberRepository.save(Member.builder()
                .idProjeto(idProject)
                .idPessoa(idMember)
                .build());
    }

    private Person getFuncionarioById(final Long id) {
        Optional<Person> person = personRepository.findById(id);
        if (!person.isPresent()) {
            log.error(messageHelper.get(ERROR_PERSON_NOT_FOUND, ID, id));
            throw BusinessException.builder()
                    .status(NOT_FOUND)
                    .message(messageHelper.get(ERROR_PERSON_NOT_FOUND, ID, id))
                    .build();
        }
        return person.get();
    }
}
