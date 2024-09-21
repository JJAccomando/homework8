1. What is Stream?

In java, a Stream is a sequence of data that allows for functions to perform computations on the data in a declarative and functional manner.

2. What stream operations do you know? Why do we need them? 

Streams have various operations that can be categorized into either non-terminal operations, or terminal operations. Non-terminal operations are operations that transform streams into other streams. Some of these include, filter, map, and sorted. Terminal operations are operations that return a non-stream result such as a primitive, a collection, or even no value. Some terminal operations are forEach, count, collect, and reduce. Having all of these operations available allows programs to more easily traverse through and manipulate data especially when working with collections. The code can be more readable, more efficient, and more functional. 

3. What is reflection?

Reflection in Java is a tool that programmers can use to inspect and manipulate a program's structure and behavior at runtime. Using reflection, information about a program’s classes, including its methods, fields, and constructors can be inspected and modified dynamically. 

4. How to call the method using reflection?

When using reflection in Java, you can call a method by first obtaining the Class object that represents the class containing the method to be called. Then you can obtain the Method object using getMethod() or getDeclaredMethod() to create the object representing the method you want to call. Finally, using the invoke method, you can use this method on the method object.

5. How to create objects from reflection?

First, to create objects using reflection, you must dynamically load the Class object using Class.forName() to create a container representing the class. Second, you need to create an instance of the class by using getDeclaredConstructor() to get the default constructor of the class, and then using newInstance() to create an instance of the class with the constructor.
