package semantic.symbol;

import codeGenerator.Address;
import codeGenerator.Memory;

public class SymbolTableFacade {

    private Memory mem;
    private SymbolTable symbolTable;

    public SymbolTableFacade(Memory mem){
        this.mem = mem;
        this.symbolTable = new SymbolTable(mem);
    }

    public void addMethod(String className, String methodName, int address) {
        symbolTable.addMethod(className, methodName, address);
    }

    public void addClass(String className) {
        symbolTable.addClass(className);
    }

    public Symbol get(String fieldName, String className) {
        return symbolTable.get(fieldName, className);
    }

    public Symbol get(String className, String methodName, String variable) {
        return symbolTable.get(className, methodName, variable);
    }

    public Address get(String keywordName) {
        return symbolTable.get(keywordName);
    }

    public void startCall(String className, String methodName){
        symbolTable.startCall(className, methodName);
    }

    public Symbol getNextParam(String className, String methodName){
        return symbolTable.getNextParam(className, methodName);
    }

    public SymbolType getMethodReturnType(String className, String methodName){
        return symbolTable.getMethodReturnType(className, methodName);
    }

    public int getMethodReturnAddress(String className, String methodName){
        return symbolTable.getMethodReturnAddress(className, methodName);
    }

    public int getMethodCallerAddress(String className, String methodName){
        return symbolTable.getMethodCallerAddress(className, methodName);
    }

    public int getMethodAddress(String className, String methodName){
        return symbolTable.getMethodAddress(className, methodName);
    }

    public void setSuperClass(String className, String methodName){
        symbolTable.setSuperClass(className, methodName);
    }

    public void addField(String className, String methodName){
        symbolTable.addField(className, methodName);
    }

    public void addMethodLocalVariable(String className, String methodName, String localVariableName){
        symbolTable.addMethodLocalVariable(className, methodName, localVariableName);
    }

    public void addMethodParameter(String className, String methodName, String parameterName){
        symbolTable.addMethodParameter(className, methodName, parameterName);
    }

    public void setLastType(SymbolType type){
        symbolTable.setLastType(type);
    }
}
