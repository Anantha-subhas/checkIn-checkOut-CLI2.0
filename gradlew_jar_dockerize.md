Got it — you want to **avoid a separate `fatJar` step** and just have your regular `./gradlew build` produce a **ready-to-run JAR** (including all dependencies), which Docker can use directly.

---

### ✅ Here's how to do that — use the **`Shadow plugin`** for an all-in-one JAR:

This plugin automatically creates a **fat JAR** when you run `./gradlew shadowJar`.

---

### 🛠 Step-by-step setup

#### 1. `build.gradle.kts` (Kotlin DSL)

```kotlin
plugins {
    kotlin("jvm") version "1.9.0"
    application
    id("com.github.johnrengelman.shadow") version "8.1.1" // ✅ Fat jar support
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib"))
}

application {
    mainClass.set("your.package.MainKt") // 🔁 Replace with your actual main class
}

// For stdin input during run
tasks.named<JavaExec>("run") {
    standardInput = System.`in`
}
```

> Replace `"your.package.MainKt"` with your fully qualified `main()` class. If your file is `Main.kt` and it’s in the root package, then just `"MainKt"`.

---

#### 2. Build the fat jar:

```sh
./gradlew shadowJar
```

You’ll get a single executable JAR in:

```
build/libs/your-project-name-all.jar
```

No need for a separate manual fatJar task — this is now your one-liner.

---

### ✅ Dockerfile for this JAR

```Dockerfile
FROM eclipse-temurin:17-jdk-jammy

WORKDIR /app

COPY build/libs/*.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]
```

---

### 🧪 Final Workflow

```sh
./gradlew shadowJar             # creates fat jar
docker build -t checkin-app .  # builds Docker image
docker run -it checkin-app     # runs the app interactively
```

---

Would you like me to generate a full example project layout or help fix your `mainClass` path? Just send me the name of your Kotlin file or the full `fun main()` block.
