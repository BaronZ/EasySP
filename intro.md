
Android开发中，我们经常存储一些数据到文件里，这时候就需要写SharedPreferences代码。
但是写SharedPreferences代码会有很多重复的工作，类似下面这样的代码
# 重复代码

```java
class GlobalSp{
    private static final String SP_FILE_NAME = "SP_FILE_NAME";
    private static final String KEY_USER_NAME = "USER_NAME";
    private static final String KEY_PHONE_NUMBER = "PHONE_NUMBER"
    private static final String FRIEND_COUNT = "FRIEND_COUNT"
    
    public static void setUserName(Context context, String userName){
        setString(context, KEY_USER_NAME, userName);
    }
    public static String getUserName(Context context){
        return getString(context, KEY_USER_NAME, "default_name");
    }
    public static void setPhoneNumber(Context context, String phoneNumber){
        setString(context, KEY_PHONE_NUMBER, userName);
    }
    public static String getPhoneNumber(Context context){
        return getString(context, KEY_PHONE_NUMBER, "default_phone_number");
    }
    public static void setFriendCount(Context context, int friendCount){
        setInt(context, FRIEND_COUNT, friendCount);
    }
    public static int getFriendCount(Context context){
        return getInt(context, FRIEND_COUNT, 0);
    }
    //更多其它字段设置，重复getString, setString...
    
    public static void setString(Context context, String key, String value){
        SharedPreferences sharedPreferences = context.getSharedPreferences(SP_FILE_NAME, Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.apply();
    }
    public static String getString(Context context, String key, String defaultValue){
        SharedPreferences sharedPreferences = context.getSharedPreferences(SP_FILE_NAME, Activity.MODE_PRIVATE);
        return sharedPreferences.getString(key, defaultValue);
    }
    public static void setInt(Context context, String key, int value){
        SharedPreferences sharedPreferences = context.getSharedPreferences(SP_FILE_NAME, Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(key, value);
        editor.apply();
    }
    public static String getInt(Context context, String key, int defaultValue){
        SharedPreferences sharedPreferences = context.getSharedPreferences(SP_FILE_NAME, Activity.MODE_PRIVATE);
        return sharedPreferences.getInt(key, defaultValue);
    }
    //其他类型操作getFloat, getLong, getBoolean...
} 
//使用
class ExampleActivity extends Activity{
    void onCreate(Bundle bundle){
        GlobalSp.setUserName("this is user name");
        GlobalSp.getUserName();
    }
}
```
每次需要存储一个字段，就需要重复写一些类似SharedPreferences操作代码。这些重复的模板代码完全可以用Annotation Processor来自动生成，我们只需要一个注解就可以完成这些操作。
# EasySP，不用再写SharedPreferences重复代码
只需写一个pojo类，给类添加一个注解@EasySP，在编译期间，Annotation Processor会自动生成代码，就可以免去写上面这些重复的SharedPreferences操作代码。  
具体代码如下
```java
@EasySP//对一个类添加注解
class GlobalSp{//类名默认是sp的文件名
    @DefaultValue("default name")
    String userName;//字段名就是sp操作时候的key
    String phoneNumber;//字段类型就是sp存储的类型
    int friendCount;
    float anyFloat;
    //每次要存储多一个内容，仅仅添加一个字段即可
}

//使用
class ExampleActivity extends Activity{
    void onCreate(Bundle bundle){
        //Annotation Processor在编译时自动生成EasySPGlobalSp
        EasySPGlobalSp globalSp = EasySPGlobalSp.create(context, "customFileName");//创建一个叫customFileName的sp文件
        globalSp.setUserName("this is user name");//相当于sp.putString("UserName", "this is user name")
        globalSp.getUserName();//相当于sp.getString("UserName", "")
    }
}
```
# 引用@EasySP
对于Annotation Processor的项目，在android gradle plugin 2.2之前，需要引用[apt插件](http://www.littlerobots.nl/blog/Whats-next-for-android-apt/)，在2.2之后，已经原生支持Annotation Processor，不再需要引用apt插件。旧的apt插件仍可使用，但是本文不做介绍。android gradle plugin 2.2之后引用方法如下
```groovy
dependencies{
    provided "com.zzb.easysp:easysp-annotations:${version}"//目前最新版本是0.4
    annotationProcessor "com.zzb.easysp:easysp-compiler:${version}"//目前最新版本是0.4
}
```
# 关于EasySP
- 每个pojo类上加注解`@EasySP`, EasySP库就会在编译时自动生成一个`EasySP前缀+原来的类名`的类，假设pojo类叫`GlobalSp`，则自动生成sp操作类`EasySPGlobalSp`对应一个SharedPreferences文件
- 支持任何SharedPreferences支持的存储类型，比如`String`, `int`, `Set<String>`等
- 假如我们想用SharedPreferences存储一个字符类型的内容，只需要对pojo类添加一个字段即可，之后的get、set操作可以通过下面代码获得  

```java
EasySPXXX xxxSp = EasySPXXX.create(context);
xxxSp.setYYY("this is the value for YYY");//相当于sp.putString("UserName", "this is user name")
xxxSp.getYYY();//相当于sp.getString("UserName", "")
```
- 默认一个pojo类对应一个SharedPreferences文件，可以通过`EaxySPXXX.create(context, "customFilename")`去创建特定名称的SharedPreferences文件，比如这个方法适用于多用户登录，保存不同的用户信息  

```java
//假如我们有一个pojo类XXX
class XXX{
    String stringField;//这个字段名就是sp get set时候的key
}
//编译时会自动生成
class EasySPXXX extends XXX{
    //这个方法会默认以类名做为文件名创建sp文件
    public static EasySPXXX create(Context context){
        this(context, "");
    }
    //这个方法会以自定义名称创建一个sp文件
    public static EasySPXXX create(Context context, String fileName){
        //一些sp操作代码，创建文件为fileName的sp文件
        //SharedPreferences sharedPreferences = context.getSharedPreferences(fileName, Activity.MODE_PRIVATE);
    } 
    String getStringField(){
        //key是pojo类的字段名
        //一些sp操作代码去获取string
    };
    void setStringField(String value){
        //key是pojo类的字段名
        //一些sp操作代码去存储string
    };
}
```

# Annotation Processor
上面的内容多次提到Annotation Processor(后面简称AP)这个库的关键就是AP。  
Android开发中写得最多的重复代码应该就是findViewById了。而Jake Wharton就写了一个很实用的库，使我们免去重复写findViewById, 这个库就是用AP自动生成findViewById等这些代码的。  
下面我来讲讲什么是AP，以及怎么用AP去自动生成这些代码
#### 1. 什么是Annotation Processor 
AP会在编译时扫描源文件中的注解，并处理注解。我们可以自定义注解，然后在编译时处理这些注解，根据注解去生成java文件，自动生成的代码与我们手写的代码一样。这可以使我们不需要再去手动写那些重复的模板代码。AP的代码我们只需要写一次，后续就可以用注解去生成需要的模板代码
#### 2. Annotation Processor怎么工作
如果我们要写自己的注解处理器
###### 2.1继承`AbstractProcessor`
```java
class CustomProcessor extends AbstractProcessor {
    @Override
    public boolean process(Set<? extends TypeElement> annoations, RoundEnvironment env) {}
    @Override
    public Set<String> getSupportedAnnotationTypes() {}
}
```
- `public boolean process(Set<? extends TypeElement> annoations, RoundEnvironment env)`这个方法获得所有这个处理器支持的注解(支持的注解类型可以通过`getSupportedAnnotationTypes()`获得)，我们可以根据这些注解获得相应的类、成员变量或者方法等，可以解析这些注解并生成相应的java文件
- `public Set<String> getSupportedAnnotationTypes()`这个方法我们需要返回所有这个注解处理器想要处理的注解类型  

###### 2.2注册Processor
在项目要目录下，创建META-INF/services目录，目录里新建一个文件，并命名为javax.annotation.processing.Processor，文件里面的内容填写我们自定义类的完整类名，eg: com.example.CustomProcessor。注意，由于这个库的代码不打包进我们的apk，我们可以引入一个第三方库来自动帮我们注册processor，这个库就是google的auto-service(注，这个库里包含了guava，假如我们需要打包进apk，是不可接受的)，我们只需要在CustomProcessor这个类上面添加一个注解@AutoService，就可以自动帮我们注册Processor了  

###### 2.3自定义注解
以本库的注解为例
```java
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.SOURCE)
public @interface EasySP {
}
```
- `@Target(ElementType.TYPE)`声明这个注解在哪里可以使用，`ElementType.TYPE`表示这个注解可以声明在Class, interface或者enum
- `@Retention(RetentionPolicy.SOURCE)`表示这个注解只存在于源码中，编译生成的class文件没有这个注解  

#### 3. 如何解析注解  

###### 3.1获得注解的类
```java
@Override
public boolean process(Set<? extends TypeElement> annoations, RoundEnvironment env) {
    for (Element classElement : env.getElementsAnnotatedWith(EasySP.class)) {
        //这里可以拿到所有注解为EasySP的Element,我们可以根据Element拿到类名，成员变量的类型及名称等，再对这些数据进行处理
        parseElement(classElement);
    }
}
```
###### 3.2根据注解的Element去获得相应的内容
```java
private void parseElement(Element element){
    //拿到类中的字段
    List<VariableElement> fields = ElementFilter.fieldsIn(element.getEnclosedElements());
    //类名
    String clazzName = element.getSimpleName().toString();
    //根据这些内容去生成java文件
}
```
#### 4. 生成java文件
生成java文件我们可以手动去拼写字符串并生成java文件，但是太过于繁琐且容易出错，所以这里我们引入square公司的[javapoet](https://github.com/square/javapoet)。javapoet通过builder模式，可以很方便地生成java文件。  
下面展示了如何根据字段去组合一个方法
```java
private MethodSpec getter(VariableElement field, DefaultValue defaultValue) {
        String fieldName = field.getSimpleName().toString();
        TypeMirror typeMirror = field.asType();
        Type type = Utils.getType(typeMirror);
        MethodSpec method = MethodSpec.methodBuilder(Utils.getGetterMethodName(fieldName))//方法名
                .addModifiers(Modifier.PUBLIC)//方法的访问属性
                .addStatement(Utils.getSpGetterStatement(typeMirror, fieldName, defaultValue))//方法体内容
                .returns(type)//方法返回类型
                .build();
        return method;
    }
```
解析内容并创建一个java文件
```java
List<VariableElement> fields = ElementFilter.fieldsIn(element.getEnclosedElements());
        String superClazz = element.getSimpleName().toString();
        String className = Const.LIBRARY_PREFIX + superClazz;
        TypeSpec.Builder clazzBuilder = TypeSpec.classBuilder(className)//要创建的java类名
                .superclass(ClassName.get(Utils.getPackageName(element),//要继承的类 Utils.getClassName(element)))
                .addModifiers(Modifier.PUBLIC, Modifier.FINAL)//类的访问属性
                .addField(TypeNameEx.STRING, "customFileName")//类的成员变量
                .addField(TypeNameEx.CONTEXT, "context")
                .addField(TypeNameEx.SP_HELPER, "spHelper")
                .addMethod(getter());//添加一个方法

        TypeSpec clazz = clazzBuilder.build();
        JavaMaker.brewJava(clazz, processingEnv);//生成java文件
}
```
更多实现细节，请参考源码，[源码开源在github](https://github.com/BaronZ/EasySP)


# 结论
- Annotation Processor可以生成模板代码，减少手写重复代码  
- Annotation Processor在编译时生成代码，与手写代码一样，不会在运行时对App有额外性能损耗
- Annotation Processor库不会打包进apk，所以不用担心引入Annotation Processor的库会导致APK方法数增加，包变大等问题
- 开发中重复性较多的代码都可以考虑用Annotation Processor去自动生成代码，比如android比较知名的第三方库Butterknife, Dagger2, EventBus3, AutoValue都用到了Annotation Processor


最后感谢蔡剑文在开发过程中的建议还有对本文的review。
# TODO
- [ ] pojo类生成的getter setter可以在添加一个字段的时候，省去等待编译的时间就可以直接使用相应的方法，但是可能会让使用者有误解，需要在pojo类有getter setter报错, 报错的时候说明可以设置`Config.failOnGetterAndSetter = false;`使之不报错   
- [ ] 对于不支持的类型，需要在编译报错并给提示
