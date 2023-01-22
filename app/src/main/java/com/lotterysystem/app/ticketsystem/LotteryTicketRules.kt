package com.lotterysystem.app.ticketsystem

object LotteryTicketRules {

    // test cases
    val emptyLine: List<Int> = mutableListOf()
    val lineSumTwo: List<Int> = mutableListOf(1, 1, 0)
    val allItemSame: List<Int> = mutableListOf(1, 1, 1)
    val firstDiffThanOther: List<Int> = mutableListOf(1, 0, 2)

    fun getScoreFromLine(numbers: List<Int>): Int {

        /*
        * if empty return 0
        * */
        if (numbers.isEmpty())
            return 0



        /**
         * if the sum of the values on a line is 2
         * ...the result for that line is €10
         */
        var sum = 0
        numbers.forEach {
            sum += it
        }

        if (sum == 2) {
            return 10
        }
        /**
         *  Otherwise if they are all the
         *  ...same, the result is €5
         */
        else if (numbers[0] == numbers[1] && numbers[1] == numbers[2]) {
            return 5
        }

        /*
        * Otherwise so long as both 2nd and
        * ...3rd numbers are different from
        * ...the 1st, the result is €1
        * */
        else if (numbers[0] != numbers[1] && numbers[0] != numbers[2]) {
            return 1
        }

        /*
        * Otherwise the result is €0
        * */
        return 0
    }

}