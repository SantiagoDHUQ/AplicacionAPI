package com.example.aplicacionapi.ui.home;

import com.example.aplicacionapi.domain.repository.MovieRepository;
import com.example.aplicacionapi.util.NetworkObserver;
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
public final class HomeViewModel_Factory implements Factory<HomeViewModel> {
  private final Provider<MovieRepository> repositoryProvider;

  private final Provider<NetworkObserver> networkObserverProvider;

  public HomeViewModel_Factory(Provider<MovieRepository> repositoryProvider,
      Provider<NetworkObserver> networkObserverProvider) {
    this.repositoryProvider = repositoryProvider;
    this.networkObserverProvider = networkObserverProvider;
  }

  @Override
  public HomeViewModel get() {
    return newInstance(repositoryProvider.get(), networkObserverProvider.get());
  }

  public static HomeViewModel_Factory create(Provider<MovieRepository> repositoryProvider,
      Provider<NetworkObserver> networkObserverProvider) {
    return new HomeViewModel_Factory(repositoryProvider, networkObserverProvider);
  }

  public static HomeViewModel newInstance(MovieRepository repository,
      NetworkObserver networkObserver) {
    return new HomeViewModel(repository, networkObserver);
  }
}
