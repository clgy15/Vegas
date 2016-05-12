package vegas.DSL

import monocle.macros.GenLens
import vegas.spec.{ Spec, Transform, Formula }

/**
  * @author Aish Fenton.
  */
trait TransformDSL {
  self: SpecBuilder =>

  private val _transform = GenLens[Spec](_.transform)

  private val _calculate = GenLens[Transform](_.calculate)
  private val _filterNull = GenLens[Transform](_.filterNull)
  private val _filter = GenLens[Transform](_.filter)

  def addTransformCalculation(field: String, expr: String) = {
    val formula = Formula(field, expr)
    (_spec composeLens _transform composePrism _orElse(Transform()) composeLens _calculate composePrism _orElse(Nil)).modify((xs: Seq[Formula]) => xs :+ formula)(this)
  }

  def transformFilter(filter: String) = {
    (_spec composeLens _transform composePrism _orElse(Transform()) composeLens _filter).set(Some(filter))(this)
  }

  def transformFilterNull(filterNull: Boolean = true) = {
    (_spec composeLens _transform composePrism _orElse(Transform()) composeLens _filterNull).set(Some(filterNull))(this)
  }

}