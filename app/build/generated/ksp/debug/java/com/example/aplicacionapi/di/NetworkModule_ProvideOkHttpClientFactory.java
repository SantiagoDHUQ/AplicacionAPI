package com.example.aplicacionapi.di;

import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;

@ScopeMetadata("javax.inject.Singleton")
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
public final class NetworkModule_ProvideOkHttpClientFactory implements Factory<OkHttpClient> {
  private final Provider<Interceptor> apiKeyInterceptorProvider;

  public NetworkModule_ProvideOkHttpClientFactory(Provider<Interceptor> apiKeyInterceptorProvider) {
    this.apiKeyInterceptorProvider = apiKeyInterceptorProvider;
  }

  @Override
  public OkHttpClient get() {
    return provideOkHttpClient(apiKeyInterceptorProvider.get());
  }

  public static NetworkModule_ProvideOkHttpClientFactory create(
      Provider<Interceptor> apiKeyInterceptorProvider) {
    return new NetworkModule_ProvideOkHttpClientFactory(apiKeyInterceptorProvider);
  }

  public static OkHttpClient provideOkHttpClient(Interceptor apiKeyInterceptor) {
    return Preconditions.checkNotNullFromProvides(NetworkModule.INSTANCE.provideOkHttpClient(apiKeyInterceptor));
  }
}
