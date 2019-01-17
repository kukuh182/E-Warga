package id.bengkelaplikasi.ewarga.api;

import java.util.HashMap;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

/**
 * Created by Kukuh182 on 08-Sep-17
 * Bengkel Aplikasi
 * kukuhpr21@gmail.com
 */

public interface Service {

    @FormUrlEncoded
    @POST("loginApp")
    Call<ResponseBody> Login(@FieldMap HashMap<String, String> params);

    @FormUrlEncoded
    @PUT("updateLongLat/{rktp_nik}")
    Call<ResponseBody> updateLokasi(@Path("rktp_nik") String rktp_nik, @FieldMap HashMap<String, Double> params);

    @FormUrlEncoded
    @POST("getListWarga")
    Call<ResponseBody> getListWarga(@FieldMap HashMap<String, String> params);

    @FormUrlEncoded
    @POST("ubahPassword")
    Call<ResponseBody> ubahPassword(@FieldMap HashMap<String, String> params);

    @FormUrlEncoded
    @POST("getAgenda")
    Call<ResponseBody> getAgenda(@FieldMap HashMap<String, String> params);

    @FormUrlEncoded
    @POST("getPengumuman")
    Call<ResponseBody> getPengumuman(@FieldMap HashMap<String, String> params);

    @FormUrlEncoded
    @POST("getBerita")
    Call<ResponseBody> getBerita(@FieldMap HashMap<String, String> params);

    @FormUrlEncoded
    @POST("getProvinsi")
    Call<ResponseBody> getProvinsi(@FieldMap HashMap<String, String> params);

    @FormUrlEncoded
    @POST("getKota")
    Call<ResponseBody> getKota(@FieldMap HashMap<String, String> params);

    @FormUrlEncoded
    @POST("getKecamatan")
    Call<ResponseBody> getKecamatan(@FieldMap HashMap<String, String> params);

    @FormUrlEncoded
    @POST("getKelurahan")
    Call<ResponseBody> getKelurahan(@FieldMap HashMap<String, String> params);

    @FormUrlEncoded
    @POST("insertMember")
    Call<ResponseBody> registrasi(@FieldMap HashMap<String, String> params);

    @FormUrlEncoded
    @POST("getListEvent")
    Call<ResponseBody> getListEvent(@FieldMap HashMap<String, String> params);

    @FormUrlEncoded
    @POST("insertPengaduan")
    Call<ResponseBody> sendPengaduan(@FieldMap HashMap<String, String> params);

    @FormUrlEncoded
    @POST("getListFasum")
    Call<ResponseBody> getListFasum(@FieldMap HashMap<String, String> params);

    @FormUrlEncoded
    @POST("insertFasum")
    Call<ResponseBody> addFasilitasUmum(@FieldMap HashMap<String, String> params);

    @FormUrlEncoded
    @POST("insertLayanan")
    Call<ResponseBody> addLayanan(@FieldMap HashMap<String, String> params);

    @FormUrlEncoded
    @POST("getGroupJLayan")
    Call<ResponseBody> getGroupLayanan(@FieldMap HashMap<String, String> params);

    @FormUrlEncoded
    @POST("getJLayan")
    Call<ResponseBody> getJenisLayanan(@FieldMap HashMap<String, String> params);

    @FormUrlEncoded
    @POST("getRKategori")
    Call<ResponseBody> getKategoriFasum(@FieldMap HashMap<String, String> params);
}
