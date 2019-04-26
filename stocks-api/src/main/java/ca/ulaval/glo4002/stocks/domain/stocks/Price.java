package ca.ulaval.glo4002.stocks.domain.stocks;

import org.springframework.data.rest.core.annotation.RestResource;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.Instant;

@Entity
public class Price {

  @Id
  @GeneratedValue
  private Integer id;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "stock_id", nullable = false)
  @RestResource(exported = false)
  private Stock stock;

  @Column
  private Instant date;

  @Column(name = "amount")
  private BigDecimal price;

  public Instant getDate() {
    return date;
  }

  public BigDecimal getPrice() {
    return price;
  }
}
