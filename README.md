# Amulet
A very simple collections &amp; general util api

[![Release](https://jitpack.io/v/ArcaneArts/Amulet.svg)](https://jitpack.io/#ArcaneArts/Amulet)

```groovy
maven { url 'https://jitpack.io' }
```

```groovy
implementation 'com.github.ArcaneArts:Amulet:JITPACK-VERSION'

// or you can use the latest cached release 
// ~~~~(may be a version or two behind sometimes)
implementation 'com.github.ArcaneArts:Amulet:latest.release'
```

# Project Setup
To use Amulet in your project, there is some additional setup required. Please follow the setup instructions to ensure everything will work properly.
1. Make sure jitpack is a repo & you have added the dependency to your build.gradle
2. Install the Mantifold Plugin to Intellij (then restart)
   * Note: You do not need to buy a license, use CTRL + SHIFT + A > Manage Licenses > Mantifold > Evaluate 30 Days (once expired, it will still work but may remind you to register). Though you can just re-evaluate after that time.
3. Add this to the bottom of your build.gradle
    ```groovy
    if (JavaVersion.current() != JavaVersion.VERSION_1_8 &&
        sourceSets.main.allJava.files.any {
        it.name == "module-info.java"}) {
        tasks.withType(JavaCompile) {
            // if you DO define a module-info.java file:
            options.compilerArgs += ['-Xplugin:Manifold', '--module-path', it.classpath.asPath]
        }
    } else {
        tasks.withType(JavaCompile) {
            // If you DO NOT define a module-info.java file:
            options.compilerArgs += ['-Xplugin:Manifold']
        }
    }
    ```
4. (OPTIONAL) If you use unit testing with Amulet
    ```groovy
    configurations {
        testImplementation.extendsFrom annotationProcessor
    }
    ```
5. (OPTIONAL) If you are a dependency to another project add
    ```groovy
    jar {
        manifest {
            attributes('Contains-Sources':'java,class')
        }
    }
    ```
