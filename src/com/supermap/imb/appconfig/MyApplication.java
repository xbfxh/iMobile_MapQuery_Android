package com.supermap.imb.appconfig;

import com.supermap.data.Environment;
import com.supermap.data.Workspace;
import com.supermap.data.WorkspaceConnectionInfo;
import com.supermap.data.WorkspaceType;
import com.supermp.imb.file.MyAssetManager;
import com.supermp.imb.file.MySharedPreferences;
import android.app.Application;
import android.util.Log;
import android.view.Gravity;
import android.widget.Toast;

public class MyApplication extends Application {
	public static String DATAPATH = "";
	public static String SDCARD = android.os.Environment.getExternalStorageDirectory().getAbsolutePath()+"/";
	private static MyApplication sInstance = null;
	
	private Workspace mWorkspace = null;
	
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		
		DATAPATH = this.getFilesDir().getAbsolutePath()+"/";
		sInstance = this;
		
		//��һ���������û�����������ʼ����iMobile
		Environment.setLicensePath(DefaultDataConfig.LicPath);
		Environment.initialization(this);
		
		//��ʼ��ϵͳ��ص���
		MySharedPreferences.init(this);
		MyAssetManager.init(this);
		
		//��������
		new DefaultDataConfig().autoConfig();
		
		mWorkspace = new Workspace();
	}

	public void openWorkspace() {

		WorkspaceConnectionInfo info = new WorkspaceConnectionInfo();
		info.setServer(DefaultDataConfig.WorkspacePath);
		info.setType(WorkspaceType.SMWU);
		if (!mWorkspace.open(info)) {
			ShowError("�����ռ���!");
		}
	}
	/**
	 * ��ȡ�����ռ�
	 * @return
	 */
	public Workspace getOpenedWorkspace(){
		return mWorkspace;
	}
	
	/**
	 * ��ȡӦ�ö���
	 * @return
	 */
	public static MyApplication getInstance(){
		return sInstance;
	}
	
	/**
	 * Toast��ʾ��Ϣ
	 * @param info
	 */
	public void ShowInfo(String info){
		Toast toast = Toast.makeText(sInstance, info, 500);
		toast.setGravity(Gravity.CENTER_VERTICAL|Gravity.CENTER_HORIZONTAL, 0, 0);
		toast.show();
	}
	
	/**
	 * Toast��ʾ������Ϣ
	 * @param err
	 */
	public void ShowError(String err){
		Toast toast = Toast.makeText(sInstance, "Error: "+err, 500);
		toast.setGravity(Gravity.CENTER_VERTICAL|Gravity.CENTER_HORIZONTAL, 0, 0);
		toast.show();
		Log.e(this.getClass().getName(), err);
	}
	
	/**
	 * ��ȡ��ʾ�ߴ�ֵ
	 * @param dp
	 * @return
	 */
	public static int dp2px(int dp){
		return (int) (dp*sInstance.getResources().getDisplayMetrics().density);
	}
}
