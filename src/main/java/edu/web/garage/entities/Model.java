package edu.web.garage.entities;

import javax.persistence.*;

@Entity
@Table(name="models")
public class Model {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private int id;

    @Column(name="name")
    private String name;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "mark_id")
    private Mark mark;

    @OneToOne(mappedBy = "model")
    private OwnModel ownModel;

    // for hibernate
    public Model() {}
    public Model(String name, Mark mark) {
        this.name = name;
        this.mark = mark;
    }

    // getters for jackson
    public int getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public Mark getMark() {
        return mark;
    }
}