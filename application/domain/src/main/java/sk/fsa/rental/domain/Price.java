package sk.fsa.rental.domain;

import java.math.BigDecimal;

public class Price {
    private BigDecimal amount;
    private String currency;

    public Price() {
    }

    public Price(BigDecimal amount, String currency) {
        this.amount = amount;
        this.currency = currency;
    }

    public void validate() {
        require(amount != null,
                "Price amount is required.");
        require(amount.compareTo(BigDecimal.ZERO) > 0,
                "Price amount must be positive.");
        require(currency != null && !currency.trim().isEmpty(),
                "Currency is required.");
    }

    private void require(boolean condition, String message) {
        if (!condition) {
            throw new RentalException(RentalException.Type.VALIDATION, message);
        }
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }
}
