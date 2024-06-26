/*
 * Designed and developed by 2018 skydoves (Jaewoong Eum)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.skydoves.androidveil

import android.graphics.drawable.Drawable
import androidx.annotation.ColorInt
import androidx.annotation.FloatRange
import androidx.annotation.Px
import com.facebook.shimmer.Shimmer

internal data class VeilParams(
  @ColorInt var baseColor: Int,
  @ColorInt var highlightColor: Int,
  var drawable: Drawable?,
  @Px var radius: Float,
  @FloatRange(from = 0.0, to = 1.0) var baseAlpha: Float,
  @FloatRange(from = 0.0, to = 1.0) var highlightAlpha: Float,
  var dropOff: Float,
  var shimmerEnable: Boolean,
  var shimmer: Shimmer?,
  var defaultChildVisible: Boolean,
)
