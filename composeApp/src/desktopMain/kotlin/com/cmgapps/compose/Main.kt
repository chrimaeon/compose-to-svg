/*
 * Copyright (c) 2025. Christian Grach <christian.grach@cmgapps.com>
 *
 * SPDX-License-Identifier: Apache-2.0
 */

package com.cmgapps.compose

import java.awt.Toolkit

fun main(args: Array<String>) {
    val screenSize = Toolkit.getDefaultToolkit().screenSize
    svg(
        width = screenSize.width,
        height = screenSize.height,
        outputFilePath = args.firstOrNull() ?: "compose.svg",
    ) {
        App()
    }
}
