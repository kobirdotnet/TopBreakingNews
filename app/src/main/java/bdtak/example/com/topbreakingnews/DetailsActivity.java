package bdtak.example.com.topbreakingnews;

import android.app.ActionBar;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.graphics.Palette;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

public class DetailsActivity extends AppCompatActivity {

    TextView NTitle ,NDescription;
    Button NLink;
    ImageView NImageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        /*ActionBar actionBar = getActionBar();
        ColorDrawable colorDrawable = new ColorDrawable(Color.parseColor("#93E9FA"));
        actionBar.setBackgroundDrawable(colorDrawable);
*/

        String NewsTitle = getIntent().getExtras().getString("NEWS_TITLE");
        String NewsDescription = getIntent().getExtras().getString("News_Description");
        String NewsLink = getIntent().getExtras().getString("News_Link");
        String ImageUrl = getIntent().getExtras().getString("ImageUrl");
Log.i("KARIR SUCCESS",ImageUrl);

        NTitle = (TextView) findViewById(R.id.NTitle);
        NTitle.setText(NewsTitle);
        NDescription = (TextView) findViewById(R.id.NDescription);
        NDescription.setText(NewsDescription);
        NLink = (Button) findViewById(R.id.NLink);
        NLink.setText(NewsLink);
        NImageView=(ImageView) findViewById(R.id.NImageView);

        Glide.with(this)
                .load(ImageUrl)
                .asBitmap()
                //.crossFade()
                .error(R.drawable.ic_cloud_off_red)
                .thumbnail(0.5f)
                .listener(new RequestListener<String, Bitmap>() {

                    @Override
                    public boolean onException(Exception e, String model, Target<Bitmap> target, boolean isFirstResource) {
                        Log.e("IMG LINK: ",String.valueOf(e));

                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Bitmap resource, String model, Target<Bitmap> target, boolean isFromMemoryCache, boolean isFirstResource) {

                        onPalette(Palette.from(resource).generate());
                        NImageView.setImageBitmap(resource);

                        return false;
                    }

                    public void onPalette(Palette palette) {
                       /* if (null != palette) {
                            ViewGroup parent = (ViewGroup) imageView.getParent().getParent();
                            parent.setBackgroundColor(palette.getDarkVibrantColor(Color.GRAY));
                        }*/
                    }
                })
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(NImageView);


        NLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // Toast.makeText(getApplicationContext(),NLink.getText().toString() , Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(DetailsActivity.this,WebActivity.class);
                intent.putExtra("News_Link", NLink.getText().toString());
                startActivity(intent);
            }
        });

    }

}
