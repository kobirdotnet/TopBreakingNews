package bdtak.example.com.topbreakingnews;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.support.v7.graphics.Palette;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

/**
 * Created by User on 2/7/2018.
 */

public class ItemAdapter extends ArrayAdapter<ItemModel> {
    private List<ItemModel> itemModelList;
    private Context iCtx;
    private byte[] url;
    private byte[] internetUrl;

    public ItemAdapter(List<ItemModel> itemModelList, Context iCtx) {
        super(iCtx, R.layout.list_items,itemModelList);
        this.itemModelList = itemModelList;
        this.iCtx = iCtx;
    }

    @Override
    public View getView(int position, final View convertView, ViewGroup parent) {

        LayoutInflater inflater = LayoutInflater.from(iCtx);

        View listViewItem = inflater.inflate(R.layout.list_items,null,true);

        TextView textView = listViewItem.findViewById(R.id.NewsTitle);
        TextView textView1 = listViewItem.findViewById(R.id.NewsDescription);
        TextView textView2 = listViewItem.findViewById(R.id.NewsLink);
        TextView textView3 = listViewItem.findViewById(R.id.NewsSource);

        TextView textView4 = listViewItem.findViewById(R.id.ImageUrl);

        TextView textView5 = listViewItem.findViewById(R.id.TimeUpdate);

        final ImageView imageView = listViewItem.findViewById(R.id.NewsImageView);

        final ItemModel itemModel = itemModelList.get(position);

        textView.setText(itemModel.getTitle());
        Log.i("IMG LINK! :",itemModel.getImage());
        textView1.setText(itemModel.getDescription());
        textView2.setText(itemModel.getLink());
        textView3.setText(itemModel.getSource());

        textView4.setText(itemModel.getImage());



        try {
            SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");


            String dt = itemModel.getTimeUpdate().toString();  // Start date
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            Calendar c = Calendar.getInstance();
            c.setTime(sdf.parse(dt));
            c.add(Calendar.HOUR, 12);  // number of days to add
            dt = sdf.format(c.getTime());  // dt is now the new date

            Date past = format.parse(dt);


            Date now = new Date();

            System.out.println(TimeUnit.MILLISECONDS.toMillis(now.getTime() - past.getTime()) + " milliseconds ago");
            System.out.println(TimeUnit.MILLISECONDS.toMinutes(now.getTime() - past.getTime()) + " minutes ago");
            System.out.println(TimeUnit.MILLISECONDS.toHours(now.getTime() - past.getTime()) + " hours ago");
            System.out.println(TimeUnit.MILLISECONDS.toDays(now.getTime() - past.getTime()) + " days ago");

String status="";

if(TimeUnit.MILLISECONDS.toMinutes(now.getTime() - past.getTime())<60){


 status=TimeUnit.MILLISECONDS.toMinutes(now.getTime() - past.getTime())+" min ago";
}else if(TimeUnit.MILLISECONDS.toHours(now.getTime() - past.getTime())<24){

    status=TimeUnit.MILLISECONDS.toHours(now.getTime() - past.getTime())+" hour ago";

}else if(TimeUnit.MILLISECONDS.toDays(now.getTime() - past.getTime())<30){
    status= TimeUnit.MILLISECONDS.toDays(now.getTime() - past.getTime())  +" day ago";


}

            textView5.setText(status);


        }
        catch (Exception j){
            j.printStackTrace();
        }



        //textView5.setText(new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(Locale.getDefault().toString()));

        Glide.with(iCtx)
                .load(itemModel.getImage())
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
                        imageView.setImageBitmap(resource);

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
                .into(imageView);



        return listViewItem;
    }

    public void reset() {


    }
}

