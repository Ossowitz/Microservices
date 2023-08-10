package ru.iooko.ch1;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/hello")
public class ApplicationController {

    @GetMapping("/{firstName}")
    public String helloGET(
            @PathVariable("firstName") String firstName,
            @RequestParam("lastName") String lastName
    ) {
        return String.format("{\"message\":\"Hello %s %s\"}", firstName, lastName);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public String hello() {
        return "Hello!";
    }
}
