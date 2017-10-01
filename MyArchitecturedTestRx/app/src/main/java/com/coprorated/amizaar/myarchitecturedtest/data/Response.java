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

package com.coprorated.amizaar.myarchitecturedtest.data;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Common class used by API responses.
 * @param <T>
 */
public class Response<T> {
    @NonNull
    public final Status status;

    @Nullable
    public final T data;

    @Nullable
    public final Throwable throwable;

    private Response(@NonNull Status status, @Nullable T data, @Nullable Throwable throwable) {
        this.status = status;
        this.data = data;
        this.throwable = throwable;
    }

    public static <T> Response<T> success(@Nullable T data) {
        return new Response<>(Status.SUCCESS, data, null);
    }

    public static <T> Response<T> error(Throwable throwable, @Nullable T data) {
        return new Response<>(Status.ERROR, data, throwable);
    }

    public static <T> Response<T> error(Throwable throwable) {
        return error(throwable, null);
    }
}
