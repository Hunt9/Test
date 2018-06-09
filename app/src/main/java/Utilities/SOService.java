package Utilities;


import com.example.ammar.test.Model.ModelInsert;
import com.example.ammar.test.Model.ModelRead;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;


public interface SOService {


    @GET("insert_product.php")
    Call<ModelInsert> insertProduct(@Query("p_name") String p_name, @Query("p_quantity") String p_quantity);

    @GET("read_product.php")
    Call<ModelRead> readProduct ();


}