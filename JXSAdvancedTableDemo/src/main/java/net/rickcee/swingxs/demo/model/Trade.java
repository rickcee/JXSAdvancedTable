package net.rickcee.swingxs.demo.model;

import java.util.Date;

/**
 * Created on Jan 23, 2012
 */

/**
 * @author RickCeeNet
 * 
 */
public class Trade {

	private Integer entityId;
	private String customerId;
	private String transactionNumber;
	private String buySellCode;
	private Long quantity;
	private Double price;
	private Long principal;
	private String tradeCurrency;
	private String settlementCurrency;
	private Date tradeDate;
	private Date settlementDate;
	private String cusip;
	private String isin;
	private String sedol;
	private String notes;

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Trade [entityId=" + entityId + ", customerId=" + customerId + ", transactionNumber=" + transactionNumber
				+ ", buySellCode=" + buySellCode + ", quantity=" + quantity + ", price=" + price + ", principal="
				+ principal + ", tradeCurrency=" + tradeCurrency + ", settlementCurrency=" + settlementCurrency
				+ ", tradeDate=" + tradeDate + ", settlementDate=" + settlementDate + ", cusip=" + cusip + ", isin="
				+ isin + ", sedol=" + sedol + ", notes=" + notes + "]";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((entityId == null) ? 0 : entityId.hashCode());
		result = prime * result + ((tradeDate == null) ? 0 : tradeDate.hashCode());
		result = prime * result + ((transactionNumber == null) ? 0 : transactionNumber.hashCode());
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Trade other = (Trade) obj;
		if (entityId == null) {
			if (other.entityId != null)
				return false;
		} else if (!entityId.equals(other.entityId))
			return false;
		if (tradeDate == null) {
			if (other.tradeDate != null)
				return false;
		} else if (!tradeDate.equals(other.tradeDate))
			return false;
		if (transactionNumber == null) {
			if (other.transactionNumber != null)
				return false;
		} else if (!transactionNumber.equals(other.transactionNumber))
			return false;
		return true;
	}

	/**
	 * @return the entityId
	 */
	public Integer getEntityId() {
		return entityId;
	}

	/**
	 * @param entityId
	 *            the entityId to set
	 */
	public void setEntityId(Integer entityId) {
		this.entityId = entityId;
	}

	/**
	 * @return the customerId
	 */
	public String getCustomerId() {
		return customerId;
	}

	/**
	 * @param customerId
	 *            the customerId to set
	 */
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	/**
	 * @return the transactionNumber
	 */
	public String getTransactionNumber() {
		return transactionNumber;
	}

	/**
	 * @param transactionNumber
	 *            the transactionNumber to set
	 */
	public void setTransactionNumber(String transactionNumber) {
		this.transactionNumber = transactionNumber;
	}

	/**
	 * @return the buySellCode
	 */
	public String getBuySellCode() {
		return buySellCode;
	}

	/**
	 * @param buySellCode
	 *            the buySellCode to set
	 */
	public void setBuySellCode(String buySellCode) {
		this.buySellCode = buySellCode;
	}

	/**
	 * @return the quantity
	 */
	public Long getQuantity() {
		return quantity;
	}

	/**
	 * @param quantity
	 *            the quantity to set
	 */
	public void setQuantity(Long quantity) {
		this.quantity = quantity;
	}

	/**
	 * @return the price
	 */
	public Double getPrice() {
		return price;
	}

	/**
	 * @param price
	 *            the price to set
	 */
	public void setPrice(Double price) {
		this.price = price;
	}

	/**
	 * @return the principal
	 */
	public Long getPrincipal() {
		return principal;
	}

	/**
	 * @param principal
	 *            the principal to set
	 */
	public void setPrincipal(Long principal) {
		this.principal = principal;
	}

	/**
	 * @return the tradeCurrency
	 */
	public String getTradeCurrency() {
		return tradeCurrency;
	}

	/**
	 * @param tradeCurrency
	 *            the tradeCurrency to set
	 */
	public void setTradeCurrency(String tradeCurrency) {
		this.tradeCurrency = tradeCurrency;
	}

	/**
	 * @return the settlementCurrency
	 */
	public String getSettlementCurrency() {
		return settlementCurrency;
	}

	/**
	 * @param settlementCurrency
	 *            the settlementCurrency to set
	 */
	public void setSettlementCurrency(String settlementCurrency) {
		this.settlementCurrency = settlementCurrency;
	}

	/**
	 * @return the tradeDate
	 */
	public Date getTradeDate() {
		return tradeDate;
	}

	/**
	 * @param tradeDate
	 *            the tradeDate to set
	 */
	public void setTradeDate(Date tradeDate) {
		this.tradeDate = tradeDate;
	}

	/**
	 * @return the settlementDate
	 */
	public Date getSettlementDate() {
		return settlementDate;
	}

	/**
	 * @param settlementDate
	 *            the settlementDate to set
	 */
	public void setSettlementDate(Date settlementDate) {
		this.settlementDate = settlementDate;
	}

	/**
	 * @return the cusip
	 */
	public String getCusip() {
		return cusip;
	}

	/**
	 * @param cusip
	 *            the cusip to set
	 */
	public void setCusip(String cusip) {
		this.cusip = cusip;
	}

	/**
	 * @return the isin
	 */
	public String getIsin() {
		return isin;
	}

	/**
	 * @param isin
	 *            the isin to set
	 */
	public void setIsin(String isin) {
		this.isin = isin;
	}

	/**
	 * @return the sedol
	 */
	public String getSedol() {
		return sedol;
	}

	/**
	 * @param sedol
	 *            the sedol to set
	 */
	public void setSedol(String sedol) {
		this.sedol = sedol;
	}

	/**
	 * @return the notes
	 */
	public String getNotes() {
		return notes;
	}

	/**
	 * @param notes
	 *            the notes to set
	 */
	public void setNotes(String notes) {
		this.notes = notes;
	}

}
