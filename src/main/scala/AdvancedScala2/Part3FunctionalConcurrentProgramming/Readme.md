Functional Concurrency
---

### JVM Threads
Inside `Thread()` there's a *runnable* `println(..)`
```scala
val myThread = new Thread(() => println("I'm running in parallel"))
myThread.start()
```

### Executors and thread pools
```scala
import java.util.concurrent.ExecutorService
val pool = Executors.newFixedThreadPool(10)
```

### Concurrency problems
Race conditions can happen in concurrency situations. 
- In cases where multiple threads are accessing the same value at the same time, there can be a
    race condition that can be fixed using `synchronized` or with `@volatile`
```scala
class BankAccount(var amount: Int) {
  override def toString(): String = "$" + amount
}

def buySafe(account: BankAccount, thing: String, price: Int) = {
  account.synchronized {
    // no two threads can evaluate this at the same time
    account.amount = account.amount - price
    println(s"I bought $thing!!!")
    println(s"Now I currently have $account")
  }
}

@volatile var account: Int = 50000
```

### Sequencing problems: *The sleep fallacy*
```scala
  var message = ""
  val awesomeThread = new Thread(() => {
    Thread.sleep(1000)
    message = "Scala is awesome"
  })
  message = "Scala sucks"
  awesomeThread.start()
  Thread.sleep(2000)
  awesomeThread.join() //wait for the awesome thread to join
  println(message)

```

Synchronized
---
Entering a synchronized expression on an object *locks the object*:
```scala
val someObject = "hello"
someObject.synchronized { // <---- Locks the object's monitor
  // code                    <---- Any other thread trying to run this will block
}                         // <---- Release the lock
```
- when `.synchronized` is called on an object, it locks the object's `monitor`
- A `monitor` is a data structure internally used by the JVM to keep track of which object is locked by which thread
- Once an object is locked any other thread that is trying to evaluate the same expression will block until you are done evaluating. And
  when you are done evaluating, you will release the lock, and any other thread is free to evaluate the expression if it reaches that point
- Only `AnyRefs` can have synchronized blocks. Primitive types like `int` or `boolean` do not have synchronized expressions

General Principles
- make no assumptions about who gets the lock first
- keep locking to a minimum
- maintain thread safety at ALL times in parallel applications

*Wait()* and *Notify()*
---

#### Waiting
wait()-ing on an object's monitor suspends the thread indefinitely
```scala
//thread 1
val someObject = "hello"
someObject.synchronized {  // <--- lock's the object's monitor
  // ... code part 1
  someObject.wait()        // <--- release the lock and.. wait
  // ... code part 2       // <--- when allowed to proceed, lock the monitor again and continue
}
```
Here, if one of the thread reaches a synchronized expression first, then this will lock the object's monitor
and the thread is free to execute some code. But, when it calls wait it will release the lock on the monitor and suspend
at this point, and after it's allowed to proceed it will take the lock on the monitor and continue evaluating code.

In summary, the `wait()` method is used to suspend a thread within a synchronized block, releasing the lock on the object's monitor 
and allowing other threads to acquire the lock and potentially notify the waiting thread to continue execution.

#### Notify
```scala
//thread 2
someObject.synchronized { // <--- lock the object's monitor
  //... code
  someObject.notify() // <--- signal ONE sleeping thread they may continue
  //... code
}  // <--- but only after I'm done and unlock the monitor
```
This call signals one of the threads that are waiting on someObject's monitor that it may continue. 
However, the notified thread will only proceed after the current thread releases the lock on someObject's monitor.

From another thread, if we call another synchronized expression which will lock the object's monitor then
this thread is free to execute some code. And when it calls notify, it will give the signal to one of the 
sleeping threads that are waiting on this object's monitor that they may continue after acquire the lock on 
the monitor again.

In summary, the `notify()` method is used to signal one waiting thread within a synchronized block, allowing it to continue execution once the lock on the object's monitor is released. 
Multiple threads may be waiting on the same object's monitor, but notify() will only signal one of them to proceed.

Multiple threads may be waiting on this object's monitor, so calling `notify()` will signal only one of them that they may continue.
We won't know which one.

`wait()` and `notify()` are only allowed in synchronous expressions.
