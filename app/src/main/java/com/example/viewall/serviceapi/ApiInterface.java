package com.example.viewall.serviceapi;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;

import androidx.annotation.NonNull;

import com.example.viewall.models.advert.AdvertResponse;
import com.example.viewall.models.bannerlist.BannerResponse;
import com.example.viewall.models.channel.ChannelResponse;
import com.example.viewall.models.channel1.Channel1Response;
import com.example.viewall.models.contact.ContactResponse;
import com.example.viewall.models.databasemodels.TableOfflineModel;
import com.example.viewall.models.getuser.GetUserResponse;
import com.example.viewall.models.homecategorylist.HomeCategoryResponse;
import com.example.viewall.models.index.IndexResponse;
import com.example.viewall.models.index1.Index1Response;
import com.example.viewall.models.offlineupdate.OfflineDataResponse;
import com.example.viewall.models.popularviedos.PopularVideoResponse;
import com.example.viewall.models.register.RegisterResponse;
import com.example.viewall.models.seenvideolist.SeenVideoResponse;
import com.example.viewall.models.singlecategorylist.SingleCategoryResponse;
import com.example.viewall.models.singlevideo.SingleVideoResponse;
import com.example.viewall.models.track.TrackResponse;
import com.example.viewall.models.videosmodel.VideoResponse;
import com.example.viewall.models.watch1.Watch1Response;
import com.example.viewall.models.watch2.Watch2Response;
import com.example.viewall.models.watch3.Watch3Response;
import com.example.viewall.models.watch4.Watch4Response;
import com.example.viewall.models.watch5.Watch5Response;
import com.example.viewall.models.watchadvert.WatchAdvertResponse;
import com.example.viewall.models.watchapi.WatchResponse;
import com.example.viewall.models.watchmarker.WatchMarkerResponse;
import com.example.viewall.models.watchvideo.WatchVideoResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface ApiInterface {

    //Getting the base url from shared preference
    SharedPreferences sharedPreferences = GlobalApplication.getAppContext().getSharedPreferences("VIEWALL", MODE_PRIVATE);
    String myU = sharedPreferences.getString("baseurl2", "");

    String BASE_URL = sharedPreferences.getString("baseurl2", "");

    //http://dev.view4all.tv/API/api.php?action=registero_first_name:Hemrajo_last_name:Sharmao_phone:7890657457pindex:4o_custom_1:ghgho_custom_2:fgfhffemail:mymail@gmail.com
    @NonNull
    @FormUrlEncoded
    @POST("api.php?action=register")
    Call<RegisterResponse> register(@Field("o_first_name") String o_first_name,
                                    @Field("o_last_name") String o_last_name,
                                    @Field("o_phone") String o_phone,
                                    @Field("mac_address") String mac_address
            /*@Field("email") String email*/);

    //http://dev.view4all.tv/API/api.php?action=getallcategory
    @NonNull
    @GET("api.php?action=getallcategory")
    Call<HomeCategoryResponse> homeCategory(@Query("referror") String referror);

    //http://dev.view4all.tv/API/api.php?action=getsinglecategory&id=0e3b2725-de08-44c0-a47a-eccb94bd77d9&=
    @NonNull
    @GET("api.php?action=getsinglecategory")
    Call<SingleCategoryResponse> singleCategory(@Query("id") String id,
                                                @Query("referror") String referror);

    //http://dev.view4all.tv/API/api.php?action=getalladvertimage
    @NonNull
    @GET("api.php?action=getalladvertimage")
    Call<BannerResponse> bannerList(@Query("referror") String referror);

    //http://dev.view4all.tv/API/api.php?action=getallvideos
    @NonNull
    @GET("api.php?action=getallvideos")
    Call<PopularVideoResponse> popularVideos(@Query("referror") String referror);

    //http://dev.view4all.tv/API/api.php?action=getsinglevideo&id=154fe4de-f06c-4bbf-86fd-513fca0091ab&=
    @NonNull
    @GET("api.php?action=getsinglevideo")
    Call<SingleVideoResponse> singleVideo(@Query("id") String id,
                                          @Query("referror") String referror);

    //http://dev.view4all.tv/API/api.php?action=getuser&&id=bfc6c4339390eaa33f032f8fc8e96c35
    @NonNull
    @GET("api.php?action=getuser")
    Call<GetUserResponse> getUserData(@Query("id") String id);

    //http://dev.view4all.tv/API/api.php?action=insertcontact
    @NonNull
    @POST("api.php?action=insertcontact")
    Call<ContactResponse> sendContact(@Query("user_id") String user_id,
                                      @Query("name") String name,
                                      @Query("phone_number") String phone_number,
                                      @Query("subject") String subject,
                                      @Query("message") String message);

    //http://dev.view4all.tv/API/api.php?action=track
    @NonNull
    @POST("api.php?action=track")
    Call<TrackResponse> sendTrack(@Query("user_id") String user_id,
                                  @Query("type") String type);

    //http://dev.view4all.tv/API/api.php?action=whatch_videos_saved
    @NonNull
    @POST("api.php?action=whatch_videos_saved")
    Call<WatchVideoResponse> saveWatchVideo(@Query("user_id") String user_id,
                                            @Query("channel_id") String channel_id,
                                            @Query("video_id") String video_id);

    //http://dev.view4all.tv/API/api.php?action=get_watch_video_list&user_id=30c501461fee0e175a3c602242d34b03&channel_id=4ccc9046-a697-4459-a424-6250e4a15242&current_video_id=0300377f-39a2-4bfa-bf4e-3620ce37d8aa
    @NonNull
    @POST("api.php?action=get_watch_video_list")
    Call<SeenVideoResponse> showWatchVideo(@Query("user_id") String user_id,
                                           @Query("channel_id") String channel_id,
                                           @Query("current_video_id") String current_video_id,
                                           @Query("referror") String referror);

    //http://dev.view4all.tv/API/api.php?action=index
    @NonNull
    @POST("api.php?action=index")
    Call<IndexResponse> index(@Query("contact_id") String contact_id,
                              @Query("referror") String referror);

    //http://dev.view4all.tv/API/api.php?action=advert
    @NonNull
    @POST("api.php?action=advert")
    Call<AdvertResponse> advert(@Query("video") String video);

    //http://dev.view4all.tv/API/api.php?action=video
    @NonNull
    @POST("api.php?action=video")
    Call<VideoResponse> videoApi(@Query("video_id") String video_id,
                                 @Query("contact_id") String contact_id,
                                 @Query("ip") String ip);

    //http://dev.view4all.tv/API/api.php?action=watch
    @NonNull
    @POST("api.php?action=watch")
    Call<WatchResponse> watchApi(@Query("channel_name") String channel_name,
                                 @Query("contact_id") String contact_id,
                                 @Query("channel_id") String channel_id,
                                 @Query("referror") String referror);

    //http://dev.view4all.tv/API/api.php?action=index1
    @NonNull
    @POST("api.php?action=index1")
    Call<Index1Response> index1(@Query("contact_id") String contact_id,
                                @Query("banner") String banner,
                                @Query("referror") String referror);

    //http://dev.view4all.tv/API/api.php?action=channel_1
    @NonNull
    @POST("api.php?action=channel_1")
    Call<Channel1Response> channel1(@Query("banner") String banner,
                                    @Query("contact_id") String contact_id,
                                    @Query("referror") String referror);

    //http://dev.view4all.tv/API/api.php?action=watch1
    @NonNull
    @POST("api.php?action=watch1")
    Call<Watch1Response> watch1(@Query("video_id") String video_id,
                                @Query("contact_id") String contact_id,
                                @Query("channel_id") String channel_id,
                                @Query("referror") String referror);

    //http://dev.view4all.tv/API/api.php?action=watch2
    @NonNull
    @POST("api.php?action=watch2")
    Call<Watch2Response> watch2(@Query("ad_id") String ad_id,
                                @Query("contact_id") String contact_id,
                                @Query("channel_id") String channel_id,
                                @Query("ip") String ip,
                                @Query("referror") String referror);

    //http://dev.view4all.tv/API/api.php?action=watch3
    @NonNull
    @POST("api.php?action=watch3")
    Call<Watch3Response> watch3(@Query("ad_id") String ad_id,
                                @Query("ip") String ip,
                                @Query("contact_id") String contact_id,
                                @Query("video_id") String video_id,
                                @Query("referror") String referror);

    //http://dev.view4all.tv/API/api.php?action=watch4
    @NonNull
    @POST("api.php?action=watch4")
    Call<Watch4Response> watch4(@Query("channel_name") String channel_name,
                                @Query("ip") String ip,
                                @Query("contact_id") String contact_id,
                                @Query("video_id") String video_id,
                                @Query("referror") String referror);

    //http://dev.view4all.tv/API/api.php?action=watch5
    @NonNull
    @POST("api.php?action=watch5")
    Call<Watch5Response> watch5(@Query("ip") String ip,
                                @Query("contact_id") String contact_id,
                                @Query("video_id") String video_id,
                                @Query("referror") String referror);

    //http://dev.view4all.tv/API/api.php?action=marker_watch
    @NonNull
    @POST("api.php?action=marker_watch")
    Call<WatchMarkerResponse> watchMarker(@Query("ip") String ip,
                                          @Query("contact_id") String contact_id,
                                          @Query("video_id") String video_id,
                                          @Query("referror") String referror);

    //http://dev.view4all.tv/API/api.php?action=watch_advert
    @NonNull
    @POST("api.php?action=watch_advert")
    Call<WatchAdvertResponse> watchAdvert(@Query("contact_id") String contact_id,
                                          @Query("banner") String banner,
                                          @Query("channel_id") String channel_id,
                                          @Query("referror") String referror);

    //http://dev.view4all.tv/API/api.php?action=channel
    @NonNull
    @POST("api.php?action=channel")
    Call<ChannelResponse> channelRes(@Query("channel_type") String channel_type,
                                     @Query("contact_id") String contact_id,
                                     @Query("referror") String referror);

    //http://dev.view4all.tv/API/api.php?action=offline_watch_data
    @Multipart
    @POST("api.php?action=offline_watch_data&time=43")
    Call<OfflineDataResponse> offlineWatch(@Part("offline_data[]") List<TableOfflineModel> contact_list
                                           /*@Query("offline_data") String offline_data*/);

}
