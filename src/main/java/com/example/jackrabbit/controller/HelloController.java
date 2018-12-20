package com.example.jackrabbit.controller;

import com.example.jackrabbit.domain.Hello;
import org.apache.jackrabbit.ocm.manager.ObjectContentManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/hello")
public class HelloController {

    private final ObjectContentManager ocm;

    @Autowired
    public HelloController(ObjectContentManager ocm) {
        this.ocm = ocm;
    }

    @GetMapping("/{id}")
    public Hello show(@PathVariable String id) {
        return (Hello) ocm.getObject("/" + id);
    }

    @PostMapping
    public Hello create(@RequestBody Hello hello) {
        ocm.insert(hello);
        ocm.save();

        return hello;
    }

    @PutMapping
    public Hello update(@RequestBody Hello hello) {
        ocm.update(hello);
        ocm.save();

        return hello;
    }
}
