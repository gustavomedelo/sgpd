package br.com.medelo.sgpd.repository;

import br.com.medelo.sgpd.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {

    Optional<Person> findByCpf(String cpf);
    List<Person> findByEmployee(Boolean employee);
    Optional<Person> findByCpfAndEmployee(String cpf, Boolean employee);
}
