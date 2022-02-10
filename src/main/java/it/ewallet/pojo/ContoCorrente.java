package it.ewallet.pojo;

import java.util.Objects;

public class ContoCorrente {
	
	private int iban;
	private String intestatario;
	private double saldo;
	private String dataApertura;
	
	public int getIban() {
		return iban;
	}
	public String getIntestatario() {
		return intestatario;
	}
	public double getSaldo() {
		return saldo;
	}
	public String getDataApertura() {
		return dataApertura;
	}
	public void setIban(int iban) {
		this.iban = iban;
	}
	public void setIntestatario(String intestatario) {
		this.intestatario = intestatario;
	}
	public void setSaldo(double saldo) {
		this.saldo = saldo;
	}
	public void setDataApertura(String dataApertura) {
		this.dataApertura = dataApertura;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(iban);
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ContoCorrente other = (ContoCorrente) obj;
		return iban == other.iban;
	}
	@Override
	public String toString() {
		return "ContoCorrente [iban=" + iban + ", intestatario=" + intestatario + ", saldo=" + saldo + ", dataApertura="
				+ dataApertura + "]";
	}
	
}
