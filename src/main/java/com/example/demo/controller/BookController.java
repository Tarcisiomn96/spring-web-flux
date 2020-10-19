package com.example.demo.controller;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.example.demo.domain.Book;
import com.example.demo.service.BookService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;

@RequiredArgsConstructor
@RestController
@RequestMapping("book")
@Slf4j
public class BookController {

	@Autowired
	private BookService bookService;
	
	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	public Flux<Book> listAll(){
		
		return bookService.findAll();
		
	}
	
	@GetMapping(path = "{id}")
	@ResponseStatus(HttpStatus.OK)
	public Mono<Book> findById(@PathVariable Long id){
		
		return bookService.findById(id)
				.switchIfEmpty(Mono.
						error(new ResponseStatusException(HttpStatus.NOT_FOUND, 
								"book not found")));
		
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Mono<Book> save(@RequestBody Book book){
		
		return bookService.save(book);
		
	}
	
	@Transactional
	@PostMapping("/batch")
	@ResponseStatus(HttpStatus.CREATED)
	public Flux<Book> saveBatch(@RequestBody List<Book> book){
		
		return bookService.saveAll(book);
		
	}
	
	@GetMapping(value="/webflux", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	public Flux<Tuple2<Long, Book>> getPlaylistByWebflux(){

		System.out.println("---Start get Playlists by WEBFLUX--- " + LocalDateTime.now());
		Flux<Long> interval = Flux.interval(Duration.ofSeconds(10));
        Flux<Book> playlistFlux = bookService.findAll();

        return Flux.zip(interval, playlistFlux);
        
	}
	
	
	
	
	
	
	
	
	
	
	
}
