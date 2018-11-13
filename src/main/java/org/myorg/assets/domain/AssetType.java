package org.myorg.assets.domain;

import java.io.Serializable;
import java.util.Collection;
import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import javax.json.bind.annotation.JsonbTransient;
import javax.persistence.Basic;
import javax.persistence.Cacheable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *  Defines the types of assets a company has
 * @author jnamla
 */
@Entity
@Cacheable
@Table(name = "asset_type")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AssetType.findAll", query = "SELECT a FROM AssetType a")
    , @NamedQuery(name = "AssetType.findById", query = "SELECT a FROM AssetType a WHERE a.id = :id")
    , @NamedQuery(name = "AssetType.findByName", query = "SELECT a FROM AssetType a WHERE a.name = :name")
    , @NamedQuery(name = "AssetType.findByCode", query = "SELECT a FROM AssetType a WHERE a.code = :code")})
public class AssetType implements Serializable {

    private static final long serialVersionUID = 1L;
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
    @Size(min = 1, max = 4)
    @Column(name = "code")
    private String code;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "assetType", fetch = FetchType.LAZY)
    @JsonbTransient
    private Collection<FixedAsset> fixedAssetCollection;

    public AssetType() {
    }

    public AssetType(Integer id) {
        this.id = id;
    }

    public AssetType(Integer id, String name, String code) {
        this.id = id;
        this.name = name;
        this.code = code;
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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @XmlTransient
    public Collection<FixedAsset> getFixedAssetCollection() {
        return fixedAssetCollection;
    }

    public void setFixedAssetCollection(Collection<FixedAsset> fixedAssetCollection) {
        this.fixedAssetCollection = fixedAssetCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof AssetType)) {
            return false;
        }
        AssetType other = (AssetType) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
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
