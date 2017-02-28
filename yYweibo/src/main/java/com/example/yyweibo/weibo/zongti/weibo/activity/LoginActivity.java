package com.example.yyweibo.weibo.zongti.weibo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;


import com.example.yyweibo.R;
import com.sina.weibo.sdk.auth.AuthInfo;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.sina.weibo.sdk.auth.WeiboAuthListener;
import com.sina.weibo.sdk.auth.sso.SsoHandler;
import com.sina.weibo.sdk.exception.WeiboException;

import java.text.SimpleDateFormat;

import com.example.yyweibo.weibo.zongti.weibo.constants.AccessTokenKeeper;
import com.example.yyweibo.weibo.zongti.weibo.constants.Config;
import com.example.yyweibo.weibo.zongti.weibo.BaseActivity;

public class LoginActivity extends BaseActivity {
	/**显示验证之后的信息*/
	private TextView mTokenText;

	private SsoHandler mSsoHandler;

	private AuthInfo mAuthInfo;
	/** 封装了 "access_token"，"expires_in"，"refresh_token"，并提供了他们的管理功能  */
	private Oauth2AccessToken mAccessToken;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		mTokenText = (TextView) findViewById(R.id.tvToken);
		//创建微博授权类对象，将应用的信息保存
		mAuthInfo = new AuthInfo(this, Config.APP_KEY,Config.REDIRECT_URL,Config.SCOPE);

		mSsoHandler = new SsoHandler(LoginActivity.this,mAuthInfo);

		findViewById(R.id.getCD).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				mSsoHandler.authorize(new AuthListener());
			}
		});
		mAccessToken = AccessTokenKeeper.readAccessToken(this);
		updateTokenView(true);
	}
/**
 * 当 SSO 授权 Activity 退出时，该函数被调用。
 * */
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		// SSO 授权回调
		// 重要：发起 SSO 登陆的 Activity 必须重写 onActivityResults
		if (mSsoHandler != null) {
			mSsoHandler.authorizeCallBack(requestCode,resultCode,data);
		}
	}
	/**实现WeiboAuthListener接口
	 * 授权成功后，SDK 会将 access_token、expires_in、uid
	 * 等通过 Bundle 的形式返回，在 onComplete 凼数中， 可以获取该信息。
	 * 具体如何保存呾Token信息由开发者自行处理.
	 */
	class AuthListener implements WeiboAuthListener {
		@Override
		public void onComplete(Bundle bundle) {
			/**从Bundle中解析token*/
			mAccessToken = Oauth2AccessToken.parseAccessToken(bundle);
			String phoneNum = mAccessToken.getPhoneNum();
			if(mAccessToken.isSessionValid()) {
				updateTokenView(false);
//				ToastUtils.showToast(getApplicationContext(),"授权成功",Toast.LENGTH_SHORT);
				/**保存 Token 到 SharedPreferences**/
				AccessTokenKeeper.writeAccessToken(LoginActivity.this, mAccessToken);
				startActivity(new Intent(LoginActivity.this,MainActivity.class));
			}else {
				// 以下几种情况，您会收到 Code：
				// 1. 当您未在平台上注册的应用程序的包名与签名时；
				// 2. 当您注册的应用程序包名与签名不正确时；
				// 3. 当您在平台上注册的包名和签名与您当前测试的应用的包名和签名不匹配时。
				String code = bundle.getString("code");
				String message = "授权失败";
				if (!TextUtils.isEmpty(code)) {
					message = message + "\nObtained the code: " + code;
				}
				Toast.makeText(LoginActivity.this, message, Toast.LENGTH_LONG).show();
			}

		}

		@Override
		public void onCancel() {

		}

		@Override
		public void onWeiboException(WeiboException e) {

		}
	}

	/**
	 * 显示当前 Token 信息。
	 *
	 * @param hasExisted 配置文件中是否已存在 token 信息并且合法
	 */
	private void updateTokenView(boolean hasExisted) {
		String date = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(
				new java.util.Date(mAccessToken.getExpiresTime()));
		String format = "Token：%1$s \n有效期：%2$s";

		mTokenText.setText(String.format(format, mAccessToken.getToken(), date));

		String message = String.format(format, mAccessToken.getToken(), date);
		if (hasExisted) {
			message = "Token 仍在有效期内，无需再次登录。"+ "\n" + message;
		}
		mTokenText.setText(message);
	}

}
