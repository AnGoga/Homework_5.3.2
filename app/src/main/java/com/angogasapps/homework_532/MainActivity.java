package com.angogasapps.homework_532;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.angogasapps.homework_532.databinding.ActivityMainBinding;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnSend.setOnClickListener(v -> {
            sendPOST();
        });

        binding.btnGet.setOnClickListener(v -> {
            sendGET();
        });

    }

    private void sendPOST() {
        Observable.create(emitter -> {
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost("http://192.168.72.3:8080");
            try {
                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
                nameValuePairs.add(new BasicNameValuePair("lastname", binding.lastName.getText().toString()));
                nameValuePairs.add(new BasicNameValuePair("firstname", binding.firstName.getText().toString()));
                httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs, "UTF-8"));
                // Выполняем HTTP Post запрос
                HttpResponse response = httpclient.execute(httppost);
                if (response.getStatusLine().getStatusCode() == 200) {
                    HttpEntity entity = response.getEntity();
                    String answerHTTP = EntityUtils.toString(entity);

                    emitter.onNext(answerHTTP);
                }else{
                    System.err.println(response.getStatusLine().getStatusCode());
                }
            } catch (ClientProtocolException e) {
                e.printStackTrace();
                // TODO Auto-generated catch block
            } catch (IOException e) {
                e.printStackTrace();
                // TODO Auto-generated catch block
            }catch (Exception e){
                e.printStackTrace();
            }
        })
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Object>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {}

                    @Override
                    public void onNext(@NonNull Object o) {
                        if (!(o instanceof String))
                            return;
                        String answer = (String)o;

                        binding.textView.setText(answer);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onComplete() {}
                });
    }

    private void sendGET(){
        Observable.create(emitter -> {
            HttpClient httpclient = new DefaultHttpClient();
            HttpGet httpGet = new HttpGet("http://192.168.72.3:8080");
            try {
                // Выполняем HTTP Post запрос
                HttpResponse response = httpclient.execute(httpGet);
                if (response.getStatusLine().getStatusCode() == 200) {
                    HttpEntity entity = response.getEntity();
                    String answerHTTP = EntityUtils.toString(entity);

                    emitter.onNext(answerHTTP);
                }else{
                    System.err.println(response.getStatusLine().getStatusCode());
                }
            } catch (ClientProtocolException e) {
                e.printStackTrace();
                // TODO Auto-generated catch block
            } catch (IOException e) {
                e.printStackTrace();
                // TODO Auto-generated catch block
            }catch (Exception e){
                e.printStackTrace();
            }
        })
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Object>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {}

                    @Override
                    public void onNext(@NonNull Object o) {
                        if (!(o instanceof String))
                            return;
                        String answer = (String)o;

                        binding.textView.setText(answer);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onComplete() {}
                });
    }
}