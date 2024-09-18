1. What is lambda?

In Java, a Lambda function allows you to write more readable code, treating a function as a method argument, as a way to represent an instance of a functional interface. Instead of needing to use an object and then a method implementation for that object, we can bypass the need of creating the object and instead just pass in the implementation.

2. How to create lambda in Java?

To create a lambda expression in Java, first you need to use a functional interface, either one you create or one of Java's built-in functional interfaces. Then instead of having a class that overrides the method with its own implementation, you can basically create a variable as an instance of the functional interface, and then use a lambda expression to represent the implementation. This is basically like having an object of a class that implements the functional interface, but without having to write the class.

3. What lambda functions do you know? Where are they located? 

Java offers many built-in functional interfaces, most of which are located in the java.util.function library. Predicate<T> is a functional interface that uses generic type T and returns a boolean. Function<T, R> is a useful functional interface that accepts an argument of one type, and will return another type for the result. Runnable is another interface that can be used to execute a task without taking in any parameters which is located in the java.lang library.

4. What is enum?

In Java, an enum is a data type that is used to represent a group of constants and provide a way to define and use these constants in a type-safe way. Enums can also have methods, constructors, and instance variables. 

5. What enum types do you know?

Java has several built-in enum types. DayOfWeek is an enum for representing days of the week. Following the theme of DayOfWeek, Java also offers a Month enum for representing the different months. There is also an enum for various currencies called Currency, and also an enum called ChronoUnit for representing units of time, such as days, hours, and minutes. All of these enums also have several methods and instance variables that make them powerful tools in Java. 
