package com.curiouslad.thunderforge.multiblocks.util

import com.google.common.annotations.VisibleForTesting
import com.google.common.base.MoreObjects
import com.google.common.cache.CacheBuilder
import com.google.common.cache.CacheLoader
import com.google.common.cache.LoadingCache
import net.minecraft.block.pattern.CachedBlockPosition
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Direction
import net.minecraft.util.math.Vec3i
import net.minecraft.world.WorldView
import java.util.function.Predicate
class CustomBlockPattern(@get:VisibleForTesting val pattern: Array<Array<Array<Predicate<CachedBlockPosition>?>>>) {
    val depth: Int
    var height = 0
    var width = 0
    var posArray: Array<BlockPos> = arrayOf()

    init {
        depth = pattern.size
        if (depth > 0) {
            height = pattern[0].size
            if (height > 0) {
                width = pattern[0][0].size
            } else {
                width = 0
            }
        } else {
            height = 0
            width = 0
        }
    }

    @VisibleForTesting
    fun testTransform(
        world: WorldView?,
        frontTopLeft: BlockPos,
        forwards: Direction,
        up: Direction
    ): Result? {
        val loadingCache: LoadingCache<BlockPos, CachedBlockPosition?> = makeCache(world, false)
        return this.testTransform(frontTopLeft, forwards, up, loadingCache)
    }

    private fun testTransform(
        frontTopLeft: BlockPos,
        forwards: Direction,
        up: Direction,
        cache: LoadingCache<BlockPos, CachedBlockPosition?>
    ): Result? {
        for (i in 0 until width) {
            for (j in 0 until height) {
                for (k in 0 until depth) {
                    if (!pattern[k][j][i]?.test(
                            cache.getUnchecked(
                                CustomBlockPattern.Companion.translate(
                                    frontTopLeft,
                                    forwards,
                                    up,
                                    i,
                                    j,
                                    k
                                )
                            ) as CachedBlockPosition
                        )!!
                    ) {
                        return null
                    }
                }
            }
        }
        return Result(
            frontTopLeft, forwards, up, cache,
            width, height, depth
        )
    }

    fun searchAround(world: WorldView?, pos: BlockPos): Result? {
        val loadingCache: LoadingCache<BlockPos, CachedBlockPosition?> = makeCache(world, false)
        val i = Math.max(Math.max(width, height), depth)
        val var5: Iterator<*> = BlockPos.iterate(pos, pos.add(i - 1, i - 1, i - 1)).iterator()
        while (var5.hasNext()) {
            val blockPos = var5.next() as BlockPos
            val var7 = Direction.values()
            val var8 = var7.size
            for (var9 in 0 until var8) {
                val direction = var7[var9]
                val var11 = Direction.values()
                val var12 = var11.size
                for (var13 in 0 until var12) {
                    val direction2 = var11[var13]
                    if (direction2 != direction && direction2 != direction.opposite) {
                        val result = this.testTransform(blockPos, direction, direction2, loadingCache)
                        posArray.plus(blockPos)
                        if (result != null) {
                            return result
                        }
                    }
                }
            }
        }
        return null
    }

    class Result(
        val frontTopLeft: BlockPos,
        val forwards: Direction,
        val up: Direction,
        private val cache: LoadingCache<BlockPos, CachedBlockPosition?>,
        val width: Int,
        val height: Int,
        val depth: Int
    ) {

        fun translate(offsetLeft: Int, offsetDown: Int, offsetForwards: Int): CachedBlockPosition {
            return cache.getUnchecked(
                CustomBlockPattern.Companion.translate(
                    frontTopLeft,
                    forwards,
                    up, offsetLeft, offsetDown, offsetForwards
                )
            ) as CachedBlockPosition
        }

        override fun toString(): String {
            return MoreObjects.toStringHelper(this).add("up", up).add("forwards", forwards).add(
                "frontTopLeft",
                frontTopLeft
            ).toString()
        }
    }

    private class BlockStateCacheLoader(private val world: WorldView, private val forceLoad: Boolean) :
        CacheLoader<BlockPos, CachedBlockPosition?>() {
        override fun load(blockPos: BlockPos): CachedBlockPosition {
            return CachedBlockPosition(world, blockPos, forceLoad)
        }
    }

    companion object {
        fun makeCache(world: WorldView?, forceLoad: Boolean): LoadingCache<BlockPos, CachedBlockPosition?> {
            return CacheBuilder.newBuilder().build(CustomBlockPattern.BlockStateCacheLoader(world!!, forceLoad))
        }

        protected fun translate(
            pos: BlockPos,
            forwards: Direction,
            up: Direction,
            offsetLeft: Int,
            offsetDown: Int,
            offsetForwards: Int
        ): BlockPos {
            return if (forwards != up && forwards != up.opposite) {
                val vec3i = Vec3i(forwards.offsetX, forwards.offsetY, forwards.offsetZ)
                val vec3i2 = Vec3i(up.offsetX, up.offsetY, up.offsetZ)
                val vec3i3 = vec3i.crossProduct(vec3i2)
                pos.add(
                    vec3i2.x * -offsetDown + vec3i3.x * offsetLeft + vec3i.x * offsetForwards,
                    vec3i2.y * -offsetDown + vec3i3.y * offsetLeft + vec3i.y * offsetForwards,
                    vec3i2.z * -offsetDown + vec3i3.z * offsetLeft + vec3i.z * offsetForwards
                )
            } else {
                throw IllegalArgumentException("Invalid forwards & up combination")
            }
        }
    }
}
