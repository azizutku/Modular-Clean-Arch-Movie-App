package com.azizutku.movie.di

import android.app.Dialog
import com.azizutku.movie.presentation.ui.dialogs.AlertDialog
import com.azizutku.movie.presentation.ui.dialogs.LoadingDialog
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.scopes.ActivityScoped

@Module
@InstallIn(ActivityComponent::class)
abstract class DialogModule {
    @ActivityScoped
    @Binds
    abstract fun bindLoadingDialog(impl: LoadingDialog): Dialog

    @ActivityScoped
    @Binds
    abstract fun bindAlertDialog(impl: AlertDialog): Dialog
}
