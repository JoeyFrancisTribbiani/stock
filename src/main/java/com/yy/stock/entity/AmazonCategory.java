package com.yy.stock.entity;

import com.yy.stock.bot.amazonbot.model.TraversalStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@Entity
@Accessors(chain = true)
@Table(name = "amazon_category")
//@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class AmazonCategory implements Serializable {
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
//    @OneToMany(fetch = FetchType.LAZY)
//    @JoinColumn(name = "parent_id")
@Transient
    private List<AmazonCategory> childrena = new ArrayList<>();
    public void setChildren(List<AmazonCategory> children) {
        this.childrena = children;
    }
    @Transient
    public List<AmazonCategory> getChildren() {
        return childrena;
    }

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
