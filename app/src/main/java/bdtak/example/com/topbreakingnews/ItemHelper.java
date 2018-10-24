package bdtak.example.com.topbreakingnews;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.os.Parcel;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by User on 2/19/2018.
 */

public class ItemHelper extends SQLiteOpenHelper{

    private static final String DATABASE_NAME = "item.db";

    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_NAME = "tbitem";

    private static final String ITEM_COLUMN_TITLE = "title";
    private static final String ITEM_COLUMN_IMAGE = "image";
    private static final String ITEM_COLUMN_DESCRIPTION = "description";
    private static final String ITEM_COLUMN_LINK = "link";
    private static final String ITEM_COLUMN_SOURCE = "source";

    private static final String CURRENT_TIMESTAM = "timeStamp";




    private String getDateTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "dd/MM/yyyy HH:mm:ss", Locale.getDefault());
        Date date = new Date();
        return dateFormat.format(date);
    }

    private static final String TAG = "ItemAdapter";
    private static Bitmap bitmap;

    ItemModel openHelper;

    private SQLiteDatabase database;

    public ItemHelper(Context context){

        super(context,DATABASE_NAME,null,DATABASE_VERSION);

        openHelper = new ItemModel(context);
        database = openHelper.getWritableDatabase();

    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String CREATE_ITEM_TABLE = "CREATE TABLE " + TABLE_NAME + "("
                + ITEM_COLUMN_TITLE + " TEXT," + ITEM_COLUMN_IMAGE + " TEXT," + ITEM_COLUMN_DESCRIPTION + " TEXT," + ITEM_COLUMN_LINK + " TEXT," + ITEM_COLUMN_SOURCE + " TEXT," + CURRENT_TIMESTAM + " TEXT" + ")";
        db.execSQL(CREATE_ITEM_TABLE);

        /*String CREATE_ITEM_TABLE = "CREATE TABLE " + TABLE_NAME + "("
                + ITEM_COLUMN_TITLE + " TEXT," + ITEM_COLUMN_IMAGE + " TEXT," + ITEM_COLUMN_DESCRIPTION + " TEXT," + ITEM_COLUMN_LINK + " TEXT," + ITEM_COLUMN_SOURCE + " TEXT" + ")";
        db.execSQL(CREATE_ITEM_TABLE);*/

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public List<ItemModel> getAllItems(){
        List<ItemModel> itemModelList = new ArrayList<ItemModel>();

        String selectQuery = "SELECT * FROM " + TABLE_NAME;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()){

            do {

                ItemModel itemModel = new ItemModel(Parcel.obtain());

                itemModel.setTitle(cursor.getString(0));
                itemModel.setImage(cursor.getString(1));
                itemModel.setDescription(cursor.getString(2));
                itemModel.setLink(cursor.getString(3));
                itemModel.setSource(cursor.getString(4));

                itemModel.setTimeUpdate(cursor.getString(5));


                itemModelList.add(itemModel);

                Log.e("KABIR!",cursor.getString(0)+" : "+cursor.getString(1)+" : "+cursor.getString(2)+" : 4TH: "+cursor.getString(3)+" : "+cursor.getString(4)+" : "+cursor.getString(5));

               // Log.e("KABIR!",cursor.getString(0)+" : "+cursor.getString(1)+" : "+cursor.getString(2)+" : 4TH: "+cursor.getString(3)+" : "+cursor.getString(4));


            }while (cursor.moveToNext());
        }

        return itemModelList;

    }

    public void saveItemModelRecord(String title, String image, String description, String link, String source){

        SQLiteDatabase db    = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(ITEM_COLUMN_TITLE, title);
        contentValues.put(ITEM_COLUMN_IMAGE, image);
        contentValues.put(ITEM_COLUMN_DESCRIPTION, description);
        contentValues.put(ITEM_COLUMN_LINK, link);
        contentValues.put(ITEM_COLUMN_SOURCE, source);

        //contentValues.put("CURRENT_TIMESTAM", System.currentTimeMillis());
        contentValues.put(CURRENT_TIMESTAM, getDateTime());

       /* contentValues.put("CURRENT_TIMESTAM",
                new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));*/

        db.insert(TABLE_NAME, null, contentValues);

        //db.insert(TABLE_NAME, null, contentValues (CURRENT_TIMESTAM) VALUES(DateTime('now'));

        Log.i("KABIR","Save Success");

    }

    public void delete_all_previous_data() {

        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from "+ TABLE_NAME);
        db.close();

    }
}

