public class ResourceRouteTraceMain {

    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("File no found!");
        } else {
            try {
                String path = args[0];
                FileProcessor processor = new FileProcessor();
                processor.processFile(path);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
