package AdvancedScala2.Part3FunctionalConcurrentProgramming

import java.util.concurrent.Executors
import scala.Console.{in, println}

object Intro extends App {

  /** JVM threads
    * Thread: Thread is an instance of a class
    * in java lang there's a interface Runnable
    * interface Runable {
    *  public void run()
    *  }
    */
  val aThread = new Thread(new Runnable {
    override def run(): Unit = println("Running in parallel")
  })

  /** this creates a JVM thread => OS thread, opens a new thread
    */
  aThread.start() // gives the signal to the JVM to start a thread
  aThread.join() // blocks until a thread finishes running

  val threadHello = new Thread(() => (1 to 4).foreach(_ => println(s"hello")))
  val threadGoodbye = new Thread(() =>
    (1 to 4).foreach(_ => println(s"goodbye"))
  )
  threadHello.start()
  threadGoodbye.start()

  /** Different runs in different threads produce different results
    */

  /** Executors:
    */
  val pool = Executors.newFixedThreadPool(10)
  pool.execute(() => println("something in the thread pool"))
  pool.execute(() => {
    Thread.sleep(1000)
//    println("done after 1 second")
  })
  pool.execute(() => {
    Thread.sleep(1000)
//    println("Almost done")
    Thread.sleep(1000)
//    println("done after 2 seconds")
  })
  pool.shutdown()

  /** shutdownNow interrupts the sleeping threads running underneath the pool
    */
//  pool.shutdownNow()
//  println(pool.isShutdown)

  /** RejectedExecutionException because pool is shutdown
    *
    *  pool.execute(() => {
    *    println("shouldn't appear")
    *  })
    */

  def runInParallel = {
    var x = 0
    val thread1 = new Thread(() => x = 1)
    val thread2 = new Thread(() => x = 2)
    thread1.start()
    thread2.start()
//    println(x)
  }
  for (_ <- 1 to 100) runInParallel

  /** x = 0 in most cases, that means println statement executed before thread was evaluated
    */

  /** Race Condition
    * For example: Two threads trying to attempt to set the same memory zone at the same time
    */

  class BankAccount(var amount: Int) {
    override def toString(): String = "$" + amount
  }
  def buy(account: BankAccount, thing: String, price: Int) = {
    account.amount -= price
//    println(s"I bought $thing!!!")
//    println(s"Now I currently have $account")
  }

  /** This introduces a race condition
    * Two threads entered a race condition here: a user was able to buy both a shoe and iphone 12
    * and only extract $3000 form their bank account
    */
  for (_ <- 1 to 3) {
    val account = new BankAccount(50000)
    val thread1 = new Thread(() => {
      buy(account, "shoes", 3000)
    })
    val thread2 = new Thread(() => {
      buy(account, "iphone20", 2000)
    })
    thread1.start()
    thread2.start()
    if (account.amount != 47000)
//      println(s"AHA!!. Amount is ${account.toString()}")
      Thread.sleep(100)
    println
  }

  /** Why this happened: both started with the same bank account info
    * Thread(1) shoes: 50000
    *  - account = 50000 - 3000 = 47000
    * Thread(2) iphone: 50000
    *  - account = 5000 - 2000 = 48000
    */

  /** How to solve race conditions
    * Option #1: Use synchronized()
    * This is more widely used
    */
  def buySafe(account: BankAccount, thing: String, price: Int) = {
    account.synchronized {
      // no two threads can evaluate this at the same time
      account.amount = account.amount - price
      println(s"I bought $thing!!!")
      println(s"Now I currently have $account")
    }
  }

  /** Option #2 : Use @volatile
    * @volatile only protects against concurrent single operations
    * The -= operator is NOT a single operation. It consists of a read, a compute and write
    * To be safe: USE SYNCHRONIZED
    */

  /** Exercises
    * 1. Construct 50 "inception" threads
    *  Inception threads are threads that creates other threads
    *   Thread1 -> Thread2 -> Thread3 -> ..
    *    println(Hello from thread #____)
    *    in reverse order
    */
  val reverseThreads =
    (50 to 1).map(x => new Thread(() => println(s"My reverse thread# $x")))
  reverseThreads.foreach(x => x.start())

  /** This is a stack recursive call
    * The new threads are started and joined before the current thread has the chance to say hello
    * So all threads are instantiated first and then in reverse order prints out thread numbers
    * @param maxThreads
    * @param index
    * @return
    */
  def inceptionThreads(maxThreads: Int, index: Int = 1): Thread =
    new Thread(() => {
      if (index < maxThreads) {
        val newThread = inceptionThreads(maxThreads, index + 1)
        newThread.start()
        newThread.join()
      }
      println(s"Hello from thread#$index")
    })

  inceptionThreads(50).start()

  /** Exercise 2.
    * In the following code, what is the biggest value possible for x
    * The biggest possible value is 100, smallest possible is 1
    *
    * thread 1: x = 0
    * thread 2: x =0
    * ....
    * thread100: x = 0
    * for all threads: x = 1 and write it back to x
    */
  var x = 0
  val threads = (1 to 100).map(_ => new Thread(() => x += 1))
  threads.foreach(_.start())
  threads.foreach(_.join())
  println(x)

  /** Exercise 3: Sleep fallacy
    * what is the value of message below?
    * Is it guaranteed, why or why not?
    * - The value is Scala is awesome almost always, but it is not guaranteed
    * - Why not? Because the execution might go like this
    *  (main thread)
    *    message => "scala sucks"
    *    awesomeThread.start()
    *    sleep() - relieves execution
    *  (awesomeThread)
    *    sleep() - relieves execution
    *    (OS gives the CPU to some important thread, which takes the CPU more than 2 seconds)
    *     (OS gives the CPU back to the main thread, not the awesomeThread)
    *      println("Scala sucks")
    *      (OS gives the CPU to awesome threads)
    *       message = "Scala is awesome"
    *  but since main thread has already executed by now, we see "scala sucks" and not "Scala is awesome"
    */
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

  /** How do we fix this?
    * - synchronizing doesn't work here: Synchronizing is only useful for concurrent modifications
    * such as bank account, because multiple threads tried to accesss account.amount at the same time
    * - Here we have a sequential problem, so the only solution is to have threads join
    */
}
