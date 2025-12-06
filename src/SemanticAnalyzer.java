public class SemanticAnalyzer {

    public SemanticAnalyzer(boolean state) {
        nocheck = state;
    }

    // Métodos de verificación semántica simplificados
    public boolean isIntegerType(String type) {
        return "int".equals(type);
    }

    public boolean isNumericType(String type) {
        return "int".equals(type) || "float".equals(type);
    }

    public boolean isBooleanType(String type) {
        return "bool".equals(type);
    }

    public boolean isArrayType(String type) {
        return type != null && type.endsWith("[]");
    }

    public boolean isFunctionType(String type) {
        return type != null && (type.startsWith("function:") || type.startsWith("main:"));
    }

    public boolean isStringType(String type) {
        return "string".equals(type);
    }

    public boolean isVoidType(String type) {
        return "void".equals(type);
    }

    public boolean nocheck = false;

    public boolean inLoop = false;

    public boolean inFunction = false;

    public String currentFunctionReturnType = null;

    public boolean hasReturnInFunction = false;

    // Verificaciones generales de tipos
    public void checkNumericContext(String type, String operation) {
        if (nocheck) return;
        if (!isNumericType(type)) {
            System.err.println("ERROR SEMANTICO: valor no numérico en contexto numérico para operación " + operation);
        }
    }

    public void checkBooleanContext(String type, String operation) {
        if (nocheck) return;
        if (!isBooleanType(type)) {
            System.err.println("ERROR SEMANTICO: valor no booleano en contexto booleano para operación " + operation);
        }
    }

    public void checkNonVoidContext(String type, String context) {
        if (nocheck) return;
        if (isVoidType(type)) {
            System.err.println("ERROR SEMANTICO: tipo void no válido en contexto " + context);
        }
    }

    public void checkNonArrayContext(String type, String context) {
        if (nocheck) return;
        if (isArrayType(type)) {
            System.err.println("ERROR SEMANTICO: array en contexto no array para " + context);
        }
    }

    public void checkNonFunctionContext(String type, String context) {
        if (nocheck) return;
        if (isFunctionType(type)) {
            System.err.println("ERROR SEMANTICO: función en contexto no función para " + context);
        }
    }

    public void checkAssignmentCompatibility(String targetType, String sourceType) {
        if (nocheck) return;
        if (targetType.equals("int") && sourceType.equals("float")) {
            System.err.println("ERROR SEMANTICO: pérdida de precisión en asignación numérica (float a int)");
        } else if (!targetType.equals(sourceType) && 
                  !(targetType.equals("float") && sourceType.equals("int"))) {
            System.err.println("ERROR SEMANTICO: tipos incompatibles en asignación: " + targetType + " vs " + sourceType);
        }
    }

    public void checkOperationCompatibility(String type1, String type2, String operation) {
        if (nocheck) return;
        if (!type1.equals(type2)) {
            System.err.println("ERROR SEMANTICO: tipos incompatibles en operación " + operation + ": " + type1 + " vs " + type2);
        }
    }

    public void checkForLoopControlVariable(String idStr, String type) {
        if (nocheck) return;
        if (type.equals("null")) {
            System.err.println("ERROR SEMANTICO: variable de control '" + idStr + "' no declarada en bucle for");
        } else if (!isIntegerType(type)) {
            System.err.println("ERROR SEMANTICO: variable de control '" + idStr + "' debe ser de tipo int en bucle for");
        }
    }

    public void checkFunctionCall(String funcName, String funcType) {
        if (nocheck) return;
        if (funcType.equals("null")) {
            System.err.println("ERROR SEMANTICO: función '" + funcName + "' no declarada");
        } else if (!isFunctionType(funcType)) {
            System.err.println("ERROR SEMANTICO: '" + funcName + "' no es una función");
        }
    }

    public void checkReturnType(String expectedType, String actualType, boolean hasValue) {
        if (nocheck) return;
        if ("void".equals(expectedType)) {
            if (hasValue) {
                System.err.println("ERROR SEMANTICO: función void no puede retornar valor");
            }
        } else {
            if (!hasValue) {
                System.err.println("ERROR SEMANTICO: función no void debe retornar valor");
            } else {
                checkAssignmentCompatibility(expectedType, actualType);
            }
        }
    }

    public void checkArrayIndex(String indexType) {
        if (nocheck) return;
        if (!"int".equals(indexType)) {
            System.err.println("ERROR SEMANTICO: índice de array debe ser entero");
        }
    }

    public void checkDivisionByZero(String divisor) {
        if (nocheck) return;
        if ("0".equals(divisor) || "0.0".equals(divisor)) {
            System.err.println("ERROR SEMANTICO: división por cero");
        }
    }

    public void checkBreakInLoop() {
        if (nocheck) return;
        if (!inLoop) {
            System.err.println("ERROR SEMANTICO: break fuera de estructura de loop");
        }
    }

    public void checkReturnInFunction() {
        if (nocheck) return;
        if (!inFunction) {
            System.err.println("ERROR SEMANTICO: return fuera de función");
        }
    }

    public void checkArrayDeclaration(String arrayName, String size) {
        if (nocheck) return;
        if (nocheck) return;
        if (size == null || "unknown".equals(size)) {
            System.err.println("ERROR SEMANTICO: array '" + arrayName + "' declarado sin tamaño");
        }
    }

    public void checkMissingReturn(String currentHash) {
        if (inFunction && !"void".equals(currentFunctionReturnType) && !hasReturnInFunction) {
            System.err.println("ERROR SEMANTICO: función '" + currentHash + "' no tiene return");
        }
    }

    public void captureVariableUsage(String name, String context, String type) {
        if (nocheck) return;
        if (name == null) return;
        
        if (type.equals("null")) {
            System.err.println("ERROR SEMANTICO: variable '" + name + "' no declarada en " + context);
        }
    }

}
