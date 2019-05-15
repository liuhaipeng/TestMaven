PS:上传自己的library 到 jcenter,maven

### 1、Maven是什么？
让项目可重复使用、易维护、更容易理解,是一个项目管理工具，主要用于存放一些类库、插件等方便共享。
### 2.Maven仓库在哪里？ 
Maven 就类似git，gi作用重在区别版本之间的差异，而Maven作用重在共享依赖库。
依赖包可以放在本地或者在maven、jcenter
### 3.目的
我们最终需要发布的library是core
最终目的方式： implementation 'com.example.core:core:1.0.0'
### 4.本地仓库
```
在core/build.gradle中添加
1.
//发布到本地仓库
//apply plugin: 'maven'
2.
//发布到本地仓库
uploadArchives {
    repositories.mavenDeployer {
       repository(url: uri('../repository')) // 配置本地仓库路径，项目根目录下的repository目录中
        pom.groupId = "com.example.core"// 唯一标识（通常为模块包名，也可以任意）
       pom.artifactId = "core" // 项目名称（通常为类库模块名称，也可以任意）
       pom.version = "1.0.0" // 版本号
   }
}

//打包main目录下代码和资源的 task
task androidSourcesJar(type: Jar) {
    classifier = 'sources'
    from android.sourceSets.main.java.srcDirs
}
//配置需要上传到maven仓库的文件
artifacts {
    archives androidSourcesJar
}

3.Sync Now 之后，找到Gradle projects窗口中的core/Tasks/upload/uploadArchives，双击执行，成功之后会在会提示build successful

在TestMaven/build.gradle中添加
4.
allprojects {
    repositories { 
       //本地Maven仓库地址
       maven {
           url 'file://F:\\Android\\as_project\\SmartRefreshLayout-release\\TestMaven\\repository'
       } 
    }
}
在app/build.gradle中添加
5.
 implementation 'com.example.core:core:1.0.0'
6.Sync Now之后就可以使用core中的类库了
```

### 5 maven
```
1.注册  https://bintray.com/signup/oss  亲测使用foxmial邮箱可以注册
2.在该网站依次创建（组织，在组织下创建maven 仓库）
在TestMaven/build.gradle中添加
3.
buildscript {
    dependencies {
        //jcenter上传到jcenter的工具类
        classpath 'com.novoda:bintray-release:0.9.1'
    }
}
4.
//发布到jcenter
apply plugin: 'com.novoda.bintray-release'
publish {
    userOrg = 'orgid1' //bintray账户下某个组织id
    groupId = 'com.example.core' //maven仓库下库的包名，一般为模块包名
    artifactId = 'core' //项目名称
    publishVersion = '1.0.0' //版本号
    desc = 'util library' //项目介绍，可以不写
    website = 'https://github.com/liuhaipeng/TestMaven' //亲测不写无法成功
}
//打包main目录下代码和资源的 task
task androidSourcesJar(type: Jar) {
    classifier = 'sources'
    from android.sourceSets.main.java.srcDirs
}
//配置需要上传到maven仓库的文件
artifacts {
    archives androidSourcesJar
}
5.在终端执行
//执行上传任务到bintray上，成功之后会在网站看到上传上去的项目，就可以使用了，替换自己的PbintrayUser，PbintrayKey（获取方式自行查看）
//gradlew clean build bintrayUpload -PbintrayUser=lhp -PbintrayKey=219e22864958870e718116f7d78cd0216c3be23a -PdryRun=false
成功后会提示build successful ，这时候就能在远程bintray仓库中 【 组织/maven/ 】下看到core了 

在TestMaven/build.gradle中添加
6.使用 
allprojects {
    repositories { 
        //https://bintray.com仓库地址,自己的
        maven { url 'https://dl.bintray.com/orgid1/maven' }
    }
}
在app/build.gradle中添加   implementation 'com.example.core:core:1.0.0'
7.Sync Now之后就可以使用core中的类库了


```

### 6.jcenter
```
在上述bintray仓库中找到core,找到 add to jcenter ，提交之后等待审核，成功之后会收到邮件

在TestMaven/build.gradle中添加
allprojects {
    repositories { 
       jcenter()
    }
}
在app/build.gradle中添加   implementation 'com.example.core:core:1.0.0'
Sync Now之后就可以使用core中的类库了
```


