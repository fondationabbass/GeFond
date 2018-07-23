package com.bdi.fondation.service.dto;

import java.util.Arrays;

import com.bdi.fondation.domain.Echeance;
import com.bdi.fondation.domain.ElementFinancement;
import com.bdi.fondation.domain.Garantie;
import com.bdi.fondation.domain.Pret;

public class PretAggregate {
	private Pret pret;
	private ElementFinancement[] elementFinancements;
	private Garantie[] garanties;
	private Echeance[] echeances;
	public Pret getPret() {
		return pret;
	}
	public void setPret(Pret pret) {
		this.pret = pret;
	}
	public ElementFinancement[] getElementFinancements() {
		return elementFinancements;
	}
	public void setElementFinancements(ElementFinancement[] elementFinancements) {
		this.elementFinancements = elementFinancements;
	}
	public Garantie[] getGaranties() {
		return garanties;
	}
	public void setGaranties(Garantie[] garanties) {
		this.garanties = garanties;
	}
	public Echeance[] getEcheances() {
		return echeances;
	}
	public void setEcheances(Echeance[] echeances) {
		this.echeances = echeances;
	}
	@Override
	public String toString() {
		return "PretAggregate [pret=" + pret + ", elementFinancements=" + Arrays.toString(elementFinancements)
				+ ", garanties=" + Arrays.toString(garanties) + ", echeances=" + Arrays.toString(echeances) + "]";
	}
	
}
