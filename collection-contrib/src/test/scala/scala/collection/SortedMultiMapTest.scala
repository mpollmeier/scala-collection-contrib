package scala.collection


import org.junit.{Assert, Test}
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(classOf[JUnit4])
class SortedMultiMapTest {

  def sortedMultiMap(smm: SortedMultiDict[Int, Int]): Unit = {
    Assert.assertEquals(Set(1), smm.get(1))
    Assert.assertEquals(Set(0, 1), smm.get(2))
    Assert.assertEquals(1, smm.firstKey)
    Assert.assertEquals(3, smm.lastKey)
    Assert.assertEquals(SortedMultiDict(3 -> 2, 2 -> 1, 2 -> 0), smm.rangeFrom(2))
  }

  @Test def run(): Unit = {
    sortedMultiMap(immutable.SortedMultiDict(2 -> 0, 1 -> 1, 3 -> 2, 2 -> 1))
    sortedMultiMap(mutable.SortedMultiDict(2 -> 0, 1 -> 1, 3 -> 2, 2 -> 1))
  }

}
