# Report

## Code Generator Facade
CodeGenerator class has a lot of public methods that makes it difficult to work with. Only two of them are used so we made a facade class that only exposes those two methods.
Here is the implementation:

```java
package codeGenerator;

import scanner.token.Token;

public class CodeGeneratorFacade {
    private CodeGenerator codeGenerator;

    public CodeGeneratorFacade() {
        codeGenerator = new CodeGenerator();
    }

    public void semanticFunction(int func, Token next) {
        codeGenerator.semanticFunction(func, next);
    }

    public void printMemory() {
        codeGenerator.printMemory();
    }
}
```

## Symbol Table Facade
SymbolTable class has various amount of methods which cause complexity while their usage in code generator class. So, we added a facade class to remove side complexity of this class. Here are the initial lines of the implemented class:

```java
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
    ...
```

## Action Handler Strategy Interface
In the parser class, initially the functionality of three actions: `reduce`, `accept`, `shift` are implemented using switch-case. But it is better to handle them using an interface called `ActionHandler`. So here is the implemented interface:
```java
package parser.actionHandler;

import parser.Action;

public interface ActionHandler {
    void execute(Action action);
}
```

# Function 

## Memory Self Encapsulation Field
Memory class directly accesses and modifies its own fields. We Add private methods to the class to control setting the fields and make sure they are only modified through those methods.

## Memory Separate Query From Modifier
Memory class has methods that both modify internal attributes and return them. We separate these functionalities to make them more clear and call both of them in all use cases.

## Address Encapsulation Field
Address class has public attributes and other classed directly acceess them which makes it dangerous. We made them private and add getter methods (setter isn't required) And call them instead of directly accessing attributes.

## Extract Method
In code generator class, most of the methods are doing similar stuff for fetching addresses from stack and also for validating fetched values based on their type. So, we did two method extraction:

```java
    private Address[] fetchAddress(varType var, Boolean returnTemp){
        Address temp = new Address(memory.getTempIndex(), var);
        memory.increaseTempAddress();
        Address s2 = ss.pop();
        Address s1 = ss.pop();
        Address[] addresses = new Address[]{s2, s1, null};
        if(returnTemp)
            addresses[2] = temp;
        return addresses;
    }

    private Boolean checkVar(Address s1, Address s2, varType var){
        if(s2 == null)
            return s1.getVarType() == var;
        if(var == null)
            return s1.getVarType() == s2.getVarType();
        return s1.getVarType() == var || s2.getVarType() == var;
    }
```

and here is an example of their usage:

```java
    public void add() {
        Address[] addresses = fetchAddress(varType.Int, true);

        if (!checkVar(addresses[0], addresses[1], varType.Int)) {
            ErrorHandler.printError("In add two operands must be integer");
        }
        memory.add3AddressCode(Operation.ADD, addresses[0], addresses[1], addresses[2]);
        ss.push(addresses[2]);
    }
```

# Questions

## 1
### A
Clean Code: Code base that is easy to read, understand and modify. We use best practices to sustain clean code.
### B
Technical Debt: Describes what results when we develop code with lack of resources (like time, developers, expertise) and we need to refactor that code later to have clean code.
### C
Bad Smell: Any characteristic in the source code of a program that possibly indicates a deeper problem.

### 3
### A
Dispensables
### B
Inline Class and Collapse Hierarchy
### C
Sometimes a Lazy Class is created in order to delineate intentions for future development.