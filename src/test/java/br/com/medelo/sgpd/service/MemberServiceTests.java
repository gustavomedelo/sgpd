package br.com.medelo.sgpd.service;

import br.com.medelo.sgpd.dto.CreatePersonDTO;
import br.com.medelo.sgpd.enums.AssignmentEnum;
import br.com.medelo.sgpd.exception.BusinessException;
import br.com.medelo.sgpd.helper.MessageHelper;
import br.com.medelo.sgpd.model.Person;
import br.com.medelo.sgpd.repository.MemberRepository;
import br.com.medelo.sgpd.repository.PersonRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class MemberServiceTests {

    @InjectMocks
    private MemberService memberService;
    @Mock
    private PersonRepository personRepository;
    @Mock
    private MemberRepository memberRepository;
    @Mock
    private MessageHelper messageHelper;

    @Test
    void shouldCreatePerson() {
        CreatePersonDTO createPersonDTO = CreatePersonDTO.builder()
                .cpf("12345678900")
                .assignment(AssignmentEnum.FUNCIONARIO)
                .build();

        when(personRepository.findByCpf(anyString())).thenReturn(Optional.empty());

        memberService.create(createPersonDTO);

        verify(personRepository, times(1)).findByCpf(anyString());
        verify(personRepository, times(1)).save(any());
    }

    @Test
    void shouldThrow_whenPersonAlreadyExists() {
        CreatePersonDTO createPersonDTO = CreatePersonDTO.builder()
                .cpf("12345678900")
                .assignment(AssignmentEnum.FUNCIONARIO)
                .build();

        Person person = Person.builder()
                .cpf("12345678900")
                .build();

        when(personRepository.findByCpf(anyString())).thenReturn(Optional.of(person));
        when(messageHelper.get(any(), any())).thenReturn("Person already exists");

        BusinessException exception = Assertions.assertThrows(BusinessException.class, () -> memberService.create(createPersonDTO));

        Assertions.assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
        Assertions.assertEquals("Person already exists", exception.getMessage());

        verify(personRepository, times(1)).findByCpf(anyString());
        verify(personRepository, never()).save(any());
    }

    @Test
    void shouldFindAllPersons() {
        List<Person> persons = Arrays.asList(Person.builder().build());
        when(personRepository.findAll()).thenReturn(persons);
        List<Person> allPersons = memberService.findAllPerson();

        Assertions.assertEquals(persons, allPersons);
        verify(personRepository, times(1)).findAll();
    }

    @Test
    void shouldAssociateMemberProject() {
        when(personRepository.findById(any())).thenReturn(Optional.of(Person.builder().build()));
        memberService.associateMemberProject(1L, 1L);

        verify(memberRepository, times(1)).save(any());
    }
}
