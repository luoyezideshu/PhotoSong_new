package com.game.photosong;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class FileBrowserActivity extends Activity {
	private ListView listView; //�ļ�Ŀ¼ListView
	private GridView toolbarGrid; //������
	private TextView title; //����-·��
	private List fileName = null; //�ļ�����
	private List fileNum = null; //��¼�ļ����ٻ����ж��ٸ���Ŀ¼
	private List filePath = null; //�ļ�·��
	private List isFile = null; //�Ƿ��ļ���
	private boolean flag = true;
	private String sdCardPath; //SD����·��
	private String parentPath; //·�����ϼ�

	/** �ײ��˵�ͼƬ **/
	int[] menu_toolbar_image_array = {
		R.drawable.menu_paste,
		R.drawable.controlbar_back,
		R.drawable.menu_penselectmodel,
		R.drawable.menu_quit 
	};
	/** �ײ��˵����� **/
	String[] menu_toolbar_name_array = { "��Ŀ¼", "����", "ȷ��", "�˳�" };
	
	public void toast(String msg){
		Toast.makeText(this, msg, 1).show();
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_file);
		if(!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
			toast("Sd��������");
		}
		sdCardPath = Environment.getExternalStorageDirectory().getPath();
		// File fff=new File(sdCardPath+"/test/ddd");
		// if(!fff.exists())fff.mkdirs();
		listView = (ListView)findViewById(R.id.list_view);
		title = (TextView) findViewById(R.id.title2);
		getFileDir(sdCardPath);
		// �����ײ��˵� Toolbar
		toolbarGrid = (GridView) findViewById(R.id.filedir_toolbar); //��ȡ�ײ���GridView�ؼ�
		toolbarGrid.setNumColumns(4); // ����ÿ������
		toolbarGrid.setAdapter(getMenuAdapter(menu_toolbar_name_array,menu_toolbar_image_array));// ���ò˵�Adapter������getMenuAdapter��������MAP����
		/** �����ײ��˵�ѡ�� **/
		toolbarGrid.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView arg0, View arg1, int arg2,long arg3) {
				switch (arg2) {
				case 0:	 //��ҳ
					getFileDir(sdCardPath);
					break;
				case 1: //����
					getFileDir(parentPath);
					title.setText("·��:"+parentPath);
					break;
				case 2:
					// TODO Auto-generated method stub
					break;
				case 3:
					FileBrowserActivity.this.finish();
					break;
				}
			}
		});
	}
	
	/**
	 * ����˵�Adapter
	 * @param menuNameArray ����
	 * @param imageResourceArray ͼƬ
	 * @return SimpleAdapter
	 */
	private SimpleAdapter getMenuAdapter(String[] menuNameArray,int[] imageResourceArray) {
		ArrayList data = new ArrayList();
		for (int i = 0; i < menuNameArray.length; i++) {
			HashMap map = new HashMap();
			map.put("itemImage", imageResourceArray[i]);
			map.put("itemText", menuNameArray[i]);
			data.add(map);
		}
		SimpleAdapter simperAdapter = new SimpleAdapter(
			this, //������
			data, //����Դ
			R.layout.item_menu, //�󶨵���ͼ
new String[] { "itemImage", "itemText" }, //������Դ��Ӧ�ı���
new int[] { R.id.item_image, R.id.item_text }); //����ͼ��Ӧ�Ŀؼ�
return simperAdapter;
}
/**
* ��ȡ·������Ŀ¼�б�
* @param path ·��
*/
private void getFileDir(String path) {
File f = new File(path); //�����ļ�
if(!path.equals(sdCardPath)){ //�ж��ļ��Ƿ�SD����·��
parentPath = f.getParent(); //������Ǹ�·���ͻ�ȡ��·�����ϼ�·��
}else
parentPath = sdCardPath; //����Ǹ�·����
//��ʼ����Ÿ����ݵ�List
fileName = new ArrayList();
fileNum = new ArrayList();
filePath = new ArrayList();
isFile = new ArrayList();
File file=new File(path); //��ȡ���ս�����·��
int fileSize = file.list().length; //��ȡ·��һ���ж��ٸ����ļ�
//��һ�α�������ļ���
for(int i=0;i< p>
File fileChild=new File(path, file.list()[i]); //�������ļ�
if(fileChild.isDirectory() && !fileChild.isHidden()){ //�ж����ļ��Ƿ��ļ��в��Ҳ��������ļ�
fileName.add(fileChild.getName()); //��ȡ���ļ�������
fileNum.add(String.valueOf(fileChild.list().length)+"�����ļ�"); //��ȡ���ļ������м������ļ�
filePath.add(fileChild.getPath()); //��ȡ���ļ���·��
isFile.add(true); //����Ƿ��ļ���
}
}
//��2�α�����ͼƬ�ļ�
for(int i=0;i< p>
File fileChild=new File(path, file.list()[i]);
if(fileChild.getName().endsWith(".jpg")){ //�жϺ�׺��ͼƬ
fileName.add(fileChild.getName());
fileNum.add(String.valueOf(formatSize(fileChild.length()))); //��ʽ��ͼƬ���٣���ʾKB/MB/GB
filePath.add(file.getPath());
isFile.add(false);
}
}
String[] str={"fileImage","fileName","isFileImage","fileNum","filePath"};
List> list =new ArrayList>();
for(int i=0;i< p>
Map map =new HashMap();
map.put("fileName", fileName.get(i));
map.put("filePath", filePath.get(i));
map.put("fileNum", fileNum.get(i));
//�ж����ļ��л���ͼƬ����ʾ��ͬ��ͼƬ
if(isFile.get(i)){
map.put("fileImage", R.drawable.image_folder);
map.put("isFileImage", R.drawable.list_icon);
}else if(fileName.get(i).endsWith(".jpg")){
map.put("fileImage", R.drawable.imagefile);
map.put("isFileImage", R.drawable.list_icon);
}
list.add(map);
}
SimpleAdapter adapter=new SimpleAdapter(MainActivity.this,
list,
R.layout.filedir_item,
str,
new int[]{R.id.item_image,R.id.item_name,R.id.item_icon,R.id.item_num});
listView.setAdapter(adapter);
listView.setOnItemClickListener(new OnItemClickListener() {
@Override
public void onItemClick(AdapterView parent, View view,int position, long id) {
Map map=(Map) parent.getItemAtPosition(position); // ��ȡ�����Item���󶨵�����
getFileDir(map.get("filePath").toString()); //��ȡ����ļ���·��
title.setText("·��:"+map.get("filePath").toString()); //���ñ�������
}
});
}
/**
* ��ʽ���ļ����٣���ʾKB/MB/GB
* @param size
* @return ���ظ�ʽ�����ַ���
*/
private String formatSize(long size) {
long SIZE_KB = 1024;
long SIZE_MB = SIZE_KB * 1024;
long SIZE_GB = SIZE_MB * 1024;
if(size < SIZE_KB) {
return String.format( "%d B" , (int) size);
} else if(size < SIZE_MB) {
return String.format( "%.2f KB" , (float)size / SIZE_KB);
} else if(size < SIZE_GB) {
return String.format( "%.2f MB" , (float)size / SIZE_MB);
} else {
return String.format( "%.2f GB" , (float)size / SIZE_GB);
}
}
// @Override
// public boolean onKeyDown(int keyCode, KeyEvent event) {
// if(keyCode==KeyEvent.KEYCODE_BACK){
// if(parentPath!=null)
// getFileDir(parentPath);
// return true;
// }
//
//
// return super.onKeyDown(keyCode, event);
// }
}