package com.game.photosong;

import org.apache.http.util.EncodingUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.util.Log;
import android.os.Bundle;
import android.os.Environment;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.inputmethod.EditorInfo;
import android.widget.ImageButton;
import android.widget.Toast;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ImageView;
import android.widget.AdapterView;
import android.widget.SimpleAdapter;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;

public class MainActivity extends Activity {

	private boolean isSaved;
	private String dirPath;
	private String fileName = "";
	private Dialog onSaveDialog;
	private View drawView;
	private ImageView musicBar, tempoBar;
	private ImageButton menuBtn, colorBtn, undoBtn;
	private EditText edit;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//隐去标题栏（应用程序的名字）  
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        //隐去状态栏部分(电池等图标和一切修饰部分)
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        
        getDirPath();   
		//setContentView(R.layout.activity_main);
		//declare = (Declare) getApplication();
		initView(); 
	}	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	void initView() {
		Declare.setMenuStatus(1);
		setContentView(R.layout.activity_main);
		//LayoutInflater layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		Declare.screen_width = getWindowManager().getDefaultDisplay().getWidth();
		Declare.screen_height = getWindowManager().getDefaultDisplay().getHeight();
		
		LinearLayout drawLayout = (LinearLayout) findViewById(R.id.view_draw);
		//drawLayout.setLayoutParams(new LayoutParams())
		drawView = new DrawLines(this,Declare.screen_width,Declare.screen_height);
		drawView.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.FILL_PARENT,ViewGroup.LayoutParams.FILL_PARENT));  
	    drawLayout.addView(drawView);
	    
	    LinearLayout tempoBarLayout = (LinearLayout) findViewById(R.id.tempo_bar);
	    tempoBar = new ImageView(this);
	    tempoBar.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.FILL_PARENT,ViewGroup.LayoutParams.FILL_PARENT));
	    tempoBarLayout.addView(tempoBar);    
	 //   tempoBar.setImageDrawable(getResources().getDrawable(R.drawable.ic_action_eraser));   
	    
	    LinearLayout musicBarLayout = (LinearLayout) findViewById(R.id.music_bar);
	    musicBar = new ImageView(this);
	    musicBar.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.FILL_PARENT,ViewGroup.LayoutParams.FILL_PARENT));
	    musicBarLayout.addView(musicBar);    
	//     musicBar.setImageDrawable(getResources().getDrawable(R.drawable.ic_action_eraser));   
		
		menuBtn = (ImageButton) findViewById(R.id.button_menu);
	    colorBtn = (ImageButton) findViewById(R.id.button_color);
		undoBtn = (ImageButton) findViewById(R.id.button_undo);
		
	    //设置按钮的监听 		
		menuBtn.setOnClickListener(new OnClickListener() {  
	        
	        public void onClick(View v) {  
	            Log.v("S","Click on Menu");
	            if(Declare.getMenuStatus()==1){
	            	menuBtn.setImageDrawable(getResources().getDrawable(R.drawable.button_status_eraser));
					Declare.setMenuStatus(2);
					Toast.makeText(MainActivity.this, R.string.prompt_status_eraser, Toast.LENGTH_SHORT).show();
				}
				else if (Declare.getMenuStatus()==2) {
					menuBtn.setImageDrawable(getResources().getDrawable(R.drawable.button_status_voice));
					Declare.setMenuStatus(3);
					Toast.makeText(MainActivity.this, R.string.prompt_status_voice, Toast.LENGTH_SHORT).show();
				}
				else {
					menuBtn.setImageDrawable(getResources().getDrawable(R.drawable.button_status_draw));
					Declare.setMenuStatus(1);
					Toast.makeText(MainActivity.this, R.string.prompt_status_draw, Toast.LENGTH_SHORT).show();
				}	
	       }
	            
	    }); 
		
		colorBtn.setOnClickListener(new OnClickListener() {  
	        
	        public void onClick(View v) {  
	            Log.v("S","Click on Color");
	            if(Declare.getColorStatus()==1){
	            	colorBtn.setImageDrawable(getResources().getDrawable(R.drawable.button_color_piano));
					Declare.setColorStatus(2);
				}
	            else if (Declare.getColorStatus()==2){
	            	colorBtn.setImageDrawable(getResources().getDrawable(R.drawable.button_color_string));
					Declare.setColorStatus(3);
				}
				else {
					colorBtn.setImageDrawable(getResources().getDrawable(R.drawable.button_color_status));
					Declare.setColorStatus(1);
				}	
	        }    
	    }); 
	} 
	
	
	public boolean onOptionsItemSelected(MenuItem item) {  
	
		// 在此说明一下，Menu相当于一个容器，而MenuItem相当于容器中容纳的东西 
		switch(item.getItemId()) { 
		case R.id.action_playCurrent: 
			setTitle("播放当前"); 
			break; 
		case R.id.action_playWhole: 
			setTitle("播放整曲"); 
			break; 
		case R.id.action_save: 
			if (fileName == "") {
				edit = (EditText)findViewById(R.id.name_editor);
				edit.setVisibility(View.VISIBLE);
				//提醒用户做保存的事情的一个框
				onSaveDialog = new AlertDialog.Builder(MainActivity.this).setTitle("保存")
						.setMessage("想一个靠谱的名字吧，不要包括“.”＝ ＝、").setView(edit)
					//设置按钮
					.setPositiveButton("保存", new DialogInterface.OnClickListener(){  
						public void onClick(DialogInterface dialog, int which) {
							fileName = edit.getText().toString();
							Log.v("debuga", "fileName: " + fileName);
							
							if (new File(dirPath + "/" + fileName).exists()) {
								AlertDialog temp = new AlertDialog.Builder(MainActivity.this)
									.setTitle("保存失败").setMessage("有重名")
									.setPositiveButton("确定", new DialogInterface.OnClickListener(){
										public void onClick(DialogInterface dialog, int which) {
											//不做任何操作
											edit.setVisibility(View.GONE);
										}
									}).create();
								temp.show();
							}
							else {
								//保存应该做的事
								try {
									onSave();
								} catch (IOException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								
								fileName = "";
								//清空画布要做的事！！
								
								
								
								
							}
						}  
					})
					.setCancelable(true).create();
				onSaveDialog.show();
			}

			else {
				//保存应该做的事
				try {
					onSave();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				fileName = "";
				//清空画布要做的事！！
				
				
			}
			break; 
		case R.id.action_history:
			if (isSaved == false) {
				Dialog isSaveDialog = new AlertDialog.Builder(MainActivity.this).setMessage("当前曲目是否保存？")
					//设置按钮
					.setPositiveButton("保存", new DialogInterface.OnClickListener(){ 
						
						@Override
						public void onClick(DialogInterface dialog, int which) {
							onSaveDialog.show();
							isSaved = true;
						}  
					})
					.setNegativeButton("不保存", new DialogInterface.OnClickListener() {
						
						@Override
						public void onClick(DialogInterface dialog, int which) {
							isSaved = true;
						}
					})
					.setCancelable(true).create();
				isSaveDialog.show();
			}
			
			if (isSaved == true) {
				//调文件系统，找出文件
				showFileList();

				Log.v("debuga", "fileName: " + fileName);
			
				//从文件中恢复
				String res= "";
				try{
					FileInputStream fin = new FileInputStream(fileName);
					int length = fin.available();
					byte [] buffer = new byte[length];
					fin.read(buffer);  
					res = EncodingUtils.getString(buffer, "UTF-8");
					fin.close(); 
				}
				catch(Exception e){
			       e.printStackTrace();
		        }
					
			}
			break; 
		case R.id.action_import: 
			
			break; 
		case R.id.action_export:

			break;
		default:
			Log.v("debuga", "menu_id: " + item.getItemId());
			break;
		}  
		return true;  
	}  

	@SuppressLint("SimpleDateFormat")
	private void showFileList() {
		ListView listview = (ListView)this.findViewById(R.id.list_view);
		List<HashMap<String, String>> data = new ArrayList<HashMap<String, String>>();
		listview.setBackgroundColor(Color.GRAY);
		File f = new File(this.dirPath); //strPath为路径
		//File f = new File(Environment.getRootDirectory().getPath()); //strPath为路径

		//Log.v("debuga", f.getPath());
		if (!(f.exists())) {
			Log.v("debuga", "not exists");
			Dialog dialog = new AlertDialog.Builder(MainActivity.this)  
				//设置对话框要显示的消息  
				.setMessage("无历史曲目")  
				//设置按钮
				.setPositiveButton("确定", new DialogInterface.OnClickListener(){  
					public void onClick(DialogInterface dialog, int which) {  
						//好像什么都不用干
					}  
				}).create();
			dialog.show();
		}
		else {
			String[] file_list = f.list(); //String[] 
			SimpleDateFormat dateformat1=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss E");
			Log.v("debuga", "file_list:" +f.exists());
			for (int i = 0; i < file_list.length; i++) {
				HashMap<String, String> info = new HashMap<String, String>();
				//Log.v("debuga", "aaa:" + dateformat1.format(((new File(dirPath + "/" + file_list[i])).lastModified())) + " length:" + file_list[i]);
				info.put("name", file_list[i].split(".")[0]);
				info.put("time", dateformat1.format(((new File(dirPath + "/" + file_list[i])).lastModified())));
				data.add(info);
			}
		}
		
		SimpleAdapter adapter = new SimpleAdapter(MainActivity.this, data, R.layout.item_menu, 
				new String[] {"name", "time"}, new int[] {R.id.name, R.id.time});
		listview.setAdapter(adapter);
		
		//Log.v("debuga", listview.toString());
		/*listview.setOnItemLongClickListener(new OnItemLongClickListener() {
			public boolean onItemLongClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				Toast.makeText(MainActivity.this, "LongClick", 200).show();
				return false;
			}
		});
		*/
	
		listview.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				ListView lv = (ListView)arg0;
				@SuppressWarnings("unchecked")
				HashMap<String, String> tem = (HashMap<String, String>)lv.getItemAtPosition(arg2);
				fileName = tem.get("name");
				lv.setVisibility(View.GONE);
			}
		});
		
	}

	private void onSave() throws IOException {
		File f = new File(this.dirPath); //strPath为路径
		if (!f.exists()) {
			f.mkdirs(); //建立文件夹 
			Log.v("debug", "mkdir~");
			Log.v("debug", "f.exists(): " + f.getPath() + " " + f.exists());
		}
		File file = new File(dirPath + fileName + ".psong"); 
		Log.v("debuga", file.getPath());
		try{
			FileOutputStream fout = new FileOutputStream(file); 
			//byte [] bytes = write_str.getBytes();
			String a = "a";
			String[] ab = {"a", "b"};
			class aaa {
				private String s = "a";
			}
			Log.v("debuga", "ab.toString()" + ab.toString());
			byte[] bytes = a.getBytes();
			fout.write(bytes);
			fout.close();
		}catch(Exception e){
			e.printStackTrace();
		}
		this.isSaved = true;
		Toast.makeText(MainActivity.this, "Already Saved", Toast.LENGTH_SHORT).show();
	}
	
	private void getDirPath() {
		if (Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED)) {
			this.dirPath = Environment.getExternalStorageDirectory().getPath() + "/PhotoSong/history/";
		}
		else 
			this.dirPath = Environment.getRootDirectory().getPath() + "/PhotoSong/history/";
	}
}
