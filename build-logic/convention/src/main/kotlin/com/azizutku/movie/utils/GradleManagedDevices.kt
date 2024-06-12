package com.azizutku.movie.utils

import com.android.build.api.dsl.CommonExtension
import com.android.build.api.dsl.ManagedVirtualDevice
import com.azizutku.movie.extensions.capitalize
import org.gradle.kotlin.dsl.get
import org.gradle.kotlin.dsl.invoke
import java.util.Locale

private const val API_LEVEL_30 = 30
private const val API_LEVEL_31 = 31

internal fun configureGradleManagedDevices(
    commonExtension: CommonExtension<*, *, *, *, *, *>,
) {
    val deviceConfigs = listOf(
        DeviceConfig("Pixel 4", API_LEVEL_30, "aosp-atd"),
        DeviceConfig("Pixel 4", API_LEVEL_31, "aosp"),
        DeviceConfig("Pixel 4", API_LEVEL_31, "google", groups = listOf("phoneAndTablet")),
        DeviceConfig("Nexus 9", API_LEVEL_31, "google", groups = listOf("phoneAndTablet")),
    )
    val groups = mutableMapOf<String, List<DeviceConfig>>()
    deviceConfigs.forEach { deviceConfig ->
        deviceConfig.groups?.forEach { group ->
            groups[group] = groups[group].orEmpty() + deviceConfig
        }
    }
    commonExtension.testOptions {
        managedDevices {
            devices {
                deviceConfigs.forEach { deviceConfig ->
                    maybeCreate(deviceConfig.taskName, ManagedVirtualDevice::class.java).apply {
                        device = deviceConfig.device
                        apiLevel = deviceConfig.apiLevel
                        systemImageSource = deviceConfig.systemImageSource
                    }
                }
            }
            groups {
                groups.forEach { group ->
                    maybeCreate(group.key).apply {
                        group.value.forEach { deviceConfig ->
                            targetDevices.add(devices[deviceConfig.taskName])
                        }
                    }
                }
            }
        }
    }
}

private data class DeviceConfig(
    val device: String,
    val apiLevel: Int,
    val systemImageSource: String,
    val groups: List<String>? = null,
) {
    val taskName = buildString {
        append(device.lowercase(Locale.ROOT).replace(" ", ""))
        append("Api")
        append(apiLevel.toString())
        append(systemImageSource.capitalize(Locale.ROOT).replace("-", ""))
    }
}
