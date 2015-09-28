package br.com.cast.turmaformacao.controledeestoque.model.entities;

import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Product implements Parcelable {

    @JsonIgnore
    private Long id;

    @JsonProperty("id")
    private Long idWeb;

    @JsonProperty("image")
    private String image;

    @JsonProperty("name")
    private String name;

    @JsonProperty("description")
    private String description;

    @JsonProperty("stock")
    private Integer quantity;

    @JsonProperty("minimunStock")
    private Integer minQuantity;

    @JsonProperty("unitaryValue")
    private Double price;

    @JsonIgnore
    private Long date;

    public Product() {
        super();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
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

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Integer getMinQuantity() {
        return minQuantity;
    }

    public void setMinQuantity(Integer minQuantity) {
        this.minQuantity = minQuantity;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Long getIdWeb() {
        return idWeb;
    }

    public void setIdWeb(Long idWeb) {
        this.idWeb = idWeb;
    }

    public Long getDate() {
        return date;
    }

    public void setDate(Long date) {
        this.date = date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Product)) return false;

        Product product = (Product) o;

        if (getId() != null ? !getId().equals(product.getId()) : product.getId() != null)
            return false;
        if (getIdWeb() != null ? !getIdWeb().equals(product.getIdWeb()) : product.getIdWeb() != null)
            return false;
        if (getImage() != null ? !getImage().equals(product.getImage()) : product.getImage() != null)
            return false;
        if (getName() != null ? !getName().equals(product.getName()) : product.getName() != null)
            return false;
        if (getDescription() != null ? !getDescription().equals(product.getDescription()) : product.getDescription() != null)
            return false;
        if (getQuantity() != null ? !getQuantity().equals(product.getQuantity()) : product.getQuantity() != null)
            return false;
        if (getMinQuantity() != null ? !getMinQuantity().equals(product.getMinQuantity()) : product.getMinQuantity() != null)
            return false;
        if (getPrice() != null ? !getPrice().equals(product.getPrice()) : product.getPrice() != null)
            return false;
        return !(getDate() != null ? !getDate().equals(product.getDate()) : product.getDate() != null);

    }

    @Override
    public int hashCode() {
        int result = getId() != null ? getId().hashCode() : 0;
        result = 31 * result + (getIdWeb() != null ? getIdWeb().hashCode() : 0);
        result = 31 * result + (getImage() != null ? getImage().hashCode() : 0);
        result = 31 * result + (getName() != null ? getName().hashCode() : 0);
        result = 31 * result + (getDescription() != null ? getDescription().hashCode() : 0);
        result = 31 * result + (getQuantity() != null ? getQuantity().hashCode() : 0);
        result = 31 * result + (getMinQuantity() != null ? getMinQuantity().hashCode() : 0);
        result = 31 * result + (getPrice() != null ? getPrice().hashCode() : 0);
        result = 31 * result + (getDate() != null ? getDate().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", idWeb=" + idWeb +
                ", image='" + image + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", quantity=" + quantity +
                ", minQuantity=" + minQuantity +
                ", price=" + price +
                ", date=" + date +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.id);
        dest.writeValue(this.idWeb);
        dest.writeString(this.image);
        dest.writeString(this.name);
        dest.writeString(this.description);
        dest.writeValue(this.quantity);
        dest.writeValue(this.minQuantity);
        dest.writeValue(this.price);
        dest.writeValue(this.date);
    }

    protected Product(Parcel in) {
        this.id = (Long) in.readValue(Long.class.getClassLoader());
        this.idWeb = (Long) in.readValue(Long.class.getClassLoader());
        this.image = in.readString();
        this.name = in.readString();
        this.description = in.readString();
        this.quantity = (Integer) in.readValue(Integer.class.getClassLoader());
        this.minQuantity = (Integer) in.readValue(Integer.class.getClassLoader());
        this.price = (Double) in.readValue(Double.class.getClassLoader());
        this.date = (Long) in.readValue(Long.class.getClassLoader());
    }

    public static final Creator<Product> CREATOR = new Creator<Product>() {
        public Product createFromParcel(Parcel source) {
            return new Product(source);
        }

        public Product[] newArray(int size) {
            return new Product[size];
        }
    };
}
