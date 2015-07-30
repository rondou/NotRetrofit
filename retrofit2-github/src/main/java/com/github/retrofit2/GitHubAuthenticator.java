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
import rx.android.schedulers.AndroidSchedulers;
import rx.Observable;
import rx.functions.*;

public class GitHubAuthenticator implements RequestAuthenticator {
    // TODO persistents
    String token;
    List<String> activePermissions;

    @Override
    public void intercept(RequestFacade request) {
        System.out.println("retrogithub: intercept");
        if (token != null) request.addHeader("Authorization", "Bearer " + token);
    }

    @Override
    public String authorize(Object context, Collection<String> permissions) {
        System.out.println("retrogithub: authorize");
        List<String> neededPermissions = new ArrayList<>(permissions);
        if (token != null) {
            neededPermissions.removeAll(activePermissions);
            if (neededPermissions.isEmpty()) return token;
        }
        token = getAuthToken((Activity) context, permissions);
        if (token != null) {
            activePermissions = new ArrayList<>(permissions);
        }
        return token;
    }

    public String getAuthToken(Activity activity, Collection<String> permissions) {
        System.out.println("retrogithub: getAuthToken");
        AccountManager accountManager = AccountManager.get(activity);
        String accountType = "com.github";
        //String authTokenType = TextUtils.join(",", permissions);
        String authTokenType = accountType;
        Account account = getAccount(accountManager, accountType);
        System.out.println("retrogithub: account: " + account);
        AccountManagerFuture<Bundle> bundleTask = null;
        if (account == null) {
            System.out.println("retrogithub: addAccount");
            //bundleTask = accountManager.addAccount(accountType, authTokenType, permissions.toArray(new String[permissions.size()]), null, activity, null, null);
            bundleTask = accountManager.addAccount(accountType, authTokenType, null, null, activity, null, null);
        } else {
            System.out.println("retrogithub: getAuthToken");
            bundleTask = accountManager.getAuthToken(account, authTokenType, null, activity, null, null);
        }

        if (bundleTask == null) {
            System.out.println("retrogithub: bundleTask: " + bundleTask);
            return null;
        }

        final AccountManagerFuture<Bundle> finalBundleTask = bundleTask;
        Bundle tokenBundle = null;
        try {
            System.out.println("retrogithub: tokenBundle");
            tokenBundle = finalBundleTask.getResult();
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("retrogithub: tokenBundle: " + tokenBundle);
        if (tokenBundle == null) return null;

        System.out.println("retrogithub: AccountManager.KEY_AUTHTOKEN");
        return tokenBundle.getString(AccountManager.KEY_AUTHTOKEN);
    }

    public Account getAccount(AccountManager accountManager, String accountType) {
        System.out.println("retrogithub: getAccount");
        Account[] accounts = accountManager.getAccountsByType(accountType);
        if (accounts.length > 0) return accounts[0];
        return null; // not found
    }

    public String getToken() {
        return token;
    }
}
