package com.example.aplicacionapi.data.repository;

import com.example.aplicacionapi.data.api.TmdbApiService;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata
@QualifierMetadata
@DaggerGenerated
@Generated(
    value = "dagger.internal.codegen.ComponentProcessor",
    comments = "https://dagger.dev"
)
@SuppressWarnings({
    "unchecked",
    "rawtypes",
    "KotlinInternal",
    "KotlinInternalInJava",
    "cast"
})
public final class MovieRepositoryImpl_Factory implements Factory<MovieRepositoryImpl> {
  private final Provider<TmdbApiService> apiServiceProvider;

  public MovieRepositoryImpl_Factory(Provider<TmdbApiService> apiServiceProvider) {
    this.apiServiceProvider = apiServiceProvider;
  }

  @Override
  public MovieRepositoryImpl get() {
    return newInstance(apiServiceProvider.get());
  }

  public static MovieRepositoryImpl_Factory create(Provider<TmdbApiService> apiServiceProvider) {
    return new MovieRepositoryImpl_Factory(apiServiceProvider);
  }

  public static MovieRepositoryImpl newInstance(TmdbApiService apiService) {
    return new MovieRepositoryImpl(apiService);
  }
}
