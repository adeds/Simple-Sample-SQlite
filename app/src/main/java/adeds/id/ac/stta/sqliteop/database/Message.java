package adeds.id.ac.stta.sqliteop.database;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by multimedia 6 on 4/4/2018.
 */

public class Message {
    public static void message(Context context, String mess){
        Toast.makeText(context, mess, Toast.LENGTH_SHORT).show();
    }
}
