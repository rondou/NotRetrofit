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

import retrofit.RequestInterceptor;
import retrofit.http.Retrofit.RequestAuthenticator;
import java.util.Collection;
import java.util.List;
import java.util.ArrayList;
import android.content.Context;
import android.text.TextUtils;
import retrofit.RequestInterceptor.RequestFacade;
import android.accounts.AccountManager;
import android.accounts.Account;
import android.accounts.AccountManagerFuture;
import android.app.Activity;
import android.os.Bundle;
import rx.Observable;
import rx.functions.*;
import com.squareup.okhttp.OkHttpClient;
import retrofit.http.Retrofit.*;

public class GitHubOkHttpClienter implements OkHttpClienter {
    @Override public OkHttpClient get() {
        return new OkHttpClient();
    }
}
