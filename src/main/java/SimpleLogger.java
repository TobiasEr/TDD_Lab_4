public class SimpleLogger implements Logger {
    @Override
    public void log(Integer number) {
        System.out.println("LOG: " + number);
    }
}
