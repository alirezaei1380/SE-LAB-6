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