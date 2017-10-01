/*
 * Copyright (C) 2017 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.coprorated.amizaar.myarchitecturedtest.repository;

import android.support.annotation.NonNull;

import com.coprorated.amizaar.myarchitecturedtest.data.Response;

import io.reactivex.Observable;

/**
 * A generic class that can provide a resource backed by both the sqlite database and the network.
 * <p>
 * You can read more about it in the <a href="https://developer.android.com/arch">Architecture
 * Guide</a>.
 * @param <ResultType>
 * @param <RequestType>
 */
public abstract class NetworkBoundResource<ResultType, RequestType> {
    private Observable<Response<ResultType>> result;

    NetworkBoundResource() {
        result = createCall()
                .map(response -> Response.success(processResponse(response)))
                .doOnNext(apiResponse -> {
                    saveCallResult(apiResponse.data);
                })
                .onErrorResumeNext(throwable -> {
                    ResultType result = loadFromDb();
                    Response<ResultType> response = Response.error(throwable, result);
                    return Observable.just(response);
                });
    }

    @NonNull
    protected abstract Observable<RequestType> createCall();

    @NonNull
    protected abstract ResultType processResponse(RequestType response);

    protected abstract void saveCallResult(ResultType result);

    @NonNull
    protected abstract ResultType loadFromDb();

    protected Observable<Response<ResultType>> asObservable() {
        return result;
    }
}
