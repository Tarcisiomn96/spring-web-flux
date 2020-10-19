package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.domain.Book;
import com.example.demo.repository.BookRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@Slf4j
@RequiredArgsConstructor
public class BookService {
	
	@Autowired
	private BookRepository bookRepository;

	public Flux<Book> findAll() {
		return bookRepository.findAll();
	}
	
	public Mono<Book> findById(Long id){
		return bookRepository.findById(id);
	}

	public Mono<Book> save(Book book) {
		return bookRepository.save(book);
	}

	public Flux<Book> saveAll(List<Book> book) {
		return bookRepository.saveAll(book);
	}
	
	

}
