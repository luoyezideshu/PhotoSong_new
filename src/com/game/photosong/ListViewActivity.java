package com.game.photosong;

import java.io.File;
import java.io.FileOutputStream;
import android.os.Bundle;
import android.os.Environment;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.HashMap;
import java.util.ArrayList;
import android.widget.Toast;


public class ListViewActivity extends Activity {
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.item_menu);
		ListView listview = (ListView)this.findViewById(R.id.list_view);
		List<HashMap<String, String>> data = new ArrayList<HashMap<String, String>>();
		
		String dirPath;
		if (Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED)) {
			dirPath = Environment.getExternalStorageDirectory().getPath();
		}
		else 
			dirPath = Environment.getRootDirectory().getPath();
		
		File f = new File(dirPath + "/PhotoSong/history"); //strPath为路径
		File f2 = new File(dirPath + "/PhotoSong");
		if (!f2.exists()) {
			HashMap<String, String> info = new HashMap<String, String>();
			info.put("name", "No History");
			info.put("time", "Null");
			data.add(info);
		}
		else {
			String[] file_list = f.list(); //String[] 
			SimpleDateFormat dateformat1=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss E");
			for (int i = 0; i < file_list.length; i++) {
				HashMap<String, String> info = new HashMap<String, String>();
				info.put("name", file_list[i]);
				info.put("time", dateformat1.format(((new File(dirPath + "/" + file_list[i])).lastModified())));
				data.add(info);
			}
		}
		
		SimpleAdapter adapter = new SimpleAdapter(ListViewActivity. this, data, R.layout.item_menu, new String[] {"name", "time"}, new int[] {R.id.name, R.id.time});
		
		listview.setOnItemLongClickListener(new OnItemLongClickListener() {
			public boolean onItemLongClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				Toast.makeText(ListViewActivity.this, "LongClick", 200);
				return false;
			}
		});
		
		listview.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				ListView lv = (ListView)arg0;
				@SuppressWarnings("unchecked")
				HashMap<String, String> tem = (HashMap<String, String>)lv.getItemAtPosition(arg2);
				String time = tem.get("time");
				String name = tem.get("name");
				Toast.makeText(ListViewActivity.this, arg2+name+time, 200).show();
			}
		});
		listview.setAdapter(adapter);
	} 
} 