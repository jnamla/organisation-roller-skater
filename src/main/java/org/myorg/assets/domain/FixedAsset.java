package org.myorg.assets.domain;

import org.myorg.organisation.domain.Area;
import org.myorg.organisation.domain.Personnel;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;
import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import javax.json.bind.annotation.JsonbDateFormat;
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
import javax.validation.constraints.Size;

/**
 *  Class that represents the fixedAssets of the organisation
 * 
 * @author jnamla
 */
@Entity
@Table(name = "fixed_asset")
@NamedQueries({
    @NamedQuery(name = FixedAsset.FIND_ALL , query = "SELECT f FROM FixedAsset f"),
    @NamedQuery(name = FixedAsset.FIND_BY_TYPE, query = "SELECT f FROM FixedAsset f WHERE f.assetType.id = :id"),
    @NamedQuery(name = FixedAsset.FIND_BY_PURCHASE_DATE , query = "SELECT f FROM FixedAsset f WHERE f.purchaseDate = :date"),
    @NamedQuery(name = FixedAsset.FIND_BY_SERIAL_NUMBER, query = "SELECT f FROM FixedAsset f WHERE f.serialNumber like :number"),
    @NamedQuery(name = FixedAsset.FIND_BY_UNIQUE_FIELDS, query = "SELECT f FROM FixedAsset f WHERE f.serialNumber = :serialNumber or f.internalStockNumber = :internalStockNumber")})
public class FixedAsset implements Serializable {

    private static final long serialVersionUID = 1L;
    public static final String FIND_ALL = "FixedAsset.findAll";
    public static final String FIND_BY_TYPE = "FixedAsset.findByType";
    public static final String FIND_BY_PURCHASE_DATE = "FixedAsset.findByPurchase";
    public static final String FIND_BY_SERIAL_NUMBER = "FixedAsset.findBySerialNumber";
    public static final String FIND_BY_UNIQUE_FIELDS = "FixedAsset.findByUniqueFields";
    
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
    
    @Size(max = 45)
    @Column(name = "description")
    private String description;
    
    @Basic(optional = false)
    @Size(min = 1, max = 45)
    @Column(name = "serial_number")
    private String serialNumber;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "internal_stock_number")
    private int internalStockNumber;
    
    @Basic(optional = false)
    @Column(name = "weight")
    private float weight;
    
    @Basic(optional = false)
    @Column(name = "height")
    private float height;
    
    @Basic(optional = false)
    @Column(name = "width")
    private float width;
    
    @Basic(optional = false)
    @Column(name = "asset_length")
    private float assetLength;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "purchase_value")
    private float purchaseValue;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "purchase_date")
    @JsonbDateFormat("dd-MM-yyyy")
    private LocalDate purchaseDate;
    
    @Column(name = "out_of_stock_date")
    @JsonbDateFormat("dd-MM-yyyy")
    private LocalDate outOfStockDate;
    
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "color")
    private String color;
    
    @NotNull
    @JoinColumn(name = "asset_type_id", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private AssetType assetType;
     
    @NotNull
    @JoinColumn(name = "status_id", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private AssetStatus status;
          
    @JoinColumn(name = "area_id", referencedColumnName = "id")
    @ManyToOne(fetch = FetchType.EAGER)
    private Area area;
    
    @JoinColumn(name = "personnel_id", referencedColumnName = "id")
    @ManyToOne(fetch = FetchType.EAGER)
    private Personnel personnel;
    
    public FixedAsset() {
    }

    public FixedAsset(Integer id) {
        this.id = id;
    }

    public FixedAsset(Integer id, String name, String serialNumber, int internalStockNumber, float purchaseValue, LocalDate purchaseDate, String color) {
        this.id = id;
        this.name = name;
        this.serialNumber = serialNumber;
        this.internalStockNumber = internalStockNumber;
        this.purchaseValue = purchaseValue;
        this.purchaseDate = purchaseDate;
        this.color = color;
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

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public int getInternalStockNumber() {
        return internalStockNumber;
    }

    public void setInternalStockNumber(int internalStockNumber) {
        this.internalStockNumber = internalStockNumber;
    }

    public float getWeight() {
        return weight;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public float getWidth() {
        return width;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public float getAssetLength() {
        return assetLength;
    }

    public void setAssetLength(float length) {
        this.assetLength = length;
    }

    public float getPurchaseValue() {
        return purchaseValue;
    }

    public void setPurchaseValue(float purchaseValue) {
        this.purchaseValue = purchaseValue;
    }

    public LocalDate getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(LocalDate purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public LocalDate getOutOfStockDate() {
        return outOfStockDate;
    }

    public void setOutOfStockDate(LocalDate outOfStockDate) {
        this.outOfStockDate = outOfStockDate;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public AssetType getAssetType() {
        return assetType;
    }

    public void setAssetType(AssetType assetType) {
        this.assetType = assetType;
    }

    public AssetStatus getStatus() {
        return status;
    }

    public void setStatus(AssetStatus status) {
        this.status = status;
    }
    
    public Area getArea() {
        return area;
    }

    public void setArea(Area area) {
        this.area = area;
    }
    
    public Personnel getPersonnel() {
        return personnel;
    }

    public void setPersonnel(Personnel personnel) {
        this.personnel = personnel;
    }
    
    public boolean isOutOfStockDateValid () {
        if (Objects.nonNull(this.outOfStockDate)){
            return this.purchaseDate.isBefore(this.outOfStockDate);
        }
        return true;
    }
            
            
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof FixedAsset)) {
            return false;
        }
        FixedAsset other = (FixedAsset) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        if(!this.serialNumber.equals(other.serialNumber)){
            return false;
        }
        return this.internalStockNumber == other.internalStockNumber;
    }

    @Override
    public String toString() {
        Jsonb jsonb = JsonbBuilder.create(); 
        return jsonb.toJson(this);
    }
    
}
