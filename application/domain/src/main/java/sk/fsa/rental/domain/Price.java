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
        requireField(amount != null,
                "price.amount", "Price amount is required.");
        requireField(amount.compareTo(BigDecimal.ZERO) > 0,
                "price.amount", "Price amount must be positive.");
        requireField(currency != null && !currency.trim().isEmpty(),
                "price.currency", "Currency is required.");
    }

    private void requireField(boolean condition, String field, String message) {
        if (!condition) {
            throw new RentalException(RentalException.Type.VALIDATION, message, field);
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
