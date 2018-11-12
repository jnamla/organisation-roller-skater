package org.myorg.organisation.domain;

import java.io.Serializable;
import java.util.Objects;
import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 * Concrete areas with their home city.
 * 
 * @author jnamla
 */
@Entity
@Table(name = "area")
@NamedQueries({
    @NamedQuery(name = Area.FIND_ALL, query = "SELECT a FROM Area a"),
    @NamedQuery(name = Area.FIND_BY_CITY_AND_GENERIC_AREA, query = "SELECT a FROM Area a WHERE a.city.id = :idCity and a.genericArea.id = :idGenericArea")})
public class Area implements Serializable {

    private static final long serialVersionUID = 1L;
    public static final String FIND_ALL = "Area.findAll";
    public static final String FIND_BY_CITY_AND_GENERIC_AREA = "Area.findByCityAndGenericArea";
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    
    @NotNull
    @JoinColumn(name = "city_id", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private City city;
    
    @NotNull
    @JoinColumn(name = "generic_area_id", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private GenericArea genericArea;

    public Area() {
    }

    public Area(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public GenericArea getGenericArea() {
        return genericArea;
    }

    public void setGenericArea(GenericArea genericArea) {
        this.genericArea = genericArea;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Area)) {
            return false;
        }
        Area other = (Area) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        if ((Objects.nonNull(this.city) && !this.city.equals(other.city)) || (Objects.nonNull(this.genericArea) && (!this.genericArea.equals(other.genericArea)))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        Jsonb jsonb = JsonbBuilder.create(); 
        return jsonb.toJson(this);
    }
    
}
