package com.alex.kotlin.test.util

import android.content.Context
import com.alex.kotlin.test.repo.Repository
import com.alex.kotlin.test.repo.db.LocalRepository
import com.alex.kotlin.test.repo.remote.RemoteRepository
import com.alex.kotlin.test.repo.socket.SocketRepo
import com.alex.kotlin.test.repo.sp.SharedPrefRepo


object Injection {
    fun provideRepository(context: Context): Repository {
        return Repository.getInstance(RemoteRepository, LocalRepository, SharedPrefRepo, SocketRepo)
    }
}
