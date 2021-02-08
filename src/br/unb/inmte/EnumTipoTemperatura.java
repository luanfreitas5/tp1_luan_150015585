package br.unb.inmte;

public enum EnumTipoTemperatura {

	MAXIMO("máximo"), MINIMO("mínimo");

	private String tipo;

	private EnumTipoTemperatura(String tipo) {
		this.tipo = tipo;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

}
