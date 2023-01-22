package com.lotterysystem.app.ticketsystem

import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class LotteryTicketRulesTest {
    @Before
    fun setUp() {
    }

    @After
    fun tearDown() {
    }

    @Test
    fun whenAllItemSame() {
        var allItemSame = LotteryTicketRules.getScoreFromLine(LotteryTicketRules.allItemSame)
        assertEquals(allItemSame, 5)
    }

    @Test
    fun whenEmptyLine() {
        var emptyLine = LotteryTicketRules.getScoreFromLine(LotteryTicketRules.emptyLine)
        assertEquals(emptyLine, 0)
    }


    @Test
    fun whenLineSumTwo() {
        var lineSumTwo = LotteryTicketRules.getScoreFromLine(LotteryTicketRules.lineSumTwo)
        assertEquals(lineSumTwo, 10)
    }


    @Test
    fun whenFirstDiffThanOther() {
        var firstDiffThanOther = LotteryTicketRules.getScoreFromLine(LotteryTicketRules.firstDiffThanOther)
        assertEquals(firstDiffThanOther, 1)
    }

}