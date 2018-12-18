package com.fastsoft.testcurrencyexchange.di;

import com.fastsoft.testcurrencyexchange.di.annotaion.ActivityScope;
import com.fastsoft.testcurrencyexchange.di.annotaion.FragmentScope;
import com.fastsoft.testcurrencyexchange.presentation.graph.GraphActivity;
import com.fastsoft.testcurrencyexchange.presentation.graph.GraphView;
import com.fastsoft.testcurrencyexchange.presentation.main.MainActivity;
import com.fastsoft.testcurrencyexchange.presentation.main.MainView;
import com.fastsoft.testcurrencyexchange.presentation.main.nbu.NBUFragment;
import com.fastsoft.testcurrencyexchange.presentation.main.nbu.NBUView;
import com.fastsoft.testcurrencyexchange.presentation.main.privat.PrivatFragment;
import com.fastsoft.testcurrencyexchange.presentation.main.privat.PrivateView;

import dagger.Binds;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public interface AndroidBindingModule {
    @ActivityScope
    @ContributesAndroidInjector
    MainActivity getMainActivity();
    @ActivityScope
    @Binds
    MainView getMainView(MainActivity settingsActivity);

    @FragmentScope
    @ContributesAndroidInjector
    NBUFragment getNBUFragment();
    @FragmentScope
    @Binds
    NBUView getNBUView(NBUFragment nbuFragment);

    @FragmentScope
    @ContributesAndroidInjector
    PrivatFragment getPrivatFragment();
    @FragmentScope
    @Binds
    PrivateView getPrivateView(PrivatFragment privatFragment);

    @ActivityScope
    @ContributesAndroidInjector
    GraphActivity getGraphActivity();
    @ActivityScope
    @Binds
    GraphView getGraphView(GraphActivity settingsActivity);

}