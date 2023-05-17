package com.example.firstrestalishev.controllers;

import com.example.firstrestalishev.dto.PersonDTO;
import com.example.firstrestalishev.entity.Person;
import com.example.firstrestalishev.service.PersonService;
import com.example.firstrestalishev.util.PersonErrorResponse;
import com.example.firstrestalishev.util.PersonNotCreatedException;
import com.example.firstrestalishev.util.PersonNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/people")
public class PeopleController {

    private final PersonService personService;


    public PeopleController(PersonService personService) {
        this.personService = personService;
    }

    @GetMapping()
    public List<Person> getPeople() {
        return personService.findAll(); //Jackson конвертирует объекты в JSON
    }

    @GetMapping("/{id}")
    public Person getPerson(@PathVariable("id") int id) {
        return personService.findById(id); //Jackson конвертирует объекты в JSON
    }

    //Принимаем от клиента JSON, Jackson конвертирует его в объект,
    //Затем Hibernate validator проверяет объект на валидность
    //возвращение HTTP ответа (простейший ответ), можно вернуть
    //человека который был помещен в БД
    @PostMapping()
    public ResponseEntity<HttpStatus> create(@RequestBody @Valid PersonDTO personDTO,
                                             BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            StringBuilder errorMessage = new StringBuilder();

            //Создание строки ошибки
            List<FieldError> errorList = bindingResult.getFieldErrors();
            for (FieldError fieldError : errorList) {
                errorMessage.append(fieldError.getField())
                        .append(" - ")
                        .append(fieldError.getDefaultMessage())
                        .append("; ");
            }

            //бросаем исключение в котором хранится сообщение об ошибке
            throw new PersonNotCreatedException(errorMessage.toString());
        }

        personService.save(convertToPerson(personDTO));
        return ResponseEntity.ok(HttpStatus.OK);
    }

    public Person convertToPerson(PersonDTO personDTO) {
        Person person = new Person();
        person.setName(personDTO.getName());
        person.setBirthYear(personDTO.getBirthYear());

        return person;
    }

    //обработчик исключения
    //PersonNotFoundException - исключение
    //PersonErrorResponse - JSON объект исключения
    @ExceptionHandler
    private ResponseEntity<PersonErrorResponse> handleException(PersonNotFoundException e) {

        PersonErrorResponse personErrorResponse = new PersonErrorResponse(
                "Person with this id wasn't found", System.currentTimeMillis()
        );

        return new ResponseEntity<>(personErrorResponse, HttpStatus.NOT_FOUND);

    }

    //обработчик исключения
    //PersonNotFoundException - исключение
    //PersonErrorResponse - JSON объект исключения
    @ExceptionHandler
    private ResponseEntity<PersonErrorResponse> handleException(PersonNotCreatedException e) {

        PersonErrorResponse personErrorResponse = new PersonErrorResponse(
                e.getMessage(), System.currentTimeMillis()
        );

        //сообщает о том что клиент отправил неправильный запрос, BAD_REQUEST
        return new ResponseEntity<>(personErrorResponse, HttpStatus.BAD_REQUEST);

    }

}
