<p align="center">
<img src="https://user-images.githubusercontent.com/24237865/50557081-fdd3a300-0d24-11e9-82e3-6ddad326cd40.png"/>
</p>
<p align="center">
An easy, flexible way to implement veil skeletons and shimmering effect for Android.
</p>

<p align="center">
  <a href="https://opensource.org/licenses/Apache-2.0"><img alt="License" src="https://img.shields.io/badge/License-Apache%202.0-blue.svg"/></a>
  <a href="https://android-arsenal.com/api?level=15"><img alt="API" src="https://img.shields.io/badge/API-15%2B-brightgreen.svg?style=flat"/></a>
  <a href="https://travis-ci.org/skydoves/AndroidVeil"><img alt="Build Status" src="https://travis-ci.org/skydoves/AndroidVeil.svg?branch=master"/></a>
  <a href="https://androidweekly.net/issues/issue-334"><img alt="Android Weekly" src="https://img.shields.io/badge/Android%20Weekly-%23334-orange.svg"/></a>
    <a href="https://skydoves.github.io/libraries/androidveil/javadoc/androidveil/index.html"><img alt="Android Weekly" src="https://img.shields.io/badge/Javadoc-AndroidVeil-yellow.svg"/></a>
</p>


# AndroidVeil
![gif0](https://user-images.githubusercontent.com/24237865/47777293-c736d780-dd37-11e8-9107-5242746a7b8c.gif)
![gif1](https://user-images.githubusercontent.com/24237865/47781860-1aae2300-dd42-11e8-8c5c-a2417b03b74d.gif)

## Download
[![Download](https://api.bintray.com/packages/devmagician/maven/androidveil/images/download.svg)](https://bintray.com/devmagician/maven/androidveil/_latestVersion)
[![Jitpack](https://jitpack.io/v/skydoves/AndroidVeil.svg)](https://jitpack.io/#skydoves/AndroidVeil)
### Gradle
Add below codes to your **root** `build.gradle` file (not your module build.gradle file).
```gradle
allprojects {
    repositories {
        jcenter()
    }
}
```

And add a dependency code to your **module**'s `build.gradle` file.
```gradle
dependencies {
    implementation "com.github.skydoves:androidveil:1.0.5"
}
```

## Usage
First, add following XML namespace inside your XML layout file.

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
      app:veilLayout_dropOff="0.5"// sets how quickly the shimmer`s gradient drops-off.>

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
        app:veilFrame_highlightAlpha="1.0" // sets shimmer highlight alpha value />
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
shimmerEnable | Boolean | true | sets shimmer enable.
baseColor | ColorInt | Color.LTGRAY | sets shimmer base color.
highlightColor | ColorInt | Color.DKGRAY | sets shimmer highlight color.
baseAlpha | Float | 1.0f | sets shimmer base alpha value.
highlightAlpha | Float | 1.0f | sets shimmer highlight alpha value.
dropOff | Float | 0.5f | sets how quickly the shimmer's gradient drops-off.

## Find this library useful? :heart:
Support it by joining __[stargazers](https://github.com/skydoves/AndroidVeil/stargazers)__ for this repository. :star:

## Supports :coffee:
If you feel like support me a coffee for my efforts, I would greatly appreciate it. <br><br>
<a href="https://www.buymeacoffee.com/skydoves" target="_blank"><img src="https://www.buymeacoffee.com/assets/img/custom_images/purple_img.png" alt="Buy Me A Coffee" style="height: auto !important;width: auto !important;" ></a>

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
