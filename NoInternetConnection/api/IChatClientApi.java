package net.gbksoft.chatclient.api;

import net.gbksoft.chatclient.BuildConfig;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;


public interface IChatClientApi {

    @GET("api/v2/user/login")
    Observable<AuthResponseObject> userAuth(@Field(ConstantsApi.USERNAME) String username,
                                                  @Field(ConstantsApi.PASSWORD) String password;
}
