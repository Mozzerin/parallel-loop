./gradlew clean build
java -jar build/libs/task1-1.0.jar

Flow:
1. Scan value from System.in
2. Iterate from 0 to entered value
3. Compare each iteration in separate thread(using FixedThreadPool)
4. Once Future return true for comparing index and entered value - shutdown the executor and print the result

This task also could be done using completionservice and many other solutions. Unit tests not provided
