package com.flipperdevices.archive.impl.fragments

import android.os.Bundle
import androidx.compose.runtime.Composable
import com.flipperdevices.archive.impl.composable.ComposableArchive
import com.flipperdevices.archive.impl.di.ArchiveComponent
import com.flipperdevices.connection.api.ConnectionApi
import com.flipperdevices.core.di.ComponentHolder
import com.flipperdevices.core.ui.ComposeFragment
import javax.inject.Inject

class ArchiveFragment : ComposeFragment() {
    @Inject
    lateinit var connectionApi: ConnectionApi

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ComponentHolder.component<ArchiveComponent>().inject(this)
    }

    @Composable
    override fun RenderView() {
        ComposableArchive(connectionBar = { connectionApi.ConnectionTab() })
    }
}