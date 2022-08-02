package com.informatorio.festmovies.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity @Data @Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "directory")
@SQLDelete(sql = "UPDATE directory SET deleted = true WHERE id=?")
@Where(clause ="deleted=false")
public class DirectoryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String lastName;
    private LocalDate birthDate;
    @Column(unique = true)
    private Integer passport;
    private LocalDate inscription;

    private boolean deleted = Boolean.FALSE;

    @OneToMany(mappedBy = "directory", cascade = CascadeType.ALL)
    private List<MovieEntity> movieEntityList = new ArrayList<>();

    @Override
    public String toString() {
        return "DirectoryEntity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", lastName='" + lastName + '\'' +
                ", birthDate=" + birthDate +
                ", passport=" + passport +
                ", inscription=" + inscription +
                '}';
    }
}
