package br.com.medelo.sgpd.controller;

import br.com.medelo.sgpd.dto.CreatePersonDTO;
import br.com.medelo.sgpd.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("/members")
public class MemberController {

    @Autowired
    private MemberService service;

    @PostMapping
    @ResponseStatus(CREATED)
    public void create(@RequestBody @Valid CreatePersonDTO createPersonDTO) {
        service.create(createPersonDTO);
    }
}

