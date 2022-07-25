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
import java.util.Set;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "characters")
@SQLDelete(sql = "UPDATE characters SET deleted = true WHERE id=?")
@Where(clause ="deleted=false")
public class CharacterEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String lastName;
    private LocalDate birthDate;
    private Integer passport;

    private boolean deleted = Boolean.FALSE;

    @ManyToMany(mappedBy = "characters")
    private Set<MovieEntity> movies = new HashSet<>();


    @Override
    public String toString() {
        return "CharacterEntity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", lastName='" + lastName + '\'' +
                ", birthDate=" + birthDate +
                ", passport=" + passport +
                '}';
    }
}
