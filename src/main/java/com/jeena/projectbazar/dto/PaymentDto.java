package com.asiya.projectbazar.dto;

public class PaymentDto {
	private double amount;
	private double tax_amount;
	private double total_amount;
	private String transactionalUUID;
	private double psc;
	private double pdc;
	private String product_code;
	private String success_url;
	private String failure_url;
	private String signed_field_name;
	private String signature;

	public PaymentDto() {
		super();
	}

	public PaymentDto(double amount, double tax_amount, double total_amount, String transactionalUUID, double psc,
			double pdc, String product_code, String success_url, String failure_url, String signed_field_name,
			String signature) {
		super();
		this.amount = amount;
		this.tax_amount = tax_amount;
		this.total_amount = total_amount;
		this.transactionalUUID = transactionalUUID;
		this.psc = psc;
		this.pdc = pdc;
		this.product_code = product_code;
		this.success_url = success_url;
		this.failure_url = failure_url;
		this.signed_field_name = signed_field_name;
		this.signature = signature;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public double getTax_amount() {
		return tax_amount;
	}

	public void setTax_amount(double tax_amount) {
		this.tax_amount = tax_amount;
	}

	public double getTotal_amount() {
		return total_amount;
	}

	public void setTotal_amount(double total_amount) {
		this.total_amount = total_amount;
	}

	public String getTransactionalUUID() {
		return transactionalUUID;
	}

	public void setTransactionalUUID(String transactionalUUID) {
		this.transactionalUUID = transactionalUUID;
	}

	public double getPsc() {
		return psc;
	}

	public void setPsc(double psc) {
		this.psc = psc;
	}

	public double getPdc() {
		return pdc;
	}

	public void setPdc(double pdc) {
		this.pdc = pdc;
	}

	public String getProduct_code() {
		return product_code;
	}

	public void setProduct_code(String product_code) {
		this.product_code = product_code;
	}

	public String getSuccess_url() {
		return success_url;
	}

	public void setSuccess_url(String success_url) {
		this.success_url = success_url;
	}

	public String getFailure_url() {
		return failure_url;
	}

	public void setFailure_url(String failure_url) {
		this.failure_url = failure_url;
	}

	public String getSigned_field_name() {
		return signed_field_name;
	}

	public void setSigned_field_name(String signed_field_name) {
		this.signed_field_name = signed_field_name;
	}

	public String getSignature() {
		return signature;
	}

	public void setSignature(String signature) {
		this.signature = signature;
	}
}
