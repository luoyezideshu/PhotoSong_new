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
	private ListView listView; //文件目录ListView
	private GridView toolbarGrid; //工具条
	private TextView title; //标题-路径
	private List fileName = null; //文件名称
	private List fileNum = null; //记录文件大少或者有多少个子目录
	private List filePath = null; //文件路径
	private List isFile = null; //是否文件夹
	private boolean flag = true;
	private String sdCardPath; //SD卡根路径
	private String parentPath; //路径的上级

	/** 底部菜单图片 **/
	int[] menu_toolbar_image_array = {
		R.drawable.menu_paste,
		R.drawable.controlbar_back,
		R.drawable.menu_penselectmodel,
		R.drawable.menu_quit 
	};
	/** 底部菜单文字 **/
	String[] menu_toolbar_name_array = { "主目录", "返回", "确定", "退出" };
	
	public void toast(String msg){
		Toast.makeText(this, msg, 1).show();
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_file);
		if(!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
			toast("Sd卡不存在");
		}
		sdCardPath = Environment.getExternalStorageDirectory().getPath();
		// File fff=new File(sdCardPath+"/test/ddd");
		// if(!fff.exists())fff.mkdirs();
		listView = (ListView)findViewById(R.id.list_view);
		title = (TextView) findViewById(R.id.title2);
		getFileDir(sdCardPath);
		// 创建底部菜单 Toolbar
		toolbarGrid = (GridView) findViewById(R.id.filedir_toolbar); //获取底部的GridView控件
		toolbarGrid.setNumColumns(4); // 设置每行列数
		toolbarGrid.setAdapter(getMenuAdapter(menu_toolbar_name_array,menu_toolbar_image_array));// 设置菜单Adapter。调用getMenuAdapter方法构造MAP参数
		/** 监听底部菜单选项 **/
		toolbarGrid.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView arg0, View arg1, int arg2,long arg3) {
				switch (arg2) {
				case 0:	 //首页
					getFileDir(sdCardPath);
					break;
				case 1: //返回
					getFileDir(parentPath);
					title.setText("路径:"+parentPath);
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
	 * 构造菜单Adapter
	 * @param menuNameArray 名称
	 * @param imageResourceArray 图片
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
			this, //上下文
			data, //数据源
			R.layout.item_menu, //绑定的视图
new String[] { "itemImage", "itemText" }, //绑定数据源对应的标题
new int[] { R.id.item_image, R.id.item_text }); //绑定视图对应的控件
return simperAdapter;
}
/**
* 获取路径的子目录列表
* @param path 路径
*/
private void getFileDir(String path) {
File f = new File(path); //建立文件
if(!path.equals(sdCardPath)){ //判断文件是否SD卡根路径
parentPath = f.getParent(); //如果不是根路径就获取该路径的上级路径
}else
parentPath = sdCardPath; //如果是根路径就
//初始化存放个数据的List
fileName = new ArrayList();
fileNum = new ArrayList();
filePath = new ArrayList();
isFile = new ArrayList();
File file=new File(path); //获取接收进来的路径
int fileSize = file.list().length; //获取路径一共有多少个子文件
//第一次遍历存放文件夹
for(int i=0;i< p>
File fileChild=new File(path, file.list()[i]); //定义子文件
if(fileChild.isDirectory() && !fileChild.isHidden()){ //判断子文件是否文件夹并且不是隐藏文件
fileName.add(fileChild.getName()); //获取子文件的名称
fileNum.add(String.valueOf(fileChild.list().length)+"个子文件"); //获取子文件里面有几个子文件
filePath.add(fileChild.getPath()); //获取子文件的路径
isFile.add(true); //标记是否文件夹
}
}
//第2次遍历放图片文件
for(int i=0;i< p>
File fileChild=new File(path, file.list()[i]);
if(fileChild.getName().endsWith(".jpg")){ //判断后缀是图片
fileName.add(fileChild.getName());
fileNum.add(String.valueOf(formatSize(fileChild.length()))); //格式化图片大少，显示KB/MB/GB
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
//判断是文件夹还是图片，显示不同的图片
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
Map map=(Map) parent.getItemAtPosition(position); // 获取点击的Item所绑定的数据
getFileDir(map.get("filePath").toString()); //获取点击文件夹路径
title.setText("路径:"+map.get("filePath").toString()); //设置标题文字
}
});
}
/**
* 格式化文件大少，显示KB/MB/GB
* @param size
* @return 返回格式化的字符串
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