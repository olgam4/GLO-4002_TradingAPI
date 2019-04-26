package ca.ulaval.glo4002.stocks.domain.companies;

import ca.ulaval.glo4002.stocks.domain.stocks.Stock;
import org.springframework.data.rest.core.annotation.RestResource;

import javax.persistence.*;
import java.util.List;

@Entity
public class Company {

  @Id
  @GeneratedValue
  private Integer id;

  @Column
  private String fullName;

  @Column
  private String industry;

  @OneToMany(fetch = FetchType.LAZY, mappedBy = "company")
  @RestResource(exported = false)
  private List<Stock> stocks;

  public Integer getId() {
    return id;
  }

  public String getFullName() {
    return fullName;
  }

  public String getIndustry() {
    return industry;
  }

  public List<Stock> getStocks() {
    return stocks;
  }
}
