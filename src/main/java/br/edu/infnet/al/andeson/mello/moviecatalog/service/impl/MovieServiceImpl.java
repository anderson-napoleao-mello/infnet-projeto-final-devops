package br.edu.infnet.al.andeson.mello.moviecatalog.service.impl;

import br.edu.infnet.al.andeson.mello.moviecatalog.model.Movie;
import br.edu.infnet.al.andeson.mello.moviecatalog.redis.RedisCache;
import br.edu.infnet.al.andeson.mello.moviecatalog.repository.MovieRepository;
import br.edu.infnet.al.andeson.mello.moviecatalog.service.MovieService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.SplittableRandom;
import java.util.UUID;

@AllArgsConstructor
@Slf4j
@Service
public class MovieServiceImpl implements MovieService {
  private final MovieRepository repository;
  private final RedisCache redis;

  @Override
  public List<Movie> getAll() {
    randomRuntimeError();
    return repository.findAll();
  }

  @Override
  public Optional<Movie> getById(UUID id) {
    randomRuntimeError();
    return repository.findById(id);
  }

  @Override
  public List<String> getNewReleases() {
    randomRuntimeError();
    return redis.getNewReleases();
  }

  @Override
  public Movie save(Movie movie) {
    randomRuntimeError();
    var saved = repository.save(movie);
    redis.insert(saved);
    return saved;
  }

  private void randomRuntimeError(){
    SplittableRandom random = new SplittableRandom();
    int i = random.nextInt(1000);
    if(i > 900){
      throw new RuntimeException("Um Erro Ocorreu");
    }
  }
}
