package ru.learn.www.entity;


import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@DynamicUpdate
@DynamicInsert
@EqualsAndHashCode(of="id")
@ToString(of = "name", includeFieldNames = false)
@Table(name = "author")

public class Author implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @NonNull
    @Column(name = "name")
    private String name;

    @Column(name = "sur_name")
    private String surName;

    @OneToMany(fetch = FetchType.EAGER, targetEntity = Book.class, mappedBy="author")// может быть автором нескольких книг (Book)
    private List<Book> books = new ArrayList<>();

    public Author(long id, String name){
        this.id = id;
        this.name = name;
    }

    public Author(String name){
        this.name = name;
    }

}
