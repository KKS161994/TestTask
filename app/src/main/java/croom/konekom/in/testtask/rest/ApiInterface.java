package croom.konekom.in.testtask.rest;

import java.util.List;

import croom.konekom.in.testtask.model.Photo;
import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiInterface {
    @GET("/photos")
    Call<List<Photo>> getAllPhotos();
}
