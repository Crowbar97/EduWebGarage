package edu.web.garage.entities;

import javax.persistence.*;

@Entity
@Table(name="own_models")
public class OwnModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private int id;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="model_id")
    private Model model;

    @Column(name="count")
    private int count;

    // for hibernate
    public OwnModel() {}
    public OwnModel(Model model, int count) {
        this.model = model;
        this.count = count;
    }

    // getters for jackson
    public int getCount() {
        return count;
    }
    public int getId() {
        return id;
    }
    public Model getModel() {
        return model;
    }

    public void incCount() {
        count++;
    }
    public void decCount() {
        count--;
    }
}