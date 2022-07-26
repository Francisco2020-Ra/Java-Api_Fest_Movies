package com.informatorio.festmovies.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;


@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "movie")
@SQLDelete(sql = "UPDATE movie SET deleted = true WHERE id=?")
@Where(clause ="deleted=false")
public class MovieEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String description;
    private double duration;
    private LocalDate inscription;

    private boolean deleted = Boolean.FALSE;

    @ManyToOne(fetch = FetchType.LAZY)
    private CategoryEntity category;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "movie_character",
    joinColumns = @JoinColumn(name = "movie_id"),
    inverseJoinColumns = @JoinColumn(name = "character_id"))
    private Set<CharacterEntity> characters = new HashSet<>();

    public void addCharacter(CharacterEntity characterEntity){
        characters.add(characterEntity);
        characterEntity.getMovies().add(this);
    }

    @Override
    public String toString() {
        return "MovieEntity{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", duration=" + duration +
                ", inscription=" + inscription +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MovieEntity movie = (MovieEntity) o;
        return Double.compare(movie.duration, duration) == 0 && deleted == movie.deleted && Objects.equals(id, movie.id) && Objects.equals(title, movie.title) && Objects.equals(description, movie.description) && Objects.equals(inscription, movie.inscription) && Objects.equals(category, movie.category);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, description, duration, inscription, deleted, category);
    }
}
