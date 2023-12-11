# Report

## Code Generator Facade
CodeGenerator class has a lot of public methods that makes it difficult to work with. Only two of them are used so we made a facade class that only exposes those two methods.

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

## Memory Seperate Query From Modifier
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
Address class has public attributes and other classed directly acceess them which makes it dangerous. We made them private and add getter methods (setter isn't requried) And call them instead of directly accessing attributes. Here is new Address class:
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