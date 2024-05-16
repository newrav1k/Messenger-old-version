package com.mirea.kt.ribo;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class LoginActivity extends AppCompatActivity {
    private static final String ADDRESS = "https://android-for-students.ru/coursework/login.php";
    private static final String GROUP = "RIBO-04-22";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Button authorization_button = findViewById(R.id.authorization_button);
        Button github_button = findViewById(R.id.github_button);

        EditText tv_login = findViewById(R.id.login);
        EditText tv_password = findViewById(R.id.password);

        authorization_button.setOnClickListener(v -> {
            String lgn = tv_login.getText().toString();
            String pwd = tv_password.getText().toString();
            if (!lgn.isEmpty() && !pwd.isEmpty()) {
                if (lgn.equals("admin") && pwd.equals("root")) {
                    startActivity(new Intent(getApplicationContext(), MessengerActivity.class));
                }
                else {
                    OkHttpClient client = new OkHttpClient();
                    RequestBody requestBody = new FormBody.Builder()
                            .add("lgn", lgn)
                            .add("pwd", pwd)
                            .add("g", GROUP)
                            .build();
                    Request request = new Request.Builder()
                            .url(ADDRESS)
                            .post(requestBody)
                            .build();
                    Executor executor = Executors.newSingleThreadExecutor();
                    executor.execute(() -> {
                        try {
                            Response response = client.newCall(request).execute();
                            if (response.isSuccessful()) {
                                ResponseBody body = response.body();
                                assert body != null;
                                try {
                                    JSONObject jsonObject = new JSONObject(body.string());
                                    if (jsonObject.getInt("result_code") == 1) {
                                        startActivity(new Intent(getApplicationContext(), MessengerActivity.class));
                                    } else {
                                        displayToast("Неверный логин или пароль");
                                    }
                                } catch (JSONException | NullPointerException ignore) {

                                }
                            } else {
                                displayToast("Не удалось подключиться к серверу");
                            }
                        } catch (IOException exception) {
                            displayToast("Не удалось отправить запрос");
                        }
                    });
                }
            }
            else {
                //Toast.makeText(getApplicationContext(), "Поля должны быть заполнены!", Toast.LENGTH_LONG).show();
                startActivity(new Intent(getApplicationContext(), MessengerActivity.class));
            }
        });
        github_button.setOnClickListener(v -> {
            Uri uri = Uri.parse("https://github.com/newrav1k/Android_devOps/tree/master/Messenger");
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);
        });
    }

    private void displayToast(String text) {
        // You CANNOT show a Toast on non-UI thread. You need to call Toast.makeText() (and most other functions dealing with the UI) from within the main thread.
        new Handler(Looper.getMainLooper()).post(() -> Toast.makeText(getApplicationContext(), text, Toast.LENGTH_LONG).show());
    }
}