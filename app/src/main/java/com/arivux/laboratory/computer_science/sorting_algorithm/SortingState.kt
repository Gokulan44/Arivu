package com.arivux.laboratory.computer_science.sorting_algorithm

data class SortingState(
    val array: MutableList<Int> = mutableListOf(24, 68, 12, 45, 9, 36),
    var i: Int = 0,
    var j: Int = 0,
    var swapped: Boolean = false,
    
    var indexComparedA: Int = -1,
    var indexComparedB: Int = -1,
    
    var comparisonsCount: Int = 0,
    var swapsCount: Int = 0,
    var isSorted: Boolean = false
) {
    fun reset(initialArray: List<Int>) {
        array.clear()
        array.addAll(initialArray)
        i = 0
        j = 0
        swapped = false
        indexComparedA = -1
        indexComparedB = -1
        comparisonsCount = 0
        swapsCount = 0
        isSorted = false
    }
}
