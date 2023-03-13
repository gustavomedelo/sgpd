# SGPD

> Aplicação responsável pelo gerenciamento dos dados do portfólio de projetos de uma empresa.

[![Java Version][java-image]][java-url]
[![Spring Version][spring-image]][spring-url]

## Configuração de Desenvolvimento

### Build

Para fazer o build da aplicação deve ser executado o seguinte comando.

```sh
mvn clean install -U
```

### Executar

#### Intellij

No diretório raiz do projeto crie um novo diretório chamado __.run__, dentro deste novo diretório deve ser criado o arquivo __launch.run.xml__ com as configurações abaixo.

```xml
<component name="ProjectRunConfigurationManager">
    <configuration default="false" name="Launch Application" type="Application" factoryName="Application">
        <envs>
            <env name="SPRING_PROFILES_ACTIVE" value="local" />
        </envs>
        <option name="MAIN_CLASS_NAME" value="br.com.medelo.sgpd.SgpdApplication" />
        <module name="sgpd" />
        <method v="2">
            <option name="Make" enabled="true" />
        </method>
    </configuration>
</component>
```

Para que essa configuração fique disponível menu de execução deve ser feito um build (CRTL+F9) do projeto.

#### Visual Studio Code

No diretório raiz do projeto crie um novo diretório chamado __.vscode__, dentro deste novo diretório deve ser criado o arquivo __launch.json__ com as configurações abaixo.

```json
{
  "version": "0.2.0",
  "configurations": [
    {
      "type": "java",
      "name": "Launch Application",
      "request": "launch",
      "cwd": "${workspaceFolder}",
      "console": "internalConsole",
      "mainClass": "br.com.medelo.sgpd.SgpdApplication",
      "projectName": "sgpd",
      "env": { "SPRING_PROFILES_ACTIVE": "local" }
    }
  ]
}
```

### Request

http://localhost:8080/home

- Cadastro membro via webservice
```
curl --location 'http://localhost:8080/members' \
--header 'Content-Type: application/json' \
--data '{
"name": "Joe Doe",
"birthDate": "1994-10-11",
"cpf": "28875432023",
"assignment": "FUNCIONARIO" // FUNCIONARIO | GERENTE
}'
```


<!-- Markdown link & img dfn's -->
[java-image]: https://img.shields.io/badge/Java-v11-green
[spring-image]: https://img.shields.io/badge/Spring--Boot-v2.7.9-green
[java-url]: https://docs.oracle.com/en/java/javase/11/
[spring-url]: https://mvnrepository.com/artifact/org.springframework.boot/spring-boot-dependencies/2.7.9