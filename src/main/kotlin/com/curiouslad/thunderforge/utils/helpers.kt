package com.curiouslad.thunderforge.utils

import net.minecraft.util.Identifier

fun getIdentifier(name: String): Identifier? {
    return Identifier.of(name, "thunderforge")
}