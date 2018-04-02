package com.alex.kotlin.test.repo

import com.alex.kotlin.test.repo.db.LocalDataSource
import com.alex.kotlin.test.repo.remote.RemoteDataSource
import com.alex.kotlin.test.repo.socket.SocketDataSource
import com.alex.kotlin.test.repo.sp.SpDataSource


interface DataSource : /*RemoteDataSource,*/  /*LocalDataSource,*/ SpDataSource, SocketDataSource{


}
