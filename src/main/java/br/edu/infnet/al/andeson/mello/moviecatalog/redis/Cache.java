package br.edu.infnet.al.andeson.mello.moviecatalog.redis;

import br.edu.infnet.al.andeson.mello.moviecatalog.model.Movie;

import java.util.List;
import java.util.Optional;

public interface Cache {
  public void insert(Movie movie);
  public List<String> getNewReleases();
}
