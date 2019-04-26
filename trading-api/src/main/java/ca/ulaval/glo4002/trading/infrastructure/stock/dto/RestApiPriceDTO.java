package ca.ulaval.glo4002.trading.infrastructure.stock.dto;

public class RestApiPriceDTO {

    private Integer id;
    private RestApiStockDTO stock;
    private String date;
    private double price;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public RestApiStockDTO getStock() {
        return stock;
    }

    public void setStock(RestApiStockDTO stock) {
        this.stock = stock;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

}
