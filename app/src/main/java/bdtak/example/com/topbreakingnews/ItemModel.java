package bdtak.example.com.topbreakingnews;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by User on 2/7/2018.
 */

public class ItemModel implements Parcelable{

    private String mTitle;
    private String mImage;
    private String mDescription;
    private String mLink;
    private String mSource;
    private String mImageUrl;

    private String mTimeUpdate;

    public ItemModel(String title, String image, String description, String link, String source, String imageurl,String timeupdate) {

        mTitle = title;
        mImage = image;
        mDescription = description;
        mLink = link;
        mSource = source;
        mImageUrl = imageurl;

        mTimeUpdate = timeupdate;

    }

    public ItemModel(Context context) {

    }

    protected ItemModel(Parcel in) {

        mTitle = in.readString();
        mImage = in.readString();
        mDescription = in.readString();
        mLink = in.readString();
        mSource = in.readString();
        mImageUrl = in.readString();

        mTimeUpdate = in.readString();

    }

    public static final Creator<ItemModel> CREATOR = new Creator<ItemModel>() {
        @Override
        public ItemModel createFromParcel(Parcel in) {
            return new ItemModel(in);
        }

        @Override
        public ItemModel[] newArray(int size) {
            return new ItemModel[size];
        }
    };

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getImage() {
        return mImage;
    }

    public void setImage(String image) {
        mImage = image;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        mDescription = description;
    }

    public String getLink() {
        return mLink;
    }

    public void setLink(String link) {
        mLink = link;
    }

    public String getSource() {
        return mSource;
    }

    public void setSource(String source) {
        mSource = source;
    }

//getImageUrl
    public String getImageUrl() {
        return mImageUrl;
    }

    public void setImageUrl(String imageurl) {
        mImageUrl = imageurl;
    }

    //getTimeUpdate
    public String getTimeUpdate() {
        return mTimeUpdate;
    }

    public void setTimeUpdate(String timeupdate) {
        mTimeUpdate = timeupdate;
    }

    public SQLiteDatabase getWritableDatabase() {
        return null;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {

        parcel.writeString(mTitle);
        parcel.writeString(mImage);
        parcel.writeString(mDescription);
        parcel.writeString(mLink);
        parcel.writeString(mSource);
        parcel.writeString(mImageUrl);

        parcel.writeString(mTimeUpdate);

    }
}

