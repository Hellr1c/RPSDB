package alvaro.mt.midterm;

import android.app.ListActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class RecordActivity extends ListActivity {

    ListView lv;

    //HistoryAdapter adapter;
    Database db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_history);

        Database db = new Database(this);
        ArrayList<String> items = db.getFullHistory();

        if (items.size() > 0) {
            setListAdapter(new ArrayAdapter<String>(RecordActivity.this, android.R.layout.simple_list_item_1, items));
        } else {
            Toast.makeText(RecordActivity.this, "No records.", Toast.LENGTH_LONG).show();
        }
    }

}
