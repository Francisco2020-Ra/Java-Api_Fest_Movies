package com.informatorio.festmovies.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;


@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MovieEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String description;
    private double duration;
    private LocalDate inscription;

    @ManyToOne(fetch = FetchType.LAZY)
    private CategoryEntity category;


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
}
