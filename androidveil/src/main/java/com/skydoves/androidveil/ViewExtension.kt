/*
 * Copyright (C) 2018 skydoves
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

import android.view.View

/** Makes visible a view. */
@JvmSynthetic
internal fun View.visible() {
  this.visibility = View.VISIBLE
}

@JvmSynthetic
internal fun View.gone() {
  this.visibility = View.GONE
}

/** Makes invisible a view. */
@JvmSynthetic
internal fun View.invisible() {
  this.visibility = View.INVISIBLE
}

/** makes visible or invisible a View align the value parameter. */
@JvmSynthetic
internal fun View.visible(visible: Boolean) {
  if (visible) {
    visible()
  } else {
    invisible()
  }
}
