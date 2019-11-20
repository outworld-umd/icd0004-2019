# Bonus Exercise #3 - Parallel Execution

Web Application under test: https://the-internet.herokuapp.com/

## Information
To change the test configuration, you should modify the file ```testng.xml```.
### Running Classes In Parallel
```xml
<suite name="InternetAppTests">
    <test name="ParallelTest" parallel="classes" thread-count="3">
        <classes>
            <class name="TheInternetAppTestsOne"/>
            <class name="TheInternetAppTestsTwo"/>
            <class name="TheInternetAppTestsThree"/>
        </classes>
    </test>
</suite>
```
### Running Methods In Parallel
```xml
<suite name="InternetAppTests">
    <test name="ParallelTest" parallel="methods" thread-count="X">
        <classes>
            <class name="TheInternetAppTestsOne"/>
            <class name="TheInternetAppTestsTwo"/>
            <class name="TheInternetAppTestsThree"/>
        </classes>
    </test>
</suite>
```
### Change Browser
Just add ```-Dbrowser [firefox|chrome|edge|opera]``` to the VM options.

Using IntelliJ IDEA: ```Run -> Edit Configurations... -> VM options: -Dbrowser [firefox|chrome|edge|opera]```

### Time/Thread Relation

![alt text](https://i.imgur.com/HdjsUTg.jpg "Time/Thread Relation")