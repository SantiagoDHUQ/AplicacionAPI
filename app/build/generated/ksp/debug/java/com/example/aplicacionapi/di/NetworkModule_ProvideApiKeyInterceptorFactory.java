package com.example.aplicacionapi.di;

import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import okhttp3.Interceptor;

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
public final class NetworkModule_ProvideApiKeyInterceptorFactory implements Factory<Interceptor> {
  @Override
  public Interceptor get() {
    return provideApiKeyInterceptor();
  }

  public static NetworkModule_ProvideApiKeyInterceptorFactory create() {
    return InstanceHolder.INSTANCE;
  }

  public static Interceptor provideApiKeyInterceptor() {
    return Preconditions.checkNotNullFromProvides(NetworkModule.INSTANCE.provideApiKeyInterceptor());
  }

  private static final class InstanceHolder {
    private static final NetworkModule_ProvideApiKeyInterceptorFactory INSTANCE = new NetworkModule_ProvideApiKeyInterceptorFactory();
  }
}
