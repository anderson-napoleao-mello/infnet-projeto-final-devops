package br.edu.infnet.al.andeson.mello.moviecatalog.model;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.UUID;

@Data
@NoArgsConstructor
@Getter
@Setter
@Entity
public class Movie {
  @Id
  @GeneratedValue
  private UUID id;

  private String name;
  private String category;
  private String directors;
  private String writers;
  private String actors;
  private String releaseDate;
}
