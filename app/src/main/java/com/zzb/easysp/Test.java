package com.zzb.easysp;

/**
 * Created by ZZB on 2016/11/29.
 */
@EasySP
public class Test {
    @DefaultValue("this is default str")
    String imString2;
    String imString;
    Object thisIsObject;
    @DefaultValue("123")
    int thisIsInt;
    @DefaultValue("123F")
    float thisFloat = 1;
    MainActivity thisIsActivity;
    Long thisIsBoxedLong;
    long thisIsPrimitiveLong;
    float thisIsPrimitiveFloat;
    Float thisIsBoxedFloat;

    private void test(){}

    public String getImString2() {
        return imString2;
    }

    public void setImString2(String imString2) {
        this.imString2 = imString2;
    }

    public String getImString() {
        return imString;
    }

    public void setImString(String imString) {
        this.imString = imString;
    }

    public Object getThisIsObject() {
        return thisIsObject;
    }

    public void setThisIsObject(Object thisIsObject) {
        this.thisIsObject = thisIsObject;
    }

    public int getThisIsInt() {
        return thisIsInt;
    }

    public void setThisIsInt(int thisIsInt) {
        this.thisIsInt = thisIsInt;
    }

    public MainActivity getThisIsActivity() {
        return thisIsActivity;
    }

    public void setThisIsActivity(MainActivity thisIsActivity) {
        this.thisIsActivity = thisIsActivity;
    }

    public Long getThisIsBoxedLong() {
        return thisIsBoxedLong;
    }

    public void setThisIsBoxedLong(Long thisIsBoxedLong) {
        this.thisIsBoxedLong = thisIsBoxedLong;
    }

    public long getThisIsPrimitiveLong() {
        return thisIsPrimitiveLong;
    }

    public void setThisIsPrimitiveLong(long thisIsPrimitiveLong) {
        this.thisIsPrimitiveLong = thisIsPrimitiveLong;
    }

    public float getThisIsPrimitiveFloat() {
        return thisIsPrimitiveFloat;
    }

    public void setThisIsPrimitiveFloat(float thisIsPrimitiveFloat) {
        this.thisIsPrimitiveFloat = thisIsPrimitiveFloat;
    }

    public Float getThisIsBoxedFloat() {
        return thisIsBoxedFloat;
    }

    public void setThisIsBoxedFloat(Float thisIsBoxedFloat) {
        this.thisIsBoxedFloat = thisIsBoxedFloat;
    }
}
