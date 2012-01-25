package com.hubspot.techtalk;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BadGenerics {

    public static class BaseObject { }

    public static class DerivedObject extends BaseObject { }

    public static void main(String[] args) {
        BaseObject bo = new DerivedObject(); // Works for one object...
        List<BaseObject> genericBaseList = new ArrayList<DerivedObject>(); // Doesn't work for two objects?
                                                                           // Well we need to think about List is a mutable collection. (Below)
        BaseObject[] baseObjArray = new DerivedObject[5]; // Or it does, but only for arrays?
        
        List<int> theBoxer = new ArrayList<int>(); // Primitives aren't generic
    }
    
    public static class OtherDerivedObject extends BaseObject { }
    
    public static List<BaseObject> mutateList(List<BaseObject> superList) {
        superList.add(new OtherDerivedObject());
        return superList;
    }

    public static void badTypeCasting() {
        List<DerivedObject> derivedList = Arrays.asList(new DerivedObject(), new DerivedObject());
        List<BaseObject> baseList = derivedList; // If this worked..
        mutateList(baseList);
        derivedList.get(derivedList.size() - 1); // What is this type? DerivedObject or BaseObject or OtherDerivedObject?
    }

}
