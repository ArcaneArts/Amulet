# Amulet
A very simple collections &amp; general util api

# But what does it do?
Amulet can do a ton to help save you time.

## Sugar
Most of Amulet adds additional "keyword-like" functionality to java, basically sugar. Always make sure to `import static art.arcane.amulet.MagicalSugar.*;` whenever you need some sugar. It's easier in ITJ if you setup a live-template for Java > Declaration

## Lists, Sets, Maps
There's a fair bit of new methods for lists sets & maps you can use on any implementation, here's the gist
```java
List<Intege> list = List.of(3, 4, 5);
list.toString(","); // "3,4,5"
(-list).toString(",") // "5,4,3"
list.pop() // Removes off 5 & returns it"
list.popLast() // Removes off 3 & returns it
list += List.of(7, 8, 9); // List is now 3, 7, 8, 9
list[2] = 46; // Array accessors

Map<String, Integer> map = Map.of();
m.qput("a", 1).qput("b", 2);

Map<Integer, String> flp = -map;
// you get it
```

## Math
Math, for the lazy man
```java
4 max 6; // Math.max
6 min 2; // Math.min

5sq; // 25
25sqrt; // 5
(5sq + 5sq)sqrt; // Math.sqrt(5sq + 5sq);
```

## Vectors
Vectors are useful
```java
Vec a = Vec.of(0); // 0,0,0
a += 1; // 1,1,1
a *= 3; // 3,3,3
a += Vec.of(1, 2, 3); // 4, 5, 6
a[0] = 2; // Array accessor for x,y, & z

Vec b = Vec.of(55,3,5);

Vec unitAngle = a direction b; // Yes this works
Vec prod = a dot b; // dot product
Vec crs = a cross b; // cross product
Vec rtx = a rotateX 90 deg; // rotate by 90 degrees on x axis
Vec rt a rotate b angle 45 deg; // rotate a around b (as plane) by 45 degrees
```

## Ranges
Gotta love ranges
```java
for(int i : 0 to 19) {} // inclusive 0 to 19
for(int i : 5 to 25 step 3) {}
int val = 34 clip 5 to 10; // restrict input (34) to a range of 5-10, so its 10

List<?> someList = ...;
for(int i : index someList)
{
    Object o = someList[i];
}

// Reverse
for(int i : reverse index someList)
{
    Object o = someList[i];
}
```

## Time
Everything works in milliseconds at the end of the day
```java
long duration;

duration = 15 seconds;

while(duration > 1 second)
{
    duration -= 0.25 seconds;
}

duration += 5 years + 3 months + 25 days + 1 hour + 25 minutes + 1 second
```

## Strings
```java
" hello   world   ".normalize(); // "hello world"
"hello world".capitalizeWords(); // "Hello World"

("hello world" split " ").forEach(...); // returns List<String>, using regex \\Q<INPUT>\\E
"what a great day!" without "great" normalized; // returns "what a day!"
"hello"uc; // "HELLO" (use lc for lower obvi)
```

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
12. (OPTIONAL) Add a live template to ITJ in Settings > Editor > Live Templates > Java and click (click + > New Template). Specify an abbriviation such as sugar, and enter `import static art.arcane.amulet.MagicalSugar.*;` in the template text. Then simply set the applicable types to Java > Declaration only.

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
