package com.cognizant.omniretail.model;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name="Category")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id", unique = true)
    private Long categoryId;

    @Column(name="Category_name",nullable = false)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)// in the context of current entity as child category
    @JoinColumn(name="parentCategory_id", referencedColumnName = "categoryId")
    @JsonBackReference
    private Category parentCategory;

    // in the context of current entity as parent category
    @OneToMany(mappedBy="parentCategory", cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<Category> subCategories;
}
