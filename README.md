# Report

## Code Generator Facade
CodeGenerator class has a lot of public methods that makes it difficult to work with. Only two of them are used so we made a facade class that only exposes those two methods.

## Memory Self Encapsulation Field
Memory class directly accesses and modifies its own fields. We Add private methods to the class to control setting the fields and make sure they are only modified through those methods.

## Memory Seperate Query From Modifier
Memory class has methods that both modify internal attributes and return them. We seperate these functionalities to make them more clear and call both of them in all use cases.

## Address Encapsulation Field
Address class has public attributes and other classed directly acceess them which makes it dangerous. We made them private and add getter methods (setter isn't requried) And call them instead of directly accessing attributes.

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