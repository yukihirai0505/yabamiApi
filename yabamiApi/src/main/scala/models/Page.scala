package models

case class Page[T](
                    list: Seq[T],
                    pageIndex: Int,
                    pageSize: Int,
                    totalRowCount: Long
                  )