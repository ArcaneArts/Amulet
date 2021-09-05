# Amulet
A very simple collections &amp; general util api

# Project Setup
To use Amulet in your project, there is some additional setup required. Please follow the setup instructions to ensure everything will work properly.
1. In settings.gradle Add
    ```groovy
    sourceControl {
      gitRepository("https://github.com/ArcaneArts/Amulet.git") {
          producesModule("art.arcane:Amulet")
      }
    }
    ````
3. Define the dependency as `implementation art.arcane:Amulet:<TAG>` such as 1.0.2
4. Install the Manifold Plugin to Intellij (then restart)
   * Note: You do not need to buy a license, use CTRL + SHIFT + A > Manage Licenses > Mantifold > Evaluate 30 Days (once expired, it will still work but may remind you to register). Though you can just re-evaluate after that time.
5. Add this to the bottom of your build.gradle
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
5. (OPTIONAL) If you use unit testing with Amulet
    ```groovy
    configurations {
        testImplementation.extendsFrom annotationProcessor
    }
    ```
6. (OPTIONAL) If you are a dependency to another project add
    ```groovy
    jar {
        manifest {
            attributes('Contains-Sources':'java,class')
        }
    }
    ```
7. Add Manifold Dependencies to your project
    ```groovy
    repositories {
        mavenCentral()
        maven { url 'https://oss.sonatype.org/content/repositories/snapshots/' }
    }
    
    dependencies {
        implementation 'systems.manifold:manifold-ext-rt:2021.1.16'
        annotationProcessor 'systems.manifold:manifold-ext:2021.1.16'
    }
    ```
8. Resync Gradle project in Intellij (hit the refresh button)
9. On the gradle sidebar, run Tasks > build > build (:build). This will grab Amulet because Intellij does not do this by re-syncing the project.
10. (OPTIONAL) Add the [lombok plugin](https://plugins.gradle.org/plugin/io.freefair.lombok) **ONLY THE PLUGIN** Do not add the dependencies or annotation processors, such as `id "io.freefair.lombok" version "6.1.0"`.
11. (OPTIONAL) Invalidate caches / restart

## Full Gradle Example

settings.gradle
```groovy
rootProject.name = 'ExampleProject' // TODO: CHANGE TO YOUR PROJECT NAME!!!

sourceControl {
    gitRepository("https://github.com/ArcaneArts/Amulet.git") {
        producesModule("art.arcane:Amulet")
    }
}
```

build.gradle
```groovy
plugins {
    id 'java'
    id 'java-library' // Optional: For apis only
    id "io.freefair.lombok" version "6.1.0" // Optional: For lombok
}

group 'art.arcane' // TODO: CHANGE TO YOUR GROUP NAME
version '1.0.0'

// Optional: Allow manifold in unit testing
configurations {
    testImplementation.extendsFrom annotationProcessor
}

// Optional: Pass on our classes to those depending on us using manifold
jar {
    manifest {
        attributes('Contains-Sources':'java,class')
    }
}

repositories {
    mavenCentral()
    maven { url 'https://jitpack.io' }
}

dependencies {
    implementation 'art.arcane:Amulet:1.0.2'
    implementation 'systems.manifold:manifold-ext-rt:2021.1.16'
    annotationProcessor 'systems.manifold:manifold-ext:2021.1.16'
    testImplementation 'org.junit.jupiter:junit-jupiter-api:5.7.0'
    testRuntimeOnly 'org.junit.jupiter:junit-jupiter-engine:5.7.0'
}

test {
    useJUnitPlatform()
}

if (JavaVersion.current() != JavaVersion.VERSION_1_8 &&
        sourceSets.main.allJava.files.any {it.name == "module-info.java"}) {
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
