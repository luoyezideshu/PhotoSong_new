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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;



public class MainActivity extends Activity {

	private ImageButton button_menu;
	private int status; //1:draw; 2:voice; 3:eraser
	private boolean isSaved;
	private String sdCardPath;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//隐去标题栏（应用程序的名字）  
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        //隐去状态栏部分(电池等图标和一切修饰部分)
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        
		setContentView(R.layout.activity_main);
		button_menu = (ImageButton)findViewById(R.id.button_menu);
		button_menu.setImageDrawable(getResources().getDrawable(R.drawable.ic_action_draw));
		status = 1;
		isSaved = true;
		button_menu.setOnClickListener(new Button.OnClickListener(){ 
			@Override 
	        public void onClick(View v) {  
				if (status == 1) { 
					button_menu.setImageDrawable(getResources().getDrawable(R.drawable.ic_action_voice));
					status = 2;
				}
				else if (status == 2) {
					button_menu.setImageDrawable(getResources().getDrawable(R.drawable.ic_action_eraser));
					status = 3;
				}
				else {
					button_menu.setImageDrawable(getResources().getDrawable(R.drawable.ic_action_draw));
					status = 1;
				}				
	        }

	    });  
	}
	
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		// 保存、退出、播放、播放整曲
	    menu.add(1, 1, 1, "播放当前");
	    menu.add(1, 2, 2, "播放整曲");
		menu.add(2, 3, 3, "保存");
	    menu.add(2, 4, 4, "历史");
		return true;
	}
	
	public boolean onOptionsItemSelected(MenuItem item) {  
		//提醒用户做保存的事情的一个框
		//对话框   Builder是AlertDialog的静态内部类  
		Dialog dialog = new AlertDialog.Builder(MainActivity.this)  
			//设置对话框的标题  
			.setTitle("哎呀呀还没保存就想退出？")  
			//设置对话框要显示的消息  
			.setMessage("当前曲目是否保存？")  
			//设置按钮
			.setPositiveButton("直接退出", new DialogInterface.OnClickListener(){  
				public void onClick(DialogInterface dialog, int which) {  
					//好像什么都不用干
				}  
			})
			.setNegativeButton("保存并退出", new DialogInterface.OnClickListener(){  
				public void onClick(DialogInterface dialog, int which) {  
					//保存应该做的事
					;
				}
			})
			.setCancelable(true).create();

		// 在此说明一下，Menu相当于一个容器，而MenuItem相当于容器中容纳的东西 
		switch(item.getItemId()) { 
			case 1: 
				setTitle("播放当前"); 
				break; 
			case 2: 
				setTitle("播放整曲"); 
				break; 
			case 3: 
				//保存应该做的事
				
				
				break; 
			case 4: 
				if (isSaved == false) {
					dialog.show();//显示一把 
				}
				//调文件系统，找出文件
				
				
				//从文件中恢复
				
				
				break; 
			case 5: 
				if (isSaved == false) {
					dialog.show();//显示一把 
				}
		        //清空缓存之类的
      			
      			
      			
				break; 
		}  
		return true;  
	}  

}
