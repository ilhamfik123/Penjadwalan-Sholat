package id.christyzer.penjadwalansholat.api;
import id.christyzer.penjadwalansholat.model.ModelJadwal;
import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiService {
    @GET("malang.json")
    Call<ModelJadwal> getJadwal();
}
