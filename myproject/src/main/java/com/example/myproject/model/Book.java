package com.example.myproject.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Objects;

@Document(collection = "books")
@Data  // Генерує гетери, сетери, toString(), equals() і hashCode()
@NoArgsConstructor  // Генерує конструктор без параметрів
@AllArgsConstructor // Генерує конструктор зі всіма параметрами
@Getter
@Setter
@ToString
@Builder
public class Book extends AuditMetadata {
    @Id
    private String id;
    private String name;
    private String code;
    private String description;

    public Book(String name, String code, String description) {
        this.name = name;
        this.code = code;
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Book book)) return false;
        return Objects.equals(getId(), book.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }
}
