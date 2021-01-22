package com.ashokvarma.graphql.services;

import com.ashokvarma.graphql.models.Book;
import com.ashokvarma.graphql.repositories.BookRepository;
import com.ashokvarma.graphql.services.datafetchers.AllBooksDataFetcher;
import com.ashokvarma.graphql.services.datafetchers.BookDataFetcher;
import graphql.GraphQL;
import graphql.schema.DataFetcher;
import graphql.schema.GraphQLSchema;
import graphql.schema.idl.RuntimeWiring;
import graphql.schema.idl.SchemaGenerator;
import graphql.schema.idl.SchemaParser;
import graphql.schema.idl.TypeDefinitionRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.stream.Stream;

@Service
public class BookService {
    @Value("classpath:books.graphql")
    Resource resource;
    private GraphQL graphQL;
    @Autowired
    private AllBooksDataFetcher allBooksDataFetcher;
    @Autowired
    private BookDataFetcher bookDataFetcher;

    @Autowired
    private BookRepository repository;

    @PostConstruct
    public void loadSchema() throws IOException {

        loadDataIntoHSQL();
        // get the schema
        File schemaFile = resource.getFile();

        // parse schema
        TypeDefinitionRegistry typeDefinitionRegistry = new SchemaParser().parse(schemaFile);
        RuntimeWiring wiring = buildRuntimeWiring();
        GraphQLSchema schema = new SchemaGenerator().makeExecutableSchema(typeDefinitionRegistry,wiring);
        graphQL = GraphQL.newGraphQL(schema).build();
    }

    private void loadDataIntoHSQL() {
        Book book = new Book("isn","Books Of Clouds","kindle Edition", new String[]{
                "Ashok","Varma"
        },"Nov 2017"
                );

        Stream.of(
                book,book,book
        ).forEach(book1 -> {
            repository.save(book1);
        });
    }

    private RuntimeWiring buildRuntimeWiring() {
        return RuntimeWiring.newRuntimeWiring()
                .type("Query",typeWiring ->

                    typeWiring
                            .dataFetcher("allBooks",allBooksDataFetcher)
                            .dataFetcher("book",bookDataFetcher)

                ).build();
    }

    public GraphQL getGraphQL() {
        return graphQL;
    }
}
