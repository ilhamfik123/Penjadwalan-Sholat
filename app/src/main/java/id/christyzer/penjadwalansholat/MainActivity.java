package id.christyzer.penjadwalansholat;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import id.christyzer.penjadwalansholat.api.ApiService;
import id.christyzer.penjadwalansholat.api.ApiUrl;
import id.christyzer.penjadwalansholat.model.ModelJadwal;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    private TextView tvsubuh, tvdhuhur, tvashar, tvmaghrib, tvisya;
    private ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvsubuh =findViewById(R.id.tv_subuh);
        tvdhuhur=findViewById(R.id.tv_dzuhur);
        tvashar=findViewById(R.id.tv_ashar);
        tvmaghrib=findViewById(R.id.tv_maghrib);
        tvisya=findViewById(R.id.tv_isya);

        getJadwal();
    }

    private void getJadwal () {

        progressDialog = new ProgressDialog(MainActivity.this);
        progressDialog.setMessage("Please Wait / Silahkan tunggu ...");
        progressDialog.show();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiUrl.URL_ROOT_HTTP)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiService apiService = retrofit.create(ApiService.class);
        Call<ModelJadwal> call = apiService.getJadwal();

        call.enqueue(new Callback<ModelJadwal>() {
            @Override
            public void onResponse(Call<ModelJadwal> call, Response<ModelJadwal> response) {

                progressDialog.dismiss();

                if (response.isSuccessful()) {
                    tvsubuh.setText(response.body().getItems().get(0).getFajr());
                    tvdhuhur.setText(response.body().getItems().get(0).getDhuhr());
                    tvashar.setText(response.body().getItems().get(0).getAsr());
                    tvmaghrib.setText(response.body().getItems().get(0).getMaghrib());
                    tvisya.setText(response.body().getItems().get(0).getIsha());

                } else {

                }
            }

            @Override
            public void onFailure(Call<ModelJadwal> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(MainActivity.this, "Sorry, please try again... server Down..", Toast.LENGTH_SHORT).show();
            }
        });

    }
}
