package AdvancedScala2.Part3FunctionalConcurrentProgramming

import scala.util.Random

object ThreadCommunication extends App {

  /** Multi threaded problem: The producer-consumer problem
    *
    * producer -> [x] -> consumer
    * producer and consumer are working in parallel
    */
  class SimpleContainer {
    private var value: Int = 0

    def isEmpty: Boolean = value == 0
    def get: Int = {
      val result = value
      value = 0
      result
    }
    def set(newValue: Int): Unit = {
      value = newValue
    }
  }

  def naiveProducerConsumer(): Unit = {
    val container = new SimpleContainer

    val consumer = new Thread(() => {
      println("[Consumer]: Start waiting...")
      while (container.isEmpty) { // busy-loop, busy waiting
        println("[Consumer]: Still waiting!!")
      }
      println(s"[Consumer]: I have consumed ${container.get}")
    })

    val producer = new Thread(() => {
      println("[Producer]: Start Computing...")
      Thread.sleep(500)
      val value = Random.nextInt(20)
      println(
        s"[Producer]: I have produced, after a long hard work, produced $value"
      )
    })
    consumer.start()
    producer.start()
  }
//  naiveProducerConsumer()

  /** Wait and Notified
    */

  /** the smartProducerConsumer method uses the wait() and notify() methods within synchronized blocks to coordinate the actions of the producer and consumer threads,
    * ensuring that the consumer waits for the producer to produce a value before consuming it.
    * In other words, it ensures that the producer and consumer threads work in a coordinated manner using the wait() and notify() methods.
    *
    * The consumer thread enters a synchronized block on the container object, ensuring exclusive access.
    * Inside the block, it calls container.wait(), which releases the lock on the container and suspends the thread until it is notified.
    *
    * The producer thread simulates some work by sleeping for 2 seconds. It then generates a random value and enters a synchronized block on the container object.
    * Inside the block, it sets the produced value in the container and calls container.notify(), which signals the waiting consumer thread to wake up.
    */
  def smartProducerConsumer(): Unit = {
    val container = new SimpleContainer
    val consumer = new Thread(() => {
      println("[Consumer]: waiting")
      // Enters a synchronized block on the container object
      container.synchronized {

        /** When the consumer thread enters this synchronized block, it locks the monitor of the container object,
          * ensuring that no other thread can enter any synchronized block on the same object until the lock is released.
          */

        /** At this point, the producer thread will release the lock on the container
          * and will suspend until someone else namely the producer will signal that
          * container that they may continue
          */
        container.wait()
      }

      /** After being notified and re-acquiring the lock on the container's monitor, the consumer thread continues execution
        * and prints a message indicating it has consumed the value from the container
        */
      println("[Consumer]: I have consumed " + container.get)
    })

    val producer = new Thread(() => {
      println("[Producer]: Hard at work...")
      Thread.sleep(2000)
      val value = Random.nextInt(314)
      container.synchronized {
        println(s"[Producer]: I am producing $value")
        container.set(value)

        /** This will signal the consumer thread that they make wake up
          * after the producer has exited the synchronized expression
          */
        container.notify()
      }
    })
    consumer.start()
    producer.start()
  }
  smartProducerConsumer()
}
