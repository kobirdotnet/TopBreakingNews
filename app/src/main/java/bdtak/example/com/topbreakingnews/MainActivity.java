package bdtak.example.com.topbreakingnews;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    String JSON_URL = "http://www.a2znewspapers.com/resource/data/json/amardesh.json";
    String JSON_URL1 = "http://www.a2znewspapers.com/resource/data/json/amarhealth.json";
    String JSON_URL2 = "http://www.a2znewspapers.com/resource/data/json/anandabazar.json";
    String JSON_URL3 = "http://www.a2znewspapers.com/resource/data/json/banglanews24.json";
    String JSON_URL4 = "http://www.a2znewspapers.com/resource/data/json/bbcbangla.json";
    String JSON_URL5 = "http://www.a2znewspapers.com/resource/data/json/bdnews24.json";
    String JSON_URL6 = "http://www.a2znewspapers.com/resource/data/json/dailyinqilab.json";
    String JSON_URL7 = "http://www.a2znewspapers.com/resource/data/json/eurobdnews.json";
    String JSON_URL8 = "http://www.a2znewspapers.com/resource/data/json/ittefaq.json";
    String JSON_URL9 = "http://www.a2znewspapers.com/resource/data/json/jugantor.json";
    String JSON_URL10 = "http://www.a2znewspapers.com/resource/data/json/kalerkantho.json";
    String JSON_URL11 = "http://www.a2znewspapers.com/resource/data/json/latest_news.json";
    String JSON_URL12 = "http://www.a2znewspapers.com/resource/data/json/most_read.json";
    String JSON_URL13 = "http://www.a2znewspapers.com/resource/data/json/nayadiganta.json";
    String JSON_URL14 = "http://www.a2znewspapers.com/resource/data/json/prothom-alo.json";
    String JSON_URL15 = "http://www.a2znewspapers.com/resource/data/json/purbobangla.json";
    String JSON_URL16 = "http://www.a2znewspapers.com/resource/data/json/samakal.json";
    String JSON_URL17 = "http://www.a2znewspapers.com/resource/data/json/world_news.json";

    JSONArray items = null;
    private ItemHelper databaseHelper;

    private SQLiteDatabase mydatabase;
    private Button last_Update;
    private Button most_readable;
    private ProgressDialog mDialog;
    private ProgressBar progressBar;
    private ImageView imageView;
    private ItemAdapter itemAdapter;
    private ListView listView;
    private List<ItemModel> itemModelList;
    private int adapterPosition;
    private Bitmap bmp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        databaseHelper = new ItemHelper(this);

        listView = findViewById(R.id.ListView);

        most_readable = findViewById(R.id.most_readable);
        last_Update = findViewById(R.id.last_Update);

        imageView = (ImageView)findViewById(R.id.NewsImageView);

        progressBar = findViewById(R.id.progressBar);

        listView.setOnItemClickListener(this);

        most_readable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getApplicationContext(),"Button is selected", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(getApplicationContext(), Main2Activity.class);
                startActivity(intent);
            }
        });

        last_Update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadList();
            }
        });


        itemModelList = new ArrayList<>();

        loadList();

    }

    public static boolean isConnected() {

        ConnectivityManager
                cm = (ConnectivityManager) MyApplication.getmInstance().getApplicationContext()
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null
                && activeNetwork.isConnectedOrConnecting();

    }

    private void loadList() {

       /* mDialog = new ProgressDialog(MainActivity.this);
        mDialog.setMessage("Loading Item Data...");
        mDialog.setCancelable(true);
        mDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mDialog.setIndeterminate(true);

        itemAdapter.reset();

        mDialog.show();*/

        if(isConnected()){

            add_item_tolist(JSON_URL,true);
            add_item_tolist(JSON_URL1,false);
            add_item_tolist(JSON_URL2,false);
            add_item_tolist(JSON_URL3,false);
            add_item_tolist(JSON_URL4,false);
            add_item_tolist(JSON_URL5,false);
            add_item_tolist(JSON_URL6,false);
            add_item_tolist(JSON_URL7,false);
            add_item_tolist(JSON_URL8,false);
            add_item_tolist(JSON_URL9,false);
            add_item_tolist(JSON_URL10,false);
            add_item_tolist(JSON_URL11,false);
            add_item_tolist(JSON_URL12,false);
            add_item_tolist(JSON_URL13,false);
            add_item_tolist(JSON_URL14,false);
            add_item_tolist(JSON_URL15,false);
            add_item_tolist(JSON_URL16,false);
            add_item_tolist(JSON_URL17,false);

            view_in_listview();

        }else{

            List<ItemModel> itemModelList1 = null;
            itemModelList1=   databaseHelper.getAllItems();
            progressBar.setVisibility(View.GONE);
            listView.setVisibility(View.VISIBLE);
            ItemAdapter itemAdapter1 = new ItemAdapter(itemModelList1, getApplicationContext());
            listView.setAdapter(itemAdapter1);
            itemAdapter1.notifyDataSetChanged();

        }
    }

    private  void  add_item_tolist(String URL,final boolean action){

        JsonObjectRequest jsonObjectRequest;
        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, URL, null,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {

                        progressBar.setVisibility(View.GONE);
                        listView.setVisibility(View.VISIBLE);

                        if(action) {

                            databaseHelper.delete_all_previous_data();

                        }

                        Log.d("json", response.toString());

                        try {

                            JSONArray jsonArray = response.getJSONArray("item");

                            for (int i = 0; i < jsonArray.length(); i++)

                            {

                                JSONObject jsonObject = jsonArray.getJSONObject(i);

                                ItemModel itemModel = new ItemModel(getApplicationContext());

                                itemModel.setTitle(jsonObject.getString("title"));
                                itemModel.setImage(jsonObject.getString("image"));
                                itemModel.setDescription(jsonObject.getString("description"));
                                itemModel.setLink(jsonObject.getString("link"));
                                itemModel.setSource(jsonObject.getString("source"));

                                //Log.i("KABIR_SUCCESS",jsonObject.getString("image")+" : Link : "+jsonObject.getString("link"));

                                itemModelList.add(itemModel);

                                databaseHelper.saveItemModelRecord(jsonObject.getString("title"),jsonObject.getString("image"),jsonObject.getString("description"),jsonObject.getString("link"),jsonObject.getString("source"));

                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressBar.setVisibility(View.GONE);

                        Toast.makeText(getApplicationContext(),error.getMessage(), Toast.LENGTH_LONG).show();

                    }
                });

        MyApplication.getmInstance().addToRequestQueue(jsonObjectRequest);

    }

    private void view_in_listview(){

        ItemAdapter itemAdapter = new ItemAdapter(itemModelList, getApplicationContext());

        listView.setAdapter(itemAdapter);
        itemAdapter.notifyDataSetChanged();

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


        position = getAdapterPosition();

        if (position != ListView.INVALID_POSITION) {

            TextView NewsTitle = (TextView) view.findViewById(R.id.NewsTitle);
            TextView NewsDescription = (TextView) view.findViewById(R.id.NewsDescription);
            TextView NewsLink = (TextView) view.findViewById(R.id.NewsLink);

            ImageView imageView = (ImageView) view.findViewById(R.id.NewsImageView);

            TextView ImageUrl = (TextView) view.findViewById(R.id.ImageUrl);

         Intent intent = new Intent(getApplicationContext(), DetailsActivity.class);

            intent.putExtra("NEWS_TITLE", NewsTitle.getText().toString());
            intent.putExtra("News_Description", NewsDescription.getText().toString());
            intent.putExtra("ImageUrl", ImageUrl.getText().toString());
            intent.putExtra("News_Link", NewsLink.getText().toString());

           startActivity(intent);
        }

    }

    public int getAdapterPosition() {
        return adapterPosition;
    }
}
