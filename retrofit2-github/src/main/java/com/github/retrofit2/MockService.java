/*
 * Copyright (C) 2015 8tory, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */
package com.github.retrofit2;

import retrofit.http.Retrofit.*;
import retrofit.http.Retrofit;

import rx.Observable;
import java.io.File;

import retrofit.converter.*;
import java.util.List;
import rx.functions.*;
import retrofit.mime.TypedFile;
import retrofit.mime.TypedString;
import com.github.mobile.model.*;
import retrofit.client.Response;
import retrofit.Callback;
import android.app.Activity;

@Retrofit("https://api.github.com")
@retrofit.http.Retrofit.Headers({ // optional
    "Accept: application/vnd.github.v3.full+json",
    "User-Agent: Retrofit2"
})
@RetryHeaders( // optional
    value = "Cache-Control: max-age=640000",
    exceptions = retrofit.http.RequestException.class
)
@Retrofit.Converter(GsonConverter.class) // optional
//@Retrofit.Converter(LoganSquareConverter.class) // default
@LogLevel(retrofit.RestAdapter.LogLevel.FULL) // optional
//@RequestInterceptor(MockServiceRequestInterceptor.class) // optional
@ErrorHandler(MockErrorHandler.class) // optional
// @Retrofit.OkHttpClient(MockServiceOkHttpClienter.class) // optional
public abstract class MockService {
    @GET("{url}")
    public abstract String get(@Path("url") String url);

    public static MockService create() {
        return new Retrofit_MockService();
    }

    public static MockService create(Activity activity) {
        return builder().context(activity).build();
    }

    @Retrofit.Builder
    public abstract static class Builder {
        public abstract Builder baseUrl(String baseUrl);
        public abstract Builder converter(retrofit.converter.Converter converter);
        public abstract Builder requestInterceptor(retrofit.RequestInterceptor requestInterceptor);
        public abstract Builder errorHandler(retrofit.ErrorHandler errorHandler);
        public abstract Builder headers(String... headers);
        public abstract Builder retryHeaders(String... headers);
        public abstract Builder logLevel(retrofit.RestAdapter.LogLevel logLevel);
        public abstract Builder context(Object context);
        //public abstract Builder cache(Cache cache);
        //public abstract Builder okHttpClient(OkHttpClient client);
        public abstract MockService build();
    }

    public static Builder builder() {
        return new Retrofit_MockService.Builder();
    }
}
