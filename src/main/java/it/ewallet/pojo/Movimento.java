package it.ewallet.pojo;

import java.util.Objects;

public class Movimento {
	
	private String tipo;
	private double importo;
	private String data;
	private int ibanM;
	
	public String getTipo() {
		return tipo;
	}
	public double getImporto() {
		return importo;
	}
	public String getData() {
		return data;
	}
	public int getIbanM() {
		return ibanM;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	public void setImporto(double importo) {
		this.importo = importo;
	}
	public void setData(String data) {
		this.data = data;
	}
	public void setIbanM(int ibanM) {
		this.ibanM = ibanM;
	}
	@Override
	public int hashCode() {
		return Objects.hash(ibanM);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Movimento other = (Movimento) obj;
		return ibanM == other.ibanM;
	}
	@Override
	public String toString() {
		return "Movimento [tipo=" + tipo + ", importo=" + importo + ", data=" + data + ", ibanM=" + ibanM + "]";
	}
		

}
