### project-build.gradle 设置
```
buildscript {
    apply from: 'snail/rely.gradle'
    addRepos(repositories)
    dependencies {
        classpath deeps.gradle.gradle
        classpath deeps.gradle.kotlin
    }
    repositories {
        google()
    }
}

allprojects {
    addRepos(repositories)
}

```
