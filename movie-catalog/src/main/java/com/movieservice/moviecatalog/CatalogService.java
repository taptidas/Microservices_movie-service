package com.movieservice.moviecatalog;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

import models.CatalogItem;
import models.Movie;
import models.Rating;
import models.UserRating;



	
	@RestController
	@RequestMapping("/catalog")
	public class CatalogService {

	    @Autowired
	    private RestTemplate restTemplate;
	    
	    @Autowired
	    MovieInfo movieInfo;
	    
	    @Autowired
	    UserRatingInfo userRatingInfo;

	   // @Autowired
	   // WebClient.Builder webClientBuilder;

	    @RequestMapping("/{userId}")
	    
	    public List<CatalogItem> getCatalog(@PathVariable("userId") String userId) {

	        UserRating userRating = userRatingInfo.getUserRating(userId);

	        return userRating.getRatings().stream()
	                .map(rating -> {
	                    return movieInfo.getCatalogItem(rating);
	                })
	                .collect(Collectors.toList());

	    }

	   
	}

	/*
	Alternative WebClient way
	Movie movie = webClientBuilder.build().get().uri("http://localhost:8082/movies/"+ rating.getMovieId())
	.retrieve().bodyToMono(Movie.class).block();
	*/

