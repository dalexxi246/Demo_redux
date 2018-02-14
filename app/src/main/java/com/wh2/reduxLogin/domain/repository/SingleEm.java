package com.wh2.reduxLogin.domain.repository;

import android.text.method.SingleLineTransformationMethod;

import com.wh2.reduxLogin.domain.model.User;

import io.reactivex.Single;

public class SingleEm {

    public SingleEm() {
        Single<User> single = Single.create(e -> {
            e.onSuccess(new User());
        });
    }
}
