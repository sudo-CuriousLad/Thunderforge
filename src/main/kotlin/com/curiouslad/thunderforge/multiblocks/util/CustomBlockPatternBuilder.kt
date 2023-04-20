package com.curiouslad.thunderforge.multiblocks.util

import com.google.common.base.Joiner
import com.google.common.collect.Lists
import com.google.common.collect.Maps
import net.minecraft.block.pattern.CachedBlockPosition
import org.apache.commons.lang3.ArrayUtils
import org.apache.commons.lang3.StringUtils
import java.util.function.Predicate

//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)

class CustomBlockPatternBuilder private constructor() {
    private val aisles: MutableList<Array<out String>> = Lists.newArrayList()
    private val charMap: MutableMap<Char, Predicate<CachedBlockPosition>?> = Maps.newHashMap()
    private var height = 0
    private var width = 0

    init {
        charMap[' '] = Predicate { pos: CachedBlockPosition? -> true }
    }

    fun aisle(vararg pattern: String): CustomBlockPatternBuilder {
        return if (!ArrayUtils.isEmpty(pattern) && !StringUtils.isEmpty(
                pattern[0]
            )
        ) {
            if (aisles.isEmpty()) {
                height = pattern.size
                width = pattern[0].length
            }
            if (pattern.size != height) {
                throw IllegalArgumentException("Expected aisle with height of " + height + ", but was given one with a height of " + pattern.size + ")")
            } else {
                val var2: Array<out String> = pattern
                val var3 = pattern.size
                for (var4 in 0 until var3) {
                    val string = var2[var4]
                    if (string.length != width) {
                        val var10002 = width
                        throw IllegalArgumentException("Not all rows in the given aisle are the correct width (expected " + var10002 + ", found one with " + string.length + ")")
                    }
                    val var6 = string.toCharArray()
                    val var7 = var6.size
                    for (var8 in 0 until var7) {
                        val c = var6[var8]
                        if (!charMap.containsKey(c)) {
                            charMap[c] = null
                        }
                    }
                }
                aisles.add(pattern)
                this
            }
        } else {
            throw IllegalArgumentException("Empty pattern for aisle")
        }
    }

    fun where(key: Char, predicate: Predicate<CachedBlockPosition>?): CustomBlockPatternBuilder {
        charMap[key] = predicate
        return this
    }

    fun build(): CustomBlockPattern {
        return CustomBlockPattern(bakePredicates())
    }

    private fun bakePredicates(): Array<Array<Array<Predicate<CachedBlockPosition>?>>> {
        validate()
        val predicates: Array<Array<Array<Predicate<CachedBlockPosition>?>>> = java.lang.reflect.Array.newInstance(
            Predicate::class.java, *intArrayOf(aisles.size, height, width)
        ) as Array<Array<Array<Predicate<CachedBlockPosition>?>>>
        for (i in aisles.indices) {
            for (j in 0 until height) {
                for (k in 0 until width) {
                    predicates[i][j][k] = charMap[aisles[i][j][k]]
                }
            }
        }
        return predicates
    }

    private fun validate() {
        val list: MutableList<Char> = Lists.newArrayList()
        val var2: Iterator<*> = charMap.entries.iterator()
        while (var2.hasNext()) {
            val (key, value) = var2.next() as Map.Entry<*, *>
            if (value == null) {
                list.add(key as Char)
            }
        }
        check(list.isEmpty()) { "Predicates for character(s) " + JOINER.join(list) + " are missing" }
    }

    companion object {
        private val JOINER = Joiner.on(",")
        fun start(): CustomBlockPatternBuilder {
            return CustomBlockPatternBuilder()
        }
    }
}
