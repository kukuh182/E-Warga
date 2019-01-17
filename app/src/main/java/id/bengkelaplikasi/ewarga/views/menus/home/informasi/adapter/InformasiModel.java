package id.bengkelaplikasi.ewarga.views.menus.home.informasi.adapter;

public class InformasiModel {
	private String rlevelinfoNama;
	private String tinformasiContent;
	private String tinformasiTglinput;
	private String rinfotypeNama;
	private String tinformasiJudul;
	private long tinformasiId;

	public void setRlevelinfoNama(String rlevelinfoNama){
		this.rlevelinfoNama = rlevelinfoNama;
	}

	public String getRlevelinfoNama(){
		return rlevelinfoNama;
	}

	public void setTinformasiContent(String tinformasiContent){
		this.tinformasiContent = tinformasiContent;
	}

	public String getTinformasiContent(){
		return tinformasiContent;
	}

	public void setTinformasiTglinput(String tinformasiTglinput){
		this.tinformasiTglinput = tinformasiTglinput;
	}

	public String getTinformasiTglinput(){
		return tinformasiTglinput;
	}

	public void setRinfotypeNama(String rinfotypeNama){
		this.rinfotypeNama = rinfotypeNama;
	}

	public String getRinfotypeNama(){
		return rinfotypeNama;
	}

	public void setTinformasiJudul(String tinformasiJudul){
		this.tinformasiJudul = tinformasiJudul;
	}

	public String getTinformasiJudul(){
		return tinformasiJudul;
	}

	public void setTinformasiId(long tinformasiId){
		this.tinformasiId = tinformasiId;
	}

	public long getTinformasiId(){
		return tinformasiId;
	}

	@Override
 	public String toString(){
		return 
			"AgendaModel{" + 
			"rlevelinfo_nama = '" + rlevelinfoNama + '\'' + 
			",tinformasi_content = '" + tinformasiContent + '\'' + 
			",tinformasi_tglinput = '" + tinformasiTglinput + '\'' + 
			",rinfotype_nama = '" + rinfotypeNama + '\'' + 
			",tinformasi_judul = '" + tinformasiJudul + '\'' + 
			",tinformasi_id = '" + tinformasiId + '\'' + 
			"}";
		}
}
