package AdvancedScalaRockTheJVM.lectures.part3FunctionalProgramming.Collections.exercises

/** What would happen if I had two original entries "Jim" -> 555 and "Jim" -> 9000
  * and ran   println(newPhoneBook.map(pair => pair._1.toLowerCase -> pair))
  *
  * Answer: Since Map can't have duplicate keys, you only get Jim -> 9000 cause when
  * creating phoneBook the second "jim" overrides the existing value for key "jim"
  * Map(jim -> (Jim,9000), sam -> (Sam,341235), june -> (June,914724))
  */

/** Design overly simplified social network based on maps
  * Person = String
  * Network will keep association (map) between each name and list of friends List[String]
  * - add a person to network
  * - remove a person(friend)
  * - friend (mutual)
  * - unfriend
  * Stats
  * - number of friends of given person
  * - person with most friends
  * - how many people have no friends
  * - if there is a social connection between two people (direct or not)
  * e.g if I know you, and you know someone else then there's a social connection between me and you
  */

object MapsExercise extends App {
  val phoneBook =
    Map(("Jim", 555), "Sam" -> 341235, "Jim" -> 9000).withDefaultValue(
      -1
    )
  val newPairing = "June" -> 914724
  val newPhoneBook = phoneBook + newPairing
  println(newPhoneBook.map(pair => pair._1.toLowerCase -> pair))

  // add a person to the network
  def addPerson(
      network: Map[String, Set[String]],
      person: String
  ): Map[String, Set[String]] = {
    network + (person -> Set())
  }

  def unfriend(
      network: Map[String, Set[String]],
      person: String,
      byeFriend: String
  ): Map[String, Set[String]] = {
    val listA = network(person)
    val listB = network(byeFriend)

    network + (person -> (listA - byeFriend)) +
      (byeFriend -> (listB - person)) //plus creates a new pairing, for exising key it'll replace old pairing with new pairing
  }

  def remove(
      network: Map[String, Set[String]],
      person: String
  ): Map[String, Set[String]] = {
    //tail recursive method to remove person from all list, using unfriend as an auxiliary function
    def removeAux(
        list: Set[String],
        networkAcc: Map[String, Set[String]]
    ): Map[String, Set[String]] = {
      if (list.isEmpty) networkAcc
      else removeAux(list.tail, unfriend(networkAcc, person, list.head))
    }
    val unfriended = removeAux(network(person), network)
    unfriended - person
  }

  def addFriend(
      network: Map[String, Set[String]],
      personA: String,
      personB: String
  ): Map[String, Set[String]] = {
    val friendsA = network(personA)
    val friendsB = network(personB)
    network + (personA -> (friendsA + personB)) + (personB -> (friendsB + personA))

    // for sets, set + value returns new set with value
  }

  def makeEachOtherFriends(
      network: Map[String, Set[String]]
  ): Map[String, Set[String]] = {
    val people = network.keySet
    def makeFriendAux(
        networkAcc: Map[String, Set[String]],
        remainingPeople: Set[String]
    ): Map[String, Set[String]] = {
      if (remainingPeople.isEmpty) networkAcc
      else {
        val person = remainingPeople.head
        val friends = people - person
        makeFriendAux(networkAcc + (person -> friends), remainingPeople.tail)
      }
    }
    makeFriendAux(network, people)
  }

  def isMutual(
      network: Map[String, Set[String]],
      person: String,
      potentialMutual: String
  ): Boolean = {
    val thisFriendsList = network.get(person)
    val potentialMutualList = network.get(potentialMutual)
    thisFriendsList.contains(potentialMutualList)
  }

  def anotherIsMutual(
      network: Map[String, Set[String]],
      person: String,
      potentialMutual: String
  ): Boolean = {
    def bfs(
        target: String,
        consideredPeople: Set[String],
        discoveredPeople: Set[String]
    ): Boolean = {
      if (discoveredPeople.isEmpty) false
      else {
        val person = discoveredPeople.head
        if (person == target) true
        else {
          val newConsideredPeople = consideredPeople + person
          val newDiscoveredPeople =
            discoveredPeople.tail ++ network(person).diff(newConsideredPeople)
          bfs(target, newConsideredPeople, newDiscoveredPeople)
        }
      }
    }
    bfs(potentialMutual, Set(), network(person) + person)
  }

  def numFriend(
      network: Map[String, Set[String]],
      person: String
  ): Int = {
    network(person).size
  }

  def mostFriends(
      network: Map[String, Set[String]]
  ): String = {
    var most = ""
    var mostCount = 0
    network.foreach(x =>
      if (x._2.size > mostCount) {
        mostCount = x._2.size
        most = x._1
      }
    )
    most
  }

  def mostPopular(
      network: Map[String, Set[String]]
  ): String = network.maxBy(pair => pair._2.size)._1

  def noFriends(
      network: Map[String, Set[String]]
  ): Set[String] = {
    network
      .filter(
        _._2.isEmpty
      )
      .keys
      .toSet
  }

  def loser(network: Map[String, Set[String]]): Set[String] = {
    network.view.filterKeys(key => network(key).isEmpty).keySet.toSet
  }

  def losersSize(
      network: Map[String, Set[String]]
  ): Int = {
    network.view.filterKeys(k => network(k).isEmpty).size
  }

  // using count to satisfy a predicate and return number of items that satisfy
  def loserAlt(
      network: Map[String, Set[String]]
  ): Int = {
    network.count(pair => pair._2.isEmpty)
    // network.count(_._2.isEmpty)
  }

  val empty: Map[String, Set[String]] = Map()
  var network = addPerson(
    addPerson(
      addPerson(
        addPerson(
          empty,
          "Wasif"
        ),
        "Cassidy"
      ),
      "Ron"
    ),
    "Tina"
  )

  println("new network: => " + network)
  network = addFriend(network, "Wasif", "Cassidy")
  println("Wasif and Cassidy are friends:  => " + network)
  network = addFriend(addPerson(network, "Sornali"), "Cassidy", "Sornali")
  println("Sornali is new to network, Cassidy adds her:  => " + network)
  println("Who has no friends?? LOL:  => " + noFriends(network))
  println("Biggest Loser? LOL:  => " + loser(network))
  println("How many losers? hmmm?:  => " + losersSize(network))

  println("Dang Mr/Mrs Popular:  => " + mostFriends(network))
  println("Are they mutual??:  => " + isMutual(network, "Wasif", "Sornali"))
  println(
    "How many friends does she/he have? :" + numFriend(network, "Cassidy")
  )

  network = makeEachOtherFriends(network)
  println("they are friends now :) => " + network)

  network = addFriend(addPerson(network, "Priyanka"), "Wasif", "Priyanka")
  println("unfriend him now!  => " + unfriend(network, "Wasif", "Ron"))

  network = remove(network, "Ron")
  println("yeah remove this idiot Ron:  => " + network)

  println(
    "make friends then remove that person:  => " + remove(
      addFriend(network, "Wasif", "Tina"),
      "Tina"
    )
  )

  // Wasif, Sornali, Yasmina
  val people =
    addPerson(
      addPerson(
        addPerson(
          addPerson(
            empty,
            "Wasif"
          ),
          "Sornali"
        ),
        "Yasmina"
      ),
      "Lena"
    )
  val wasmina = addFriend(people, "Wasif", "Yasmina")
  val sorasmina = addFriend(wasmina, "Wasif", "Sornali")
  println(sorasmina)
  println(numFriend(sorasmina, "Lena"))
  println(numFriend(sorasmina, "Wasif"))
  println("Who has the most friends? :" + mostFriends(network))
  println("Who has the most friends? :" + mostPopular(network))
}
