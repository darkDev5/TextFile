# TextFile
Work with text files easily for writing, reading, appending, search and other features.


## Usage
Create instance of the class and start using it.
First argument is the file path.

```java
TextFile tf = new TextFile("build.xml");

System.out.println(tf.read());
System.out.println("-------------------------");
System.out.println(tf.append(Arrays.asList("Mehdi", "Ahmad", "Ali")));
```
