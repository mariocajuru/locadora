package br.com.locadora.modelo;

// Generated 11/04/2014 14:12:50 by Hibernate Tools 4.0.0

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * ImovelCaracteristicasId generated by hbm2java
 */
@Embeddable
public class ImovelCaracteristicasId implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2739445489300388198L;
	private int imoId;
	private int carId;

	public ImovelCaracteristicasId() {
	}

	public ImovelCaracteristicasId(int imoId, int carId) {
		this.imoId = imoId;
		this.carId = carId;
	}

	@Column(name = "IMO_ID", nullable = false)
	public int getImoId() {
		return this.imoId;
	}

	public void setImoId(int imoId) {
		this.imoId = imoId;
	}

	@Column(name = "CAR_ID", nullable = false)
	public int getCarId() {
		return this.carId;
	}

	public void setCarId(int carId) {
		this.carId = carId;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof ImovelCaracteristicasId))
			return false;
		ImovelCaracteristicasId castOther = (ImovelCaracteristicasId) other;

		return (this.getImoId() == castOther.getImoId())
				&& (this.getCarId() == castOther.getCarId());
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result + this.getImoId();
		result = 37 * result + this.getCarId();
		return result;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		return "ImovelCaracteristicasId [imoId=" + imoId + ", carId=" + carId
				+ "]";
	}

}