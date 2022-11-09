package com.library.service;

import com.library.exception.Author.AuthorNotFoundException;
import com.library.model.Author;
import com.library.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthorService {

    @Autowired
    private AuthorRepository authorRepository;

    public Author addAuthor(Author newAuthor) {
        return authorRepository.save(newAuthor);
    }

    public List<Author> getAllAuthors() {
        return authorRepository.findAll();
    }

    public Author getAuthorById(Long id) throws AuthorNotFoundException {
        return authorRepository
                .findById(id)
                .orElseThrow(AuthorNotFoundException::new);
    }

    public void deleteAuthorById(Long id) {
        authorRepository.deleteById(id);
    }
}
