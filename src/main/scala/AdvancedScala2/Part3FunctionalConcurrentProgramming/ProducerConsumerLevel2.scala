package AdvancedScala2.Part3FunctionalConcurrentProgramming

object ProducerConsumerLevel2 {

  /** Multi threaded problem: The producer-consumer problem level 2
    *
    * producer -> [? ? ?] -> consumer (extracts any value that is new)
    * producer and consumer are working in parallel
    * Producer and consumer may block each other
    * If the buffer is full, that is the producer has produced enough values to fill the buffer
    * the producer must block until the consumer has finished extracting some value out of the buffer.
    */
}
