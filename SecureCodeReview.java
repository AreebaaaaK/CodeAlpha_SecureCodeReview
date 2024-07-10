import java.io.*;
import java.util.*;
import java.util.regex.*;
public class SecureCodeReview{
    public static void main(String[] args){
        String filePath = "sample.c";
        try {
            List<String> cppcheckIssues = runCppcheck(filePath);
            List<String> flawfinderIssues = runFlawfinder(filePath);
            List<String> manualIssues = manualCodeReview(filePath);
            generateReport(filePath, cppcheckIssues, flawfinderIssues, manualIssues);
            System.out.println("Security review report generated: " + filePath + "_security_review_report.txt");
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
    private static List<String> runCppcheck(String filePath) throws IOException, InterruptedException {
        ProcessBuilder builder = new ProcessBuilder("cppcheck", "--enable=all", filePath);
        builder.redirectErrorStream(true);
        Process process = builder.start();
        return captureOutput(process);
    }
    private static List<String> runFlawfinder(String filePath) throws IOException, InterruptedException {
        ProcessBuilder builder = new ProcessBuilder("flawfinder", filePath);
        builder.redirectErrorStream(true);
        Process process = builder.start();
        return captureOutput(process);
    }
    private static List<String> captureOutput(Process process) throws IOException, InterruptedException {
        List<String> output = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        String line;
        while ((line = reader.readLine()) != null) {
            output.add(line);
        }
        process.waitFor();
        return output;
    }
    private static List<String> manualCodeReview(String filePath) throws IOException {
        List<String> issues = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new FileReader(filePath));
        String line;
        int lineNumber = 0;
        while ((line = reader.readLine()) != null) {
            lineNumber++;
            if (line.contains("strcpy") || line.contains("strcat")) {
                issues.add("Line " + lineNumber + ": Use of " + line.trim() + " - Consider using safer alternatives like strncpy or strncat.");
            }
            if (line.contains("malloc") && !line.contains("free")) {
                issues.add("Line " + lineNumber + ": Memory allocated with malloc is not freed - Ensure proper memory management.");
            }
            if (line.contains("gets")) {
                issues.add("Line " + lineNumber + ": Use of gets() is dangerous - Use fgets() instead to avoid buffer overflows.");
            }
        }
        reader.close();
        return issues;
    }
    private static void generateReport(String filePath, List<String> cppcheckIssues, List<String> flawfinderIssues, List<String> manualIssues) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(filePath + "_security_review_report.txt"));
        writer.write("Security Review Report for " + filePath + "\n\n");
        writer.write("Cppcheck Issues:\n");
        for (String issue : cppcheckIssues) {
            writer.write(issue + "\n");
        }
        writer.write("\n");
        writer.write("Flawfinder Issues:\n");
        for (String issue : flawfinderIssues) {
            writer.write(issue + "\n");
        }
        writer.write("\n");
        writer.write("Manual Code Review Issues:\n");
        for (String issue : manualIssues) {
            writer.write(issue + "\n");
        }
        writer.write("\n");
        writer.close();
    }
}
