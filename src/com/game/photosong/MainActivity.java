package com.game.photosong;

import java.io.FileOutputStream;

import android.os.Bundle;
import android.os.Environment;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
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
		//��ȥ��������Ӧ�ó�������֣�  
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        //��ȥ״̬������(��ص�ͼ���һ�����β���)
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
		// ���桢�˳������š���������
	    menu.add(1, 1, 1, "���ŵ�ǰ");
	    menu.add(1, 2, 2, "��������");
		menu.add(2, 3, 3, "����");
	    menu.add(2, 4, 4, "��ʷ");
	    menu.add(2, 5, 5, "�˳�");
		return true;
	}
	
	public boolean onOptionsItemSelected(MenuItem item) {  
		//�����û�������������һ����
		//�Ի���   Builder��AlertDialog�ľ�̬�ڲ���  
		Dialog dialog = new AlertDialog.Builder(MainActivity.this)  
			//���öԻ���ı���  
			.setTitle("��ѽѽ��û��������˳���")  
			//���öԻ���Ҫ��ʾ����Ϣ  
			.setMessage("��ǰ��Ŀ�Ƿ񱣴棿")  
			//���ð�ť
			.setPositiveButton("ֱ���˳�", new DialogInterface.OnClickListener(){  
				public void onClick(DialogInterface dialog, int which) {  
					//����ʲô�����ø�
				}  
			})
			.setNegativeButton("���沢�˳�", new DialogInterface.OnClickListener(){  
				public void onClick(DialogInterface dialog, int which) {  
					//����Ӧ��������
					sdCardPath = Environment.getExternalStorageDirectory().getPath();
					String fileName = "";
					//FileOutputStream out = this.openFileOutput(sdCardPath + "/" + fileName, Mode_);
  			
  			
				}  
			})
			.setCancelable(true).create();
		
		// �ڴ�˵��һ�£�Menu�൱��һ����������MenuItem�൱�����������ɵĶ��� 
		switch(item.getItemId()) { 
			case 1: 
				setTitle("���ŵ�ǰ"); 
				break; 
			case 2: 
				setTitle("��������"); 
				break; 
			case 3: 
				//����Ӧ��������
				
				
				break; 
			case 4: 
				if (isSaved == false) {
					dialog.show();//��ʾһ�� 
				}
				//���ļ�ϵͳ���ҳ��ļ�
				
				
				//���ļ��лָ�
				
				
				break; 
			case 5: 
				if (isSaved == false) {
					dialog.show();//��ʾһ�� 
				}
		        //��ջ���֮���
      			
      			
      			
				break; 
		}  
		return true;  
	}  

}
