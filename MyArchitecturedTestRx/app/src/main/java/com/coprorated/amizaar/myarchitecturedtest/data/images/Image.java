package com.coprorated.amizaar.myarchitecturedtest.data.images;

import android.arch.persistence.room.Entity;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by amizaar on 03.07.2017.
 */

@Entity(primaryKeys = "id")
public class Image implements Parcelable {
    private String id;
    private String pageURL;
    private String type;
    private String tags;
    private String previewURL;
    private String previewWidth;
    private String previewHeight;
    private String webformatURL;
    private String webformatWidth;
    private String webformatHeight;
    private String imageWidth;
    private String imageHeight;
    private String imageSize;
    private int views;
    private int downloads;
    private int favorites;
    private int likes;
    private int comments;
    @SerializedName("user_id")
    private String userId;
    private String user;
    private String userImageURL;


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.pageURL);
        dest.writeString(this.type);
        dest.writeString(this.tags);
        dest.writeString(this.previewURL);
        dest.writeString(this.previewWidth);
        dest.writeString(this.previewHeight);
        dest.writeString(this.webformatURL);
        dest.writeString(this.webformatWidth);
        dest.writeString(this.webformatHeight);
        dest.writeString(this.imageWidth);
        dest.writeString(this.imageHeight);
        dest.writeString(this.imageSize);
        dest.writeInt(this.views);
        dest.writeInt(this.downloads);
        dest.writeInt(this.favorites);
        dest.writeInt(this.likes);
        dest.writeInt(this.comments);
        dest.writeString(this.userId);
        dest.writeString(this.user);
        dest.writeString(this.userImageURL);
    }

    public Image() {
    }

    protected Image(Parcel in) {
        this.id = in.readString();
        this.pageURL = in.readString();
        this.type = in.readString();
        this.tags = in.readString();
        this.previewURL = in.readString();
        this.previewWidth = in.readString();
        this.previewHeight = in.readString();
        this.webformatURL = in.readString();
        this.webformatWidth = in.readString();
        this.webformatHeight = in.readString();
        this.imageWidth = in.readString();
        this.imageHeight = in.readString();
        this.imageSize = in.readString();
        this.views = in.readInt();
        this.downloads = in.readInt();
        this.favorites = in.readInt();
        this.likes = in.readInt();
        this.comments = in.readInt();
        this.userId = in.readString();
        this.user = in.readString();
        this.userImageURL = in.readString();
    }

    public static final Creator<Image> CREATOR = new Creator<Image>() {
        @Override
        public Image createFromParcel(Parcel source) {
            return new Image(source);
        }

        @Override
        public Image[] newArray(int size) {
            return new Image[size];
        }
    };

    public String getId() {
        return id;
    }

    public String getPageURL() {
        return pageURL;
    }

    public String getType() {
        return type;
    }

    public String getTags() {
        return tags;
    }

    public String getPreviewURL() {
        return previewURL;
    }

    public String getPreviewWidth() {
        return previewWidth;
    }

    public String getPreviewHeight() {
        return previewHeight;
    }

    public String getWebformatURL() {
        return webformatURL;
    }

    public String getWebformatWidth() {
        return webformatWidth;
    }

    public String getWebformatHeight() {
        return webformatHeight;
    }

    public String getImageWidth() {
        return imageWidth;
    }

    public String getImageHeight() {
        return imageHeight;
    }

    public String getImageSize() {
        return imageSize;
    }

    public int getViews() {
        return views;
    }

    public int getDownloads() {
        return downloads;
    }

    public int getFavorites() {
        return favorites;
    }

    public int getLikes() {
        return likes;
    }

    public int getComments() {
        return comments;
    }

    public String getUserId() {
        return userId;
    }

    public String getUser() {
        return user;
    }

    public String getUserImageURL() {
        return userImageURL;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setPageURL(String pageURL) {
        this.pageURL = pageURL;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public void setPreviewURL(String previewURL) {
        this.previewURL = previewURL;
    }

    public void setPreviewWidth(String previewWidth) {
        this.previewWidth = previewWidth;
    }

    public void setPreviewHeight(String previewHeight) {
        this.previewHeight = previewHeight;
    }

    public void setWebformatURL(String webformatURL) {
        this.webformatURL = webformatURL;
    }

    public void setWebformatWidth(String webformatWidth) {
        this.webformatWidth = webformatWidth;
    }

    public void setWebformatHeight(String webformatHeight) {
        this.webformatHeight = webformatHeight;
    }

    public void setImageWidth(String imageWidth) {
        this.imageWidth = imageWidth;
    }

    public void setImageHeight(String imageHeight) {
        this.imageHeight = imageHeight;
    }

    public void setImageSize(String imageSize) {
        this.imageSize = imageSize;
    }

    public void setViews(int views) {
        this.views = views;
    }

    public void setDownloads(int downloads) {
        this.downloads = downloads;
    }

    public void setFavorites(int favorites) {
        this.favorites = favorites;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public void setComments(int comments) {
        this.comments = comments;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public void setUserImageURL(String userImageURL) {
        this.userImageURL = userImageURL;
    }

    @Override
    public String toString() {
        return "Image{" +
                "id='" + id + '\'' +
                ", pageURL='" + pageURL + '\'' +
                ", type='" + type + '\'' +
                ", tags='" + tags + '\'' +
                ", previewURL='" + previewURL + '\'' +
                ", previewWidth='" + previewWidth + '\'' +
                ", previewHeight='" + previewHeight + '\'' +
                ", webformatURL='" + webformatURL + '\'' +
                ", webformatWidth='" + webformatWidth + '\'' +
                ", webformatHeight='" + webformatHeight + '\'' +
                ", imageWidth='" + imageWidth + '\'' +
                ", imageHeight='" + imageHeight + '\'' +
                ", imageSize='" + imageSize + '\'' +
                ", views=" + views +
                ", downloads=" + downloads +
                ", favorites=" + favorites +
                ", likes=" + likes +
                ", comments=" + comments +
                ", userId='" + userId + '\'' +
                ", user='" + user + '\'' +
                ", userImageURL='" + userImageURL + '\'' +
                '}';
    }
}
