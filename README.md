
<h1 align="center">AndroidVeil</h1></br>

<p align="center">
<img src="https://user-images.githubusercontent.com/24237865/50557081-fdd3a300-0d24-11e9-82e3-6ddad326cd40.png"/>
</p>
<p align="center">
An easy, flexible way to implement veil skeletons and shimmering effect for Android.
</p><br>

<p align="center">
  <a href="https://opensource.org/licenses/Apache-2.0"><img alt="License" src="https://img.shields.io/badge/License-Apache%202.0-blue.svg"/></a>
  <a href="https://android-arsenal.com/api?level=15"><img alt="API" src="https://img.shields.io/badge/API-15%2B-brightgreen.svg?style=flat"/></a>
  <a href="https://github.com/skydoves/AndroidVeil/actions"><img alt="Build Status" src="https://github.com/skydoves/TransformationLayout/workflows/Android%20CI/badge.svg"/></a> 
  <a href="https://androidweekly.net/issues/issue-334"><img alt="Android Weekly" src="https://img.shields.io/badge/Android%20Weekly-%23334-orange.svg"/></a>
  <a href="https://medium.com/swlh/how-to-implement-veil-skeletons-and-shimmering-effects-to-your-layouts-and-recyclerview-on-android-44af35d90de5"><img alt="Medium" src="https://skydoves.github.io/badges/Story-Medium.svg"/></a>
  <a href="https://skydoves.github.io/libraries/androidveil/javadoc/androidveil/index.html"><img alt="Android Weekly" src="https://img.shields.io/badge/Javadoc-AndroidVeil-yellow.svg"/></a>
</p><br>

<p align="center">
<img src="https://github.com/skydoves/AndroidVeil/blob/master/art/shimmer01.gif" width="32%"/>
<img src="https://github.com/skydoves/AndroidVeil/blob/master/art/shimmer02.gif" width="32%"/>
</p>

## Download
[![Maven Central](https://img.shields.io/maven-central/v/com.github.skydoves/androidveil.svg?label=Maven%20Central)](https://search.maven.org/search?q=g:%22com.github.skydoves%22%20AND%20a:%22androidveil%22)

### Gradle
Add the codes below to your **root** `build.gradle` file (not your module build.gradle file):
```gradle
allprojects {
    repositories {
        mavenCentral()
    }
}
```

And add the dependency below to your **module**'s `build.gradle` file:

```gradle
dependencies {
    implementation "com.github.skydoves:androidveil:1.1.3"
}
```

## Usage
First, add following XML namespace inside your XML layout file:

```gradle
xmlns:app="http://schemas.android.com/apk/res-auto"
```

#### VeilLayout in layout

```gradle
<com.skydoves.androidveil.VeilLayout
      android:id="@+id/veilLayout"
      android:layout_width="match_parent"
      android:layout_height="match_parent"
      app:veilLayout_veiled="true" // shows veils initially
      app:veilLayout_shimmerEnable="true" // sets shimmer enable
      app:veilLayout_baseColor="@android:color/holo_green_dark" // sets shimmer base color
      app:veilLayout_highlightColor="@android:color/holo_green_light" // sets shimmer highlight color
      app:veilLayout_baseAlpha="0.6" // sets shimmer base alpha value
      app:veilLayout_highlightAlpha="1.0" // sets shimmer highlight alpha value
      app:veilLayout_dropOff="0.5"// sets how quickly the shimmer`s gradient drops-off
      app:veilLayout_radius="6dp" // sets a corner radius of the whole veiled items >

      <TextView
         android:layout_width="match_parent"
         android:layout_height="wrap_content"
         android:text="And now here is my secret, a very simple secret"
         android:textColor="@android:color/white"
         android:textSize="22sp"/>

      <!-- Skip -->    

</com.skydoves.androidveil.VeilLayout>
```
#### Veil and UnVeil
We can implement veiled skeletons using below methods.

```java
veilLayout.veil()
veilLayout.unVeil()
```

#### Implement veils using layout resource
We can implement veiled skeletons using the layout resource.

```java
veilLayout.layout = R.layout.layout_item_test
```

## VeilRecyclerFrameView
`VeilRecyclerFrameView` implements veiled skeletons for RecyclerView with the shimmer effect.

#### VeilRecyclerFrameView in layout

```gradle
<com.skydoves.androidveil.VeilRecyclerFrameView
        android:id="@+id/veilRecyclerView"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        app:veilFrame_layout="@layout/item_profile" // sets to make veiling target layout
        app:veilFrame_veiled="true" // shows veils initially
        app:veilFrame_shimmerEnable="true" // sets shimmer enable
        app:veilFrame_baseColor="@android:color/holo_green_dark" // sets shimmer base color
        app:veilFrame_highlightColor="@android:color/holo_green_light" // sets shimmer highlight color
        app:veilFrame_baseAlpha="0.6" // sets shimmer base alpha value
        app:veilFrame_highlightAlpha="1.0" // sets shimmer highlight alpha value
        app:veilFrame_radius="8dp" // sets a corner radius of the whole veiled items 
        app:veilFrame_isItemWrapContentHeight="true"  // sets height of list item wrap_content
        app:veilFrame_isItemWrapContentWidth="true"   // sets width of list item wrap_content />
```

And we should attach our own adapter and LayoutManager.
```java
veilRecyclerView.setAdapter(adapter) // sets your own adapter
veilRecyclerView.setLayoutManager(LinearLayoutManager(this)) // sets LayoutManager
veilRecyclerView.addVeiledItems(15) // add veiled 15 items
```

#### Veil and UnVeil
We can implement veiled skeletons using below methods.

```java
veilRecyclerView.veil() // shows veil skeletons
veilRecyclerView.unVeil() // disappear veils and shows your own recyclerView
```

#### RecyclerView
We can access our Recyclerview and veiledRecyclerView using the  below methods.
```java
veilRecyclerView.getRecyclerView() // veilRecyclerView.getRecyclerView().setHasFixedSize(true)
veilRecyclerView.getVeiledRecyclerView()
```

## Shimmer
This library using [shimmer-android](https://github.com/facebook/shimmer-android) by Facebook.<br>
Here are the detail [shimmer-instruction](http://facebook.github.io/shimmer-android/) about shimmer or you can reference below examples.

### create using Builder
This is how to create `Shimmer`'s instance using `Shimmer.Builder` class.
```kotlin
val shimmer = Shimmer.ColorHighlightBuilder()
      .setBaseColor(ContextCompat.getColor(context, R.color.shimmerBase0))
      .setHighlightColor(ContextCompat.getColor(context, R.color.shimmerHighlight0))
      .setBaseAlpha(1f)
      .setHighlightAlpha(1f)
      .build()

veilLayout.shimmer = shimmer // sets shimmer to VeilLayout
veilRecyclerView.shimmer = shimmer // sets shimmer to VeilRecyclerView
```

### create using kotlin dsl
This is how to create `Shimmer`'s instance using kotlin dsl.
```kotlin
val shimmer_alpha = alphaShimmer {
      setBaseAlpha(1.0f)
      setHighlightAlpha(0.5f)
}
val shimmer_color = colorShimmer {
      setBaseAlpha(1.0f)
      setHighlightAlpha(0.5f)
      setBaseColor(ContextCompat.getColor(context, R.color.colorPrimary))
      setHighlightColor(ContextCompat.getColor(context, R.color.colorPrimaryDark))
}
```

## AndroidVeil Attributes
Attributes | Type | Default | Description
--- | --- | --- | ---
veiled | Boolean | false | shows veils initially.
layout | Int | -1 | implement veils using the layout resource.
radius | dimension | 8dp | sets corner radius to the veil items.
drawable | Drawable | null | sets background drawable to the veil items.
shimmerEnable | Boolean | true | sets shimmer enable.
baseColor | ColorInt | Color.LTGRAY | sets shimmer base color.
highlightColor | ColorInt | Color.DKGRAY | sets shimmer highlight color.
baseAlpha | Float | 1.0f | sets shimmer base alpha value.
highlightAlpha | Float | 1.0f | sets shimmer highlight alpha value.
dropOff | Float | 0.5f | sets how quickly the shimmer's gradient drops-off.
defaultChildVisible | Boolean | false | sets the child view's visibility when called veil and unveil.
isItemWrapContentHeight | Boolean | false | sets height of veiled list item wrap_content
isItemWrapContentWidth | Boolean | false | sets width of veiled list item wrap_content

## Find this repository useful? :heart:
Support it by joining __[stargazers](https://github.com/skydoves/androidveil/stargazers)__ for this repository. :star: <br>
Also __[follow](https://github.com/skydoves)__ me for my next creations! 🤩

# License
```xml
Copyright 2018 skydoves

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
