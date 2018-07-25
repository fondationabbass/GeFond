package com.bdi.fondation.service.dto;

import com.bdi.fondation.domain.Parametrage;

public class PeriodType{
	private int coeff;
	private String type;
	private String label;
	public PeriodType() {
	}
	public PeriodType(Parametrage parametrage) {
		this.coeff = Integer.valueOf(parametrage.getLib2());
		this.type = parametrage.getLib1();
		this.label = parametrage.getLibelle();
	}
	public int getCoeff() {
		return coeff;
	}
	public void setCoeff(int coeff) {
		this.coeff = coeff;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	@Override
	public String toString() {
		return "PeriodType [coeff=" + coeff + ", type=" + type + ", label=" + label + "]";
	}
	
}