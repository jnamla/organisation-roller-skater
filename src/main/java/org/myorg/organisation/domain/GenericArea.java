package org.myorg.organisation.domain;

import java.io.Serializable;
import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *  Definition of the areas in which the company is divided
 * 
 * @author jnamla
 */
@Entity
@Table(name = "generic_area")
@NamedQueries({
    @NamedQuery(name = GenericArea.FIND_ALL, query = "SELECT g FROM GenericArea g"),
    @NamedQuery(name = GenericArea.FIND_BY_NAME, query = "SELECT g FROM GenericArea g WHERE upper(g.name) = :name")})
public class GenericArea implements Serializable {

    private static final long serialVersionUID = 1L;
    public static final String FIND_ALL = "GenericArea.findAll";
    public static final String FIND_BY_NAME = "GenericArea.findByName";
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "name")
    private String name;
    
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 90)
    @Column(name = "description")
    private String description;
    
    public GenericArea() {
    }

    public GenericArea(Integer id) {
        this.id = id;
    }

    public GenericArea(Integer id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof GenericArea)) {
            return false;
        }
        GenericArea other = (GenericArea) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return this.name.equalsIgnoreCase(other.name);
    }

    @Override
    public String toString() {
        Jsonb jsonb = JsonbBuilder.create(); 
        return jsonb.toJson(this);
    }
    
}
