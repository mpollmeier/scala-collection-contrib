package scala
package collection
package immutable

import scala.collection.mutable.{Builder, ImmutableBuilder}

/**
  * An immutable multidict
  * @tparam K the type of keys
  * @tparam V the type of values
  */
class MultiDict[K, V] private (elems: Map[K, Set[V]])
  extends collection.MultiDict[K, V]
    with Iterable[(K, V)]
    with collection.MultiDictOps[K, V, MultiDict, MultiDict[K, V]]
    with collection.IterableOps[(K, V), Iterable, MultiDict[K, V]] {

  def sets: Map[K, Set[V]] = elems

  override def multiDictFactory: MapFactory[MultiDict] = MultiDict
  override protected def fromSpecific(coll: IterableOnce[(K, V)]): MultiDict[K, V] = multiDictFactory.from(coll)
  override protected def newSpecificBuilder: mutable.Builder[(K, V), MultiDict[K, V]] = multiDictFactory.newBuilder[K, V]
  override def empty: MultiDict[K, V] = multiDictFactory.empty
  override def withFilter(p: ((K, V)) => Boolean): MultiDictOps.WithFilter[K, V, Iterable, MultiDict] =
    new MultiDictOps.WithFilter(this, p)

  /**
    * @return a new multidict that contains all the entries of this multidict
    *         excepted the entry defined by the given `key` and `value`
    */
  def remove(key: K, value: V): MultiDict[K, V] =
    new MultiDict(elems.updatedWith(key) {
      case Some(vs) =>
        val updatedVs = vs - value
        if (updatedVs.nonEmpty) Some(updatedVs) else None
      case None => None
    })

  /** Alias for `remove` */
  @`inline` final def - (kv: (K, V)): MultiDict[K, V] = remove(kv._1, kv._2)

  /**
    * @return a new multidict that contains all the entries of this multidict
    *         excepted those associated with the given `key`
    */
  def removeKey(key: K): MultiDict[K, V] = new MultiDict(elems - key)

  /** Alias for `removeKey` */
  @`inline` final def -* (key: K): MultiDict[K, V] = removeKey(key)

  /**
    * @return a new multidict that contains all the entries of this multidict
    *         and the entry defined by the given `key` and `value`
    */
  def add(key: K, value: V): MultiDict[K, V] =
    new MultiDict(elems.updatedWith(key) {
      case None     => Some(Set(value))
      case Some(vs) => Some(vs + value)
    })

  /** Alias for `add` */
  @`inline` final def + (kv: (K, V)): MultiDict[K, V] = add(kv._1, kv._2)

}

object MultiDict extends MapFactory[MultiDict] {

  def empty[K, V]: MultiDict[K, V] = new MultiDict[K, V](Map.empty)

  def from[K, V](source: IterableOnce[(K, V)]): MultiDict[K, V] =
    source match {
      case mm: MultiDict[K, V] => mm
      case _ => (newBuilder[K, V] ++= source).result()
    }

  def newBuilder[K, V]: Builder[(K, V), MultiDict[K, V]] =
    new ImmutableBuilder[(K, V), MultiDict[K, V]](empty[K, V]) {
      def addOne(elem: (K, V)): this.type = { elems = elems + elem; this }
    }

}