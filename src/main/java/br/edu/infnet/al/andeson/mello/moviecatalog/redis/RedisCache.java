package br.edu.infnet.al.andeson.mello.moviecatalog.redis;

import br.edu.infnet.al.andeson.mello.moviecatalog.model.Movie;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class RedisCache implements Cache{

  private final Jedis jedis;

  public RedisCache(){
    jedis = new Jedis("redis", 6379);
    jedis.auth("Redis2022!");
    jedis.flushAll();
  }

  @Override
  public void insert(Movie movie) {
    if(isNewRealise(movie.getReleaseDate())){
      put(movie.getName(),movie.getReleaseDate());
      log.info("Movie name inserted in cache [{} - {}]", movie.getName(), movie.getReleaseDate());
    }
    log.info("Movie [{} - {}] is not a new release so it was not inserted in cache", movie.getName(), movie.getReleaseDate());
  }

  public void put(String key, String value) {
    jedis.set(key, value);
  }

  @Override
  public List<String> getNewReleases() {
    var movies = jedis.hgetAll("*");
    deleteOld(movies);
    return getNewReleases(movies);
  }

  private void deleteOld(final Map<String, String> movies) {
    movies.entrySet().stream()
      .filter(e -> !isNewRealise(e.getValue()))
      .map(Map.Entry::getKey)
      .forEach(jedis::del);
  }

  private List<String> getNewReleases(final Map<String, String> movies) {
    return movies.entrySet().stream()
      .filter(e -> isNewRealise(e.getValue()))
      .map(Map.Entry::getKey)
      .collect(Collectors.toList());
  }

  private boolean isNewRealise(String date){
    return LocalDate.parse(date, DateTimeFormatter.BASIC_ISO_DATE).isAfter(LocalDate.now().minusMonths(1));
  }
}
