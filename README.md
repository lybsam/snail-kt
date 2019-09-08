# project-build.gradle 下添加
buildscript {.  
   apply from: 'snail/rely.gradle'//1. 
    addRepos(repositories)//2.  
    dependencies {. 
        classpath deeps.gradle.gradle//3.  
        classpath deeps.gradle.kotlin//4. 
    }. 
}. 

allprojects {.  
    addRepos(repositories). 
    
}
