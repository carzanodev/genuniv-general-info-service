package carzanodev.genuniv.microservices.general.persistence.entity;

import java.io.Serializable;
import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;

@Entity
@Data
public class SchoolYear implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "is_active")
    private boolean isActive = true;

    @Column(name = "inserted_at", insertable = false, updatable = false)
    private Timestamp insertedAt;

    @Column(name = "updated_at", insertable = false, updatable = false)
    private Timestamp updatedAt;

}
