/*
 * Copyright 2018, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package com.example.android.marsrealestate.network

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.Deferred
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET

//Root web address of the Mars server endpoint
private const val BASE_URL = "https://mars.udacity.com/"

//Moshi is a modern JSON library for Android and Kotlin. It makes it easy to parse JSON into Kotlin
// objects
private val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

//Retrofit object
private val retrofit = Retrofit.Builder()
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        //replacing the callback and interfaces with coroutines
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .baseUrl(BASE_URL)
        .build()

//interface, and define a getProperties() method to request the JSON response list of Mars
// properties
interface MarsApiService {
    @GET("realestate")
    //Deferred -> It's a kind of coroutines job that can directly return a result
    fun getPropertiesAsync(): Deferred<List<MarsProperty>>
}

//MarsApi to expose the Retrofit service to the rest of the app
object MarsApi {
    val retrofitService: MarsApiService by lazy {
        retrofit.create(MarsApiService::class.java)
    }
}