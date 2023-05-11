package com.yy.stock.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerator;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.yy.stock.bot.amazonbot.model.TraversalStatus;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Entity
@Accessors(chain = true)
@Table(name = "amazon_category")
//@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class AmazonCategory {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "marketplace_id", nullable = false)
    private String marketplaceId;

    @Column(name = "category_id", nullable = false)
    private String categoryId;
    @Column(name = "parent_id")
    private String parentId;
//    @ToString.Exclude
//    @ManyToOne
//    @JoinColumn(name = "parent_id", referencedColumnName = "category_id")
//    @JsonIgnoreProperties(value = {"children"}, allowSetters = true)
//    private AmazonCategory parent;
    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "parent_id")
    private List<AmazonCategory> children;

    @Column(name = "ancestors_id")
    private String ancestorsId;
    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "url", nullable = false)
    private String url;
    @Column(name = "traversal_status")
    @Enumerated(EnumType.STRING)
    private TraversalStatus traversalStatus;
    @Column(name = "last_traversal_time")
    private Date lastTraversalTime;

    @Override
    public String toString() {
        return "Category [id=" + categoryId + ", name=" + name + "]";
    }
}
