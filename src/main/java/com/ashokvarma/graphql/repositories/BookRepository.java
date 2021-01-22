package com.ashokvarma.graphql.repositories;

import com.ashokvarma.graphql.models.Book;
import org.springframework.data.jpa.repository.JpaRepository;



public interface BookRepository extends JpaRepository<Book,String> {

}
