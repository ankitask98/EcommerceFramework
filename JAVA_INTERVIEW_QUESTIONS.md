# Java Coding Questions for Automation Testing Interviews

## 1. Core Java Concepts

### 1.1 OOP Basics
**Q: Explain the four pillars of OOP and their relevance in test automation.**
- Encapsulation: Hiding implementation details (Page Object Model)
- Inheritance: Reusing code across page classes
- Polymorphism: Different element interactions with common interface
- Abstraction: Abstracting complex locator strategies

**Q: What is the difference between abstract classes and interfaces?**
```java
// Abstract Class - Can have state and implementation
abstract class BaseTest {
    private WebDriver driver;
    abstract void setup();
    void login() { } // Can have implementation
}

// Interface - All methods are abstract (Java 8+: can have default methods)
interface Element {
    void click();
    void sendKeys(String text);
}
```

### 1.2 String Handling
**Q: Write a method to validate email format.**
```java
public boolean isValidEmail(String email) {
    return email.matches("^[A-Za-z0-9+_.-]+@(.+)$");
}
```

**Q: How would you reverse a string without using built-in reverse methods?**
```java
public String reverseString(String str) {
    String reversed = "";
    for (int i = str.length() - 1; i >= 0; i--) {
        reversed += str.charAt(i);
    }
    return reversed;
}
```

**Q: Find the first non-repeated character in a string.**
```java
public Character firstNonRepeatedChar(String str) {
    Map<Character, Integer> charCount = new LinkedHashMap<>();
    for (char c : str.toCharArray()) {
        charCount.put(c, charCount.getOrDefault(c, 0) + 1);
    }
    return charCount.entrySet().stream()
        .filter(e -> e.getValue() == 1)
        .map(Map.Entry::getKey)
        .findFirst()
        .orElse(null);
}
```

---

## 2. Collections & Data Structures

**Q: What are the differences between List, Set, and Map?**
| Interface | Order | Duplicates | Use Case |
|-----------|-------|-----------|----------|
| List | Maintained | Allowed | Test steps, Assertions list |
| Set | No | Not allowed | Unique test data |
| Map | No | Keys unique | Test configuration |

**Q: Remove duplicates from a list while maintaining order.**
```java
public List<String> removeDuplicates(List<String> list) {
    return list.stream()
        .distinct()
        .collect(Collectors.toList());
    
    // Alternative using LinkedHashSet
    // return new ArrayList<>(new LinkedHashSet<>(list));
}
```

**Q: Sort a list of custom objects.**
```java
class TestCase implements Comparable<TestCase> {
    String name;
    int priority;
    
    @Override
    public int compareTo(TestCase other) {
        return Integer.compare(this.priority, other.priority);
    }
}

// Usage
List<TestCase> tests = new ArrayList<>();
Collections.sort(tests);

// Or using Comparator
tests.sort(Comparator.comparingInt(tc -> tc.priority));
```

**Q: What's the difference between HashMap and ConcurrentHashMap?**
- HashMap: Not thread-safe, faster
- ConcurrentHashMap: Thread-safe using bucket-level locking, suitable for parallel test execution

---

## 3. Exception Handling

**Q: Write a method to handle WebDriver exceptions gracefully.**
```java
public WebElement findElement(By locator, int timeoutSeconds) {
    try {
        WebDriverWait wait = new WebDriverWait(driver, 
            Duration.ofSeconds(timeoutSeconds));
        return wait.until(ExpectedConditions.presenceOfElementLocated(locator));
    } catch (TimeoutException e) {
        System.out.println("Element not found: " + locator);
        throw new AssertionError("Element timeout: " + locator, e);
    } catch (NoSuchElementException e) {
        throw new AssertionError("Element does not exist: " + locator, e);
    }
}
```

**Q: Explain try-catch-finally and try-with-resources.**
```java
// Traditional
try {
    FileReader reader = new FileReader("data.txt");
} catch (FileNotFoundException e) {
    e.printStackTrace();
} finally {
    // Cleanup code
}

// Try-with-resources (Java 7+) - Auto-closes resources
try (FileReader reader = new FileReader("data.txt")) {
    // Use reader
} catch (FileNotFoundException e) {
    e.printStackTrace();
}
```

---

## 4. File & I/O Operations

**Q: Read data from an Excel file.**
```java
public List<String> readExcelColumn(String filePath, int sheetIndex, int columnIndex) {
    List<String> data = new ArrayList<>();
    try (FileInputStream fis = new FileInputStream(filePath);
         Workbook workbook = new XSSFWorkbook(fis)) {
        Sheet sheet = workbook.getSheetAt(sheetIndex);
        for (Row row : sheet) {
            Cell cell = row.getCell(columnIndex);
            if (cell != null) {
                data.add(cell.getStringCellValue());
            }
        }
    } catch (IOException e) {
        e.printStackTrace();
    }
    return data;
}
```

**Q: Read properties file for test configuration.**
```java
public String getProperty(String key) {
    Properties props = new Properties();
    try (FileInputStream fis = new FileInputStream("config.properties")) {
        props.load(fis);
        return props.getProperty(key);
    } catch (IOException e) {
        throw new RuntimeException("Failed to load config.properties", e);
    }
}
```

**Q: Write test results to a CSV file.**
```java
public void writeResultsToCSV(List<TestResult> results, String filePath) {
    try (FileWriter writer = new FileWriter(filePath);
         CSVPrinter printer = new CSVPrinter(writer, CSVFormat.DEFAULT
             .withHeader("Test Name", "Status", "Duration"))) {
        
        for (TestResult result : results) {
            printer.printRecord(result.getName(), result.getStatus(), result.getDuration());
        }
    } catch (IOException e) {
        e.printStackTrace();
    }
}
```

---

## 5. Concurrency & Threading

**Q: What are the differences between Thread and Runnable?**
```java
// Thread - Extends Thread class (single inheritance)
class TestThread extends Thread {
    @Override
    public void run() {
        // Test code
    }
}

// Runnable - Implements interface (multiple inheritance)
class TestRunnable implements Runnable {
    @Override
    public void run() {
        // Test code
    }
}

// Usage - Runnable is preferred
Thread t = new Thread(new TestRunnable());
t.start();
```

**Q: Implement thread pool for parallel test execution.**
```java
public void executeTestsInParallel(List<TestCase> tests) {
    ExecutorService executor = Executors.newFixedThreadPool(4);
    
    for (TestCase test : tests) {
        executor.submit(() -> {
            try {
                test.execute();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
    
    executor.shutdown();
    executor.awaitTermination(5, TimeUnit.MINUTES);
}
```

**Q: What is synchronization and when would you use it?**
```java
// Synchronized method - Thread-safe
synchronized void updateTestCount() {
    count++;
}

// Synchronized block - More granular control
void updateMetrics() {
    synchronized (this) {
        totalTests++;
    }
    // Other code can run in parallel
}
```

---

## 6. Functional Programming & Lambda

**Q: Filter and map a list of test results.**
```java
// Get all passed tests with names only
List<String> passedTests = results.stream()
    .filter(r -> r.getStatus().equals("PASSED"))
    .map(TestResult::getName)
    .collect(Collectors.toList());
```

**Q: Group test results by status.**
```java
Map<String, List<TestResult>> byStatus = results.stream()
    .collect(Collectors.groupingBy(TestResult::getStatus));

// byStatus: {"PASSED": [...], "FAILED": [...]}
```

**Q: Find the longest test execution time.**
```java
long longestDuration = results.stream()
    .mapToLong(TestResult::getDuration)
    .max()
    .orElse(0);
```

---

## 7. Design Patterns in Automation

### Singleton Pattern
```java
public class WebDriverFactory {
    private static WebDriver driver;
    
    private WebDriverFactory() {} // Private constructor
    
    public static synchronized WebDriver getDriver() {
        if (driver == null) {
            driver = new ChromeDriver();
        }
        return driver;
    }
    
    public static void quitDriver() {
        if (driver != null) {
            driver.quit();
            driver = null;
        }
    }
}
```

### Factory Pattern
```java
public class BrowserFactory {
    public static WebDriver createBrowser(String browserName) {
        return switch (browserName.toLowerCase()) {
            case "chrome" -> new ChromeDriver();
            case "firefox" -> new FirefoxDriver();
            case "safari" -> new SafariDriver();
            default -> throw new IllegalArgumentException("Unknown browser: " + browserName);
        };
    }
}
```

### Page Object Model
```java
public class LoginPage {
    private WebDriver driver;
    private By usernameField = By.id("username");
    private By passwordField = By.id("password");
    private By loginButton = By.id("loginBtn");
    
    public LoginPage(WebDriver driver) {
        this.driver = driver;
    }
    
    public void enterUsername(String username) {
        driver.findElement(usernameField).sendKeys(username);
    }
    
    public void enterPassword(String password) {
        driver.findElement(passwordField).sendKeys(password);
    }
    
    public void clickLogin() {
        driver.findElement(loginButton).click();
    }
}
```

---

## 8. Common Automation Testing Questions

### Q: How would you handle dynamic waits in Selenium?
```java
public WebElement waitForElement(By locator, int timeoutSeconds) {
    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutSeconds));
    return wait.until(ExpectedConditions.presenceOfElementLocated(locator));
}

public void waitForElementToBeClickable(By locator, int timeoutSeconds) {
    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutSeconds));
    wait.until(ExpectedConditions.elementToBeClickable(locator));
}
```

### Q: How would you handle element visibility across different screen sizes?
```java
public boolean isElementVisible(By locator) {
    try {
        WebElement element = driver.findElement(locator);
        return element.isDisplayed() && element.getSize().getHeight() > 0 
            && element.getSize().getWidth() > 0;
    } catch (NoSuchElementException e) {
        return false;
    }
}
```

### Q: Implement a custom retry mechanism for flaky tests.
```java
public interface RetryLogic {
    void execute() throws Exception;
}

public void retryTest(RetryLogic logic, int maxAttempts) throws Exception {
    for (int attempt = 1; attempt <= maxAttempts; attempt++) {
        try {
            logic.execute();
            System.out.println("Attempt " + attempt + " succeeded");
            return;
        } catch (Exception e) {
            if (attempt == maxAttempts) {
                throw e;
            }
            System.out.println("Attempt " + attempt + " failed, retrying...");
            Thread.sleep(2000); // Wait before retry
        }
    }
}
```

---

## 9. Algorithms & Problem Solving

**Q: Find the most frequent element in an array.**
```java
public int mostFrequentElement(int[] arr) {
    Map<Integer, Integer> frequency = new HashMap<>();
    for (int num : arr) {
        frequency.put(num, frequency.getOrDefault(num, 0) + 1);
    }
    return frequency.entrySet().stream()
        .max(Map.Entry.comparingByValue())
        .map(Map.Entry::getKey)
        .orElse(-1);
}
```

**Q: Two Sum - Find two numbers that add up to a target.**
```java
public int[] twoSum(int[] arr, int target) {
    Map<Integer, Integer> map = new HashMap<>();
    for (int i = 0; i < arr.length; i++) {
        int complement = target - arr[i];
        if (map.containsKey(complement)) {
            return new int[]{map.get(complement), i};
        }
        map.put(arr[i], i);
    }
    return new int[]{-1, -1};
}
```

**Q: Check if a string is a palindrome.**
```java
public boolean isPalindrome(String str) {
    String cleaned = str.replaceAll("[^a-zA-Z0-9]", "").toLowerCase();
    int left = 0, right = cleaned.length() - 1;
    
    while (left < right) {
        if (cleaned.charAt(left) != cleaned.charAt(right)) {
            return false;
        }
        left++;
        right--;
    }
    return true;
}
```

**Q: Fibonacci sequence (recursive and iterative).**
```java
// Recursive - Simple but inefficient for large numbers
public int fibonacciRecursive(int n) {
    if (n <= 1) return n;
    return fibonacciRecursive(n - 1) + fibonacciRecursive(n - 2);
}

// Iterative - More efficient
public int fibonacciIterative(int n) {
    if (n <= 1) return n;
    int prev = 0, curr = 1;
    for (int i = 2; i <= n; i++) {
        int next = prev + curr;
        prev = curr;
        curr = next;
    }
    return curr;
}
```

---

## 10. Performance & Best Practices

**Q: How would you optimize test execution time?**
- Use parallel execution with TestNG
- Implement efficient waits (WebDriverWait with Duration)
- Minimize unnecessary sleep() calls
- Use headless browser mode
- Implement proper test data management

**Q: Write a performance monitoring utility.**
```java
public class PerformanceUtil {
    private static Map<String, Long> startTimes = new HashMap<>();
    
    public static void startTimer(String testName) {
        startTimes.put(testName, System.currentTimeMillis());
    }
    
    public static long endTimer(String testName) {
        long startTime = startTimes.getOrDefault(testName, 0L);
        long duration = System.currentTimeMillis() - startTime;
        System.out.println(testName + " took " + duration + "ms");
        return duration;
    }
}
```

**Q: Implement logging for test execution.**
```java
public class TestLogger {
    private static final Logger logger = LoggerFactory.getLogger(TestLogger.class);
    
    public static void logTestStart(String testName) {
        logger.info("========== Starting Test: " + testName + " ==========");
    }
    
    public static void logAction(String action) {
        logger.info("Action: " + action);
    }
    
    public static void logError(String error, Exception e) {
        logger.error("Error: " + error, e);
    }
}
```

---

## Tips for Interview Success

1. **Understand the Problem**: Ask clarifying questions before coding
2. **Think Out Loud**: Explain your approach before implementing
3. **Consider Edge Cases**: Handle null values, empty collections, etc.
4. **Optimize**: Start with working solution, then optimize if needed
5. **Test Your Code**: Walk through with examples
6. **Know Your Framework**: Understand Selenium, TestNG, or your testing tool deeply
7. **Design Patterns**: Know Page Object Model, Singleton, Factory patterns
8. **Version Control**: Be comfortable with Git/GitHub
9. **Debugging**: Know how to use debuggers and log files
10. **Communication**: Clearly explain your test automation strategy

---

## Additional Resources

- **Java Streams API**: https://docs.oracle.com/javase/8/docs/api/java/util/stream/package-summary.html
- **Selenium Documentation**: https://www.selenium.dev/documentation/
- **TestNG**: https://testng.org/doc/documentation-main.html
- **Design Patterns**: https://refactoring.guru/design-patterns
- **LeetCode**: Practice coding problems related to Java

---

*Last Updated: July 2026*
