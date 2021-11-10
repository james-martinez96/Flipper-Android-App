package com.flipperdevices.bridge.api.model

import com.flipperdevices.protobuf.Flipper

data class FlipperRequest(
    val data: Flipper.Main,
    val priority: FlipperRequestPriority = FlipperRequestPriority.DEFAULT
)

fun Flipper.Main.wrapToRequest(
    priority: FlipperRequestPriority = FlipperRequestPriority.DEFAULT
) = FlipperRequest(data = this, priority = priority)