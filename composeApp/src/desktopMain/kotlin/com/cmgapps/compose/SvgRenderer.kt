/*
 * Copyright (c) 2025. Christian Grach <christian.grach@cmgapps.com>
 *
 * SPDX-License-Identifier: Apache-2.0
 */

package com.cmgapps.compose

import androidx.compose.runtime.Composable
import androidx.compose.ui.InternalComposeUiApi
import androidx.compose.ui.graphics.asComposeCanvas
import androidx.compose.ui.scene.CanvasLayersComposeScene
import androidx.compose.ui.unit.IntSize
import org.jetbrains.skia.OutputWStream
import org.jetbrains.skia.Rect
import org.jetbrains.skia.svg.SVGCanvas
import java.io.File
import java.io.FileOutputStream

private const val DEFAULT_SVG_WIDTH = 1280
private const val DEFAULT_SVG_HEIGHT = 720

fun svg(
    width: Int = DEFAULT_SVG_WIDTH,
    height: Int = DEFAULT_SVG_HEIGHT,
    outputFilePath: String,
    content: @Composable () -> Unit,
) {
    SvgRenderer(
        width = width,
        height = height,
        outputFilePath = outputFilePath,
    ).use { renderer ->
        renderer.setContent(content)
    }
}

@OptIn(InternalComposeUiApi::class)
private class SvgRenderer(
    width: Int,
    height: Int,
    outputFilePath: String,
) : AutoCloseable {
    private val outputFile = File(outputFilePath)
    private val scene =
        CanvasLayersComposeScene(
            size =
                IntSize(
                    width = width,
                    height = height,
                ),
        )

    fun setContent(content: @Composable () -> Unit) {
        scene.setContent(content)
        FileOutputStream(outputFile).use { fos ->
            OutputWStream(fos).use {
                val screensize = scene.size ?: throw IllegalStateException("Scene size is null")
                SVGCanvas
                    .make(
                        Rect.makeWH(
                            w = screensize.width.toFloat(),
                            h = screensize.height.toFloat(),
                        ),
                        it,
                    ).use { canvas ->
                        scene.render(
                            canvas.asComposeCanvas(),
                            0,
                        )
                    }
            }
        }
    }

    override fun close() {
        scene.close()
    }
}
