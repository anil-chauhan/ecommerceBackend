package org.ecommerce.shared_database_api.models;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.List;

@Entity
@Table(name = "categories")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id", nullable = false)
    private Integer categoryId;

    @Column(name = "category_name", nullable = false, unique = true, length = 100)
    private String categoryName;

    @Column(name = "url_slug", nullable = false, length = Integer.MAX_VALUE)
    private String urlSlug;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.SET_NULL)
    @JoinColumn(name = "parent_cat_id")
    @ToString.Exclude
    private Category parentCat;

    @Column(name = "status", nullable = false, length = 20)
    private String status;

    @OneToMany(mappedBy = "parentCat", cascade = CascadeType.ALL)
    private List<Category> subCategories;

    //@Column(name = "isExpanded")
    //private boolean isExpanded; // New property for expand/collapse state

}
