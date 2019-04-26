package ca.ulaval.glo4002.trading.infrastructure.stock.dto;

import java.util.List;

public class RestApiStockDTO {

    private Integer id;
    private String symbol;
    private String type;
    private String market;
    private List<RestApiPriceDTO> prices;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMarket() {
        return market;
    }

    public void setMarket(String market) {
        this.market = market;
    }

    public List<RestApiPriceDTO> getPrices() {
        return prices;
    }

    public void setPrices(List<RestApiPriceDTO> prices) {
        this.prices = prices;
    }

}
