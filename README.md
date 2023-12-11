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
Memory class directly accesses and modifies its own fields. We Add private methods to the class to control setting the fields and make sure they are only modified through those methods. Here are Memory new methods:

```java
    private void increaseDataAddress() {
        lastDataAddress += dataSize;
    }
    private void increaseTempAddress() {
        lastTempIndex += tempSize;
    }

    private void addCodeBlock() {
        codeBlock.add(new _3AddressCode());
    }

    public int getTemp() {
        increaseTempAddress();
        return lastTempIndex - tempSize;
    }
    public int getDateAddress() {
        increaseDataAddress();
        return lastDataAddress - dataSize;
    }

    public int saveMemory() {
        addCodeBlock();
        return codeBlock.size() - 1;
    }
```

## Memory Separate Query From Modifier
Memory class has methods that both modify internal attributes and return them. We seperate these functionalities to make them more clear and call both of them in all use cases. Here are Memory modified methods:

```java
    public int getTempIndex() {
        return lastTempIndex;
    }
    public int getDateAddress() {
        return lastDataAddress;
    }
    public int getMemorySize() {
        return codeBlock.size();
    }
```

Memory use case examples:
```java
    Address temp = new Address(memory.getTempIndex(), t);
    memory.increaseTempAddress();

    ss.push(new Address(memory.getMemorySize(), varType.Address));
    memory.addCodeBlock();

    klasses.get(className).Fields.put(fieldName, new Symbol(lastType, mem.getDateAddress()));
    mem.increaseDataAddress();
```
## Address Encapsulation Field
Address class has public attributes and other classed directly access them which makes it dangerous. We made them private and add getter methods (setter isn't required) And call them instead of directly accessing attributes. Here is new Address class:
```java
public class Address {
    private int num;
    private TypeAddress Type;
    private varType varType;

    public int getNum() {
        return num;
    }

    public varType getVarType() {
        return varType;
    }
```
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

## 2

### Bloaters

These smells usually occur when we have some classes or data that are very hard to work with because of different issues like long functions, god classes(enormous class), ... . Usually these types of issues are being generated when we don't pay attention to periodic refactoring on codes or not handling changes in efficient way, this approach ends in logic accumulation and complexity. So later we will not be able to use the implemented codes in efficient and simple way. Also we need to pay attention on release and reuse granulation, which means we need to consider what we are implementing and how we are going to use later.  

### Object-Orientation Abusers

These smells usually can be sniffed from the code when we are not meeting OOP rules properly. Like temporary fields, imagine we are using some temp fields inside the code which are only accepting values under some specific circumstances. So when we try to use them out of their scope we may face null values. Or on the other hand we might use switch-cases as states using patterns in an improper way. Or we might have two separate classes which are doing some identical stuff but they are separate in their definition.

### Change Preventers

These kinds of smells generate when we need to change many parts of the code after changing a specific part. Like shotgun surgery, it happens when we change a single part of a code, we are persuaded to change several places in different classes to keep the code consistent. Or we have Divergent Change which means when we have to make a single change in one class, we are persuaded to do some extra stuff like defining several methods inside that class.

### Dispensables

Sometimes we follow some practices which end in unclean codes and we program in a way that we have some useless codes which can be refactored or maybe removed totally. Like putting comments, which shouldn't explain `How` code is implemented and code should be a self explainer. Or we might have some duplications that are causing the code to be hard to read. Or sometimes we have some classes which can be merged into another class because they follow the same logic but making them separate classes created a lazy and useless class.

### Couplers

When we realise there are some classes in our code that are places between two other classes and they are used a lot without any effect on the streamed data, we should suspect there is an coupler smell exists in our code. One of the most famous types of this issue is Message Chaining. In this issue we have several classes which act like a chain on a sequential logic. But the class in the middle passes the whole object of the first class to the third class instead of returning what first class needs. So, it is not needed and just causes complexity in the code.
## 3
### A
Dispensables
### B
Inline Class and Collapse Hierarchy
### C
Sometimes a Lazy Class is created in order to delineate intentions for future development.