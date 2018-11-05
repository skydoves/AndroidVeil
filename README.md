# AndroidVeil
[![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](https://opensource.org/licenses/Apache-2.0)
[![API](https://img.shields.io/badge/API-15%2B-brightgreen.svg?style=flat)](https://android-arsenal.com/api?level=15) 
[![Build Status](https://travis-ci.org/skydoves/AndroidVeil.svg?branch=master)](https://travis-ci.org/skydoves/AndroidVeil) 
[![Android Weekly](https://img.shields.io/badge/Android%20Weekly-%23334-orange.svg)](https://androidweekly.net/issues/issue-334) <br>
A library that let you implement skeletons and shimmer animation for veiling layouts and recyclerView.

![gif0](https://user-images.githubusercontent.com/24237865/47777293-c736d780-dd37-11e8-9107-5242746a7b8c.gif)
![gif1](https://user-images.githubusercontent.com/24237865/47781860-1aae2300-dd42-11e8-8c5c-a2417b03b74d.gif)

## Download
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
    implementation "com.github.skydoves:androidveil:1.0.2"
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
      app:veilLayout_veiled="true" // showing veils initially
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
You can implement veil skeletons using below methods.

```java
veilLayout.veil()
veilLayout.unVeil()
```

#### Implement veils using layout resource
You can implement veils using the layout resource without any child.

```java
veilLayout.layout = R.layout.layout_item_test
```

## VeilRecyclerFrameView
`VeilRecyclerFrameView` lets implement skeletons and shimmer animation about RecyclerView.

#### VeilRecyclerFrameView in layout

```gradle
<com.skydoves.androidveil.VeilRecyclerFrameView
        android:id="@+id/veilRecyclerView"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        app:veilFrame_layout="@layout/item_profile" // sets to make veiling target layout
        app:veilFrame_veiled="true" // sets shimmer enable
        app:veilFrame_shimmerEnable="true" // sets shimmer enable
        app:veilFrame_baseColor="@android:color/holo_green_dark" // sets shimmer base color
        app:veilFrame_highlightColor="@android:color/holo_green_light" // sets shimmer highlight color
        app:veilFrame_baseAlpha="0.6" // sets shimmer base alpha value
        app:veilFrame_highlightAlpha="1.0" // sets shimmer highlight alpha value />
```

And you should attach your own adapter and LayoutManager.
```java
veilRecyclerView.setAdapter(adapter) // sets your own adapter
veilRecyclerView.setLayoutManager(LinearLayoutManager(this)) // sets LayoutManager
veilRecyclerView.addVeiledItems(15) // add veiled 15 items
```

#### Veil and UnVeil
You can implement veil skeletons using below methods.

```java
veilRecyclerView.veil() // shows veil skeletons
veilRecyclerView.unVeil() // disappear veils and shows your own recyclerView
```

#### RecyclerView
You can access recyclerview and veiledRecyclerView using below methods.
```java
veilRecyclerView.getRecyclerView() // veilRecyclerView.getRecyclerView().setHasFixedSize(true)
veilRecyclerView.getVeiledRecyclerView()
```

## Shimmer
This library is used [shimmer-android](https://github.com/facebook/shimmer-android) by Facebook.<br>
So you can customizing shimmer following by [shimmer-instruction](http://facebook.github.io/shimmer-android/) or following example.

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
