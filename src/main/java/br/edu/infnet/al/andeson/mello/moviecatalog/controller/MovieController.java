package br.edu.infnet.al.andeson.mello.moviecatalog.controller;

import br.edu.infnet.al.andeson.mello.moviecatalog.model.Movie;
import br.edu.infnet.al.andeson.mello.moviecatalog.service.MovieService;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.Meter;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@RestController
@RequestMapping("/movie")
public class MovieController {
  private final MovieService service;
  Counter getByIdFound;
  Counter getByIdNotFound;
  Timer timerDatabase;
  Timer timerRedis;

  @Autowired
  public MovieController(MeterRegistry meterRegistry, MovieService service){
    this.service = service;
    getByIdFound = Counter.builder("get_by_id_found").register(meterRegistry);
    getByIdNotFound = Counter.builder("get_by_id_not_found").register(meterRegistry);
  }

  @GetMapping
  public ResponseEntity<List<Movie>> getAll(){
    var movies = service.getAll();
    return ResponseEntity.ok().body(movies);
  }

  @GetMapping("/{id}")
  public ResponseEntity<Movie> getById(@PathVariable UUID id){
    var movie = service.getById(id);
    return movie.map(this::returnOk).orElseGet(this::returnNotFound);

  }

  private ResponseEntity<Movie> returnNotFound() {
    getByIdNotFound.increment();
    return ResponseEntity.notFound().build();
  }

  private ResponseEntity<Movie> returnOk(Movie movie) {
    getByIdFound.increment();
    return ResponseEntity.ok().body(movie);
  }

  @GetMapping("/newreleases")
  public ResponseEntity<List<String>> getNewReleases(){
    var movies = service.getNewReleases();
    return ResponseEntity.ok().body(movies);
  }

  @PostMapping
  public ResponseEntity<Movie> create(@RequestBody Movie movie){
    return new ResponseEntity<Movie>(service.save(movie), HttpStatus.CREATED);
  }
}
