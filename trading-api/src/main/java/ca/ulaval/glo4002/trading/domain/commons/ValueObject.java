package ca.ulaval.glo4002.trading.domain.commons;

import org.apache.commons.lang.builder.*;

public class ValueObject implements Comparable<Object> {

    @Override
    public boolean equals(Object o) {
        return EqualsBuilder.reflectionEquals(this, o);
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }

    @Override
    public int compareTo(Object o) {
        return CompareToBuilder.reflectionCompare(this, o);
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SIMPLE_STYLE);
    }

}
