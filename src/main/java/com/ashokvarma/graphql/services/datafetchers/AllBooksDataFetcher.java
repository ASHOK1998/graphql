package com.ashokvarma.graphql.services.datafetchers;

import com.ashokvarma.graphql.models.Book;
import com.ashokvarma.graphql.repositories.BookRepository;
import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AllBooksDataFetcher implements DataFetcher<List<Book> >{

    @Autowired
    private BookRepository bookRepository;
    @Override
    public List<Book> get(DataFetchingEnvironment dataFetchingEnvironment) {
        return bookRepository.findAll();
    }
}
