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

#-useuniqueclassmembernames
#-renamesourcefileattribute
#-repackageclasses ''
#-allowaccessmodification
#-optimizationpasses 5
#-dontusemixedcaseclassnames
#-dontskipnonpubliclibraryclasses
#-dontpreverify
#-verbose

#########--------Android Support--------#########
-keep class android.support.v4.app.** { *; }
-keep interface android.support.v4.app.** { *; }
-dontwarn android.support.**

##########--------Webview--------#########
#-keepclassmembers class * {
#    @android.webkit.JavascriptInterface <methods>;
#}

#########--------Remove Log--------#########
-assumenosideeffects class android.util.Log { *; }
-assumenosideeffects class com.logger.min.easylogger.Logger { *; }

#########--------Retrofit + RxJava--------#########
-dontwarn okio.**
-dontwarn java.lang.invoke.*
-dontwarn retrofit2.Platform$Java8
-dontwarn javax.annotation.Nullable
-dontwarn javax.annotation.ParametersAreNonnullByDefault

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
#-keep class com.google.gson.** { *; }
#-keep class com.google.inject.** { *; }
#-keep class javax.inject.** { *; }

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

###### ICEPICK ######
#-dontwarn icepick.**
#-keep class icepick.** { *; }
#-keep class **$$Icepick { *; }
#-keepclasseswithmembernames class * {
#    @icepick.* <fields>;
#}
#-keepnames class * { @icepick.State *;}


####### BUTTER KNIFE ######
## Retain generated class which implement Unbinder.
#-keep public class * implements butterknife.Unbinder { public <init>(**, android.view.View); }
#
## Prevent obfuscation of types which use ButterKnife annotations since the simple name
## is used to reflectively look up the generated ViewBinding.
#-keep class butterknife.*
#-keepclasseswithmembernames class * { @butterknife.* <methods>; }
#-keepclasseswithmembernames class * { @butterknife.* <fields>; }



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

-keep class com.facebook.** { *; }
-keep interface com.facebook.** { *; }
-dontwarn com.facebook.**


-keeppackagenames org.jsoup.nodes


# Keep Model Classes
#-keep class my.gov.islam.smarthalal.model.** { *; }
#-keep class my.gov.islam.smarthalal.client.entity.response.** { *; }
#-keep class my.gov.islam.smarthalal.client.entity.request.** { *; }