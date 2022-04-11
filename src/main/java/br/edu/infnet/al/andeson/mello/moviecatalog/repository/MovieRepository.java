package br.edu.infnet.al.andeson.mello.moviecatalog.repository;

import br.edu.infnet.al.andeson.mello.moviecatalog.model.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface MovieRepository extends JpaRepository<Movie, UUID> {
}
