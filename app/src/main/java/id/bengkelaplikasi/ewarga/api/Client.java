package id.bengkelaplikasi.ewarga.api;

import org.json.JSONException;
import org.json.JSONObject;

import id.bengkelaplikasi.ewarga.helper.Constanta;
import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Kukuh182 on 08-Sep-17
 * Bengkel Aplikasi
 * kukuhpr21@gmail.com
 */

public class Client {

    private static Retrofit retrofit;
    public interface CallBackRequest{
        void successCode(String message,String rd);
        void unsuccessCode(String rc, String rm);
        void unsuccessResponse(String message);
        void parsingError(String message);
        void otherError(String message);
        void failure(String message);
    }

    public static Service initialize(String url){
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();
        retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();

        return  retrofit.create(Service.class);
    }

    public static void request(Call<ResponseBody> result, final CallBackRequest callBack){

        result.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    if(response.isSuccessful()){
                        JSONObject obj1 = new JSONObject(response.body().string());
                        String rc       = obj1.getString("response_code");
                        String rm       = obj1.getString("response_message");

                        if(rc.equals("00")){
                            String rd = obj1.getString("response_data");
                            callBack.successCode(rm, rd);
                        }else {
                            callBack.unsuccessCode(rc, rm);
                        }

                    }else {
                        callBack.unsuccessResponse(Constanta.Message.UNSUCCESS_RESPONSE);
                    }
                } catch (JSONException e) {
                    callBack.parsingError(Constanta.Message.ERROR_PARSING);
                }catch (Exception e){
                    callBack.otherError(Constanta.Message.ERROR_RESPONSE);
                }
            }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                callBack.failure(Constanta.Message.FAILURE);
            }
        });
    }
}
