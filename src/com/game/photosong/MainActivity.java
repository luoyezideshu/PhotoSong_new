package com.game.photosong;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;


public class MainActivity extends Activity {

	private ImageButton button_menu;
	private int status = 1; //1:draw; 2:voice; 3:......
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//隐去标题栏（应用程序的名字）  
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        //隐去状态栏部分(电池等图标和一切修饰部分)
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

		setContentView(R.layout.activity_main);
		button_menu = (ImageButton)findViewById(R.id.button_menu);
		//button_menu.setImageDrawable(getResources().getDrawable(android.R.drawable.ic_action_menu));
		
		button_menu.setOnClickListener(new Button.OnClickListener(){ 
			@Override 
	            public void onClick(View v) {  
	                //对话框   Builder是AlertDialog的静态内部类  
	                Dialog dialog = new AlertDialog.Builder(MainActivity.this)  
	                //设置对话框的标题  
	                    .setTitle("小航提示")  
	                //设置对话框要显示的消息  
	                    .setMessage("我真的是ImageButton1")  
	                //给对话框来个按钮 叫“确定定” ，并且设置监听器 这种写法也真是有些BT  
	                    .setPositiveButton("确定定", new DialogInterface.OnClickListener(){  
	  
	                        public void onClick(DialogInterface dialog, int which) {  
	                            //点击 "确定定" 按钮之后要执行的操作就写在这里  
	                        }  
	                    }).create();//创建按钮  
	                    dialog.show();//显示一把  
	            }

	        });  
	}
	
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
