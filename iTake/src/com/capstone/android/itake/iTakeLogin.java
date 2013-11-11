package com.capstone.android.itake; 

import android.accounts.Account;  
import android.accounts.AccountAuthenticatorActivity;  
import android.accounts.AccountManager;  
import android.content.ContentResolver;  
import android.content.Intent;  
import android.content.SharedPreferences;  
import android.graphics.Color;  
import android.os.Bundle;  
import android.view.View;  
import android.widget.TextView;   
  
public class iTakeLogin extends AccountAuthenticatorActivity 
{  
  /*  public static final String PARAM_AUTHTOKEN_TYPE = "auth.token";  
    public static final String PARAM_CREATE = "create";  
  
    public static final int REQ_CODE_CREATE = 1;  
  
    public static final int REQ_CODE_UPDATE = 2;  
  
    public static final String EXTRA_REQUEST_CODE = "req.code";  
  
    public static final int RESP_CODE_SUCCESS = 0;  
  
    public static final int RESP_CODE_ERROR = 1;  
  
    public static final int RESP_CODE_CANCEL = 2;  
  
    @Override  
    protected void onCreate(Bundle icicle) 
    {  
        // TODO Auto-generated method stub  
        super.onCreate(icicle);  
        this.setContentView(R.layout.user_reg);  
    }  
  
    public void onCancelClick(View v) 
    {  
        this.finish();  
    }  
  
    public void onSaveClick(View v) 
    {  
        TextView tvUsername;  
        TextView tvPassword;  
        TextView tvConfirm;
        
        String username;  
        String password;  
        String ConfirmKey;
        
        boolean hasErrors = false;  
  
        tvUsername = (TextView) this.findViewById(R.id.emaillogin);  
        tvPassword = (TextView) this.findViewById(R.id.passwordlogin);  
        tvConfirm = (TextView) this.findViewById(R.id.passwordconfirm);  
  
        tvUsername.setBackgroundColor(Color.WHITE);  
        tvPassword.setBackgroundColor(Color.WHITE);  
        tvConfirm.setBackgroundColor(Color.WHITE);  
  
        username = tvUsername.getText().toString();  
        password = tvPassword.getText().toString();  
        ConfirmKey = tvConfirm.getText().toString();  
  
        if (username.length() < 3) 
        {  
            hasErrors = true;  
            tvUsername.setBackgroundColor(Color.MAGENTA);  
        }  
        if (password.length() < 3) 
        {  
            hasErrors = true;  
            tvPassword.setBackgroundColor(Color.MAGENTA);  
        }  
        if (ConfirmKey.length() < 3) 
        {  
            hasErrors = true;  
            tvConfirm.setBackgroundColor(Color.MAGENTA);  
        }  
  
        if (hasErrors) 
        {  
            return;  
        }  
          
        // Now that we have done some simple "client side" validation it  
        // is time to check with the server  
  
        // ... perform some network activity here  
  
        // finished  
  
        String accountType = this.getIntent().getStringExtra(PARAM_AUTHTOKEN_TYPE);  
        if (accountType == null) 
        {  
            accountType = iTakeLogin.ACCOUNT_TYPE;  
        }  
  
        AccountManager accMgr = AccountManager.get(this);  
  
        if (hasErrors) 
        {  
  
            // handle errors  
  
        }
        else 
        {    
            // This is the magic that adds the account to the Android Account Manager  
            final Account account = new Account(username, accountType);  
            accMgr.addAccountExplicitly(account, password, null);  
  
            // Now we tell our caller, could be the Android Account Manager or even our own application  
            // that the process was successful  
            final Intent intent = new Intent();  
            intent.putExtra(AccountManager.KEY_ACCOUNT_NAME, username);  
            intent.putExtra(AccountManager.KEY_ACCOUNT_TYPE, accountType);  
            intent.putExtra(AccountManager.KEY_AUTHTOKEN, accountType);  
            this.setAccountAuthenticatorResult(intent.getExtras());  
            this.setResult(RESULT_OK, intent);  
            this.finish();  
        }  
    }*/  
}  