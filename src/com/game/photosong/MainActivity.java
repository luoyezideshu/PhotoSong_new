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
		//��ȥ��������Ӧ�ó�������֣�  
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        //��ȥ״̬������(��ص�ͼ���һ�����β���)
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

		setContentView(R.layout.activity_main);
		button_menu = (ImageButton)findViewById(R.id.button_menu);
		//button_menu.setImageDrawable(getResources().getDrawable(android.R.drawable.ic_action_menu));
		
		button_menu.setOnClickListener(new Button.OnClickListener(){ 
			@Override 
	            public void onClick(View v) {  
	                //�Ի���   Builder��AlertDialog�ľ�̬�ڲ���  
	                Dialog dialog = new AlertDialog.Builder(MainActivity.this)  
	                //���öԻ���ı���  
	                    .setTitle("С����ʾ")  
	                //���öԻ���Ҫ��ʾ����Ϣ  
	                    .setMessage("�������ImageButton1")  
	                //���Ի���������ť �С�ȷ������ ���������ü����� ����д��Ҳ������ЩBT  
	                    .setPositiveButton("ȷ����", new DialogInterface.OnClickListener(){  
	  
	                        public void onClick(DialogInterface dialog, int which) {  
	                            //��� "ȷ����" ��ť֮��Ҫִ�еĲ�����д������  
	                        }  
	                    }).create();//������ť  
	                    dialog.show();//��ʾһ��  
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
