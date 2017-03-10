# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in /Users/Terato/Library/Android/sdk/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}
-useuniqueclassmembernames
-mergeinterfacesaggressively
-allowaccessmodification

#########--------Android Support--------#########
-keep class android.support.v4.app.** { *; }
-keep interface android.support.v4.app.** { *; }
-dontwarn android.support.**

#########--------Webview--------#########
-keepclassmembers class * {
    @android.webkit.JavascriptInterface <methods>;
}

#########--------Remove Log--------#########
-assumenosideeffects class android.util.Log { *; }

#########--------Retrofit + RxJava--------#########
-dontwarn okio.**
-dontwarn java.lang.invoke.*
-dontwarn retrofit2.Platform$Java8
-keep class retrofit.** { *; }
-keep class retrofit2.** { *; }
-keepattributes Signature
-keepattributes Exceptions
-keepclasseswithmembers class * {
    @retrofit.http.* <methods>;
}
-keepclasseswithmembers interface * {
    @retrofit.http.* <methods>;
}
-keepclasseswithmembers interface * {
    @retrofit2.* <methods>;
}

#
-keep class com.google.gson.** { *; }
-keep class com.google.inject.** { *; }
-keep class javax.inject.** { *; }

-dontwarn sun.misc.**

-keep class rx.schedulers.Schedulers {
    public static <methods>;
}
-keep class rx.schedulers.ImmediateScheduler {
    public <methods>;
}
-keep class rx.schedulers.TestScheduler {
    public <methods>;
}
-keep class rx.schedulers.Schedulers {
    public static ** test();
}
-keepclassmembers class rx.internal.util.unsafe.*ArrayQueue*Field* {
    long producerIndex;
    long consumerIndex;
}
-keepclassmembers class rx.internal.util.unsafe.BaseLinkedQueueProducerNodeRef {
    long producerNode;
    long consumerNode;
}

##### ICEPICK ######
-dontwarn icepick.**
-keep class icepick.** { *; }
-keep class **$$Icepick { *; }
-keepclasseswithmembernames class * {
    @icepick.* <fields>;
}
-keepnames class * { @icepick.State *;}


###### BUTTER KNIFE ######
# Retain generated class which implement Unbinder.
-keep public class * implements butterknife.Unbinder { public <init>(**, android.view.View); }

# Prevent obfuscation of types which use ButterKnife annotations since the simple name
# is used to reflectively look up the generated ViewBinding.
-keep class butterknife.*
-keepclasseswithmembernames class * { @butterknife.* <methods>; }
-keepclasseswithmembernames class * { @butterknife.* <fields>; }


# Keep Model Classes
-keep class com.mvp.model.** { *; }
-keep class com.mvp.client.entity.response.** { *; }
-keep class com.mvp.client.request.** { *; }