/*
 * Copyright (c) 2025. Christian Grach <christian.grach@cmgapps.com>
 *
 * SPDX-License-Identifier: Apache-2.0
 */

package com.cmgapps.compose

import androidx.compose.ui.InternalComposeUiApi
import androidx.compose.ui.graphics.asComposeCanvas
import androidx.compose.ui.scene.CanvasLayersComposeScene
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.window.application
import org.jetbrains.skia.OutputWStream
import org.jetbrains.skia.Rect
import org.jetbrains.skia.svg.SVGCanvas
import java.io.File

const val CANVAS_WIDTH = 1280
const val CANVAS_HEIGHT = 720

@OptIn(InternalComposeUiApi::class)
fun main(args: Array<String>) =
    application {
        val outputFilePath = args.firstOrNull() ?: "./output.svg"
        val outFile = File(outputFilePath)
        outFile.outputStream().use { fos ->
            OutputWStream(fos).use {
                val scene =
                    CanvasLayersComposeScene(
                        size =
                            IntSize(
                                width = CANVAS_WIDTH,
                                height = CANVAS_HEIGHT,
                            ),
                    )
                scene.setContent {
                    App()
                }

                SVGCanvas
                    .make(
                        Rect.makeWH(
                            w = CANVAS_WIDTH.toFloat(),
                            h = CANVAS_HEIGHT.toFloat(),
                        ),
                        it,
                    ).use { canvas ->
                        scene.render(
                            canvas.asComposeCanvas(),
                            0,
                        )
                    }
                scene.close()
            }
        }
    }
