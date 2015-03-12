package br.com.mobicare.viajabessa.viajebem.models;

import java.io.Serializable;

import com.google.gson.annotations.SerializedName;

public class Pacote implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5609930187737658091L;

	@SerializedName("id")
	public int idPacote;

	@SerializedName("nome")
	public String nome;

	@SerializedName("titulo")
	public String titulo;

	@SerializedName("descricao")
	public String descricao;

	@SerializedName("pagamento")
	public String pagamento;

	@SerializedName("valor")
	public String valor;

	@SerializedName("url_foto")
	public String urlImagem;

	public byte[] byteArrayImage;

}
