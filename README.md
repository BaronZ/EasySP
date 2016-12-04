# EasySP
[![Build Status](https://travis-ci.org/BaronZ/EasySP.png?branch=master)](https://travis-ci.org/BaronZ/EasySP)[ ![Download](https://api.bintray.com/packages/baronz/maven/easysp-compiler/images/download.svg) ](https://bintray.com/baronz/maven/easysp-compiler/_latestVersion)  
AnnotationProdessor 编译时期自动生成 SharedPreference操作的代码  

 * Eliminate SharedPreferences operating code by using `@EasySP` on pojo class
 * All the code generate at compile time automaticaly
 * Support any type SharedPreferences supports, eg: `String`, `int`, `Set<String>` etc.
 
 
Example
--------

```java

@EasySP
class UserPref {
  @DefaultValue("this is default string")
  String userName;
  int age;
  @DefaultValue("123F")
  float thisIsFloat;
  @DefaultValue("123L")
  long thisIsLong;
  
  //getters and setters
}  

class ExampleActivity extends Activity {
  @Override 
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.simple_activity);
    
    UserPref pref = EasySPUserPref.create(this);//EasySPUserPref is auto generate at compile time
    //EasySPUserPref.create(this, "customFileName");
    pref.setUserName("thisIsUserName");
    //pref.getUserName() returns "thisIsUserName"
    
    //above code is equivalent to below code
    SharedPreferences sp = getSharedPreferences("UserPref", Activity.MODE_PRIVATE);
    Editor editor = sp.edit();
    editor.putString("UserName", "thisIsUserName").apply()
    sp.getString("UserName", "this is default string");
  }
}
```
Download
--------

```groovy
dependencies {
    provided "com.zzb.easysp:easysp-annotations:${latestVersion}"
    annotationProcessor "com.zzb.easysp:easysp-compiler:${latestVersion}"
}
```
