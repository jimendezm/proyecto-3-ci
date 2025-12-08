public class SemanticAnalyzer {

    /**
     * Constructor for SemanticAnalyzer class
     * Main functionality: Initializes the analyzer with nocheck state
     * @param state - If true, semantic checks are disabled
     */
    public SemanticAnalyzer(boolean state) {
        nocheck = state;
    }

    /**
     * Checks if a type is integer
     * Main functionality: Determines if type string equals "int"
     * @param type - The type to check
     * @return true if type is "int", false otherwise
     */
    public boolean isIntegerType(String type) {
        return "int".equals(type);
    }

    /**
     * Checks if a type is numeric (int or float)
     * Main functionality: Determines if type is either "int" or "float"
     * @param type - The type to check
     * @return true if type is numeric, false otherwise
     */
    public boolean isNumericType(String type) {
        return "int".equals(type) || "float".equals(type);
    }

    /**
     * Checks if a type is boolean
     * Main functionality: Determines if type string equals "bool"
     * @param type - The type to check
     * @return true if type is "bool", false otherwise
     */
    public boolean isBooleanType(String type) {
        return "bool".equals(type);
    }

    /**
     * Checks if a type is an array
     * Main functionality: Determines if type ends with "[]" suffix
     * @param type - The type to check
     * @return true if type represents an array, false otherwise
     */
    public boolean isArrayType(String type) {
        return type != null && type.endsWith("[]");
    }

    /**
     * Checks if a type is a function
     * Main functionality: Determines if type starts with "function:" or "main:"
     * @param type - The type to check
     * @return true if type represents a function, false otherwise
     */
    public boolean isFunctionType(String type) {
        return type != null && (type.startsWith("function:") || type.startsWith("main:"));
    }

    /**
     * Checks if a type is string
     * Main functionality: Determines if type string equals "string"
     * @param type - The type to check
     * @return true if type is "string", false otherwise
     */
    public boolean isStringType(String type) {
        return "string".equals(type);
    }

    /**
     * Checks if a type is void
     * Main functionality: Determines if type string equals "void"
     * @param type - The type to check
     * @return true if type is "void", false otherwise
     */
    public boolean isVoidType(String type) {
        return "void".equals(type);
    }

    public boolean nocheck = false;
    public boolean inLoop = false;
    public boolean inFunction = false;
    public String currentFunctionReturnType = null;
    public boolean hasReturnInFunction = false;

    /**
     * Verifies that a value is numeric in numeric context
     * Main functionality: Checks if type is numeric for given operation
     * @param type - The type to check
     * @param operation - The operation being performed
     */
    public void checkNumericContext(String type, String operation) {
        if (nocheck) return;
        if (!isNumericType(type)) {
            System.err.println("ERROR SEMANTICO: valor no numérico en contexto numérico para operación " + operation);
        }
    }

    /**
     * Verifies that a value is boolean in boolean context
     * Main functionality: Checks if type is boolean for given operation
     * @param type - The type to check
     * @param operation - The operation being performed
     */
    public void checkBooleanContext(String type, String operation) {
        if (nocheck) return;
        if (!isBooleanType(type)) {
            System.err.println("ERROR SEMANTICO: valor no booleano en contexto booleano para operación " + operation);
        }
    }

    /**
     * Verifies that a type is not void in a given context
     * Main functionality: Checks that void type is not used where not allowed
     * @param type - The type to check
     * @param context - The context where type is being used
     */
    public void checkNonVoidContext(String type, String context) {
        if (nocheck) return;
        if (isVoidType(type)) {
            System.err.println("ERROR SEMANTICO: tipo void no válido en contexto " + context);
        }
    }

    /**
     * Verifies that a type is not array in non-array context
     * Main functionality: Checks that array type is not used where not allowed
     * @param type - The type to check
     * @param context - The context where type is being used
     */
    public void checkNonArrayContext(String type, String context) {
        if (nocheck) return;
        if (isArrayType(type)) {
            System.err.println("ERROR SEMANTICO: array en contexto no array para " + context);
        }
    }

    /**
     * Verifies that a type is not function in non-function context
     * Main functionality: Checks that function type is not used where not allowed
     * @param type - The type to check
     * @param context - The context where type is being used
     */
    public void checkNonFunctionContext(String type, String context) {
        if (nocheck) return;
        if (isFunctionType(type)) {
            System.err.println("ERROR SEMANTICO: función en contexto no función para " + context);
        }
    }

    /**
     * Checks compatibility between target and source types in assignment
     * Main functionality: Validates type compatibility for assignment operations
     * @param targetType - The type of the target variable
     * @param sourceType - The type of the source value
     */
    public void checkAssignmentCompatibility(String targetType, String sourceType) {
        if (nocheck) return;
        if (targetType.equals("int") && sourceType.equals("float")) {
            System.err.println("ERROR SEMANTICO: pérdida de precisión en asignación numérica (float a int)");
        } else if (!targetType.equals(sourceType) && 
                  !(targetType.equals("float") && sourceType.equals("int"))) {
            System.err.println("ERROR SEMANTICO: tipos incompatibles en asignación: " + targetType + " vs " + sourceType);
        }
    }

    /**
     * Checks compatibility between two types in an operation
     * Main functionality: Validates that types are compatible for binary operations
     * @param type1 - First operand type
     * @param type2 - Second operand type
     * @param operation - The operation being performed
     */
    public void checkOperationCompatibility(String type1, String type2, String operation) {
        if (nocheck) return;
        if (!type1.equals(type2)) {
            System.err.println("ERROR SEMANTICO: tipos incompatibles en operación " + operation + ": " + type1 + " vs " + type2);
        }
    }

    /**
     * Validates for-loop control variable
     * Main functionality: Checks that loop control variable is declared and is integer type
     * @param idStr - Variable identifier
     * @param type - Variable type
     */
    public void checkForLoopControlVariable(String idStr, String type) {
        if (nocheck) return;
        if (type.equals("null")) {
            System.err.println("ERROR SEMANTICO: variable de control '" + idStr + "' no declarada en bucle for");
        } else if (!isIntegerType(type)) {
            System.err.println("ERROR SEMANTICO: variable de control '" + idStr + "' debe ser de tipo int en bucle for");
        }
    }

    /**
     * Validates function call
     * Main functionality: Checks that called function exists and is indeed a function
     * @param funcName - Name of the function being called
     * @param funcType - Type of the function (or null if not declared)
     */
    public void checkFunctionCall(String funcName, String funcType) {
        if (nocheck) return;
        if (funcType.equals("null")) {
            System.err.println("ERROR SEMANTICO: función '" + funcName + "' no declarada");
        } else if (!isFunctionType(funcType)) {
            System.err.println("ERROR SEMANTICO: '" + funcName + "' no es una función");
        }
    }

    /**
     * Validates return statement in function
     * Main functionality: Checks return type compatibility and void function constraints
     * @param expectedType - Expected return type from function signature
     * @param actualType - Actual type of returned value
     * @param hasValue - Whether return statement has a value
     */
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

    /**
     * Validates array index type
     * Main functionality: Checks that array index is integer type
     * @param indexType - Type of the index expression
     */
    public void checkArrayIndex(String indexType) {
        if (nocheck) return;
        if (!"int".equals(indexType)) {
            System.err.println("ERROR SEMANTICO: índice de array debe ser entero");
        }
    }

    /**
     * Checks for division by zero
     * Main functionality: Detects division by constant zero
     * @param divisor - The divisor value as string
     */
    public void checkDivisionByZero(String divisor) {
        if (nocheck) return;
        if ("0".equals(divisor) || "0.0".equals(divisor)) {
            System.err.println("ERROR SEMANTICO: división por cero");
        }
    }

    /**
     * Validates break statement context
     * Main functionality: Checks that break is used within a loop
     */
    public void checkBreakInLoop() {
        if (nocheck) return;
        if (!inLoop) {
            System.err.println("ERROR SEMANTICO: break fuera de estructura de loop");
        }
    }

    /**
     * Validates return statement context
     * Main functionality: Checks that return is used within a function
     */
    public void checkReturnInFunction() {
        if (nocheck) return;
        if (!inFunction) {
            System.err.println("ERROR SEMANTICO: return fuera de función");
        }
    }

    /**
     * Validates array declaration
     * Main functionality: Checks that array has a valid size specification
     * @param arrayName - Name of the array
     * @param size - Size of the array as string
     */
    public void checkArrayDeclaration(String arrayName, String size) {
        if (nocheck) return;
        if (size == null || "unknown".equals(size)) {
            System.err.println("ERROR SEMANTICO: array '" + arrayName + "' declarado sin tamaño");
        }
    }

    /**
     * Checks for missing return in non-void function
     * Main functionality: Verifies that non-void function has at least one return
     * @param currentHash - Identifier/name of the current function
     */
    public void checkMissingReturn(String currentHash) {
        if (inFunction && !"void".equals(currentFunctionReturnType) && !hasReturnInFunction) {
            System.err.println("ERROR SEMANTICO: función '" + currentHash + "' no tiene return");
        }
    }

    /**
     * Captures variable usage and checks if variable is declared
     * Main functionality: Tracks variable usage and reports undeclared variables
     * @param name - Variable name
     * @param context - Context where variable is used
     * @param type - Variable type (or "null" if undeclared)
     */
    public void captureVariableUsage(String name, String context, String type) {
        if (nocheck) return;
        if (name == null) return;
        
        if (type.equals("null")) {
            System.err.println("ERROR SEMANTICO: variable '" + name + "' no declarada en " + context);
        }
    }
}