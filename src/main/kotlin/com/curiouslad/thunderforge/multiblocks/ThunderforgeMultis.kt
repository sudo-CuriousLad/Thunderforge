package com.curiouslad.thunderforge.multiblocks

import net.minecraft.util.StringIdentifiable

    enum class ThunderforgeMultis : StringIdentifiable {
        UNFORMED {
            override fun asString(): String {
                return "unformed"
            }
        },
        THUNDERFORGE {
            override fun asString(): String {
                return "thunderforge"
            }
        }, ;

    }