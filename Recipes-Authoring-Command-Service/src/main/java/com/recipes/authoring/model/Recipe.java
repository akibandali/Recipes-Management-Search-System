package com.recipes.authoring.model;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "recipes")
public class Recipe {
    @Id
    private String id;
    private String name;
    private String type;
    private String chef;
    private Integer servings;
    @ElementCollection(targetClass=String.class)
    private List<String> ingredients = new ArrayList<>();
    @ElementCollection(targetClass=String.class)
    private List<String> instructions = new ArrayList<>();

}

