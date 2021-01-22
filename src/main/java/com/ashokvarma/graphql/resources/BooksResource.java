package com.ashokvarma.graphql.resources;

import com.ashokvarma.graphql.services.BookService;
import graphql.ExecutionResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/rest/books")
@RestController
public class BooksResource {

    @Autowired
    private BookService graphService;

    @PostMapping
    public ResponseEntity<Object> books(@RequestBody String query){
        ExecutionResult execute = graphService.getGraphQL().execute(query);
        return new ResponseEntity<>(execute,HttpStatus.OK);
    }
}
