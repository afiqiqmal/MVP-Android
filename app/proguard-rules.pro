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
-renamesourcefileattribute SourceFile
-repackageclasses ''
-flattenpackagehierarchy
-dontskipnonpubliclibraryclasses
-forceprocessing

#########--------Android Support--------#########
-keep class android.support.v4.app.** { *; }
-keep interface android.support.v4.app.** { *; }
-dontwarn android.support.**

#########--------Remove Log--------#########
-assumenosideeffects class android.util.Log { *; }

#########--------Retrofit + RxJava--------#########
-dontwarn okio.**
-dontwarn java.lang.invoke.*
-dontwarn retrofit2.Platform$Java8
-dontwarn javax.annotation.**

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


##### RXJAVA #####

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
-keepclassmembers class rx.internal.util.unsafe.BaseLinkedQueueProducerNodeRef {
    rx.internal.util.atomic.LinkedQueueNode producerNode;
}
-keepclassmembers class rx.internal.util.unsafe.BaseLinkedQueueConsumerNodeRef {
    rx.internal.util.atomic.LinkedQueueNode consumerNode;
}

-keep public class * implements com.bumptech.glide.module.GlideModule
-keep public class * extends com.bumptech.glide.module.AppGlideModule
-keep public enum com.bumptech.glide.load.resource.bitmap.ImageHeaderParser$** {
  **[] $VALUES;
  public *;
}


#-keep,allowobfuscation @interface com.facebook.crypto.proguard.annotations.DoNotStrip
#-keep,allowobfuscation @interface com.facebook.crypto.proguard.annotations.KeepGettersAndSetters
#
## Do not strip any method/class that is annotated with @DoNotStrip
#-keep @com.facebook.crypto.proguard.annotations.DoNotStrip class *
#-keepclassmembers class * {
#    @com.facebook.crypto.proguard.annotations.DoNotStrip *;
#}
#
#-keepclassmembers @com.facebook.crypto.proguard.annotations.KeepGettersAndSetters class * {
#  void set*(***);
#  *** get*();
#}


-keep class android.support.v8.renderscript.** { *; }

# Keep Model Classes
#-keep class com.bepunct.mobile.model.** { *; }
#-keep class com.bepunct.mobile.factory.entity.response.** { *; }
#-keep class com.bepunct.mobile.factory.entity.request.** { *; }
