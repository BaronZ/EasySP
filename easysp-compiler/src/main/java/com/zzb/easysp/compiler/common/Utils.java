package com.zzb.easysp.compiler.common;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Set;

import javax.lang.model.element.Element;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.SimpleTypeVisitor6;

/**
 * Created by ZZB on 2016/11/29.
 */

public class Utils {

    public static boolean isSupportedFieldType(TypeMirror fieldType) {
        TypeKind typeKind = fieldType.getKind();
        String typeClassName = fieldType.toString();
        if (typeKind == TypeKind.BOOLEAN || Boolean.class.getName().equals(typeClassName)) {
            return true;
        } else if (typeKind == TypeKind.INT || Integer.class.getName().equals(typeClassName)) {
            return true;
        } else if (typeKind == TypeKind.LONG || Long.class.getName().equals(typeClassName)) {
            return true;
        } else if (typeKind == TypeKind.FLOAT || Float.class.getName().equals(typeClassName)) {
            return true;
        } else if (String.class.getName().equals(typeClassName)) {
            return true;
        } else if (isStringSet(fieldType)) {//(Set.class.getName().equals(typeClassName)) {
            return true;
        } else {
            return false;
        }
    }

    public static String getPackageName(Element element) {
        String fullName = element.asType().toString();
        return fullName.substring(0, fullName.lastIndexOf("."));
    }

    public static String getClassName(Element element) {
        return element.getSimpleName().toString();
    }

    public static Type getType(TypeMirror fieldType) {
        TypeKind typeKind = fieldType.getKind();
        String typeClassName = fieldType.toString();
        if (typeKind == TypeKind.BOOLEAN || Boolean.class.getName().equals(typeClassName)) {
            return typeKind == TypeKind.BOOLEAN ? boolean.class : Boolean.class;
        } else if (typeKind == TypeKind.INT || Integer.class.getName().equals(typeClassName)) {
            return typeKind == TypeKind.INT ? int.class : Integer.class;
        } else if (typeKind == TypeKind.LONG || Long.class.getName().equals(typeClassName)) {
            return typeKind == TypeKind.LONG ? long.class : Long.class;
        } else if (typeKind == TypeKind.FLOAT || Float.class.getName().equals(typeClassName)) {
            return typeKind == TypeKind.FLOAT ? float.class : Float.class;
        } else if (String.class.getName().equals(typeClassName)) {
            return String.class;
        } else if (isStringSet(fieldType)) {
            return Set.class;
        } else {
            return Object.class;
        }
    }

    public static String typeToString(TypeMirror fieldType) {
        TypeKind typeKind = fieldType.getKind();
        String typeClassName = fieldType.toString();
        if (typeKind == TypeKind.BOOLEAN || Boolean.class.getName().equals(typeClassName)) {
            return "Boolean";
        } else if (typeKind == TypeKind.INT || Integer.class.getName().equals(typeClassName)) {
            return "Int";
        } else if (typeKind == TypeKind.LONG || Long.class.getName().equals(typeClassName)) {
            return "Long";
        } else if (typeKind == TypeKind.FLOAT || Float.class.getName().equals(typeClassName)) {
            return "Float";
        } else if (String.class.getName().equals(typeClassName)) {
            return "String";
        } else if (isStringSet(fieldType)) {
            return "StringSet";
        } else {
            return "";
        }
    }
    public static boolean isStringSet(TypeMirror typeMirror) {
        String typeClassName = typeMirror.toString();
        if ("java.util.Set<java.lang.String>".equals(typeClassName)) {
            final StringBuilder genericTypeStr = new StringBuilder();
            typeMirror.accept(new SimpleTypeVisitor6<Void, Void>() {
                @Override
                public Void visitDeclared(DeclaredType declaredType, Void aVoid) {
                    List<? extends TypeMirror> typeArguments = declaredType.getTypeArguments();
                    if (!typeArguments.isEmpty()) {
                        TypeMirror genericType = typeArguments.get(0);
                        genericTypeStr.append(genericType.toString());
                    }
                    return null;
                }
            }, null);
            return String.class.getName().equals(genericTypeStr.toString());
        } else {
            return false;
        }
    }

}
